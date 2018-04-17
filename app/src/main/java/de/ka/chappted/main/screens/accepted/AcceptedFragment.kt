package de.ka.chappted.main.screens.accepted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseFragment
import de.ka.chappted.databinding.FragmentAcceptedBinding
import de.ka.chappted.main.screens.accepted.items.AcceptedContentItem

/**
 * A placeholder fragment containing a simple view.
 */
class AcceptedFragment : BaseFragment<FragmentAcceptedBinding, AcceptedViewModel>(), AcceptedListListener{

    override var viewModelClass = AcceptedViewModel::class.java
    override var bindingLayoutId = R.layout.fragment_accepted

    companion object {
        fun newInstance() = AcceptedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val adapter = AcceptedAdapter(this, acceptedListListener = this)

        viewModel?.initAdapter(adapter)

        return view
    }

    override fun onChallengeClicked(acceptedContent: AcceptedContentItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetryClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}