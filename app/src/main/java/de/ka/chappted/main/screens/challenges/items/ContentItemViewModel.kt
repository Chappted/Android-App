package de.ka.chappted.main.screens.challenges.items

import androidx.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseItemViewModel
import de.ka.chappted.main.screens.challenges.ChallengeListListener

/**
 * A view model for showing challenge item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class ContentItemViewModel : BaseItemViewModel() {

    private var challengesListListener: ChallengeListListener? = null

    val progressVisibility = MutableLiveData<Int>()
    val challengeContent = MutableLiveData<ChallengeContentItem>()
    val titleDrawable = MutableLiveData<Drawable>()

    init {
        progressVisibility.value = View.GONE
    }

    /**
     * Sets up the view model.
     * @param content the content of the  challenge
     * @param challengesListListener the listener
     */
    fun setup(content: ChallengeContentItem, challengesListListener: ChallengeListListener) {

        this.challengeContent.value = content
        this.challengesListListener = challengesListListener

        if (content.challenge.isProtected == true) {
            titleDrawable.value = appContext.getDrawable(R.drawable.ic_lock)
        }
    }

    fun onChallengeClick() = challengeContent.value?.let {
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
