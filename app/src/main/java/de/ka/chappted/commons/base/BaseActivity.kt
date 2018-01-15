package de.ka.chappted.commons.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import de.ka.chappted.Chappted
import de.ka.chappted.api.Repository
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Chappted.chapptedComponent.inject(this)
    }

    override fun onBackPressed() {
        repository.stop()
        super.onBackPressed()
    }
}
