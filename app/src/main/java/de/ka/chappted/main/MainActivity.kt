package de.ka.chappted.main

import android.os.Bundle
import de.ka.chappted.R
import de.ka.chappted.commons.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

/**
 * The main activity offering a bottom navigation.
 * Will auto switch to the first main view.
 */
class MainActivity : BaseActivity<MainActivityViewModel>() {

    override var viewModelClass = MainActivityViewModel::class.java
    override var bindingLayoutId = R.layout.activity_main

    private val navigator by lazy { MainNavigator() }
    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //OAuthUtils.instance.peekOAuthToken(this) // TODO this will open up register/login if none is available

        if (savedInstanceState == null) {
            navigator.navigateTo(R.id.action_favorites, R.id.content, this)
        }

        navigator.observedNavItem?.subscribe { item ->
            item.id?.let { viewModel?.selectedActionId?.postValue(item.id) }
        }?.addTo(compositeDisposable)
    }

    override fun onNavigateTo(element: Any?) {
        if (element is Int) {
            navigator.navigateTo(element, R.id.content, this)
        }
    }

    override fun onBackPressed() {
        navigator.navigateBack(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}
