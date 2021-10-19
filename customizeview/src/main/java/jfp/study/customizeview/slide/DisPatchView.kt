package jfp.study.customizeview.slide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import jfp.study.customizeview.R
import jfp.study.customizeview.slide.DisPatchLayout.Companion.TAG
import kotlin.math.min

class DisPatchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var oldX = 0
    private var oldY = 0

    private val mPaint = Paint()

    private var bgColor: Int = 0

    private var imageId = 0

    private lateinit var circleBitmap: Bitmap

    init {
        if (attrs != null) {
            // 自定义属性
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.DisPatchView
            )
            val color = typedArray.getColor(R.styleable.DisPatchView_content_color, R.color.black)
            bgColor = color
            imageId = typedArray.getResourceId(
                R.styleable.DisPatchView_content_image,
                R.drawable.ic_launcher_background
            )
            typedArray.recycle()
        }
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }


    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val widthResult = if (widthMode == MeasureSpec.AT_MOST) {
            300
        } else widthSize

        val heightResult = if (heightMode == MeasureSpec.AT_MOST) {
            300
        } else heightSize
        // 必须调用这个方法
        setMeasuredDimension(widthResult, heightResult)
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        option.inScaled = false
        BitmapFactory.decodeResource(resources, imageId, option)
//        option.inSampleSize = calculateInSampleSize(option.outWidth,option.outHeight,measuredWidth,measuredHeight)
        option.inSampleSize = 3
        option.inJustDecodeBounds = false
        circleBitmap = BitmapFactory.decodeResource(resources, imageId, option)
    }

    private fun calculateInSampleSize(
        ivWidth: Int,
        ivHeight: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        var inSize = 1
        if (ivHeight > reqHeight || ivWidth > reqWidth) {
            while ((ivHeight / inSize) >= reqHeight && (ivWidth / inSize) >= reqWidth) {
                inSize *= 2
            }
        }
        return inSize
    }

    //     可以根据手指移动
//    override fun onTouchEvent(event: MotionEvent): Boolean {
////        when (event.action) {
////            MotionEvent.ACTION_DOWN -> {
////                oldX = width / 2
////                oldY = height / 2
////                val offsetX = event.x.toInt() - oldX
////                val offsetY = event.y.toInt() - oldY
////                layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
////                invalidate()
////            }
////            MotionEvent.ACTION_MOVE -> {
////                val offsetX = event.x.toInt() - oldX
////                val offsetY = event.y.toInt() - oldY
////                layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
////                invalidate()
////            }
////            MotionEvent.ACTION_UP ->{
////                performClick()
////            }
//        Log.i(TAG, "onTouchEvent: ${event.action}")
//        return true
//    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.xfermode = null
        mPaint.color = bgColor
        Log.i(TAG, "onDraw: $this")
        val rect = Rect(
            paddingStart,
            paddingTop,
            width - paddingEnd,
            height - paddingBottom
        )
        canvas.drawBitmap(circleBitmap, rect, rect, mPaint)
        // 绘制的时候需要考虑对padding的支持
        val layerId = canvas.saveLayer(
            paddingStart.toFloat(),
            paddingTop.toFloat(),
            width.toFloat() - paddingEnd,
            height.toFloat() - paddingBottom, mPaint
        )
        canvas.drawCircle(
            width / 2F,
            height / 2F,
            (width - paddingStart - paddingEnd) / 2F,
            mPaint
        )
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas.drawRect(rect, mPaint)
        canvas.restoreToCount(layerId)
    }
}