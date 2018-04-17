package de.ka.chappted.main.screens.accepted.items

import de.ka.chappted.commons.arch.base.BaseItemViewModel
import de.ka.chappted.main.screens.accepted.AcceptedListListener
import de.ka.chappted.main.screens.challenges.ChallengeListListener

/**
 * A view model for showing a no connection item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class AcceptedNoConnectionItemViewModel : BaseItemViewModel() {

    private var acceptedListListener: AcceptedListListener? = null

    /**
     * Sets up the view model.
     * @param challengesListListener the listener
     */
    fun setup(acceptedListListener: AcceptedListListener) {
        this.acceptedListListener = acceptedListListener
    }

    fun submit() {
        acceptedListListener?.onRetryClicked()
    }
}
