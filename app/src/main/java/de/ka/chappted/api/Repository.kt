package de.ka.chappted.api

import de.ka.chappted.BuildConfig
import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.api.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

/**
 * A repository.
 * Created by Thomas Hofmann on 21.12.17.
 */
class Repository constructor(private val baseUrl: String = BuildConfig.BASE_URL,
                             private val logsEnabled: Boolean = BuildConfig.LOGS_ENABLED) {

    private var nonAuthorizedClient: Client? = null
    private var authenticatedClient: Client? = null

    private val pendingCalls = ArrayList<Call<*>?>()

    /**
     * Retrieves a new access token blocking the calling thread. **NOT** intended to be called on
     * the main thread!
     *
     * @param refreshToken the current refresh token, needed to fetch a new access token
     * @return the response with a new oauth token with the access token, if the call was successful
     */
    fun getNewAccessTokenBlocking(refreshToken: String): Response<OAuthToken>? {

        val token = OAuthToken()

        val caller: Call<OAuthToken>?
                = getNonAuthenticatedClient()?.getNewAccessToken(
                refreshToken,
                token.clientId,
                token.clientSecret,
                "refresh_token")

        pendingCalls.add(caller)

        val response = caller?.execute()

        pendingCalls.remove(caller)

        return response
    }

    /**
     * Starts an async call to retrieve a new oauth token containing a new access token and
     * refresh token.
     *
     * @param username the username
     * @param password the password
     * @param callback the callback of the async call
     */
    fun getNewTokens(username: String,
                     password: String,
                     callback: Callback<OAuthToken>) {

        val token = OAuthToken()

        val caller: Call<OAuthToken>?
                = getNonAuthenticatedClient()?.getNewTokens(
                username,
                password,
                token.clientId,
                token.clientSecret,
                "password")

        caller?.enqueue(object : retrofit2.Callback<OAuthToken> {
            override fun onResponse(call: Call<OAuthToken>, response: Response<OAuthToken>) {
                callback.onResponse(call, response)

                pendingCalls.remove(caller)
            }

            override fun onFailure(call: Call<OAuthToken>, t: Throwable) {
                callback.onFailure(call, t)

                pendingCalls.remove(caller)
            }
        })

        pendingCalls.add(caller)
    }

    /**
     * Starts an async call to retrieve a specific user.
     *
     * @param callback the callback of the async call
     */
    fun getUser(callback: Callback<Void>) {

        val caller: Call<Void>? = getAuthenticatedClient()?.getUser(2)

        caller?.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback.onResponse(call, response)

                pendingCalls.remove(caller)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback.onFailure(call, t)

                pendingCalls.remove(caller)
            }
        })

        pendingCalls.add(caller)
    }

    /**
     * Starts an async call to register a user.
     *
     * @param user the user to register
     * @param callback the callback of the async call
     */
    fun register(user: User, callback: Callback<User>) {

        val caller: Call<User>? = getNonAuthenticatedClient()?.register(user)

        caller?.enqueue(object : retrofit2.Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                callback.onResponse(call, response)

                pendingCalls.remove(caller)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onFailure(call, t)

                pendingCalls.remove(caller)
            }
        })

        pendingCalls.add(caller)
    }

    /**
     * Stops fetching / executing all pending api calls.
     */
    fun stop() {
        for (call: Call<*>? in pendingCalls) {
            call?.cancel()
        }
        pendingCalls.clear()
    }

    /**
     * Retrieves the authorized client for making calls with authentication.
     *
     * @return the client
     */
    private fun getAuthenticatedClient(): Client? {

        if (authenticatedClient == null) {
              authenticatedClient = ServiceGenerator.createAuthenticatedService(
                       baseUrl, logsEnabled, Client::class.java)
        }

        return authenticatedClient
    }

    /**
     * Retrieves the non authorized client for making calls without authentication.
     *
     * @return the client
     */
    private fun getNonAuthenticatedClient(): Client? {

        if (nonAuthorizedClient == null) {
            nonAuthorizedClient = ServiceGenerator.createNonAuthenticatedService(
                    baseUrl, logsEnabled, Client::class.java)
        }

        return nonAuthorizedClient
    }
}
