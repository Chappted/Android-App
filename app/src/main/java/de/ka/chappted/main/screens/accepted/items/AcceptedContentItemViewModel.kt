package de.ka.chappted.main.screens.accepted.items

import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseItemViewModel
import de.ka.chappted.main.screens.accepted.AcceptedListListener

/**
 * A view model for showing challenge item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class AcceptedContentItemViewModel : BaseItemViewModel() {

    private var acceptedListListener: AcceptedListListener? = null

    val progressVisibility = MutableLiveData<Int>()
    val acceptedContent = MutableLiveData<AcceptedContentItem>()
    val titleDrawable = MutableLiveData<Drawable>()

    init {
        progressVisibility.value = View.GONE
    }

    /**
     * Sets up the view model.
     * @param content the content of the  challenge
     * @param challengesListListener the listener
     */
    fun setup(content: AcceptedContentItem, acceptedListListener: AcceptedListListener) {

        this.acceptedContent.value = content
        this.acceptedListListener = acceptedListListener

        if (content.challenge.isProtected == true) {
            titleDrawable.value = appContext.getDrawable(R.drawable.ic_lock)
        }
    }

    fun onChallengeClick() = acceptedContent.value?.let {
        play()
        acceptedListListener?.onChallengeClicked(it)
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
