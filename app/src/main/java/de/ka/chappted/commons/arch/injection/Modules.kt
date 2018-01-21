package de.ka.chappted.commons.arch.injection

import de.ka.chappted.api.Repository
import de.ka.chappted.auth.UserManager
import org.koin.dsl.module.applicationContext

val apiModule = applicationContext {
    provide { Repository() }
}

val userModule = applicationContext {
    provide { UserManager() }
}




