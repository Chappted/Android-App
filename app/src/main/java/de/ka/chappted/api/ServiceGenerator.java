package de.ka.chappted.api;

import android.content.Context;

import java.io.IOException;

import de.ka.chappted.api.model.OAuthToken;
import de.ka.chappted.auth.OAuthUtils;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A service generator for creating and reusing multiple networking clients.
 * Created by Thomas Hofmann on 21.12.17.
 */
class ServiceGenerator {

    /**
     * Creates a new service generator.
     */
    private ServiceGenerator() {
        // hides the implicit constructor
    }

    /**
     * Builds a non authenticated api service client. Intended to be used for pre-authentication
     * calls such as register or non-harmful requests.
     *
     * @param baseUrl      the base url
     * @param serviceClass the api service
     * @param <S>          the client to build
     * @return the built client
     */
    static <S> S createNonAuthenticatedService(String baseUrl, Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        // adds logging, if wanted
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    /**
     * Builds an authenticated api service client. Intended to be used for post-authentication
     * calls such as delicate user management.
     *
     * @param baseUrl      the base url
     * @param serviceClass the api service
     * @param context      the base context, used for authentication purposes
     * @param <S>          the client to build
     * @return the built client
     */
    static <S> S createAuthenticatedService(String baseUrl, Class<S> serviceClass, final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        // adds logging, if wanted
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        // adds a interceptor for adding authentication to every request
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                // forces a 401 if there is no oauth token
                String accessToken = "empty";

                OAuthToken token = OAuthUtils.Companion.getInstance().peek(context);

                if (token != null) {
                    accessToken = token.getAccessToken();
                }

                Request original = chain.request();

                // add all needed headers, note that the authorization can fail, in this case
                // the authenticator will deal with these issues
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-type", "application/json")
                        .header("Authorization", "Bearer ".concat(accessToken))
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        // adds a response-code 401 authenticator
        httpClient.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                // responding two times with a 401 should not be tolerated, so we exit
                if (responseCount(response) >= 2) {
                    return null;
                }

                // fetch a new access token on the first 401
                String accessToken = OAuthUtils.Companion.getInstance().fetchNewAccessTokenBlocking(context);

                Request.Builder builder = response.request().newBuilder();

                // on success we can authenticate the request
                if (accessToken != null) {
                    builder.header("Authorization", "Bearer ".concat(accessToken));
                }

                return builder.build();
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    /**
     * Evaluates the response count of the same call. Useful for checking how many times
     * the same response has been given to exit a possible request / response - loop.
     *
     * @param response the response to evaluate the count of
     * @return the count
     */
    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
