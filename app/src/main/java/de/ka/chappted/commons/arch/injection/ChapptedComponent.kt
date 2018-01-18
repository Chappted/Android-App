package de.ka.chappted.commons.arch.injection

import dagger.Component
import de.ka.chappted.Chappted
import de.ka.chappted.api.AuthInterceptor
import de.ka.chappted.api.OAuthAuthenticator
import de.ka.chappted.auth.Authenticator
import de.ka.chappted.commons.arch.base.BaseViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ApiModule::class)])
interface ChapptedComponent {

    fun inject(viewModel: BaseViewModel)

    fun inject(authenticator: Authenticator)

    fun inject(interceptor: AuthInterceptor)

    fun inject(oAuthAuthenticator: OAuthAuthenticator)
}
