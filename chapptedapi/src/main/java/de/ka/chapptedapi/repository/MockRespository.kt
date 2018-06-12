package de.ka.chapptedapi.repository

import android.content.Context
import de.ka.chapptedapi.model.OAuthToken
import de.ka.chapptedapi.model.User
import de.ka.chapptedapi.jlsapi.JlsCallback
import de.ka.chapptedapi.jlsapi.JlsResponse
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.util.ArrayList

/**
 * A mock repository. This is intended to be used for internal testing without using a working
 * connection.
 *
 * Created by Thomas Hofmann on 10.04.18.
 */
class MockRespository() : Repository {

    /**
     * Will always return an empty list here, as the calls are only mocked and not really executed.
     */
    override fun pendingCalls(): ArrayList<Call<*>> = ArrayList()

    override fun getNewAccessTokenBlocking(context: Context, refreshToken: String): JlsResponse<OAuthToken>? {

        Timber.d("Test Repo: Getting a new access toking, blocking ...")

        val token = OAuthToken()
        token.accessToken = "access"

        return JlsResponse(Response.success(token))
    }

    override fun getNewTokens(context: Context, username: String, password: String, jlsCallback: JlsCallback<OAuthToken>) {

        Timber.d("Test Repo: Getting new tokens for authentication ...")

        val token = OAuthToken()
        token.accessToken = "access"
        token.refreshToken = "refresh"

        jlsCallback.onSuccess(JlsResponse(Response.success(token)))
    }

    override fun register(context: Context, user: User, jlsCallback: JlsCallback<User>) {
        Timber.d("Test Repo: registering a new user...")

        jlsCallback.onSuccess(JlsResponse(Response.success(user)))
    }

    override fun getUser(context: Context, jlsCallback: JlsCallback<Void>) {
        Timber.d("Test Repo: Fetching a user ...")

        jlsCallback.onSuccess(JlsResponse(Response.success(null as Void?)))

    }

    override fun stop() {
        Timber.d("Test Repo: Stopping all requests ...")
    }
}
