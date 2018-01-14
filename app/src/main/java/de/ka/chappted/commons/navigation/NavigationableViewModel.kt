package de.ka.chappted.commons.navigation

import android.app.Application
import de.ka.chappted.commons.base.BaseViewModel

/**
 * This is a navigational view model for all other viewmodels used for the MVVM design pattern.
 *
 * Created by Thomas Hofmann on 30.11.17.
 *
 * @param navigationListener a listener for navigational events
 */
abstract class NavigationableViewModel(application: Application) : BaseViewModel(application) {

    var navigationListener: NavigationListener? = null

    /**
     * Listens for navigational events.
     */
    interface NavigationListener {

        /**
         * Called when a navigation request with the specified id has been fired.
         *
         * @param element the element where the next navigation should lead to, may be an id
         */
        fun onNavigateTo(element: Any?)
    }

}
