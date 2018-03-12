package de.ka.chappted.api.model

import android.support.annotation.Keep
import android.support.annotation.LayoutRes
import de.ka.chappted.R

@Keep
class Challenge(val title: String? = "",
                val category: String? = "",
                val description: String? = "",
                val isProtected: Boolean? = false)


