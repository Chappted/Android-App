package de.ka.chappted.main.screens.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        // setup:
        val adapter = ChallengesAdapter(this) { challenge, viewModel ->
            viewModel.play()
            Toast.makeText(activity, "challenge " + challenge.title, Toast.LENGTH_SHORT).show()
        }

        viewModel?.let {
            it.initAdapter(adapter)
            it.loadChallenges()
        }

        return view
    }
}
