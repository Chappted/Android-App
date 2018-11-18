package de.ka.chappted.main.screens.challenges.items

import androidx.lifecycle.MutableLiveData
import androidx.core.content.ContextCompat
import de.ka.chappted.commons.arch.base.BaseItemViewModel
import de.ka.chappted.main.screens.challenges.ChallengeListListener

/**
 * A view model for showing a header item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class HeaderItemViewModel : BaseItemViewModel() {

    private var challengesListListener: ChallengeListListener? = null

    val headerItem = MutableLiveData<ChallengeHeaderItem>()

    /**
     * Sets up the view mode.
     *
     * @param item the item to setup
     * @param challengesListListener the challenge listener
     */
    fun setup(item: ChallengeHeaderItem, challengesListListener: ChallengeListListener) {

        this.headerItem.value = item
        this.challengesListListener = challengesListListener
    }

    fun submit() {
        challengesListListener?.onMoreClicked(headerItem.value?.title)
    }

    /**
     * Retrieves and return the header icon.
     */
    fun headerIcon() = ContextCompat.getDrawable(appContext, (headerItem.value?.iconResid ?: 0))
}
