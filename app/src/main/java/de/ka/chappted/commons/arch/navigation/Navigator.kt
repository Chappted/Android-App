package de.ka.chappted.commons.arch.navigation

import android.content.Intent
import android.support.v7.app.AppCompatActivity

import io.reactivex.subjects.PublishSubject

/**
 * A navigator with navigation items and navigational functions.
 *
 * Created by Thomas Hofmann on 12.12.17.
 */
abstract class Navigator<E : NavigationItem?>(var navItems: MutableList<E>?) {

    open var observedNavItem: PublishSubject<E>? = PublishSubject.create()

    var currentNavItem: E? = null

    /**
     * Navigates to the specified element.
     *
     * @param elementTo the element to navigate to, may be an id
     * @param elementFrom the element to to navigate from, may be a resource id
     * @param source the source activity
     */
    open fun navigateTo(elementTo: Any, elementFrom: Any, source: AppCompatActivity) {
    }

    /**
     * Navigates back.
     *
     * @param source the source activity
     */
    open fun navigateBack(source: AppCompatActivity) {}

    /**
     * Opens up a target activity. May close the source, if wanted.
     */
    open fun <T> open(source: AppCompatActivity, target: Class<T>, closeSource: Boolean = false) {
        val intent = Intent(source, target)
        source.startActivity(intent)

        if (closeSource) {
            source.finish()
        }

        source.startActivity(intent)

    }

    /**
     * Releases the navigator for freeing up memory. Prevents leaking of navigational items.
     */
    fun dispose() {
        currentNavItem = null
        observedNavItem = null
        navItems = null
    }
}

