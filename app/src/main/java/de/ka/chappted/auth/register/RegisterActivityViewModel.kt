package de.ka.chappted.auth.register

import android.accounts.AccountManager
import android.content.Intent
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import de.ka.chappted.auth.login.LoginActivity
import de.ka.chappted.commons.base.BaseViewModel
import de.ka.chappted.api.Repository
import de.ka.chappted.api.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A register view model.
 *
 * Created by Thomas Hofmann on 18.12.17.
 */
class RegisterActivityViewModel(val listener: RegisterListener) : BaseViewModel() {

    var userName: ObservableField<String> = ObservableField()
    var userPass: ObservableField<String> = ObservableField()
    var loadingProgress: ObservableInt = ObservableInt(View.INVISIBLE)

    interface RegisterListener {

        fun onRegistered(registerIntent: Intent)

        fun onRegisterCancelled()

    }

    fun onSubmit(): View.OnClickListener {
        return View.OnClickListener {
            register()

        }
    }

    fun onAlreadyMemberSubmit(): View.OnClickListener {
        return View.OnClickListener {
            listener.onRegisterCancelled()
        }
    }


    private fun register() {

        val data = Bundle()

        loadingProgress.set(View.VISIBLE)

        Repository.get().nonAuthorizedClient.register(User(userName.get(), userPass.get())).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                loadingProgress.set(View.GONE)

                if (response.body() != null) {

                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName.get())
                    data.putString(LoginActivity.EXTRA_USER_PASS, userPass.get())

                    listener.onRegistered(Intent().putExtras(data))
                }


            }

            override fun onFailure(call: Call<User>, t: Throwable) {

                loadingProgress.set(View.GONE)

            }
        })

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


}

