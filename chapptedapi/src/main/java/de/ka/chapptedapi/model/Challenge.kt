package de.ka.chapptedapi.model

import android.support.annotation.Keep

@Keep
class Challenge(val title: String? = "",
                val category: String? = "",
                val description: String? = "",
                val userRank: String? = "",
                val challengeLeader: String? = "",
                val challengeEndDate: String? = null,
                val isProtected: Boolean? = false)


