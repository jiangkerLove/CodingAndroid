package jfp.study.coding_android.base

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Scroller
import jfp.study.coding_android.base.LifeActivity.Companion.TAG

class LifeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {


    private val scroller: Scroller = Scroller(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(TAG, "onMeasure: $this")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.i(TAG, "onLayout: $this")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw: $this")
    }

    fun smoothScrollerTo(destX: Int, destY: Int) {
        val scrollX = scrollX
        val delta = destX - scrollX
        scroller.startScroll(scrollX, 0, delta, 0, 1000)
        invalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }
}