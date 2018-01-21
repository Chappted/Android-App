package de.ka.chappted.mock

import de.ka.chappted.App
import de.ka.chappted.api.Repository
import de.ka.chappted.commons.arch.injection.userModule
import org.koin.Koin
import org.koin.android.ext.koin.init
import org.koin.dsl.module.applicationContext

class TestInjector(url: String, logsEnabled: Boolean) {

    val repository = Repository(url, logsEnabled)

    private val testModule = applicationContext {
        provide { repository }
    }

    fun overrideInjection(app: App) {
        Koin().init(app).build(listOf(testModule, userModule))
    }



}
