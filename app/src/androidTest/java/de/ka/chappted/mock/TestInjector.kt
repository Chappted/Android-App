package de.ka.chappted.mock

import de.ka.chappted.App
import de.ka.chappted.api.repository.MainRepository
import de.ka.chappted.api.repository.Repository
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext

class TestInjector(url: String, logsEnabled: Boolean) {

    val repository = MainRepository(url, logsEnabled)

    private val testModule = applicationContext {
        bean { repository as Repository }
    }

    fun overrideInjection(app: App) {
        StandAloneContext.loadKoinModules(testModule)
    }



}
