package de.ka.chappted.main

import android.support.v4.app.Fragment
import de.ka.chappted.R
import de.ka.chappted.home.HomeFragment
import de.ka.chappted.test.TestFragment

/**
 * Lists all main views.
 *
 * Created by Thomas Hofmann on 04.12.17.
 */
enum class MainViews constructor(val fragment: Fragment,
                                 val tag: String,
                                 val actionId: Int) {

    HOME(HomeFragment.newInstance(), "home", R.id.action_favorites),
    TEST(TestFragment.newInstance(), "test", R.id.action_schedules),
    SETTINGS(HomeFragment.newInstance(), "settings", R.id.action_music)

}