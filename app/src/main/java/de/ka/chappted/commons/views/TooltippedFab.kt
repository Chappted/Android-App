package de.ka.chappted.commons.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import de.ka.chappted.R

/**
 * A custom layout holding a fab and a tooltip.
 */
class TooltippedFab : LinearLayout {

    /**
     * Creates a new tooltip fab.
     */
    constructor(context: Context)
            : super(context) {
        populate()
    }

    /**
     * Creates a new tooltip fab.
     */
    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs) {
        populate()
    }

    /**
     * Creates a new tooltip fab.
     */
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        populate()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        setFabClickListener { l }
    }

    fun setFabSize(size: Int){
        findViewById<FloatingActionButton>(R.id.fab)?.size = size
    }

    /**
     * Sets the icon of the fab.
     */
    fun setIcon(drawable: Drawable) {
        findViewById<FloatingActionButton>(R.id.fab)?.setImageDrawable(drawable)
    }

    /**
     * Sets the tooltip.
     */
    fun setTooltip(text: String) {
        findViewById<TextView>(R.id.tooltip)?.text = text
    }

    /**
     * Sets the on click listener of the tooltipped fab.
     */
    private fun setFabClickListener(listener: (View) -> (Unit)) {
        findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener(listener)
    }

    /**
     * Populates the view.
     */
    private fun populate() {
        View.inflate(context, R.layout.laylout_tooltipped_fab, this)
    }
}