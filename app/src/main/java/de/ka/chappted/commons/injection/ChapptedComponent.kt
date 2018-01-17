package de.ka.chappted.injection

import dagger.Component
import de.ka.chappted.App
import de.ka.chappted.Chappted
import de.ka.chappted.api.AuthInterceptor
import de.ka.chappted.api.OAuthAuthenticator
import de.ka.chappted.auth.Authenticator
import de.ka.chappted.commons.base.BaseActivity
import de.ka.chappted.commons.base.BaseFragment
import de.ka.chappted.commons.base.BaseViewModel
import okhttp3.Interceptor
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ApiModule::class)])
interface ChapptedComponent {

    fun init(chappted: Chappted)

    fun inject(viewModel: BaseViewModel)

    fun inject(authenticator: Authenticator)

    fun inject(interceptor: AuthInterceptor)

    fun inject(oAuthAuthenticator: OAuthAuthenticator)
}
