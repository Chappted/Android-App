package de.ka.chappted.test

import android.app.Application
import android.content.Intent
import android.view.View
import de.ka.chappted.api.Repository
import de.ka.chappted.commons.base.BaseViewModel
import de.ka.chappted.Chappted
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * Created by th on 20.12.17.
 */
class TestFragmentViewModel(application: Application) : BaseViewModel(application) {

    fun onSubmit() {
        Repository.instance.getUser(
                object : retrofit2.Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Timber.e("YAY!")

                        Chappted.resumedActivity?.let { //TODO remove it, this was just for testing. No acitivty ref allowed in a view model!!
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