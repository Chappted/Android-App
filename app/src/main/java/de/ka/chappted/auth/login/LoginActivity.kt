package de.ka.chappted.auth.login

import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import de.ka.chappted.api.Repository
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
    }

    private var viewModel: LoginActivityViewModel? = null

    private var isAddingNewAccount: Boolean = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        isAddingNewAccount = intent.getBooleanExtra(OAuthUtils.EXTRA_IS_ADDING_NEW_ACCOUNT, false)

        var name: String? = null

        if (intent.hasExtra(AccountManager.KEY_ACCOUNT_NAME)) {
            name = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        }

        viewModel = LoginActivityViewModel(name, this)

        binding.viewModel = viewModel
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

        OAuthUtils.instance.createOrUpdateAccountFromLogin(
                isAddingNewAccount,
                this,
                loginIntent)

        setAccountAuthenticatorResult(loginIntent.extras)
        setResult(Activity.RESULT_OK, loginIntent)
        finish()

    }

    override fun onBackPressed() {
        Repository.instance.stop()
        super.onBackPressed()
    }
}
