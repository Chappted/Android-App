package de.ka.chappted.main.screens.challenges

import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment
import de.ka.chappted.databinding.FragmentChallengesBinding

/**
 * The home fragment.
 */
class ChallengesFragment : BaseFragment<FragmentChallengesBinding, ChallengesFragmentViewModel>() {

    override var viewModelClass = ChallengesFragmentViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_challenges

    companion object {
        fun newInstance() = ChallengesFragment()
    }
}
