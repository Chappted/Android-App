package de.ka.chappted.main

import android.support.v7.app.AppCompatActivity
import de.ka.chappted.R
import de.ka.chappted.commons.arch.navigation.NavigationItem
import de.ka.chappted.commons.arch.navigation.Navigator
import de.ka.chappted.home.HomeFragment
import de.ka.chappted.test.TestFragment

fun mainNavItems(): MutableList<NavigationItem> {
    return mutableListOf<NavigationItem>()
            .apply {
                add(NavigationItem(HomeFragment.newInstance(), R.id.action_favorites, "home"))
                add(NavigationItem(TestFragment.newInstance(), R.id.action_schedules, "test"))
                add(NavigationItem(HomeFragment.newInstance(), R.id.action_music, "settings"))
            }
}

/**
 * A navigator for the main navigation.
 *
 * Created by Thomas Hofmann on 12.12.17.
 */
class MainNavigator : Navigator<NavigationItem>(mainNavItems()) {

    override fun navigateTo(elementTo: Any, elementFrom: Any, source: AppCompatActivity) {

        val item: NavigationItem? = {
            if (elementTo is Int) {
                navItems?.firstOrNull { it.id == elementTo }
            } else {
                null
            }
        }()

        val isAlreadyShowingThatItem = item?.tag == currentNavItem?.tag

        currentNavItem = item

        currentNavItem?.let {
            observedNavItem?.onNext(it)
        }

        if (!isAlreadyShowingThatItem
                && !source.isChangingConfigurations
                && !source.isFinishing
                && elementFrom is Int) {

            val fragmentToChangeTo = item?.fragment

            val manager = source.supportFragmentManager
            val backStackFragment = manager.findFragmentByTag(item?.tag)

            backStackFragment?.let {
                manager.popBackStackImmediate(item?.tag, 0)
                return
            }

            val fragmentTransaction = manager.beginTransaction()

            fragmentTransaction.replace(
                    elementFrom,
                    fragmentToChangeTo,
                    item?.tag)

            fragmentTransaction.addToBackStack(item?.tag)

            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    override fun navigateBack(source: AppCompatActivity) {
        val fragmentManager = source.supportFragmentManager

        val backStackCount = fragmentManager.backStackEntryCount
        if (backStackCount > 1) {
            fragmentManager.popBackStack()

            val entry = fragmentManager.getBackStackEntryAt(backStackCount - 2)

            currentNavItem = navItems?.firstOrNull { it.tag == entry.name }

            currentNavItem?.let {
                observedNavItem?.onNext(it)
            }

        } else {
            source.finish()
        }
    }
}
