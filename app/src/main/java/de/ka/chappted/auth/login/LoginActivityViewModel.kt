package de.ka.chappted.auth.login

import android.content.Context

import android.content.Intent
import android.databinding.ObservableField
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.auth.OAuthUtils
import de.ka.chappted.commons.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A login view model.
 *
 * Created by Thomas Hofmann on 15.12.17.
 */
class LoginActivityViewModel(var name: String?,
                             val listener: LoginListener) : BaseViewModel() {

    interface LoginListener {

        fun onRegisterRequested()

        fun onAccountLoginCompleted(loginIntent: Intent)
    }

    var userName: ObservableField<String> = ObservableField()
    var userPass: ObservableField<String> = ObservableField()

    init {
        userName.set(name)
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
        return View.OnClickListener {
            listener.onRegisterRequested()
        }
    }


    fun login(context: Context, username: String, password: String) {
        OAuthUtils.instance.fetchAllTokensAsync(username, password, object : Callback<OAuthToken> {
            override fun onResponse(call: Call<OAuthToken>, response: Response<OAuthToken>) {

                if (response.body() != null) {

                    val token = response.body() as OAuthToken

                    listener.onAccountLoginCompleted(OAuthUtils.instance.getLoginIntent(
                            username,
                            context,
                            token))
                }
            }

            override fun onFailure(call: Call<OAuthToken>, t: Throwable) {

            }
        })
    }
}

