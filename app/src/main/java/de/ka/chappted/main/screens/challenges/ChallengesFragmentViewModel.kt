package de.ka.chappted.main.screens.challenges

import android.app.Application
import de.ka.chappted.auth.OAuthUtils
import de.ka.chappted.commons.arch.base.BaseViewModel

/**
 * A view model for showing the home content.
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
class ChallengesFragmentViewModel(application: Application) : BaseViewModel(application) {

    fun getText() = OAuthUtils.getOAuthAccountName(getApplication())

    fun onSubmit() = OAuthUtils.deleteOAuthAccount(getApplication())

}
