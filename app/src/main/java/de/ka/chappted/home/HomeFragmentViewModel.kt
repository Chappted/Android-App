package de.ka.chappted.home

import android.content.Context
import android.view.View
import de.ka.chappted.auth.OAuthUtils
import de.ka.chappted.commons.base.BaseViewModel

/**
 * A view model for showing the home content.
 *
 * Created by Thomas Hofmann on 30.11.17.
 *
 * @param context the base context
 */
class HomeFragmentViewModel(private val context: Context?) : BaseViewModel(){

    fun getText() = OAuthUtils.instance.getOAuthAccountName(context!!)


    fun onSubmit(): View.OnClickListener {
        return View.OnClickListener {
            OAuthUtils.instance.deleteOAuthAccount(context!!)
        }
    }
}
