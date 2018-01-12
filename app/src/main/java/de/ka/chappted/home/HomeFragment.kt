package de.ka.chappted.home

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.databinding.FragmentHomeBinding

/**
 * The home fragment.
 */
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentHomeBinding.inflate(inflater)

        val viewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }
}
