package de.ka.chappted

import android.content.Context
import de.ka.chappted.commons.ViewModel

/**
 * A viewmodel for showing the main content.
 *
 * Created by Thomas Hofmann on 30.11.17.
 *
 * @param context the base context
 */
class MainFragmentViewModel(context: Context) : ViewModel(context){

    fun getText() : String = "hallo"
}
