package de.ka.chappted.commons.views

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Creates a new item decoration for items of a recyclerview.
 */
class OffsetItemDecoration(val leftOffset: Int = 0,
                               val topOffset: Int = 0,
                               val rightOffset: Int = 0,
                               val bottomOffset: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset)
    }

}