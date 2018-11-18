package de.ka.chappted.commons.arch.base

import androidx.lifecycle.ViewModel
import android.content.Context
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

abstract class BaseItemViewModel : ViewModel(), KoinComponent{

     val appContext: Context by inject()
}
