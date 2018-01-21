package de.ka.chappted.test

import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment

/**
 * A placeholder fragment containing a simple view.
 */
class TestFragment : BaseFragment<TestFragmentViewModel>() {

    override var viewModelClass = TestFragmentViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_test

    companion object {
        fun newInstance() = TestFragment()
    }


}