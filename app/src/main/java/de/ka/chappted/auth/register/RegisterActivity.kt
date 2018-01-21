package de.ka.chappted.auth.register

import android.os.Bundle
import android.content.Intent
import android.app.Activity
import de.ka.chappted.R
import de.ka.chappted.commons.arch.base.BaseActivity

/**
 * A registration activity for the authorization flow.
 *
 * Created by Thomas Hofmann on 13.12.17.
 */
class RegisterActivity : BaseActivity<RegisterActivityViewModel>(),
        RegisterActivityViewModel.RegisterListener {

    override var viewModelClass = RegisterActivityViewModel::class.java
    override var bindingLayoutId = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel?.listener = this
    }

    override fun onRegistered(registerIntent: Intent) {
        setResult(Activity.RESULT_OK, registerIntent)
        finish()
    }

    override fun onRegisterCancelled() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }
}
