package de.ka.chappted.commons;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * A helper for context classes.
 *
 * Created by Thomas Hofmann on 06.01.18.
 */
public class ContextHelper {

    private static ContextHelper sHelper;

    private WeakReference<Activity> mActivityReference;

    private WeakReference<Context> mApplicationContext;

    private static ContextHelper get() {
        if (sHelper == null) {
            sHelper = new ContextHelper();
        }
        return sHelper;
    }

    public static void init(Context applicationContext) {
        get().mApplicationContext = new WeakReference<>(applicationContext);
    }

    private ContextHelper() {
    }


    public static void onResume(Activity activity) {
        get().mActivityReference = new WeakReference<>(activity);
    }

    @Nullable
    public static Activity getActivityReference() {

        if (get().mActivityReference == null) {
            return null;
        }

        return get().mActivityReference.get();
    }

    @Nullable
    public static Context getAppReference() {

        if (get().mApplicationContext == null) {
            return null;
        }

        return get().mApplicationContext.get();
    }


}
