package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import de.ka.chappted.R
import de.ka.chappted.api.model.Challenge
import de.ka.chappted.commons.arch.base.BaseItemViewModel

/**
 * A view model for showing challenge item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class ChallengesItemViewModel : BaseItemViewModel() {

    val challenge = MutableLiveData<Challenge>()
    val titleDrawable = MutableLiveData<Drawable>()
    val progressVisibility = MutableLiveData<Int>()

    init {
        progressVisibility.value = View.GONE
    }

    private var challengesListListener: ChallengeListListener? = null

    /**
     * Sets up the view model.
     * @param setupChallenge the challenge
     * @param challengesListListener the listener
     */
    fun setup(setupChallenge: Challenge, challengesListListener: ChallengeListListener) {

        this.challenge.value = setupChallenge
        this.challengesListListener = challengesListListener

        if (setupChallenge.isProtected == true) {
            titleDrawable.value = appContext.getDrawable(R.drawable.ic_lock)
        }
    }

    fun onChallengeClick() = challenge.value?.let {
        play()
        challengesListListener?.onChallengeClicked(it)
    }

    /**
     * Plays a fancy animation for a short period of time.
     */
    private fun play() {

        progressVisibility.value = (View.VISIBLE)

        Handler().postDelayed({
            progressVisibility.value = (View.GONE)
        }, 4_500)
    }
}
