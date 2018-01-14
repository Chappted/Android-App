package de.ka.chappted.commons.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

/**
 * This is a base view model for all other viewmodels used for the MVVM design pattern.
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
abstract class BaseViewModel(applicationContext: Application) : AndroidViewModel(applicationContext)
