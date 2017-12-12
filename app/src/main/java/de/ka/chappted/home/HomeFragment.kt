package de.ka.chappted.home

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.databinding.FragmentMainBinding

/**
 * The home fragment.
 */
class HomeFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMainBinding.inflate(inflater)
        binding?.viewModel = HomeFragmentViewModel()

        return binding?.root
    }
}
