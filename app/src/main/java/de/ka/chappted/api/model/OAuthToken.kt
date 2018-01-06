package de.ka.chappted.api.model

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
    val clientId = "2"

    @SerializedName("client_secret")
    val clientSecret = "iPfiC5VNOKW332XqTmnDm3TyPAQyJ4frDLgmxbs2"
}


