package de.ka.chappted

import com.squareup.leakcanary.LeakCanary
import de.ka.chappted.commons.arch.injection.apiModule
import de.ka.chappted.commons.arch.injection.userModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * The main initialization point for the chappted application.
 */
object Chappted {

    fun initApp(app: App) {

        app.startKoin(app, listOf(apiModule, userModule))

        if (!LeakCanary.isInAnalyzerProcess(app)) {
            LeakCanary.install(app)
        }

        if (BuildConfig.LOGS_ENABLED) {
            Timber.plant(DebugTree())
        } else {
            //TODO add a crash reporting tree for reporting tools: Timber.plant(CrashReportingTree())
        }
    }
}