package de.ka.chapptedapi.model

import android.support.annotation.Keep

import com.google.gson.annotations.SerializedName

/**
 * A O Auth Token for usage with O Auth 2 password grant flow.
 * Created by Thomas Hofmann on 21.12.17.
 */
@Keep
class OAuthToken {

    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("token_type")
    val tokenType: String? = null

    @SerializedName("expires_in")
    val expiresIn: Int? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null

    @SerializedName("scope")
    val scope: String? = null

    @SerializedName("client_id")
    val clientId = "d046aaeb98fd4c479ec0f0a62a8a736161b7eb88ab2a2df35d0b66049ecebd10"

    @SerializedName("client_secret")
    val clientSecret = "db695c2e695edd5080d623bb51a54060a291280acbe5ca0a98dd576c98869225"
}


