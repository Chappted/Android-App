package de.ka.chappted.api.repository

import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.api.model.User
import retrofit2.Callback
import retrofit2.Response

interface Repository {

    /**
     * Retrieves a new access token blocking the calling thread. **NOT** intended to be called on
     * the main thread!
     *
     * @param refreshToken the current refresh token, needed to fetch a new access token
     * @return the response with a new oauth token with the access token, if the call was successful
     */
    fun getNewAccessTokenBlocking(refreshToken: String): Response<OAuthToken>?

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
                     callback: Callback<OAuthToken>)

    /**
     * Starts an async call to retrieve a specific user.
     *
     * @param callback the callback of the async call
     */
    fun getUser(callback: Callback<Void>)

    /**
     * Starts an async call to register a user.
     *
     * @param user the user to register
     * @param callback the callback of the async call
     */
    fun register(user: User, callback: Callback<User>)

    /**
     * Stops fetching / executing all pending api calls.
     */
    fun stop()

}