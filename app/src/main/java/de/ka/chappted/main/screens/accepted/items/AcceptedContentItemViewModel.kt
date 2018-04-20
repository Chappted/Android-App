package de.ka.chappted.main.screens.accepted.items

import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.v4.content.ContextCompat
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
    val categoryImage = MutableLiveData<Drawable>()

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

        categoryImage.value = when (content.challenge.category) {

            appContext.getString(R.string.challenge_category_fifa)
            -> ContextCompat.getDrawable(appContext, R.drawable.ic_soccer)
            appContext.getString(R.string.challenge_category_mario_kart)
            -> ContextCompat.getDrawable(appContext, R.drawable.ic_video_game)
            appContext.getString(R.string.challenge_category_table_tennis)
            -> ContextCompat.getDrawable(appContext, R.drawable.ic_table_tennis)

            else -> {
                ContextCompat.getDrawable(appContext, R.drawable.ic_video_game)
            }
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
