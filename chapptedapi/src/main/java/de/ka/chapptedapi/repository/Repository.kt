package de.ka.chapptedapi.repository

import android.content.Context
import de.ka.chapptedapi.model.OAuthToken
import de.ka.chapptedapi.model.User
import de.ka.chapptedapi.jlsapi.JlsCallback
import de.ka.chapptedapi.jlsapi.JlsResponse
import retrofit2.Call
import java.util.ArrayList

interface Repository {

    /**
     * Retrieves the list of pending calls.
     */
    fun pendingCalls(): ArrayList<Call<*>>

    /**
     * Retrieves a new access token blocking the calling thread. **NOT** intended to be called on
     * the main thread!
     *
     * @param context the base context
     * @param refreshToken the current refresh token, needed to fetch a new access token
     * @return the response with a new oauth token with the access token, if the call was successful
     */
    fun getNewAccessTokenBlocking(context: Context, refreshToken: String): JlsResponse<OAuthToken>?

    /**
     * Starts an async call to retrieve a new oauth token containing a new access token and
     * refresh token.
     *
     * @param context the base context
     * @param username the username
     * @param password the password
     * @param jlsCallback the callback of the async call
     */
    fun getNewTokens(context: Context,
                     username: String,
                     password: String,
                     jlsCallback: JlsCallback<OAuthToken>)

    /**
     * Starts an async call to retrieve a specific user.
     *
     * @param context the base context
     * @param jlsCallback the callback of the async call
     */
    fun getUser(context: Context, jlsCallback: JlsCallback<Void>)

    /**
     * Starts an async call to register a user.
     *
     * @param context the base context
     * @param user the user to register
     * @param jlsCallback the callback of the async call
     */
    fun register(context: Context, user: User, jlsCallback: JlsCallback<User>)

    /**
     * Stops fetching / executing all pending api calls.
     */
    fun stop()

}