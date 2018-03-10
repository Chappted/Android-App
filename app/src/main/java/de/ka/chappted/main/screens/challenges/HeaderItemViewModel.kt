package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
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

    fun submit() {
        challengesListListener?.onMoreClicked(challenge.value?.category)
    }

    fun headerIcon(): Drawable{

        when (challenge.value?.headerType) {

            appContext.getString(R.string.challenge_recommended) ->
                return appContext.getDrawable(R.drawable.ic_recommended)
            appContext.getString(R.string.challenge_nearby) ->
                return appContext.getDrawable(R.drawable.ic_nearby)
            appContext.getString(R.string.challenge_latest) ->
                return appContext.getDrawable(R.drawable.ic_latest)
        }

        return appContext.getDrawable(R.drawable.ic_recommended)
    }
}
