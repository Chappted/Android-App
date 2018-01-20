package de.ka.chappted.auth

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.accounts.NetworkErrorException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

import de.ka.chappted.R
import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.auth.login.LoginActivity

import android.accounts.AccountManager.KEY_BOOLEAN_RESULT
import android.accounts.AccountManager.get
import android.os.Build
import de.ka.chappted.api.Repository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * A authenticator. Uses OAuth2 for authenticating the user.
 *
 * Created by Thomas Hofmann on 08.12.17.
 *
 * @see OAuthUtils
 */
class Authenticator
internal constructor(private val context: Context)
    : AbstractAccountAuthenticator(context), KoinComponent {

    val repository: Repository by inject()

    private val handler = Handler()

    @Throws(NetworkErrorException::class)
    override fun addAccount(response: AccountAuthenticatorResponse,
                            accountType: String,
                            authTokenType: String?,
                            requiredFeatures: Array<String>?,
                            options: Bundle): Bundle? {

        val result = Bundle()

        if (getAccount(context) != null) {
            val errorHint = context.getString(R.string.account_error_multiple_accounts)

            result.putInt(AccountManager.KEY_ERROR_CODE, 30)
            result.putString(AccountManager.KEY_ERROR_MESSAGE, errorHint)

            handler.post { Toast.makeText(context, errorHint, Toast.LENGTH_LONG).show() }

            return result
        }

        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(OAuthUtils.EXTRA_IS_ADDING_NEW_ACCOUNT, true)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)

        result.putParcelable(AccountManager.KEY_INTENT, intent)
        return result
    }

    @Throws(NetworkErrorException::class)
    override fun getAuthToken(response: AccountAuthenticatorResponse,
                              account: Account?,
                              authTokenType: String?,
                              options: Bundle): Bundle {

        val result = Bundle()

        // case 1: authTokenType is invalid
        if (authTokenType != null && authTokenType != AUTH_TOKEN_TYPE) {
            result.putInt(AccountManager.KEY_ERROR_CODE, 31)
            result.putString(AccountManager.KEY_ERROR_MESSAGE, context.getString(R.string.account_error_tokentype))
            return result
        }

        val accountManager = AccountManager.get(context)

        // if account is available
        if (account != null) {

            val accessToken = accountManager.peekAuthToken(account, authTokenType)
            val refreshToken = accountManager.getPassword(account)
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)

            // case 2: accessToken is available
            if (accessToken != null) {
                return result
            }

            // case 3: access token is not available but refresh token is, fetch a new accesstoken!
            if (refreshToken != null) {
                OAuthUtils.fetchNewOAuthAccessTokenBlocking(repository, context)
                return result
            }

        }

        // case 4: account does not exist, ask for login
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun getAuthTokenLabel(authTokenType: String): String {
        return if (authTokenType == AUTH_TOKEN_TYPE) {
            context.getString(R.string.account_token_label)
        } else {
            authTokenType
        }
    }

    @Throws(NetworkErrorException::class)
    override fun hasFeatures(response: AccountAuthenticatorResponse, account: Account,
                             features: Array<String>): Bundle {
        val result = Bundle()
        result.putBoolean(KEY_BOOLEAN_RESULT, false)
        return result
    }

    override fun editProperties(response: AccountAuthenticatorResponse,
                                accountType: String): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun confirmCredentials(response: AccountAuthenticatorResponse, account: Account,
                                    options: Bundle): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun updateCredentials(response: AccountAuthenticatorResponse, account: Account,
                                   authTokenType: String, options: Bundle): Bundle? {
        return null
    }

    companion object {

        // Authentication process 'password grant':
        // We use the OAuth2 'authentication code flow' where we store an access token as the
        // authToken for accessing all resources.
        // additionally an refreshToken is stored as password for access to new access tokens.
        // An expired/revoked refreshToken leads to a new registration/login to get a new one.

        const val AUTH_TOKEN_TYPE = "Access"

        /**
         * Stores the access token for this account.
         *
         * @param context     the base context
         * @param accessToken the access token to store
         */
        internal fun storeAccessToken(context: Context, accessToken: String) {
            val account = Authenticator.getAccount(context)

            if (account != null) {
                get(context).setAuthToken(account, AUTH_TOKEN_TYPE, accessToken)
            }
        }

        /**
         * Fires a request for o auth. Will lead to a login/registration screen,
         * if not authenticated already.
         * Else, might lead to a new access token. Will **NOT** notify on any
         * success/failure as this is intended to be called if a new login/registration is
         * wanted.
         *
         * @param context the base context
         */
        internal fun requestNewOAuth(context: Context) {
            val bundle = Bundle()
            bundle.putBoolean(OAuthUtils.EXTRA_IS_ADDING_NEW_ACCOUNT, true)
            context.startActivity(Intent(context.applicationContext, LoginActivity::class.java)
                    .putExtras(bundle))
        }

        /**
         * Retrieves the o auth token from the account. If there is no account, null is returned.
         *
         * @param context the base context
         * @return the token or null, if there is no token
         */
        internal fun retrieveOAuth(context: Context): OAuthToken? {

            val token = OAuthToken()

            val account = getAccount(context) ?: return null

            token.refreshToken = get(context).getPassword(account)
            token.accessToken = get(context).peekAuthToken(account, AUTH_TOKEN_TYPE)

            return token

        }

        /**
         * If there is an account, invalidates the refresh token which also consumes the
         * current accessToken. This makes a new login / register for o auth mandatory.
         *
         * @param context the base context
         */
        internal fun invalidateOAuth(context: Context) {
            val accountManager = get(context)

            val account = getAccount(context) ?: return

            val token = accountManager.peekAuthToken(account, AUTH_TOKEN_TYPE)

            accountManager.setPassword(account, null)
            accountManager.invalidateAuthToken(AUTH_TOKEN_TYPE, token)
        }

        /**
         * Retrieves the current account name or an empty string if there is no account.
         *
         * @return the account name or empty string
         */
        internal fun getAccountName(context: Context): String {

            val account = getAccount(context)

            return if (account != null) {
                account.name
            } else ""

        }

        /**
         * Removes the current account.
         *
         * @param context the base context
         * @return true if there was something to remove, false otherwise
         */
        internal fun removeCurrentAccount(context: Context): Boolean {

            val account = getAccount(context)

            if (account != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    get(context).removeAccount(account, null, null, null)
                } else {
                    @Suppress("DEPRECATION")
                    get(context).removeAccount(account, null, null)
                }
                return true
            }

            return false
        }


        /**
         * Retrieves the current account, if any.
         *
         * @param context the base context
         * @return the account or null if there is none
         */
        private fun getAccount(context: Context): Account? {
            val accountManager = get(context)

            val availableAccounts = accountManager.getAccountsByType(context.getString(R.string.account_type))

            return if (availableAccounts.isNotEmpty()) {
                availableAccounts[0]
            } else null
        }
    }
}
