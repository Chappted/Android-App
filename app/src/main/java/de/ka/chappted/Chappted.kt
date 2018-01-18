package de.ka.chappted

import de.ka.chappted.commons.arch.injection.ApiModule
import de.ka.chappted.commons.arch.injection.AppModule
import de.ka.chappted.commons.arch.injection.ChapptedComponent
import de.ka.chappted.commons.arch.injection.DaggerChapptedComponent


/**
 * A chappted object for component initialisation and global method handling.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
object Chappted {

    lateinit var chapptedComponent: ChapptedComponent

    fun initApp(application: App) {

        chapptedComponent = DaggerChapptedComponent
                .builder()
                .appModule(AppModule(application))
                .apiModule(ApiModule())
                .build()
    }

}
