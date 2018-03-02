package de.ka.chappted.main.screens.challenges

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import de.ka.chappted.api.model.Challenge
import de.ka.chappted.api.model.Type
import de.ka.chappted.commons.arch.base.BaseViewModel

/**
 * A view model for showing the challenges content.
 *
 * Created by Thomas Hofmann on 01.03.18.
 */
class ChallengesFragmentViewModel(application: Application) : BaseViewModel(application) {

    var challengesAdapter = MutableLiveData<ChallengesAdapter>()

    /**
     * Initializes the adapter
     */
    fun initAdapter(adapter: ChallengesAdapter) {
        challengesAdapter.value = adapter // note that we don't postValue(), as this is immediately
    }

    /**
     * Loads challenges and immediately displays them.
     */
    fun loadChallenges() {

        challengesAdapter.value?.showLoading()

        val list = mutableListOf<Challenge>()
                .apply {
                    add(Challenge(Type.HEADER, category = "Recommended"))
                    add(Challenge(title = "yay", category = "FIFA 18", isProtected =  true))
                    add(Challenge(title = "dada", category = "Tischtennis"))
                    add(Challenge(title = "ewefwf", category = "FIFA 18", isProtected = true))
                    add(Challenge(Type.HEADER, category = "Nearby"))
                    add(Challenge(title = "fwfewf", category = "Tischtennis"))
                    add(Challenge(title = "wfwfef", isProtected = true))
                    add(Challenge(title = "fwfwfw"))
                    add(Challenge(Type.HEADER, category = "Latest"))
                    add(Challenge(title = "fef"))
                    add(Challenge(title = "fwfe"))
                    add(Challenge(title = "fwfwfwf", isProtected = true))
                    add(Challenge(Type.NO_CONNECTION))
                }


        Handler().postDelayed({
            challengesAdapter.value?.hideLoading()?.addAll(list)
        }, 4_000)

    }

    /**
     * Retrieves a layout manager.
     */
    fun getChallengesLayoutManager() = LinearLayoutManager(getApplication())
}
