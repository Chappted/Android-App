package de.ka.chappted

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.databinding.FragmentMainBinding

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    companion object {
        fun newInstance(): MainActivityFragment {
            return MainActivityFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMainBinding.inflate(inflater)

        context?.let {
            binding?.viewModel = MainFragmentViewModel(it)
        }

        return binding?.root
    }
}
