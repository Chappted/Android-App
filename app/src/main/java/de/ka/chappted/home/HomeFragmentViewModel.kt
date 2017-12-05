package de.ka.chappted.home

import android.content.Context
import de.ka.chappted.commons.ViewModel

/**
 * A view model for showing the home content.
 *
 * Created by Thomas Hofmann on 30.11.17.
 *
 * @param context the base context
 */
class HomeFragmentViewModel(context: Context) : ViewModel(context){

    fun getText() : String = "hallo"
}
