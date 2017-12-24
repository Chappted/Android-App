package de.ka.chappted.home

import android.view.View
import de.ka.chappted.commons.base.BaseViewModel

/**
 * A view model for showing the home content.
 *
 * Created by Thomas Hofmann on 30.11.17.
 *
 * @param context the base context
 */
class HomeFragmentViewModel : BaseViewModel(){

    fun getText() : String = "hallo"

    fun onSubmit(): View.OnClickListener {
        return View.OnClickListener {}
    }
}
