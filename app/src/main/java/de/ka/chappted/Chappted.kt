package de.ka.chappted

import de.ka.chappted.injection.ApiModule
import de.ka.chappted.injection.AppModule
import de.ka.chappted.injection.ChapptedComponent
import de.ka.chappted.injection.DaggerChapptedComponent


/**
 * A chappted singleton for component initialisation and global method handling.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
class Chappted {

    init {
        chapptedComponent.init(this)
    }

    companion object {
        lateinit var chapptedComponent: ChapptedComponent

        fun initApp(application: App) {

            chapptedComponent = DaggerChapptedComponent
                    .builder()
                    .appModule(AppModule(application))
                    .apiModule(ApiModule())
                    .build()
        }

    }

}
