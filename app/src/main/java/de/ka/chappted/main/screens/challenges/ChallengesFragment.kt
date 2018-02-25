package de.ka.chappted.main.screens.challenges

import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment

/**
 * The home fragment.
 */
class ChallengesFragment : BaseFragment<ChallengesFragmentViewModel>() {

    override var viewModelClass = ChallengesFragmentViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_home

    companion object {
        fun newInstance() = ChallengesFragment()
    }
}
