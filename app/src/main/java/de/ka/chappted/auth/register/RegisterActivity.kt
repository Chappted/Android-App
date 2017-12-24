package de.ka.chappted.auth.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.app.Activity
import de.ka.chappted.databinding.ActivityRegisterBinding


/**
 * Created by th on 13.12.17.
 */
class RegisterActivity : AppCompatActivity(), RegisterActivityViewModel.RegisterListener {

    private val viewModel by lazy { RegisterActivityViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.viewModel = viewModel

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
