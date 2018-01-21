package de.ka.chappted.api

import android.content.Context
import de.ka.chappted.auth.OAuthUtils
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.Request
import okhttp3.Interceptor
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A service generator for creating and reusing multiple networking clients.
 * Created by Thomas Hofmann on 21.12.17.
 */
internal object ServiceGenerator {

    /**
     * Builds a non authenticated api service client. Intended to be used for pre-authentication
     * calls such as register or non-harmful requests.
     *
     * @param baseUrl      the base url
     * @param logsEnabled  true if logs are enabled, false otherwise
     * @param serviceClass the api service
     * @param <S>          the client to build
     * @return the built client
     */
    fun <S> createNonAuthenticatedService(baseUrl: String,
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

        val client = httpClient.build()
        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }

    /**
     * Builds an authenticated api service client. Intended to be used for post-authentication
     * calls such as delicate user management.
     *
     * @param baseUrl      the base url
     * @param logsEnabled  true if logs are enabled, false otherwise
     * @param serviceClass the api service
     * @param <S>          the client to build
     * @return the built client
     */
    fun <S> createAuthenticatedService(baseUrl: String,
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
        httpClient.addInterceptor(AuthInterceptor())

        // adds a response-code 401 authenticator
        httpClient.authenticator(OAuthAuthenticator())

        val client = httpClient.build()
        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }
}

/**
 * A o auth authenticator handles 401 responses and fetches a new access token, if needed.
 * Will stop requesting new tokens if two 401s follow each other, to prevent loops.
 */
class OAuthAuthenticator : Authenticator, KoinComponent {

    private val repository: Repository by inject()
    private val context: Context by inject()

    override fun authenticate(route: Route, response: Response): Request? {
        // responding two times with a 401 should not be tolerated, so we exit
        if (response.code() == response.priorResponse()?.code()) {
            return null
        }

        // fetch a new access token on the first 401

        val accessToken = OAuthUtils.fetchNewOAuthAccessTokenBlocking(repository, context)

        val responseBuilder = response.request().newBuilder()

        // on success we can authenticate the request
        if (accessToken != null) {
            responseBuilder.header("Authorization", "Bearer " + accessToken)
        }

        return responseBuilder.build()
    }
}

/**
 * A okhttp3 interceptor for setting up o auth authorization.
 */
class AuthInterceptor : Interceptor, KoinComponent {

    private val context: Context by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        // forces a 401 if there is no oauth token
        var accessToken = "empty"
        val token = OAuthUtils.peekOAuthToken(context)?.accessToken

        if (token != null) {
            accessToken = token
        }

        val original = chain.request()

        // add all needed headers, note that the authorization can fail, in this case
        // the authenticator will deal with these issues
        val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .method(original.method(), original?.body())

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}
