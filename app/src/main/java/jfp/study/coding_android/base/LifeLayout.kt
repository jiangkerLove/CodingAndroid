package jfp.study.coding_android.base

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout

class LifeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.i(LifeActivity.TAG, "onLayout: $this")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(LifeActivity.TAG, "onMeasure: $this")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(LifeActivity.TAG, "onDraw: $this")
    }
}