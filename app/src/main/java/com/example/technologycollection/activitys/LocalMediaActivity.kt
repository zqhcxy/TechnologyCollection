package com.example.technologycollection.activitys

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R

/**
 * 拍摄的本地图片和视频（有缘再写）
 */
class LocalMediaActivity : BaseCommonActivity() {

    lateinit var mRecyclerView: RecyclerView
//    lateinit var mAdapter: GridImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loacl_media_activity)
        initToolbar()

        initView()
        initData()
    }

    fun initView() {

        mRecyclerView = findViewById(R.id.local_media_recy)


    }

    fun initData() {
        setTitle("Local Media")

        mRecyclerView.layoutManager = GridLayoutManager(this, 4)
//        mAdapter = GridImageAdapter(this)
    }
}