package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.ViewModel

/**
 * A view model for showing a no connection item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class NoConnectionItemViewModel : ViewModel() {

    var listener : ChallengeListListener? = null

    fun submit(){ listener?.onRetryClicked()}
}
