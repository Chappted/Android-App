package de.ka.chappted

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

import java.lang.ref.WeakReference

/**
 * A chappted singleton for component initialisation and global method handling.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
class Chappted private constructor() {

    private var resumedActivity: WeakReference<Activity?>? = null
    private var application: WeakReference<Application?>? = null

    private object Holder {
        val INSTANCE = Chappted()
    }

    companion object {

        val instance: Chappted by lazy { Holder.INSTANCE }

        /**
         * The initialization routine. Initializes context references.
         */
        fun init(application: Application) {
            instance.application = null
            instance.application = WeakReference(application)

            application.registerActivityLifecycleCallbacks(
                    object : Application.ActivityLifecycleCallbacks {
                        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {

                        }

                        override fun onActivityStarted(activity: Activity) {

                        }

                        override fun onActivityResumed(activity: Activity) {
                            instance.resumedActivity = null
                            instance.resumedActivity = WeakReference(activity)
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
        }

        /**
         * Retrieves the activity reference, if possible. This might not always be the case!
         * instance
         */
        val resumedActivity: Activity?
            get() = instance.resumedActivity?.get()

        /**
         * Retrieves the app context reference, if possible. This might not always be the case!
         * Should not be used if it is mandatory to retrieve a context in all cases.
         */
        val app: Application?
            get() = instance.application?.get()
    }

}
