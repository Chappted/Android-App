package de.ka.chappted.api.repository

import de.ka.chappted.BuildConfig
import de.ka.chappted.api.Client
import de.ka.chappted.api.ServiceGenerator
import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.api.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * A fallback repository. This is intended to be used for internal testing without using a working
 * repository.
 *
 * Created by Thomas Hofmann on 10.04.18.
 */
class MainFallbackRespository(val baseUrl: String = BuildConfig.BASE_URL,
                              val logsEnabled: Boolean = BuildConfig.LOGS_ENABLED)
    : Repository {

    private var client: Client? = null

    override fun getNewAccessTokenBlocking(refreshToken: String): Response<OAuthToken>? {

        Timber.d("Test Repo: Getting a new access toking, blocking ...")

        val token = OAuthToken()
        token.accessToken = "access"

        return Response.success(token)
    }

    override fun getNewTokens(username: String, password: String, callback: Callback<OAuthToken>) {

        Timber.d("Test Repo: Getting new tokens for authentication ...")

        val token = OAuthToken()
        token.accessToken = "access"
        token.refreshToken = "refresh"

        val call: Call<OAuthToken>? = getClient()?.getNewTokens(
                username,
                password,
                token.clientId,
                token.clientSecret,
                "password")

        call?.let {
            callback.onResponse(it, Response.success(token))
        }
    }

    override fun register(user: User, callback: Callback<User>) {

        Timber.d("Test Repo: registering a new user...")

        val user = User("testuser", "123")

        val call: Call<User>? = getClient()?.register(user)

        call?.let {
            callback.onResponse(it, Response.success(user))
        }

    }


    override fun getUser(callback: Callback<Void>) {

        Timber.d("Test Repo: Fetching a user ...")

        val call: Call<Void>? = getClient()?.getUser(1)

        call?.let {
            callback.onResponse(it, Response.success(null))
        }


    }

    override fun stop() {

        Timber.d("Test Repo: Stopping all requests ...")

    }


    /**
     * Retrieves the client for making calls.
     *
     * @return the client
     */
    private fun getClient(): Client? {

        if (client == null) {
            client = ServiceGenerator.createAuthenticatedService(
                    baseUrl, logsEnabled, Client::class.java)
        }

        return client
    }
}
