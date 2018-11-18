package de.ka.chapptedapi.ui.register

import android.os.Bundle
import android.content.Intent
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import de.ka.chapptedapi.ChapptedApi
import de.ka.chapptedapi.auth.OAuthUtils
import de.ka.chapptedapi.R
import de.ka.chapptedapi.model.User
import de.ka.chapptedapi.jlsapi.JlsError
import de.ka.chapptedapi.jlsapi.JlsCallback
import de.ka.chapptedapi.jlsapi.JlsResponse

/**
 * A registration activity for the authorization flow.
 *
 * Created by Thomas Hofmann on 13.12.17.
 */
class RegisterActivity : AppCompatActivity() {

    // to keep this activity as simple as possible, only standard apis and
    // networking is used here

    var progressBar: ProgressBar? = null
    var username: String? = null
    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        progressBar = findViewById(R.id.progress)
        progressBar?.visibility = View.GONE
        findViewById<EditText>(R.id.usernameEdit).addTextChangedListener(getTextWatcher("username"))
        findViewById<EditText>(R.id.passwordEdit).addTextChangedListener(getTextWatcher("password"))
        findViewById<Button>(R.id.button).setOnClickListener({ register() })
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

    fun onRegistered(registerIntent: Intent) {
        setResult(Activity.RESULT_OK, registerIntent)
        finish()
    }

    fun onRegisterCancelled() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun register() {
        progressBar?.visibility = View.VISIBLE

        ChapptedApi.repository?.register(this,
                User(username ?: "", password ?: ""),
                object : JlsCallback<User>() {

                    override fun onSuccess(response: JlsResponse<User>) {
                        progressBar?.visibility = View.GONE

                        if (response.body != null) {

                            onRegistered(
                                    OAuthUtils.getOAuthRegisterIntent(username ?: "", password
                                            ?: ""))
                        }
                    }

                    override fun onFailed(error: JlsError?) {
                        progressBar?.visibility = View.GONE
                    }

                })
    }

}
