package de.ka.chappted.commons.arch.injection

import de.ka.chappted.api.repository.MainRepository
import de.ka.chappted.api.repository.Repository
import de.ka.chappted.auth.UserManager
import org.koin.dsl.module.applicationContext

val apiModule = applicationContext {
    bean { MainRepository() as Repository}
}

val userModule = applicationContext {
    bean { UserManager() }
}




