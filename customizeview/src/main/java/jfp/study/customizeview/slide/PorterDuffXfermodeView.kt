package jfp.study.customizeview.slide

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PorterDuffXFerModeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private val mWidth = 200F
    private val mHeight = 200F

    private val mPaint = Paint().apply {
        color = Color.BLACK
        textSize = 30F
        textAlign = Paint.Align.CENTER
    }

    //目标图
    private val dstBitmap = makeDst()
    //源图像
    private val srcBitmap = makeSrc()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 都设置为match_parent的形式
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec))
    }

    private fun makeDst(): Bitmap {
        val bmp = Bitmap.createBitmap(mWidth.toInt(), mHeight.toInt(), Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.RED
        c.drawOval(RectF(0F, 0F, mWidth, mHeight), p)
        return bmp
    }

    private fun makeSrc(): Bitmap {
        val bmp = Bitmap.createBitmap(mWidth.toInt(), mHeight.toInt(), Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.BLUE
        c.drawRect(0F, 0F, mWidth / 2, mHeight, p)
        return bmp
    }

    private val modeArray = arrayOf(
        DuffMode("ADD", PorterDuff.Mode.ADD),
        DuffMode("LIGHTEN", PorterDuff.Mode.LIGHTEN),
        DuffMode("DARKEN", PorterDuff.Mode.DARKEN),
        DuffMode("MULTIPLY", PorterDuff.Mode.MULTIPLY),
        DuffMode("OVERLAY", PorterDuff.Mode.OVERLAY),
        DuffMode("SCREEN", PorterDuff.Mode.SCREEN),
        DuffMode("MULTIPLY", PorterDuff.Mode.SRC),
        DuffMode("SRC_IN", PorterDuff.Mode.SRC_IN),
        DuffMode("SRC_OUT", PorterDuff.Mode.SRC_OUT),
        DuffMode("SRC_ATOP", PorterDuff.Mode.SRC_ATOP),
        DuffMode("SRC_OVER", PorterDuff.Mode.SRC_OVER),
        DuffMode("DST", PorterDuff.Mode.DST),
        DuffMode("DST_IN", PorterDuff.Mode.DST_IN),
        DuffMode("DST_OUT", PorterDuff.Mode.DST_OUT),
        DuffMode("DST_ATOP", PorterDuff.Mode.DST_ATOP),
        DuffMode("DST_OVER", PorterDuff.Mode.DST_OVER),
        DuffMode("CLEAR", PorterDuff.Mode.CLEAR)

    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layerId = canvas.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), null)
        val metrics = mPaint.fontMetrics
        val baseLine1 = mHeight - metrics.ascent

        modeArray.forEachIndexed { index, duffMode ->
            doAndRestore(canvas, baseLine1, duffMode)
            if (index % 4 == 3 && index != modeArray.size - 1)
                canvas.translate(-width * 0.75F, baseLine1 * 1.1F)
            else
                canvas.translate(width / 4F, 0F)
        }
        canvas.restoreToCount(layerId)
    }

    private fun doAndRestore(canvas: Canvas, baseLine: Float, duffMode: DuffMode) {
        canvas.drawBitmap(dstBitmap, 0F, 0F, mPaint)
        mPaint.xfermode = duffMode.xMode
        canvas.drawBitmap(srcBitmap, mWidth / 2F, 0F, mPaint)
        mPaint.xfermode = null
        canvas.drawText(duffMode.text, mWidth / 2F, baseLine, mPaint)
    }

    class DuffMode(val text: String, mode: PorterDuff.Mode) {
        val xMode = PorterDuffXfermode(mode)
    }
}
