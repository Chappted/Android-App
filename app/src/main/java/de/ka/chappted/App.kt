package de.ka.chappted

import android.app.Activity
import android.app.Application
import android.os.Bundle

import de.ka.chappted.utils.ContextHelper

/**
 * A base application object for the app.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ContextHelper.init(this.applicationContext)

        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                ContextHelper.onResume(activity)
            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }
}
