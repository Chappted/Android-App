package de.ka.chappted.home

import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment

/**
 * The home fragment.
 */
class HomeFragment : BaseFragment<HomeFragmentViewModel>() {

    override var viewModelClass = HomeFragmentViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_home

    companion object {
        fun newInstance() = HomeFragment()
    }
}
