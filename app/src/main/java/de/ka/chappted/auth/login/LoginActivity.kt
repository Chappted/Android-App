package de.ka.chappted.auth.login

import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import de.ka.chappted.R
import de.ka.chappted.auth.AuthConfig
import de.ka.chappted.auth.Authenticator
import de.ka.chappted.auth.OAuthUtils
import de.ka.chappted.auth.register.RegisterActivity
import de.ka.chappted.databinding.ActivityLoginBinding

/**
 * A class for login procedures.
 *
 * Created by Thomas Hofmann on 08.12.17.
 */
class LoginActivity : AccountAuthenticatorActivity(), LoginActivityViewModel.LoginListener {

    companion object {
        const val REQUEST_CODE_REGISTER = 1432

        const val EXTRA_USER_PASS = "USER_PASS"
        const val EXTRA_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
        const val EXTRA_ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    private var viewModel: LoginActivityViewModel? = null

    private var mAccountManager: AccountManager? = null

    private var authConfig: AuthConfig? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mAccountManager = AccountManager.get(baseContext)

        authConfig = AuthConfig(
                intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME),
                intent.getBooleanExtra(LoginActivity.EXTRA_IS_ADDING_NEW_ACCOUNT, false))

        viewModel = LoginActivityViewModel(authConfig, this)

        binding.viewModel = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == Activity.RESULT_OK) {
            viewModel?.login(this, data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME), data.getStringExtra(EXTRA_USER_PASS))
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRegisterRequested() {
        startActivityForResult(Intent(
                baseContext,
                RegisterActivity::class.java).putExtras(intent.extras),
                REQUEST_CODE_REGISTER)
    }

    override fun onLoginFinish(loginIntent: Intent) {

        if (loginIntent.extras.isEmpty) {
            return
        }

        val accountName = loginIntent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        val accountPassword = loginIntent.getStringExtra(LoginActivity.EXTRA_USER_PASS)
        val account = Account(accountName, getString(R.string.account_type))
        val refreshToken = loginIntent.getStringExtra(AccountManager.KEY_AUTHTOKEN)
        val accessToken = loginIntent.getStringExtra(LoginActivity.EXTRA_ACCESS_TOKEN)

        authConfig?.let {

            if (it.isAddingNew) {
                val userdata = Bundle()
                mAccountManager?.addAccountExplicitly(account, accountPassword, userdata)
            } else {
                mAccountManager?.setPassword(account, accountPassword)
            }

            mAccountManager?.setAuthToken(account, Authenticator.AUTH_TOKEN_TYPE, refreshToken)
            mAccountManager?.setUserData(account, Authenticator.KEY_USERDATA_ACCESS_TOKEN, accessToken) // prevents userdata bundle bug

        }

        OAuthUtils.get().updateOAuthForAccount(this)

        setAccountAuthenticatorResult(loginIntent.extras)
        setResult(Activity.RESULT_OK, loginIntent)
        finish()

    }
}
