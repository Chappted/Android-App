package de.ka.chappted.commons.arch.base

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.ka.chappted.BR

/**
 * A base fragment. A base fragment is always combined with a view model.
 *
 * Created by Thomas Hofmann.
 */
abstract class BaseFragment<out T: ViewDataBinding, E : BaseViewModel> : Fragment() {

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

        retainInstance = true

        binding?.executePendingBindings()

        return binding?.root
    }

    /**
     * Retrieves the view binding of the fragment. May only be useful after [onCreateView].
     */
    @Suppress("UNCHECKED_CAST")
    fun getBinding() = binding as? T
}
