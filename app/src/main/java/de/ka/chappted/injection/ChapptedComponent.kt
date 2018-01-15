package de.ka.chappted.injection

import dagger.Component
import de.ka.chappted.App
import de.ka.chappted.Chappted
import de.ka.chappted.auth.Authenticator
import de.ka.chappted.commons.base.BaseActivity
import de.ka.chappted.commons.base.BaseFragment
import de.ka.chappted.commons.base.BaseViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ApiModule::class)])
interface ChapptedComponent {


    fun inject(app: App)

    fun inject(chappted: Chappted)

    fun inject(fragment: BaseFragment)

    fun inject(activity: BaseActivity)

    fun inject(viewModel: BaseViewModel)

    fun inject(authenticator: Authenticator)
}
