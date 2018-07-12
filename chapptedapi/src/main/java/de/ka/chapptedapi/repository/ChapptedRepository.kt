package de.ka.chapptedapi.repository

import android.content.Context
import de.ka.chapptedapi.repository.provider.Client
import de.ka.chapptedapi.model.OAuthToken
import de.ka.chapptedapi.model.User
import de.ka.chapptedapi.BuildConfig
import de.ka.chapptedapi.jlsapi.JlsCallback
import de.ka.chapptedapi.jlsapi.JlsResponse
import de.ka.chapptedapi.repository.provider.ServiceGenerator
import retrofit2.Call
import java.util.ArrayList

/**
 * A repository.
 *
 * Created by Thomas Hofmann on 21.12.17.
 */
open class ChapptedRepository constructor(private val baseUrl: String = BuildConfig.BASE_URL,
                                          private val logsEnabled: Boolean = BuildConfig.LOGS_ENABLED) :
        Repository {

    private var client: Client? = null

    override fun pendingCalls(): ArrayList<Call<*>> = ArrayList()

    override fun getNewAccessTokenBlocking(context: Context, refreshToken: String): JlsResponse<OAuthToken>? {

        val token = OAuthToken()

        val call: Call<OAuthToken>? = getClient(context)?.getNewAccessToken(
                refreshToken,
                token.clientId,
                token.clientSecret,
                "refresh_token")

        var response: JlsResponse<OAuthToken>? = null

        call?.let {
            response = JlsResponse<OAuthToken>(it.execute())
        }

        return response
    }

    override fun getNewTokens(
            context: Context,
            username: String,
            password: String,
            jlsCallback: JlsCallback<OAuthToken>) {

        val token = OAuthToken()

        getClient(context)?.getNewTokens(
                username,
                password,
                token.clientId,
                token.clientSecret,
                "password")?.let {
            jlsCallback.setOrigin(it)
            it.enqueue(jlsCallback)
        }
    }

    override fun getUser(context: Context,
                         jlsCallback: JlsCallback<Void>) {

        getClient(context)?.getUser(2)?.let {
            jlsCallback.setOrigin(it)
            it.enqueue(jlsCallback)
        }
    }

    override fun register(
            context: Context,
            user: User,
            jlsCallback: JlsCallback<User>) {

        getClient(context)?.register(user)?.let {
            jlsCallback.setOrigin(it)
            it.enqueue(jlsCallback)
        }
    }

    override fun stop() {
        for (call: Call<*>? in pendingCalls()) {
            call?.cancel()
        }
        pendingCalls().clear()
    }

    /**
     * Retrieves the authorized client for making calls with authentication.
     *
     * @return the client
     */
    private fun getClient(context: Context): Client? {

        if (client == null) {
            client = ServiceGenerator.createService(
                    context,
                    baseUrl,
                    logsEnabled,
                    Client::class.java)
        }

        return client
    }

}
