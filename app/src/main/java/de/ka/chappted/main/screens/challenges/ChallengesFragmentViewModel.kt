package de.ka.chappted.main.screens.challenges

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import de.ka.chappted.api.model.Challenge
import de.ka.chappted.commons.arch.base.BaseViewModel

/**
 * A view model for showing the home content.
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
class ChallengesFragmentViewModel(application: Application) : BaseViewModel(application) {

    var adapter = MutableLiveData<ChallengesAdapter>()



    val itemViewModels = mutableListOf<ChallengesItemViewModel>()
            .apply {
                add(ChallengesItemViewModel(ObservableField(Challenge("yay"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("dada"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("ewefwf"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("fwfewf"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("wfwfef"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("fwfwfw"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("fef"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("fwfe"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("fwfwfwf"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("fwfwef"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("fefefewf"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("ywfwfay"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("vdvsdvscs"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("adadad"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("yavsvsvsy"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("csvdsv"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("vsvds"))))
                add(ChallengesItemViewModel(ObservableField(Challenge("vdvdvd"))))
            }

    fun setup() {
        adapter.postValue(ChallengesAdapter(itemViewModels) {
            it.play()
            Toast.makeText(getApplication(), "challenge " + it.challenge.get()?.title, Toast.LENGTH_SHORT).show()
        })
    }


    fun getChallengesLayoutManager() = LinearLayoutManager(getApplication())

}
