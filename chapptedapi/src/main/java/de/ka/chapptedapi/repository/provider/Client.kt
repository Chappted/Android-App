package de.ka.chapptedapi.repository.provider

import de.ka.chapptedapi.model.OAuthToken
import de.ka.chapptedapi.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * A client for web services api.
 * Created by Thomas Hofmann on 21.12.17.
 */
interface Client {

    @FormUrlEncoded
    @POST("oauth/token")
    fun getNewAccessToken(
            @Field("refresh_token") refreshToken: String,
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("grant_type") grantType: String): Call<OAuthToken>

    @FormUrlEncoded
    @POST("oauth/token")
    fun getNewTokens(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("grant_type") grantType: String): Call<OAuthToken>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<Void>

    @POST("users")
    fun register(@Body user: User): Call<User>
}
