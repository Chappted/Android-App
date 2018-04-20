package de.ka.chappted.main.screens.accepted.items

import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import de.ka.chappted.api.model.Challenge

open class AcceptedItem(@param:LayoutRes @field:LayoutRes val layoutResId: Int)

data class AcceptedHeaderItem(val layout: Int,
                               val title: String,
                               @param:DrawableRes @field:DrawableRes val iconResId: Int) : AcceptedItem(layout)

data class AcceptedContentItem(val layout: Int,
                                val challenge: Challenge) : AcceptedItem(layout)

data class AcceptedNoConnectionItem(val layout: Int) : AcceptedItem(layout)

data class AcceptedLoadingItem(val layout: Int) : AcceptedItem(layout)

data class AcceptedFooterItem(val layout: Int) : AcceptedItem(layout)