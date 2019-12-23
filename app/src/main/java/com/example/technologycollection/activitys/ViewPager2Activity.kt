package com.example.technologycollection.activitys

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.BaseRecyclerView
import com.example.technologycollection.R
import com.example.technologycollection.fragmnets.SimpleStringAdapter

/**
 * ViewPager2的功能列表
 * @author zqhcxy 2019/12/18
 */
class ViewPager2Activity :BaseCommonActivity() {

    private lateinit var mRecyclerView: BaseRecyclerView
    private lateinit var mAdapter : SimpleStringAdapter
    private lateinit var mToolbarParent:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_party_component_layout)
        initToolbar()

        initView()
        initData()
    }

    private fun initView(){
        mRecyclerView = findViewById(R.id.thirdparty_recy)
        mToolbarParent = findViewById(R.id.toolbar_parent_ly)
        mToolbarParent.visibility = View.VISIBLE
    }

    private fun initData(){
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val list = arrayListOf("Horizontal ViewPager2","Vertical ViewPager2","Fragment Viewpager2")

        mAdapter = SimpleStringAdapter(this, list)
        mRecyclerView.adapter = mAdapter

        mRecyclerView.setOnItemClickListener(object :BaseRecyclerView.ItemOnClickListener{
            override fun onItemLongClick(
                view: View,
                position: Int,
                adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
            ) {
            }

            override fun onItemClick(
                view: View,
                position: Int,
                adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
            ) {
                when(position){
                    0 ->{//Horizontal ViewPager2
                        val intent = Intent(this@ViewPager2Activity, SimpleViewPager2Activity::class.java)
                        intent.putExtra(SimpleViewPager2Activity.ORIENTATION_KEY,ViewPager2.ORIENTATION_HORIZONTAL)
                        startActivity(
                            intent,
                            ActivityOptions.makeSceneTransitionAnimation(this@ViewPager2Activity).toBundle()
                        )
                    }
                    1 ->{// Vertical ViewPager2
                        val intent = Intent(this@ViewPager2Activity, SimpleViewPager2Activity::class.java)
                        intent.putExtra(SimpleViewPager2Activity.ORIENTATION_KEY,ViewPager2.ORIENTATION_VERTICAL)
                        startActivity(
                            intent,
                            ActivityOptions.makeSceneTransitionAnimation(this@ViewPager2Activity).toBundle()
                        )
                    }
                    2->{
                        val intent = Intent(this@ViewPager2Activity, Viewpager2FragmentActivity::class.java)
                        startActivity(
                            intent,
                            ActivityOptions.makeSceneTransitionAnimation(this@ViewPager2Activity).toBundle()
                        )
                    }
                }

            }

        })

    }
}