package de.ka.chappted.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import de.ka.chappted.R

import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.api.Repository
import retrofit2.Callback

/**
 * A o auth utility.
 *
 *
 * Created by Thomas Hofmann on 22.12.17.
 */
class OAuthUtils private constructor() {

    companion object {
        val instance: OAuthUtils by lazy { Holder.INSTANCE }

        const val EXTRA_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
        const val EXTRA_USER_PASSWORD = "USER_PASSWORD"
        const val EXTRA_REFRESH_TOKEN = "REFRESH_TOKEN"
    }

    private object Holder {
        val INSTANCE = OAuthUtils()
    }

    private var mOAuthToken = OAuthToken()

    /**
     * Retrieves the o auth token. Will auto-load the current o auth token, if there is any and
     * update the o auth token of this utility. If there is no auth token, a login or register is
     * presented, if possible. Pass a activity as context for auto showing login / register.
     *
     * @param context the base context, may be an activity for showing login / register
     * @return the token
     */
    fun peek(context: Context): OAuthToken? {

        // on first load, we peek if there is a refresh token stored here
        if (mOAuthToken.refreshToken == null) {

            // if not, we load it from the account
            updateOAuthForAccount(context)

            // if there is none, we have to register first
            if (mOAuthToken.refreshToken == null && context is Activity) {
                Authenticator.requestNewOAuth(context)
                return null
            }
        }

        return mOAuthToken
    }


    /**
     * Fetches a new access token. This is based on a refresh token. If neither exists, all tokens
     * are invalidated and the user has to login or register.
     * This is a blocking request, which is not asynchronous and may **NOT** be called on the
     * main thread!
     *
     * @param context the base context
     * @return the access token if one could be fetched or null
     */
    fun fetchNewAccessTokenBlocking(context: Context): String? {

        try {
            val response = Repository.instance.getNonAuthenticatedClient()?.getNewAccessToken(
                    mOAuthToken.refreshToken,
                    mOAuthToken.clientID,
                    mOAuthToken.clientSecret,
                    "refresh_token")?.execute()

            if (response?.body() != null) {

                val token = response.body() as OAuthToken

                val accessToken = token.accessToken

                mOAuthToken.accessToken = accessToken
                mOAuthToken.expiry = token.expiry
                mOAuthToken.scope = token.scope
                mOAuthToken.tokenType = token.tokenType

                Authenticator.storeAccessToken(context, accessToken)
                return accessToken
            } else {
                // 401 ? no refreshToken ? kick the o auth and start over again
                deleteOAuthAccount(context)
                return null
            }
        } catch (e: Exception) {
            // we have errors ? kick the o auth and start over again
            deleteOAuthAccount(context)
            return null
        }

    }

    fun fetchAllTokensAsync(username: String, password: String, callback: Callback<OAuthToken>) {
        val token = OAuthToken()
        Repository.instance.getNonAuthenticatedClient()?.getNewTokens(
                username,
                password,
                token.clientID,
                token.clientSecret,
                "password")?.enqueue(callback)
    }

    /**
     * Retrieves a successful logged in intent.
     *
     * @param username the username
     * @param context the base context
     * @param token the o auth token
     * @return the intent
     */
    fun getLoginIntent(username: String,
                       context: Context,
                       token: OAuthToken): Intent {

        val result = Bundle()
        result.putString(AccountManager.KEY_ACCOUNT_NAME, username)
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, context.getString(R.string.account_type))
        result.putString(EXTRA_REFRESH_TOKEN, token.refreshToken)
        result.putString(AccountManager.KEY_AUTHTOKEN, token.accessToken)

        return Intent().putExtras(result)
    }

    /**
     * Retrieves the register intent.
     * @param username the username
     * @param password the password
     * @return the intent
     */
    fun getRegisterIntent(username: String, password: String): Intent {

        val result = Bundle()
        result.putString(AccountManager.KEY_ACCOUNT_NAME, username)
        result.putString(EXTRA_USER_PASSWORD, password)

        return Intent().putExtras(result)
    }

    /**
     * Retrieves the current account name.
     */
    fun getName(context: Context): String {
        return Authenticator.getAccountName(context)
    }

    /**
     * Deletes the current o auth account.
     */
    fun deleteOAuthAccount(context: Context) {
        Authenticator.invalidateOAuth(context)

        mOAuthToken.accessToken = null
        mOAuthToken.refreshToken = null

        Authenticator.removeCurrentAccount(context)
    }

    /**
     * Creates a new account or updates it from a previous login attempt. Pass this login
     * intent here or nothing will change.
     *
     * @param isAddingNew set to true if adding new, false otherwise
     * @param context the base context
     * @param loginIntent the login intent
     */
    fun createOrUpdateAccountFromLogin(isAddingNew: Boolean, context: Context, loginIntent: Intent) {

        val mAccountManager = AccountManager.get(context)
        val accountName = loginIntent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        val account = Account(accountName, context.getString(R.string.account_type))
        val refreshToken = loginIntent.getStringExtra(EXTRA_REFRESH_TOKEN)
        val accessToken = loginIntent.getStringExtra(AccountManager.KEY_AUTHTOKEN)

        if (isAddingNew) {
            val userdata = Bundle()
            mAccountManager?.addAccountExplicitly(account, refreshToken, userdata)
        }

        mAccountManager?.setAuthToken(account, Authenticator.AUTH_TOKEN_TYPE, accessToken)
        mAccountManager?.setPassword(account, refreshToken)

        updateOAuthForAccount(context)
    }

    /**
     * Updates th o auth tokens for the current account.
     *
     * @param context the base context
     */
    private fun updateOAuthForAccount(context: Context) {

        val token = Authenticator.retrieveOAuth(context)

        if (token != null) {
            mOAuthToken = token
        }
    }
}
