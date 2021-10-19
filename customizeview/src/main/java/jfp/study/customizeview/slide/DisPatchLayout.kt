package jfp.study.customizeview.slide

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.children

class DisPatchLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    init {
        setWillNotDraw(false)
    }

    companion object {
        const val TAG = "DisPatchLayout"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        // 如果没有子元素设置宽高为0
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
            return
        }
        // 测量子元素
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            val childOne = getChildAt(0)
            val width = childOne.measuredWidth
            val height = childOne.measuredHeight
            setMeasuredDimension(childCount * width, height)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            val childOne = getChildAt(0)
            val width = childOne.measuredWidth
            setMeasuredDimension(childCount * width, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            val childOne = getChildAt(0)
            val height = childOne.measuredHeight
            setMeasuredDimension(widthSize, height)
        } else {
            setMeasuredDimension(widthSize, heightSize)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        children.forEach {
            it.layout(0, 0, it.measuredWidth, it.measuredHeight)
        }
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG, "onInterceptTouchEvent: ")
        return super.onInterceptTouchEvent(ev)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    // 这样才能支持子view的margin值
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        Log.i(TAG, "draw: ")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw: ")
    }
}