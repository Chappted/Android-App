package de.ka.chappted.commons.arch.injection

import de.ka.chappted.api.Repository
import org.koin.dsl.module.applicationContext

class Repository

val apiModule = applicationContext {
    provide { Repository() }
}




