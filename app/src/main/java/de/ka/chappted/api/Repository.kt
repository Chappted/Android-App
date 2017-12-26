package de.ka.chappted.api

import android.content.Context
import de.ka.chappted.BuildConfig

/**
 * A repository.
 * Created by Thomas Hofmann on 21.12.17.
 */
class Repository private constructor() {

    private var baseUrl = BuildConfig.BASE_URL
    private var logsEnabled = BuildConfig.LOGS_ENABLED

    private var nonAuthorizedClient: Client? = null
    private var authenticatedClient: Client? = null

    private object Holder {
        val INSTANCE = Repository()
    }

    companion object {
        val instance: Repository by lazy { Holder.INSTANCE }

        /**
         * Initializes the repository. Note that this step is not mandatory, but if called
         * it will recreate all services with the new defined initial data.
         *
         * @param baseUrl a new base url to use
         * @param logsEnabled set to true to enable logs, false otherwise
         */
        fun init(baseUrl: String, logsEnabled: Boolean) {

            instance.authenticatedClient = null
            instance.nonAuthorizedClient = null

            instance.baseUrl = baseUrl
            instance.logsEnabled = logsEnabled
        }
    }

    /**
     * Retrieves the authorized client for making calls with authentication.
     *
     * @param context the base context
     * @return the client
     */
    fun getAuthenticatedClient(context: Context): Client? {

        if (authenticatedClient == null) {
            authenticatedClient= ServiceGenerator.createAuthenticatedService(
                    baseUrl, logsEnabled, Client::class.java, context)
        }

        return authenticatedClient
    }

    /**
     * Retrieves the non authorized client for making calls without authentication.
     *
     * @param context the base context
     * @return the client
     */
    fun getNonAuthenticatedClient(): Client? {

        if (nonAuthorizedClient == null) {
            nonAuthorizedClient = ServiceGenerator.createNonAuthenticatedService(
                            baseUrl, logsEnabled, Client::class.java)
        }

        return nonAuthorizedClient
    }
}
