package de.ka.chappted.main.screens.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment
import de.ka.chappted.databinding.FragmentChallengesBinding
import de.ka.chappted.main.screens.challenges.items.ChallengeContentItem

/**
 * The home fragment.
 */
class ChallengesFragment : BaseFragment<FragmentChallengesBinding, ChallengesViewModel>(),
        ChallengeListListener {

    override var viewModelClass = ChallengesViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_challenges

    companion object {
        fun newInstance() = ChallengesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = super.onCreateView(inflater, container, savedInstanceState)

        val adapter = ChallengesAdapter(this, challengesListListener = this)

        viewModel?.let {
            it.initAdapter(adapter)
        }

        return view
    }

    override fun onChallengeClicked(challengeContent: ChallengeContentItem) {
        Toast.makeText(activity, "challenge " + challengeContent.challenge.title, Toast.LENGTH_SHORT).show()

    }

    override fun onRetryClicked() {
        Toast.makeText(activity, "retry clicked ", Toast.LENGTH_SHORT).show()

    }

    override fun onMoreClicked(category: String?) {
        Toast.makeText(activity, "more clicked " + category, Toast.LENGTH_SHORT).show()
    }
}
