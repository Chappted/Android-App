package de.ka.chappted.auth.login

import android.accounts.AccountManager
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import de.ka.chappted.auth.OAuthUtils
import de.ka.chappted.auth.register.RegisterActivity
import de.ka.chappted.databinding.ActivityLoginBinding
import android.accounts.AccountAuthenticatorResponse
import de.ka.chappted.commons.base.BaseActivity


/**
 * A class for login procedures. Note that this class includes the original code of the account
 * management class due to compatibility issues with newer architectural components.
 *
 * Created by Thomas Hofmann on 08.12.17.
 */
class LoginActivity : BaseActivity(), LoginActivityViewModel.LoginListener {

    companion object {
        const val REQUEST_CODE_REGISTER = 1432
    }

    private var accountAuthenticatorResponse: AccountAuthenticatorResponse? = null
    private var viewModel: LoginActivityViewModel? = null
    private var isAddingNewAccount: Boolean = false
    private var resultBundle: Bundle? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isAddingNewAccount = intent.getBooleanExtra(OAuthUtils.EXTRA_IS_ADDING_NEW_ACCOUNT, false)

        var name: String? = null

        if (intent.hasExtra(AccountManager.KEY_ACCOUNT_NAME)) {
            name = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        }

        accountAuthenticatorResponse =
                intent?.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)
        accountAuthenticatorResponse?.onRequestContinued()

        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)
        viewModel?.userName?.postValue(name)
        viewModel?.listener = this

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_REGISTER
                && resultCode == Activity.RESULT_OK
                && data != null) {
            viewModel?.login(
                    this,
                    data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME),
                    data.getStringExtra(OAuthUtils.EXTRA_USER_PASSWORD))
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

    override fun onAccountLoginCompleted(loginIntent: Intent) {
        if (loginIntent.extras.isEmpty) {
            return
        }

        OAuthUtils.createOrUpdateOAuthAccountFromLogin(
                isAddingNewAccount,
                this,
                loginIntent)

        resultBundle = loginIntent.extras
        setResult(Activity.RESULT_OK, loginIntent)
        finish()
    }


    override fun finish() {
        accountAuthenticatorResponse?.let {

            if (resultBundle != null) {
                it.onResult(resultBundle)
            } else {
                it.onError(AccountManager.ERROR_CODE_CANCELED, "cancelled")
            }
            accountAuthenticatorResponse = null
        }
        super.finish()
    }
}
