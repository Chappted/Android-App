package de.ka.chappted.main.screens.accepted

import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment

/**
 * A placeholder fragment containing a simple view.
 */
class AcceptedFragment : BaseFragment<AcceptedFragmentViewModel>() {

    override var viewModelClass = AcceptedFragmentViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_test

    companion object {
        fun newInstance() = AcceptedFragment()
    }


}