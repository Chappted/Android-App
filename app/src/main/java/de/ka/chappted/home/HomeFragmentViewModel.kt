package de.ka.chappted.home

import android.app.Application
import de.ka.chappted.auth.OAuthUtils
import de.ka.chappted.commons.base.BaseViewModel
import javax.inject.Inject

/**
 * A view model for showing the home content.
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
class HomeFragmentViewModel(application: Application) : BaseViewModel(application) {

    fun getText() = OAuthUtils.getOAuthAccountName(getApplication())

    fun onSubmit() = OAuthUtils.deleteOAuthAccount(getApplication())

}
