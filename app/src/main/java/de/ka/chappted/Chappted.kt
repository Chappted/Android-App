package de.ka.chappted

import de.ka.chappted.commons.arch.injection.apiModule
import org.koin.Koin
import org.koin.android.ext.koin.init

object Chappted {

    fun initApp(app: App) {
        Koin().init(app).build(listOf(apiModule))
    }
}