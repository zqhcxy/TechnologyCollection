package com.example.technologycollection
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

lateinit var clickListener: BaseRecyclerView.ItemOnClickListener

/**
 * 自定义，实现Recyclerview 的onItemclickListener，类似ListView的实现
 * @author zqhcxy 2019/06/10
 */
open class BaseRecyclerView : RecyclerView {


    var mTouchFrame: Rect? = null
    var mGestureDetector: GestureDetector? = null


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    fun setOnItemClickListener(listener: ItemOnClickListener) {
        clickListener = listener
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (mGestureDetector == null) {
            mGestureDetector = GestureDetector(context, GestureDetectorListener(this))
        }
        mGestureDetector!!.onTouchEvent(e)

        return super.onTouchEvent(e)
    }


    /**
     * 根据点击x，y坐标，判断落在哪一个itemview上面，返回对应itemview的position
     */
    fun pointToPosition(x: Int, y: Int): Int {
        var frame: Rect? = mTouchFrame
        if (frame == null) {
            mTouchFrame = Rect()
            frame = mTouchFrame
        }

        val count = childCount
        for (i in count-1 downTo  0) {
            val child = getChildAt(i)
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame)
                if (frame!!.contains(x, y)) {
                    return i
                }
            }
        }
        return AdapterView.INVALID_POSITION
    }

    class GestureDetectorListener(val parentview: BaseRecyclerView) : GestureDetector.OnGestureListener {
        val INVALID_POSITION = -1

        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            //'获取单击坐标'
            val x: Int = e?.x?.toInt() ?: 0
            val y: Int = e?.y?.toInt() ?: 0
            //'获得单击坐标对应的表项索引'
            val position = parentview.pointToPosition(x, y)
            if (position != INVALID_POSITION) {
                try {
                    //'获取索引位置的表项，通过接口传递出去'
                    val child = parentview.getChildAt(position)
                        clickListener.onItemClick(child, parentview.getChildAdapterPosition(child), parentview.getAdapter()!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return false
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            return false
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
            //'获取单击坐标'
            val x: Int = e?.x?.toInt() ?: 0
            val y: Int = e?.y?.toInt() ?: 0
            //'获得单击坐标对应的表项索引'
            val position = parentview.pointToPosition(x, y)
            if (position != INVALID_POSITION) {
                try {
                    //'获取索引位置的表项，通过接口传递出去'
                    val child = parentview.getChildAt(position)
//                    if (clickListener != null) {
                        clickListener.onItemLongClick(child, parentview.getChildAdapterPosition(child), parentview.getAdapter()!!)
//                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    interface ItemOnClickListener {
        fun onItemClick(view: View, position: Int, adapter: Adapter<RecyclerView.ViewHolder>)
        fun onItemLongClick(view: View, position: Int, adapter: Adapter<RecyclerView.ViewHolder>)
    }

}