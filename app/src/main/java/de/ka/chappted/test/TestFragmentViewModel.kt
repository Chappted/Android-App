package de.ka.chappted.test

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.view.View
import de.ka.chappted.commons.arch.base.BaseViewModel
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * Created by th on 20.12.17.
 */
class TestFragmentViewModel(application: Application) : BaseViewModel(application) {

    var needsLogin: Boolean = false

    val progressVisibility = MutableLiveData<Int>()

    init {
        progressVisibility.postValue(View.INVISIBLE)
    }


    fun onSubmit() {
        repository.getUser(
                object : retrofit2.Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Timber.e("YAY!")

                        needsLogin = response.code() == 401

                        if (response.code() == 200) {
                            // getApplication<Application>().startActivity(Intent(getApplication(), TesterActivity::class.java))
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Timber.e(t, "YAY!")
                    }
                })

        Handler().postDelayed({ progressVisibility.postValue(View.INVISIBLE) }, 10000)
        progressVisibility.postValue(View.VISIBLE)
    }

    override fun onLoggedIn() {
        if (needsLogin) {
            onSubmit()
        }
    }

}