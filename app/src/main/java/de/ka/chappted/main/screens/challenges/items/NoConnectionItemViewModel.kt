package de.ka.chappted.main.screens.challenges.items

import de.ka.chappted.commons.arch.base.BaseItemViewModel
import de.ka.chappted.main.screens.challenges.ChallengeListListener

/**
 * A view model for showing a no connection item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class NoConnectionItemViewModel : BaseItemViewModel() {

    private var challengesListListener: ChallengeListListener? = null

    /**
     * Sets up the view model.
     * @param challengesListListener the listener
     */
    fun setup(challengesListListener: ChallengeListListener) {
        this.challengesListListener = challengesListListener
    }

    fun submit() {
        challengesListListener?.onRetryClicked()
    }
}
