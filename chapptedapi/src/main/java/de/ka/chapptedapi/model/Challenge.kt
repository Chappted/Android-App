package de.ka.chapptedapi.model

import android.support.annotation.Keep

@Keep
class Challenge(val title: String? = "",
                val category: String? = "",
                val description: String? = "",
                val isProtected: Boolean? = false)


