package com.example.photoeditorpolishanything

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class NonScrollableRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        // Disable scrolling
        return false
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        // Disable scrolling
        return false
    }

    fun setRecyclerViewHeightBasedOnChildren() {
        val adapter = this.adapter ?: return
        var totalHeight = 0

        // Measure the height of each item view
        for (i in 0 until adapter.itemCount) {
            val view = adapter.createViewHolder(this, adapter.getItemViewType(i)).itemView
            view.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            measureChild(view, 0, 0)
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            val height = view.measuredHeight
            totalHeight += height
        }

        // Set the height of the RecyclerView
        val params = layoutParams
        params.height = totalHeight
        layoutParams = params
    }
}
