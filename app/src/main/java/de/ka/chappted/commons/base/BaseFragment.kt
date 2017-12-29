package de.ka.chappted.commons.base

import android.support.v4.app.Fragment

import de.ka.chappted.api.Repository

/**
 * A base fragment.
 *
 * Created by Thomas Hofmann on 27.12.17.
 */
open class BaseFragment : Fragment() {

    private var stopRepository: Boolean = false

    fun enableAutoRepositoryStopping(stopRepository: Boolean) {
        this.stopRepository = stopRepository
    }

    override fun onPause() {
        if (stopRepository) {
            Repository.instance.stop()
        }
        super.onPause()
    }
}
