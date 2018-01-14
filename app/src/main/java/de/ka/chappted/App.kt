package de.ka.chappted

import android.app.Application

/**
 * A base application object for the app.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Chappted.init(this)
    }
}
