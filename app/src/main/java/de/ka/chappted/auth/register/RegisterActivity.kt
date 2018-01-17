package de.ka.chappted.auth.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import de.ka.chappted.R
import de.ka.chappted.api.Repository
import de.ka.chappted.auth.login.LoginActivityViewModel
import de.ka.chappted.commons.base.BaseActivity
import de.ka.chappted.databinding.ActivityRegisterBinding

/**
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
