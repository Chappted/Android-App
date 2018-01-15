package de.ka.chappted

import android.app.Activity
import android.app.Application
import android.os.Bundle
import de.ka.chappted.api.Repository
import de.ka.chappted.injection.ApiModule
import de.ka.chappted.injection.AppModule
import de.ka.chappted.injection.ChapptedComponent
import de.ka.chappted.injection.DaggerChapptedComponent
import java.lang.ref.WeakReference
import javax.inject.Inject


/**
 * A chappted singleton for component initialisation and global method handling.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
class Chappted {

    @Inject
    lateinit var app: App

    @Inject
    lateinit var repository: Repository

    fun initApp(application: App) {

        application.registerActivityLifecycleCallbacks(
                object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {

                    }

                    override fun onActivityStarted(activity: Activity) {

                    }

                    override fun onActivityResumed(activity: Activity) {
                        resumedActivity = null
                        resumedActivity = WeakReference(activity)
                    }

                    override fun onActivityPaused(activity: Activity) {

                    }

                    override fun onActivityStopped(activity: Activity) {

                    }

                    override fun onActivitySaveInstanceState(activity: Activity,
                                                             bundle: Bundle?) {

                    }

                    override fun onActivityDestroyed(activity: Activity) {

                    }
                })


        chapptedComponent = DaggerChapptedComponent
                .builder()
                .appModule(AppModule(application))
                .apiModule(ApiModule())
                .build()

        chapptedComponent.inject(this)

        appRef = app
        repositoryRef = repository

    }

    companion object {
        lateinit var chapptedComponent: ChapptedComponent

        var resumedActivity: WeakReference<Activity>? = null
        var appRef: App? = null

        var repositoryRef: Repository? = null



    }

}
