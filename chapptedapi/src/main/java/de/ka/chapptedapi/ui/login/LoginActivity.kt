package de.ka.chapptedapi.ui.login

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.accounts.AccountAuthenticatorResponse
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import de.ka.chapptedapi.ChapptedApi
import de.ka.chapptedapi.auth.OAuthUtils
import de.ka.chapptedapi.R
import de.ka.chapptedapi.model.OAuthToken
import de.ka.chapptedapi.jlsapi.JlsError
import de.ka.chapptedapi.jlsapi.JlsCallback
import de.ka.chapptedapi.jlsapi.JlsResponse
import de.ka.chapptedapi.ui.register.RegisterActivity
import timber.log.Timber

/**
 * A class for login procedures. Note that this class includes the original code of the account
 * management class due to compatibility issues with newer architectural components.
 *
 * Created by Thomas Hofmann on 08.12.17.
 */
class LoginActivity : AppCompatActivity() {

    // to keep this activity as simple as possible, only standard apis and
    // networking is used here

    companion object {
        const val REQUEST_CODE_REGISTER = 1432
    }

    private var accountAuthenticatorResponse: AccountAuthenticatorResponse? = null
    private var isAddingNewAccount: Boolean = false
    private var resultBundle: Bundle? = null

    private var username: String? = null
    private var password: String? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        isAddingNewAccount = intent.getBooleanExtra(OAuthUtils.EXTRA_IS_ADDING_NEW_ACCOUNT, false)

        var name: String? = null

        if (intent.hasExtra(AccountManager.KEY_ACCOUNT_NAME)) {
            name = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        }

        accountAuthenticatorResponse =
                intent?.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)
        accountAuthenticatorResponse?.onRequestContinued()

        findViewById<EditText>(R.id.usernameEdit).addTextChangedListener(getTextWatcher("username"))
        findViewById<EditText>(R.id.passwordEdit).addTextChangedListener(getTextWatcher("password"))
        findViewById<Button>(R.id.login).setOnClickListener({performLogin(username, password)})
        findViewById<Button>(R.id.register).setOnClickListener({onRegisterRequested()})
    }

    private fun getTextWatcher(type: String = "username"): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                when (type) {
                    "username" -> username = charSequence.toString()
                    "password" -> password = charSequence.toString()
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_REGISTER
                && resultCode == Activity.RESULT_OK
                && data != null) {
            performLogin(
                    data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME),
                    data.getStringExtra(OAuthUtils.EXTRA_USER_PASSWORD))
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onRegisterRequested() {
        val intent = Intent(baseContext, RegisterActivity::class.java)

        if (getIntent()?.extras != null) {
            intent.putExtras(getIntent().extras)
        }

        startActivityForResult(intent, REQUEST_CODE_REGISTER)
    }

    fun onAccountLoginCompleted(loginIntent: Intent) {
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


    private fun performLogin(username: String?, password: String?){

        if (username == null || password == null){
            return
        }

        OAuthUtils.fetchNewTokens(ChapptedApi.repository,this, username, password, object : JlsCallback<OAuthToken>() {

            override fun onSuccess(response: JlsResponse<OAuthToken>) {
                if (response.body != null) {

                    onAccountLoginCompleted(OAuthUtils.getOAuthLoginIntent(
                            username, this@LoginActivity, response.body))
                }
            }

            override fun onFailed(error: JlsError?) {
                Timber.e("Could not login the user.")
            }
        })
    }
}
