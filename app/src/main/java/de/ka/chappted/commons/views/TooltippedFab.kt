package de.ka.chappted.commons.views

import android.content.Context
import android.graphics.drawable.Drawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import de.ka.chappted.R

/**
 * A custom layout holding a fab and a tooltip.
 */
class TooltippedFab : LinearLayout {

    private var tooltipWidth = 0

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
        populate(attrs)
    }

    /**
     * Creates a new tooltip fab.
     */
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        populate(attrs, defStyleAttr)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        setFabClickListener { l }
    }

    fun setFabSize(size: Int) {
        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)?.size = size
    }

    /**
     * Sets the icon of the fab.
     */
    fun setIcon(drawable: Drawable) {
        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)?.setImageDrawable(drawable)
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
        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)?.setOnClickListener(listener)
    }

    /**
     * Populates the view.
     */
    private fun populate(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        View.inflate(context, R.layout.layout_tooltipped_fab, this)

        attrs?.let {

            val attributes = context.obtainStyledAttributes(
                    it, R.styleable.TooltippedFab, defStyleAttr, 0)


            tooltipWidth = attributes.getDimension(R.styleable.TooltippedFab_toolTipWidth,
                    resources.getDimension(R.dimen.tooltip_default_width_normal)).toInt()

            attributes.recycle()
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {


        val tooltip = findViewById<TextView>(R.id.tooltip)

        val layoutParams = tooltip.layoutParams

        layoutParams.width = tooltipWidth

        tooltip.layoutParams = layoutParams

        super.onLayout(changed, l, t, r, b)
    }
}