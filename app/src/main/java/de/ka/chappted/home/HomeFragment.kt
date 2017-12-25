package de.ka.chappted.home

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

    private var binding: FragmentHomeBinding? = null

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentHomeBinding.inflate(inflater)
        binding?.viewModel = HomeFragmentViewModel(activity?.baseContext)

        return binding?.root
    }
}
