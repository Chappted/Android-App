package de.ka.chappted.main.screens.challenges

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel?.setup()
    }
}
