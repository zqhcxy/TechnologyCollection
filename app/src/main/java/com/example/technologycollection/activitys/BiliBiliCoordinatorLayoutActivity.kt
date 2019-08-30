package com.example.technologycollection.activitys

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import com.example.technologycollection.fragmnets.MetatialComponentFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * 仿BiliBili首页的CoordinatorLayoutActivity
 * @author zqhcxy 2019/08/21
 */
class BiliBiliCoordinatorLayoutActivity : BaseCommonActivity() {

    lateinit var bilibili_rcy: RecyclerView
    lateinit var flab_action_btn :FloatingActionButton
    lateinit var coll_tb_ly :CollapsingToolbarLayout
    lateinit var appbar :AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bilibili_coordinatorlayout_activity)

        bilibili_rcy = findViewById(R.id.bilibili_rcy)
        flab_action_btn =findViewById(R.id.flab_action_btn)
        coll_tb_ly = findViewById(R.id.coll_tb_ly)
        appbar =findViewById(R.id.appbar)


        var list= mutableListOf<String>()
        for (i in 0 until 20){
            list.add("$i")
        }
        bilibili_rcy.layoutManager = LinearLayoutManager(this)
        bilibili_rcy.adapter = MetatialComponentFragment.MetarialAdapter(this,list)
        appbar.addOnOffsetChangedListener(object :AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if(verticalOffset == 0){//expend
                    Log.i("BiliBiliCoordinator","完全展开")
                    flab_action_btn.show()
                }else if(Math.abs(verticalOffset) >= appBarLayout!!.getTotalScrollRange()){//COLLAPSED
                    Log.i("BiliBiliCoordinator","折叠完成")
                    flab_action_btn.hide()
                }else{//处理中
                    Log.i("BiliBiliCoordinator","折叠中: $verticalOffset")
                }
            }

        })

    }
}