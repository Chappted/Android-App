package de.ka.chappted.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.support.design.widget.BottomNavigationView
import de.ka.chappted.commons.arch.base.BaseViewModel

/**
 * A view model for showing and manipulating the main content.
 *
 * Created by Thomas Hofmann on 01.12.17.
 */
class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    val selectedActionId = MutableLiveData<Int>()

    fun navigationItemSelectionListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            navigationListener?.onNavigateTo(it.itemId)
            true
        }
    }


}