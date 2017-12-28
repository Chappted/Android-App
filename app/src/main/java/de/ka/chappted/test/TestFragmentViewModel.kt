package de.ka.chappted.test

import android.app.Activity
import android.view.View
import de.ka.chappted.api.Repository
import de.ka.chappted.commons.base.BaseViewModel
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * Created by th on 20.12.17.
 */
class TestFragmentViewModel(private val activity: Activity) : BaseViewModel() {

    fun onSubmit(): View.OnClickListener {
        return View.OnClickListener { view ->


            Repository.instance.getUser(activity,
                    object : retrofit2.Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            Timber.e("YAY!")
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Timber.e(t, "YAY!")
                        }
                    })

        }
    }


}