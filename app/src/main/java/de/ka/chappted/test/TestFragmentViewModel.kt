package de.ka.chappted.test

import android.app.Application
import android.content.Intent
import de.ka.chappted.Chappted
import de.ka.chappted.commons.base.BaseViewModel
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * Created by th on 20.12.17.
 */
class TestFragmentViewModel(application: Application) : BaseViewModel(application) {


    fun onSubmit() {
        repository.getUser(
                object : retrofit2.Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Timber.e("YAY!")


                        Chappted.resumedActivity?.get()?.let { //TODO remove it, this was just for testing. No acitivty ref allowed in a view model!!
                            it.startActivity(Intent(it, TesterActivity::class.java))
                            it.finish()
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Timber.e(t, "YAY!")
                    }
                })
    }


}