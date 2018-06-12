package de.ka.chapptedapi.repository.provider

import android.content.Context
import de.ka.chapptedapi.ChapptedApi
import de.ka.chapptedapi.auth.OAuthUtils
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.Request
import okhttp3.Interceptor
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A service generator for creating and reusing networking clients.
 *
 * Created by Thomas Hofmann on 21.12.17.
 */
internal object ServiceGenerator {

    /**
     * Builds an api service client which can auto-authenticate.
     *
     * @param context the base context
     * @param baseUrl      the base url
     * @param logsEnabled  true if logs are enabled, false otherwise
     * @param serviceClass the api service
     * @param <S>          the client to build
     * @return the built client
     */
    fun <S> createService(context: Context,
                          baseUrl: String,
                          logsEnabled: Boolean,
                          serviceClass: Class<S>): S {
        val httpClient = OkHttpClient.Builder()
        val builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

        // adds logging, if wanted
        if (logsEnabled) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
        }

        // adds a interceptor for adding authentication to every request
        httpClient.addInterceptor(AuthInterceptor(context))

        // adds a response-code 401 authenticator
        httpClient.authenticator(OAuthAuthenticator(context))

        val client = httpClient.build()
        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }
}

/**
 * A o auth authenticator handles 401 responses and fetches a new access token, if needed.
 * Will stop requesting new tokens if two 401s follow each other, to prevent loops.
 *
 * If there is no auth token after a follow up, a login or register is presented, if possible.
 */
class OAuthAuthenticator(val context: Context) : Authenticator {

    override fun authenticate(route: Route, response: Response): Request? {
        // responding two times with a 401 should not be tolerated, so we exit
        if (response.code() == response.priorResponse()?.code()) {
            // if there is none available, we have to login / register first
            de.ka.chapptedapi.auth.Authenticator.requestNewOAuth(context)
            return null
        }

        // fetch a new access token on the first 401

        val accessToken = OAuthUtils.fetchNewOAuthAccessTokenBlocking(ChapptedApi.repository, context)

        val responseBuilder = response.request().newBuilder()

        // on success we can authenticate the request
        if (accessToken != null) {
            responseBuilder.header("Authorization", String.format("Bearer %s", accessToken))
        }

        return responseBuilder.build()
    }
}

/**
 * A okhttp3 interceptor for setting up o auth authorization.
 */
class AuthInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // forces a 401 if there is no oauth token
        val token = OAuthUtils.peekOAuthToken(context)?.accessToken

        val original = chain.request()

        // add all needed headers, note that the authorization can fail, in this case
        // the authenticator will deal with these issues
        val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .method(original.method(), original?.body())

        if (token != null) {
            requestBuilder.header("Authorization", String.format("Bearer %s", token))
        }

        return chain.proceed(requestBuilder.build())
    }
}
