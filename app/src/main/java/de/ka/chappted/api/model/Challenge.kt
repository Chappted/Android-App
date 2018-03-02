package de.ka.chappted.api.model

import android.support.annotation.Keep
import android.support.annotation.LayoutRes
import de.ka.chappted.R

@Keep
class Challenge(val type: Type = Type.DEFAULT, val title: String? = "")


/**
 * Defines all types of challenge adapter items.
 */
enum class Type constructor(@param:LayoutRes @field:LayoutRes
                            val layoutResId: Int) {
    NO_ITEMS(R.layout.layout_item_challenge),
    LOADING(R.layout.layout_item_loading),
    NO_CONNECTION(R.layout.layout_item_no_connection),
    NO_LOCATION_PERMISSION(R.layout.layout_item_challenge),
    DEFAULT(R.layout.layout_item_challenge),
    HEADER(R.layout.layout_item_challenge);
}

