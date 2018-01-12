package de.ka.chappted.auth.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import de.ka.chappted.api.Repository
import de.ka.chappted.databinding.ActivityRegisterBinding

/**
 * Created by Thomas Hofmann on 13.12.17.
 */
class RegisterActivity : AppCompatActivity(), RegisterActivityViewModel.RegisterListener {

    var viewModel: RegisterActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(RegisterActivityViewModel::class.java)
        viewModel?.listener = this

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
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
        Repository.instance.stop()
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }
}
