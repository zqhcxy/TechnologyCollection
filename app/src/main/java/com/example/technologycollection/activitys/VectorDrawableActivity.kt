package com.example.technologycollection.activitys

import android.os.Bundle
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R

/**
 * 矢量图片。
 *<p> 不是随便的一张图都可以做成， 需要特定的图片
 * @author zqhcxy 2019-09-30
 */
class VectorDrawableActivity :BaseCommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vector_drawable_activity)
        initToolbar()


    }
}