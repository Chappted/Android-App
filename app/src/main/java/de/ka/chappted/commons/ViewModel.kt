package de.ka.chappted.commons

import android.content.Context
import android.databinding.BaseObservable

/**
 * This is a base view model for all other viewmodels used for the MVVM design pattern.
 *
 *
 * Created by Thomas Hofmann on 30.11.17.
 */
abstract class ViewModel(val context: Context) : BaseObservable()
