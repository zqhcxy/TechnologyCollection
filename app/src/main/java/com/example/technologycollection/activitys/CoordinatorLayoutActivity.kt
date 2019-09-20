package com.example.technologycollection.activitys

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import com.example.technologycollection.fragmnets.MetatialComponentFragment
import com.example.technologycollection.fragmnets.SimpleStringAdapter

/**
 * CoordinatorLayout demo
 * 最简单的coordibatorLayout 应用。
 * @author zqhcxy 2019/08/15
 */
class CoordinatorLayoutActivity :BaseCommonActivity() {

    lateinit var coor_rcy:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coordinatorlayout_activity)
        initToolbar()

        initView()
        initData()
    }

    fun initView(){
        coor_rcy =findViewById(R.id.coor_rcy)
    }

    fun initData(){
        setTitle("CoordinatorLayout")

        val list= mutableListOf<String>()
        for (i in 0 until 20){
            list.add("$i")
        }

        coor_rcy.layoutManager = LinearLayoutManager(this)
        coor_rcy.adapter = SimpleStringAdapter(this,list)
    }




}