package de.ka.chappted.main.screens.accepted

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.view.View
import de.ka.chappted.commons.arch.base.BaseViewModel
import de.ka.chapptedapi.jlsapi.JlsError
import de.ka.chapptedapi.jlsapi.JlsCallback
import de.ka.chapptedapi.jlsapi.JlsResponse
import timber.log.Timber

/**
 * Created by th on 20.12.17.
 */
class AcceptedFragmentViewModel(application: Application) : BaseViewModel(application) {

    var needsLogin: Boolean = false

    val progressVisibility = MutableLiveData<Int>()

    init {
        progressVisibility.postValue(View.INVISIBLE)
    }

    fun onSubmit() {
        progressVisibility.postValue(View.VISIBLE)

        repository.getUser(
                getApplication(),
                object : JlsCallback<Void>() {

                    override fun onSuccess(response: JlsResponse<Void>) {
                        Timber.e("YAY!")

                        progressVisibility.postValue(View.INVISIBLE)

                    }

                    override fun onFailed(error: JlsError?) {

                        progressVisibility.postValue(View.INVISIBLE)
                    }


                })

    }

    override fun onLoggedIn() {
        if (needsLogin) {
            onSubmit()
        }
    }

}