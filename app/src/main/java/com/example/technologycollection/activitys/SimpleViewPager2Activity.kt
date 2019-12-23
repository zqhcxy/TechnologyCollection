package com.example.technologycollection.activitys

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import com.example.technologycollection.adapters.Viewpager2Adapter
import com.example.technologycollection.weight.ScaleInTransformer

/**
 * 简单的Viewpager2
 *create by zqh 2019-12-18
 */
class SimpleViewPager2Activity : BaseCommonActivity() {


    companion object {
        val ORIENTATION_KEY = "orientation_key"
    }

    private val TAG = "SimpleViewPager2Activity"

    private lateinit var mViewPager2: ViewPager2
    private lateinit var mAdapter: Viewpager2Adapter
    private var mOrientation: Int = ViewPager2.ORIENTATION_HORIZONTAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_viewpager2)
        initToolbar()

        initView()
        initIntent()
        initData()


    }

    private fun initIntent() {
        mOrientation = intent.getIntExtra(ORIENTATION_KEY, ViewPager2.ORIENTATION_HORIZONTAL)
    }

    private fun initView() {
        mViewPager2 = findViewById(R.id.viewpager2_vp)
        val lastButton: Button = findViewById(R.id.viewpager2_last_btn)
        val nextButton: Button = findViewById(R.id.viewpager2_next_btn)

        lastButton.setOnClickListener {
            fakeDrag(210f)
        }
        nextButton.setOnClickListener { fakeDrag(-210f) }
    }

    private fun initData() {


        mAdapter = Viewpager2Adapter(this)
        mAdapter.setData(arrayListOf("1", "2", "3", "4"))
        mViewPager2.adapter = mAdapter
        mViewPager2.orientation = mOrientation


        /*
            设置切换动画Transformer
         */
        if (mOrientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            //CompositePageTransformer 可以接收多个Transformer
            val transformers = CompositePageTransformer()
            //左右间距Transformer
            transformers.addTransformer(MarginPageTransformer(resources.getDimension(R.dimen.viewpage2_horizontal_margin).toInt()))
            transformers.addTransformer(ScaleInTransformer())//放缩Transformer,左右切换动画
            mViewPager2.setPageTransformer(transformers)

            /*
                一屏多页，可以通过设置RecyclerView的Padding 来实现
            */
            mViewPager2.apply {
                //一屏多页，需要加载下一页，所以这个要设置
                offscreenPageLimit = 1
                val recyclerView = getChildAt(0) as RecyclerView
                recyclerView.apply {
                    val padding = resources.getDimensionPixelOffset(R.dimen.viewpage2_horizontal_page_margin)
                    setPadding(padding, 0, padding, 0)
                    clipToPadding = false
                }
            }
        }


        /*
        如果offscreenPageLimit设置为OFFSCREEN_PAGE_LIMIT_DEFAULT，就只会加载当前页面，就是不会预加载下一个页面，viewpage默认是会加载下一个，不能改，但是viewpager2
        做了优化，可以设置成加载当前，需要试验的话，可以创建Fragment的viewpager2测试。
         */
//        mViewPager2.offscreenPageLimit=ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        // 设置是否可以滑动
//        mViewPager2.isUserInputEnabled=false

        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(position: Int, positionOffset: Float,
                positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.i(TAG, "onPageScrolled position: $position positionOffsetPixels: $positionOffsetPixels")
            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.i(TAG, "onPageSelected position: $position")
            }
        })

    }

    /**
     * 用于模拟点击，可以设置拖拽的距离回弹 负数表示下一个，正数表示上一个
     */
    fun fakeDrag(dragby: Float) {
        mViewPager2.beginFakeDrag()
        if (mViewPager2.fakeDragBy(dragby)) {
            mViewPager2.endFakeDrag()
        }
    }
}