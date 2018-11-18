package de.ka.chappted.main.screens.accepted.items

import androidx.lifecycle.MutableLiveData
import androidx.core.content.ContextCompat
import de.ka.chappted.commons.arch.base.BaseItemViewModel
import de.ka.chappted.main.screens.accepted.AcceptedListListener

/**
 * A view model for showing a header item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class AcceptedHeaderItemViewModel : BaseItemViewModel() {

    private var acceptedListListener: AcceptedListListener? = null

    val headerItem = MutableLiveData<AcceptedHeaderItem>()

    /**
     * Sets up the view mode.
     *
     * @param item the item to setup
     * @param challengesListListener the challenge listener
     */
    fun setup(item: AcceptedHeaderItem, acceptedListListener: AcceptedListListener) {

        this.headerItem.value = item
        this.acceptedListListener = acceptedListListener
    }

    /**
     * Retrieves and return the header icon.
     */
    fun headerIcon() = ContextCompat.getDrawable(appContext, (headerItem.value?.iconResId ?: 0))
}
