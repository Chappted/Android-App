package de.ka.chappted.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import de.ka.chappted.R

import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.api.Repository
import retrofit2.Callback

/**
 * A O auth 2 utility with 'password grant' flow.
 *
 * This utility abstracts some of the inconvenient account management
 * and authenticator flows to simple methods. Use [OAuthUtils.peekOAuthToken] within an activity
 * for fetching the O auth token for auth / triggering the login / register, if none exists.
 *
 * Offers convenience methods for fetching access tokens or / and refresh tokens.
 *
 * Created by Thomas Hofmann on 22.12.17.
 */
object OAuthUtils {

    const val EXTRA_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    const val EXTRA_USER_PASSWORD = "USER_PASSWORD"
    private const val EXTRA_REFRESH_TOKEN = "REFRESH_TOKEN"

    /**
     * Retrieves the o auth token.
     * If there is no auth token, a login or register is presented, if possible.
     *
     * @param context the base context
     * @return the token
     */
    fun peekOAuthToken(context: Context?): OAuthToken? {

        if (context == null) {
            return null
        }

        val token = Authenticator.retrieveOAuth(context)

        // if there is none available, we have to login / register first
        if (token == null) {
            Authenticator.requestNewOAuth(context)
            return null
        }

        return token
    }

    /**
     * Fetches new o auth tokens (refresh and access token!) asynchronously. Needs a proper
     * authorization of the user with username and password.
     *
     * @param repository the repository to fetch new tokens from
     * @param username the username
     * @param password the password
     * @param callback the callback of the async call
     */
    fun fetchNewTokens(repository: Repository?,
                       username: String,
                       password: String,
                       callback: Callback<OAuthToken>) {

        if (repository == null) {
            return
        }

        repository.getNewTokens(username, password, callback)
    }

    /**
     * Fetches a new access token. This is based on a refresh token. If neither exists, all tokens
     * are invalidated and the user has to login or register.
     * This is a blocking request, which is not asynchronous and may **NOT** be called on the
     * main thread!
     *
     * @param repository the repository to fetch new oauth access tokens
     * @param context the base context
     * @return the access token if one could be fetched or null
     */
    fun fetchNewOAuthAccessTokenBlocking(repository: Repository?, context: Context?): String? {

        if (context == null || repository == null) {
            return null
        }

        try {
            val oldToken = Authenticator.retrieveOAuth(context)?.refreshToken ?: return null

            val response = repository.getNewAccessTokenBlocking(oldToken)

            return if (response?.body() != null) {

                val newToken = response.body() as OAuthToken

                val accessToken = newToken.accessToken

                if (accessToken != null) {
                    Authenticator.storeAccessToken(context, accessToken)
                }
                accessToken
            } else {
                // 401 ? no refreshToken ? kick the o auth and start over again
                deleteOAuthAccount(context)
                null
            }
        } catch (e: Exception) {
            // we have errors ? kick the o auth and start over again
            deleteOAuthAccount(context)
            return null
        }

    }

    /**
     * Retrieves a successful logged in intent.
     *
     * @param username the username
     * @param context the base context
     * @param token the o auth token
     * @return the intent
     */
    fun getOAuthLoginIntent(username: String,
                            context: Context,
                            token: OAuthToken): Intent {

        val result = Bundle()
        result.putString(AccountManager.KEY_ACCOUNT_NAME, username)
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, context.getString(R.string.account_type))
        result.putString(AccountManager.KEY_AUTHTOKEN, token.accessToken)
        result.putString(EXTRA_REFRESH_TOKEN, token.refreshToken)

        return Intent().putExtras(result)
    }

    /**
     * Retrieves the register intent.
     *
     * @param username the username
     * @param password the password
     * @return the intent
     */
    fun getOAuthRegisterIntent(username: String, password: String): Intent {

        val result = Bundle()
        result.putString(AccountManager.KEY_ACCOUNT_NAME, username)
        result.putString(EXTRA_USER_PASSWORD, password)

        return Intent().putExtras(result)
    }

    /**
     * Retrieves the current account name.
     *
     * @param context the base context
     */
    fun getOAuthAccountName(context: Context?): String {
        context?.let {
            return Authenticator.getAccountName(context)
        }
        return ""
    }

    /**
     * Deletes the current o auth account.
     *
     * @param context the base context
     */
    fun deleteOAuthAccount(context: Context?) {
        context?.let {
            Authenticator.invalidateOAuth(context)
            Authenticator.removeCurrentAccount(context)
        }
    }

    /**
     * Creates a new account or updates it from a previous login attempt. Pass this login
     * intent here or nothing will change.
     *
     * @param isAddingNew set to true if adding new, false otherwise
     * @param context the base context
     * @param loginIntent the login intent
     */
    fun createOrUpdateOAuthAccountFromLogin(isAddingNew: Boolean,
                                            context: Context,
                                            loginIntent: Intent) {

        val mAccountManager = AccountManager.get(context)
        val accountName = loginIntent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        val account = Account(accountName, context.getString(R.string.account_type))
        val refreshToken = loginIntent.getStringExtra(EXTRA_REFRESH_TOKEN)
        val accessToken = loginIntent.getStringExtra(AccountManager.KEY_AUTHTOKEN)

        mAccountManager?.setAuthToken(account, Authenticator.AUTH_TOKEN_TYPE, accessToken)
        mAccountManager?.setPassword(account, refreshToken)

        if (isAddingNew) {
            val userdata = Bundle()
            mAccountManager?.addAccountExplicitly(account, refreshToken, userdata)
        }

        Authenticator.storeAccessToken(context, accessToken)
    }


}
