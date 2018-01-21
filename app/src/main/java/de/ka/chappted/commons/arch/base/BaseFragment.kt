package de.ka.chappted.commons.arch.base

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.BR

/**
 * A base fragment. A base fragment is always combined with a view model.
 *
 * Created by Thomas Hofmann on 27.12.17.
 */
abstract class BaseFragment<E : BaseViewModel> : Fragment() {

    abstract var viewModelClass: Class<E>
    abstract var bindingLayoutId: Int

    private var binding: ViewDataBinding? = null

    val viewModel: E? by lazy { ViewModelProviders.of(this).get(viewModelClass) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, bindingLayoutId, null, true)
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.setLifecycleOwner(this)

        viewModel?.subscribe()

        return binding?.root
    }
}
