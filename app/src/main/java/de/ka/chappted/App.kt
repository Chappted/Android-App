package de.ka.chappted

import android.app.Application
import android.os.Build
import com.squareup.leakcanary.LeakCanary
import de.ka.chappted.commons.arch.injection.apiModule
import de.ka.chapptedapi.ChapptedApi
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 * A base application for the chappted app.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ChapptedApi.init()

        startKoin(this, listOf(apiModule))

        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }

        if (BuildConfig.LOGS_ENABLED) {
            Timber.plant(Timber.DebugTree())
        } else {
            //TODO add a crash reporting tree for reporting tools: Timber.plant(CrashReportingTree())
        }
    }
}
