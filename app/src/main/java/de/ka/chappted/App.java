package de.ka.chappted;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import de.ka.chappted.commons.ContextHelper;

/**
 * A base application object for the app.
 * <p>
 * Created by Thomas Hofmann on 06.01.18.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ContextHelper.init(this.getApplicationContext());

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                ContextHelper.onResume(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
