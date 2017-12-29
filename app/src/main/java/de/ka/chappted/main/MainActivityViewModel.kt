package de.ka.chappted.main

import android.databinding.ObservableInt
import android.support.design.widget.BottomNavigationView
import de.ka.chappted.commons.navigation.NavigationableViewModel

/**
 * A view model for showing and manipulating the main content.
 *
 * Created by Thomas Hofmann on 01.12.17.
 *
 * @param navigationListener a listener for navigational events
 */
class MainActivityViewModel(navigationListener: NavigationableViewModel.NavigationListener) : NavigationableViewModel(navigationListener) {

    var selectedActionId: ObservableInt = ObservableInt()

    fun navigationItemSelectionListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener { item ->
            navigationListener.onNavigateTo(item.itemId)
            true
        }
    }


}