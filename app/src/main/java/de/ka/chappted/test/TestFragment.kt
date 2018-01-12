package de.ka.chappted.test

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.commons.base.BaseFragment
import de.ka.chappted.databinding.FragmentTestBinding

/**
 * A placeholder fragment containing a simple view.
 */
class TestFragment : BaseFragment() {

    private var binding: FragmentTestBinding? = null

    companion object {
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        enableAutoRepositoryStopping(true)

        binding = FragmentTestBinding.inflate(inflater)

        val viewModel = ViewModelProviders.of(this).get(TestFragmentViewModel::class.java)

        binding?.viewModel = viewModel
        binding?.setLifecycleOwner(this)

        return binding?.root
    }
}