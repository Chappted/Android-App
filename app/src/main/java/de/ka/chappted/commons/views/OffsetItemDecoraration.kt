package de.ka.chappted.commons.views

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Creates a new item decoration for items of a recyclerview.
 */
class OffsetItemDecoration(val leftOffset: Int = 0,
                               val topOffset: Int = 0,
                               val rightOffset: Int = 0,
                               val bottomOffset: Int = 0) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: androidx.recyclerview.widget.RecyclerView,
                                state: androidx.recyclerview.widget.RecyclerView.State) {
        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset)
    }

}