package de.ka.chappted.auth.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import de.ka.chappted.api.model.OAuthToken
import de.ka.chappted.auth.OAuthUtils
import de.ka.chappted.commons.arch.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * A login view model.
 *
 * Created by Thomas Hofmann on 15.12.17.
 */
class LoginActivityViewModel(application: Application) : BaseViewModel(application) {

    interface LoginListener {

        fun onRegisterRequested()

        fun onAccountLoginCompleted(loginIntent: Intent)
    }

    var listener: LoginListener? = null
    var userName = MutableLiveData<String>()
    var userPass = MutableLiveData<String>()

    fun getUserNameWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                userName.postValue(charSequence.toString())

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
                userPass.postValue(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        }
    }

    fun onSubmit() {
        login(getApplication(), userName.value ?: "", userPass.value ?: "")
    }

    fun onRegister() {
        listener?.onRegisterRequested()
    }

    fun login(context: Context, username: String, password: String) {
        OAuthUtils.fetchNewTokens(repository, username, password, object : Callback<OAuthToken> {

            override fun onResponse(call: Call<OAuthToken>, response: Response<OAuthToken>) {

                if (response.body() != null) {

                    val token = response.body() as OAuthToken

                    listener?.onAccountLoginCompleted(OAuthUtils.getOAuthLoginIntent(
                            username,
                            context,
                            token))
                }
            }

            override fun onFailure(call: Call<OAuthToken>, t: Throwable) {
                Timber.e(t, "Could not login the user.")
            }
        })
    }
}

