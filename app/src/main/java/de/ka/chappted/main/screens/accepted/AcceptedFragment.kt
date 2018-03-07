package de.ka.chappted.main.screens.accepted

import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment
import de.ka.chappted.databinding.FragmentAcceptedBinding

/**
 * A placeholder fragment containing a simple view.
 */
class AcceptedFragment : BaseFragment<FragmentAcceptedBinding, AcceptedFragmentViewModel>() {

    override var viewModelClass = AcceptedFragmentViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_accepted

    companion object {
        fun newInstance() = AcceptedFragment()
    }


}