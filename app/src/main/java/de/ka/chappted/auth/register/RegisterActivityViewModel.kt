package de.ka.chappted.auth.register

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import de.ka.chappted.commons.arch.base.BaseViewModel
import de.ka.chappted.api.model.User
import de.ka.chappted.auth.OAuthUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A register view model.
 *
 * Created by Thomas Hofmann on 18.12.17.
 */
class RegisterActivityViewModel(application: Application) : BaseViewModel(application) {

    var listener: RegisterListener? = null

    var userName = MutableLiveData<String>()
    var userPass = MutableLiveData<String>()
    var loadingProgress = MutableLiveData<Int>()

    init {
        loadingProgress.postValue(View.INVISIBLE)
    }

    interface RegisterListener {

        fun onRegistered(registerIntent: Intent)

        fun onRegisterCancelled()

    }

    fun onSubmit() {
        register()
    }


    fun onAlreadyMemberSubmit(): View.OnClickListener {
        return View.OnClickListener {
            listener?.onRegisterCancelled()
        }
    }


    private fun register() {

        loadingProgress.postValue(View.VISIBLE)

        repository.register(User(userName.value ?: "", userPass.value ?: ""), object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                loadingProgress.postValue(View.GONE)

                if (response.body() != null) {

                    listener?.onRegistered(
                            OAuthUtils.getOAuthRegisterIntent(userName.value ?: "", userPass.value ?: ""))
                }


            }

            override fun onFailure(call: Call<User>, t: Throwable) {

                loadingProgress.postValue(View.GONE)

            }
        })

    }

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


}

