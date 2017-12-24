package de.ka.chappted.test

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.databinding.FragmentTestBinding

/**
 * A placeholder fragment containing a simple view.
 */
class TestFragment : Fragment() {

    private var binding: FragmentTestBinding? = null

    companion object {
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentTestBinding.inflate(inflater)
        binding?.viewModel = TestFragmentViewModel(activity!!)


        return binding?.root
    }
}