package de.ka.chappted

import de.ka.chapptedapi.repository.ChapptedRepository
import de.ka.chapptedapi.repository.Repository
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext

class TestInjector(url: String, logsEnabled: Boolean) {

    private val testModule = applicationContext {
        bean { ChapptedRepository(url, logsEnabled) as Repository }
    }

    fun overrideInjection(app: App) {
        StandAloneContext.loadKoinModules(testModule)
    }



}
