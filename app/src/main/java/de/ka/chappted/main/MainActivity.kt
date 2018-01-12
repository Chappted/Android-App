package de.ka.chappted.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.ka.chappted.R
import de.ka.chappted.commons.navigation.NavigationableViewModel
import de.ka.chappted.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


/**
 * The main activity offering a bottom navigation.
 * Will auto switch to the first main view.
 */
class MainActivity : AppCompatActivity(),
        NavigationableViewModel.NavigationListener{

    private val navigator by lazy { MainNavigator() }
    private val compositeDisposable by lazy { CompositeDisposable() }

    private var viewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //OAuthUtils.instance.peekOAuthToken() // TODO this will open up register/login if none is available

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel?.navigationListener = this

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

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
