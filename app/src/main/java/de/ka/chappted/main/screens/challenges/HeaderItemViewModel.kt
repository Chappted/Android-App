package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.MutableLiveData
import de.ka.chappted.R
import de.ka.chappted.api.model.Challenge
import de.ka.chappted.commons.arch.base.BaseItemViewModel

/**
 * A view model for showing a no connection item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class HeaderItemViewModel : BaseItemViewModel() {

    val challenge = MutableLiveData<Challenge>()

    private var challengesListListener: ChallengeListListener? = null

    /**
     * Sets up the view mode.
     *
     */
    fun setup(setupChallenge: Challenge, challengesListListener: ChallengeListListener) {

        this.challenge.value = setupChallenge
        this.challengesListListener = challengesListListener
    }

    fun submit(){ challengesListListener?.onMoreClicked(challenge.value?.category)}
}
