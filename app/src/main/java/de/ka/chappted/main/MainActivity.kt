package de.ka.chappted.main

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseActivity
import de.ka.chappted.databinding.ActivityMainBinding

/**
 * The main activity offering a bottom navigation.
 * Will auto switch to the first main view.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(),
        androidx.viewpager.widget.ViewPager.OnPageChangeListener {

    override var viewModelClass = MainActivityViewModel::class.java
    override var bindingLayoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBinding()?.apply {
            viewPager.addOnPageChangeListener(this@MainActivity)
            viewPager.adapter = MainPagesAdapter(supportFragmentManager, applicationContext)
            viewPager.offscreenPageLimit = 2
            tabLayout.setupWithViewPager(getBinding()?.viewPager)
        }
    }

    override fun onPageSelected(position: Int) {
        // may be needed in the future
    }

    override fun onPageScrollStateChanged(state: Int) {
        // not needed
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // not needed
    }
}
