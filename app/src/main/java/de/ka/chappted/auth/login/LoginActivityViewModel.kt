package de.ka.chappted.auth.login

import android.accounts.AccountManager
import android.content.Context

import android.content.Intent
import android.databinding.ObservableField
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import de.ka.chappted.R
import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.api.Repository
import de.ka.chappted.auth.AuthConfig
import de.ka.chappted.commons.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A login view model.
 *
 * Created by Thomas Hofmann on 15.12.17.
 */
class LoginActivityViewModel(val authConfig: AuthConfig?,
                             val listener: LoginListener) : BaseViewModel() {

    interface LoginListener {

        fun onRegisterRequested()

        fun onLoginFinish(loginIntent: Intent)
    }

    var userName: ObservableField<String> = ObservableField()
    var userPass: ObservableField<String> = ObservableField()

    init {
        userName.set(authConfig?.accountName)
    }

    fun getUserNameWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                userName.set(charSequence.toString())

            }

            override fun afterTextChanged(editable: Editable) {

            }
        }
    }

    fun getPasswordWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                userPass.set(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        }
    }

    fun onSubmit(): View.OnClickListener {
        return View.OnClickListener { view ->
            login(view.context, userName.get(), userPass.get())
        }
    }

    fun onRegister(): View.OnClickListener {
        return View.OnClickListener { view ->
            listener.onRegisterRequested()
        }
    }


    fun login(context: Context, username: String, password: String) {

        var token = OAuthToken()
        Repository.get().nonAuthorizedClient.getNewTokens(
                username,
                password,
                token.clientID,
                token.clientSecret,
                "password").enqueue(object : Callback<OAuthToken> {
            override fun onResponse(call: Call<OAuthToken>, response: Response<OAuthToken>) {

                if (response.body() != null) {

                    token = response.body() as OAuthToken

                    val result = Bundle()
                    result.putString(AccountManager.KEY_ACCOUNT_NAME, username)
                    result.putString(LoginActivity.EXTRA_USER_PASS, password)
                    result.putString(AccountManager.KEY_ACCOUNT_TYPE, context.getString(R.string.account_type))
                    result.putString(LoginActivity.EXTRA_ACCESS_TOKEN, token.accessToken)
                    result.putString(AccountManager.KEY_AUTHTOKEN, token.refreshToken)

                    listener.onLoginFinish(Intent().putExtras(result))
                }


            }

            override fun onFailure(call: Call<OAuthToken>, t: Throwable) {

            }
        })
    }
}

