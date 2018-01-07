package de.ka.chappted.utils

import android.app.Activity
import android.content.Context

import java.lang.ref.WeakReference

/**
 * A helper for context classes.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
class ContextHelper private constructor() {

    private var activityReference: WeakReference<Activity?>? = null
    private var applicationContext: WeakReference<Context?>? = null

    private object Holder {
        val INSTANCE = ContextHelper()
    }

    companion object {

        val instance: ContextHelper by lazy { Holder.INSTANCE }

        fun init(applicationContext: Context?) {
            instance.applicationContext = WeakReference(applicationContext)
        }

        fun onResume(activity: Activity?) {
            instance.activityReference = WeakReference(activity)
        }

        /**
         * Retrieves the activity reference, if possible. This might not always be the case!
         * instance
         */
        val activityReference: Activity?
            get() = if (instance.activityReference == null) {
                null
            } else instance.activityReference!!.get()

        /**
         * Retrieves the app context reference, if possible. This might not always be the case!
         * Should not be used if it is mandatory to retrieve a context in all cases.
         */
        val appReference: Context?
            get() = if (instance.applicationContext == null) {
                null
            } else instance.applicationContext!!.get()
    }


}
