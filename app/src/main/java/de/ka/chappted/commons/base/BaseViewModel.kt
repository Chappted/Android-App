package de.ka.chappted.commons.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import de.ka.chappted.Chappted
import de.ka.chappted.api.Repository
import javax.inject.Inject

/**
 * This is a base view model for all other view models used for the MVVM design pattern.
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    var navigationListener: NavigationListener? = null

    /**
     * Listens for navigational events.
     */
    interface NavigationListener {

        /**
         * Called when a navigation request with the specified element has been fired.
         *
         * @param element the element where the next navigation should lead to, may be an id
         */
        fun onNavigateTo(element: Any?)
    }

    @Inject lateinit var repository: Repository

    init {
        Chappted.chapptedComponent.inject(this)
    }

    fun onPause() {
        repository.stop()
    }
}
