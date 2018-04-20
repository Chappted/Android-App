package de.ka.chappted.main.screens.challenges.items

import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import de.ka.chapptedapi.model.Challenge

open class ChallengeItem(@param:LayoutRes @field:LayoutRes val layoutResId: Int)

data class ChallengeHeaderItem(val layout: Int,
                               val title: String,
                               @param:DrawableRes @field:DrawableRes val iconResid: Int) : ChallengeItem(layout)

data class ChallengeContentItem(val layout: Int,
                                val challenge: Challenge) : ChallengeItem(layout)

data class ChallengeNoConnectionItem(val layout: Int) : ChallengeItem(layout)

data class ChallengeLoadingItem(val layout: Int) : ChallengeItem(layout)

data class ChallengeFooterItem(val layout: Int) : ChallengeItem(layout)
