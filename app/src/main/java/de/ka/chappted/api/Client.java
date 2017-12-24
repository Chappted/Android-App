package de.ka.chappted.api;

import de.ka.chappted.api.model.OAuthToken;
import de.ka.chappted.api.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by th on 21.12.17.
 */
public interface Client {

    @FormUrlEncoded
    @POST("v1/oauth/token")
    Call<OAuthToken> getNewAccessToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("v1/oauth/token")
    Call<OAuthToken> getNewTokens(
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType);

    @GET("v1/users/{id}")
    Call<Void> getUser(@Path("id") int id);

    @POST("v1/users")
    Call<User> register(@Body User user);
}
