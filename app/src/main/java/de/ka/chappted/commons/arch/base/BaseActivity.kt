package de.ka.chappted.commons.arch.base

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.ka.chappted.BR

/**
 * A base activity using a view model.
 */
abstract class BaseActivity<E : BaseViewModel>
    : AppCompatActivity(), BaseViewModel.NavigationListener {

    abstract var viewModelClass: Class<E>
    abstract var bindingLayoutId: Int

    private var binding: ViewDataBinding? = null

    val viewModel: E? by lazy { ViewModelProviders.of(this).get(viewModelClass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel?.navigationListener = this

        binding = DataBindingUtil.inflate(layoutInflater, bindingLayoutId, null, true)
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.setLifecycleOwner(this)

        viewModel?.subscribe()

        setContentView(binding?.root)
    }

    override fun onNavigateTo(element: Any?) {
        // to be implemented by subclasses
    }
}
