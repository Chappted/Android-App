package de.ka.chappted.commons.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import de.ka.chappted.Chappted
import de.ka.chappted.api.Repository
import javax.inject.Inject

/**
 * This is a base view model for all other viewmodels used for the MVVM design pattern.
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
abstract class BaseViewModel(applicationContext: Application) : AndroidViewModel(applicationContext){

    @Inject lateinit var repository: Repository

    init {
        Chappted.chapptedComponent.inject(this)
    }
}
