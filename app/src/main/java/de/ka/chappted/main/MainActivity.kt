package de.ka.chappted.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import de.ka.chappted.R
import de.ka.chappted.databinding.ActivityMainBinding

/**
 * The main activity offering a bottom navigation.
 * Will auto switch to the first main view.
 */
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var bottomNavigation: BottomNavigationView? = null
    private var currentlyShownMainView: MainViews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel = MainActivityViewModel(this)

        binding.viewModel = viewModel

        bottomNavigation = binding.bottomNavigation
        bottomNavigation?.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            navigateTo(MainViews.HOME) // after initializing, we navigate to the first main view
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        val backStackCount = fragmentManager.backStackEntryCount
        if (backStackCount > 1) {
            fragmentManager.popBackStack()

            val entry = fragmentManager.getBackStackEntryAt(backStackCount - 2)

            currentlyShownMainView = MainViews.values().firstOrNull { it.tag == entry.name }
            showCurrentSelection()

        } else {
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var mainView: MainViews = MainViews.HOME

        when (item.itemId) {
            R.id.action_schedules -> mainView = MainViews.TEST
            R.id.action_music -> mainView = MainViews.SETTINGS
        }

        navigateTo(mainView)

        return true
    }

    /**
     * Selects the current selection optically.
     */
    private fun showCurrentSelection() {
        currentlyShownMainView?.actionId?.let {
            bottomNavigation?.selectedItemId = it
        }
    }

    /**
     * Switches the current view to the main item specified.
     *
     * @param item           the main item to switch to
     */
    private fun navigateTo(item: MainViews) {

        var isAlreadyShowingThatItem = false

        if (currentlyShownMainView == null) {
            currentlyShownMainView = item
        } else if (item.tag == currentlyShownMainView?.tag) {
            isAlreadyShowingThatItem = true
        }

        if (!isAlreadyShowingThatItem
                && !isChangingConfigurations
                && !isFinishing) {

            currentlyShownMainView = item

            val fragmentToChangeTo = item.fragment

            val manager = supportFragmentManager
            val backStackFragment = manager.findFragmentByTag(item.tag)

            if (backStackFragment != null) {
                manager.popBackStackImmediate(item.tag, 0)

                showCurrentSelection()

                return
            }

            val fragmentTransaction = manager.beginTransaction()

            fragmentTransaction.replace(
                    R.id.content,
                    fragmentToChangeTo,
                    item.tag)

            fragmentTransaction.addToBackStack(item.tag)

            fragmentTransaction.commitAllowingStateLoss()
        }
    }

}
