package de.ka.chappted.commons.arch.injection

import de.ka.chapptedapi.ChapptedApi
import de.ka.chapptedapi.repository.Repository
import org.koin.dsl.module.applicationContext

val apiModule = applicationContext {
    bean { ChapptedApi.repository as Repository }
}





