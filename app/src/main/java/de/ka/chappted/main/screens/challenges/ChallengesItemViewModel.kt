package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Handler
import android.view.View
import de.ka.chappted.api.model.Challenge

/**
 * A view model for showing challenge item content.
 *
 * Created by Thomas Hofmann on 28.02.18.
 */
class ChallengesItemViewModel : ViewModel() {

    var challenge: Challenge? = null

    val progressVisibility = ObservableInt(View.GONE)

    /**
     * Plays a fancy animation for a short period of time.
     */
    fun play() {

        progressVisibility.set(View.VISIBLE)

        Handler().postDelayed({
            progressVisibility.set(View.GONE)
        }, 4_500)
    }
}
