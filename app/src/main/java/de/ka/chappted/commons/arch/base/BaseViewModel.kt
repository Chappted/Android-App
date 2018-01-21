package de.ka.chappted.commons.arch.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import de.ka.chappted.api.Repository
import de.ka.chappted.auth.UserManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * This is a base view model for all other view models used for the MVVM design pattern.
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app), KoinComponent {

    val userManager: UserManager by inject()
    val repository: Repository by inject()

    var navigationListener: NavigationListener? = null

    private var compositeDisposable: CompositeDisposable? = null

    /**
     * Subscribes the view model.
     */
    fun subscribe() {

        dispose() // needed, as the view model may somehow be reused

        compositeDisposable = CompositeDisposable()

        compositeDisposable?.let {
            userManager.userJustLoggedIn
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        onLoggedIn()
                    }
                    .addTo(it)
        }
    }

    /**
     * Listens for navigational events.
     */
    interface NavigationListener {

        /**
         * Called when a navigation request with the specified element has been fired.
         *
         * @param element the element where the next navigation should lead to, may be an id
         */
        fun onNavigateTo(element: Any?)
    }

    /**
     * Convenience method for getting informed if a user has just logged in.
     */
    open fun onLoggedIn() {
        // to be implemented by subclasses
    }

    override fun onCleared() {
        super.onCleared()

        dispose()
    }

    /**
     * Disposes all subscriptions to take care of potential memory leaks.
     */
    private fun dispose() {
        compositeDisposable?.let {
            it.clear()
            it.dispose()
            compositeDisposable = null
        }
    }
}
