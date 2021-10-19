package jfp.study.coding_android.demo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class ContactPersonDecoration : RecyclerView.ItemDecoration() {

    private val mPaint = Paint().apply {
        color = Color.GRAY
    }

    private val mHeader = Paint().apply {
        color = Color.RED
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        repeat(parent.childCount) {
            val view = parent.getChildAt(it)
            if (isHeader(parent, view)) {
                c.drawRect(
                    view.left.toFloat(),
                    view.top - 150F,
                    view.right.toFloat(),
                    view.top.toFloat(),
                    mPaint
                )
            }
        }
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val view = parent.getChildAt(1)
        if (isHeader(parent, view)){
            c.drawRect(
                view.left.toFloat(),
                view.top - 300F,
                view.right.toFloat(),
                view.top.toFloat() - 150F,
                mHeader
            )
        }else{
            c.drawRect(
                view.left.toFloat(),
                0F,
                view.right.toFloat(),
                150F,
                mHeader
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (isHeader(parent, view)) {
            outRect.set(0, 150, 0, 0)
        }
    }

    private fun isHeader(parent: RecyclerView, view: View): Boolean {
        val position = parent.getChildAdapterPosition(view)
        val list = (parent.adapter as ContactPersonAdapter).listValue
        return position == 0 || list[position - 1].type != list[position].type
    }
}