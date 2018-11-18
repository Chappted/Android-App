package de.ka.chappted.commons.arch.navigation

import androidx.fragment.app.Fragment

/**
 * A navigation item containing a view, id and a tag.
 *
 * Created by Thomas Hofmann on 12.12.17.
 */
data class NavigationItem(val fragment: androidx.fragment.app.Fragment?, val id: Int?, val tag: String?)
