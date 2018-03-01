package de.ka.chappted.main.screens.challenges

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.LinearLayoutManager
import de.ka.chappted.api.model.Challenge
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
    fun initAdapter(adapter: ChallengesAdapter){
        challengesAdapter.value = adapter // note that we don't postValue(), as this is immediately
    }

    /**
     * Loads challenges and immediately displays them.
     */
    fun loadChallenges() {

        val list = mutableListOf<Challenge>()
                .apply {
                    add(Challenge("yay"))
                    add(Challenge("dada"))
                    add(Challenge("ewefwf"))
                    add(Challenge("fwfewf"))
                    add(Challenge("wfwfef"))
                    add(Challenge("fwfwfw"))
                    add(Challenge("fef"))
                    add(Challenge("fwfe"))
                    add(Challenge("fwfwfwf"))
                    add(Challenge("fwfwef"))
                    add(Challenge("fefefewf"))
                    add(Challenge("ywfwfay"))
                    add(Challenge("vdvsdvscs"))
                    add(Challenge("adadad"))
                    add(Challenge("yavsvsvsy"))
                    add(Challenge("csvdsv"))
                    add(Challenge("vsvds"))
                    add(Challenge("vdvdvd"))
                }

        challengesAdapter.value?.addAll(list)
    }

    /**
     * Retrieves a layout manager.
     */
    fun getChallengesLayoutManager() = LinearLayoutManager(getApplication())
}
