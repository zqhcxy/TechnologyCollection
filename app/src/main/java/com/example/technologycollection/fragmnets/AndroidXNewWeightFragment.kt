package com.example.technologycollection.fragmnets

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technologycollection.BaseRecyclerView
import com.example.technologycollection.R
import com.example.technologycollection.activitys.ViewPager2Activity

/**
 * 新控件，androidX下。现在新的都在AndroidX 下。
 * @author zqhcxy 2019/12/17
 */
class AndroidXNewWeightFragment : Fragment {

    private lateinit var mRecyclerView:BaseRecyclerView
    private lateinit var mAdapter :SimpleStringAdapter


    constructor() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = LayoutInflater.from(activity)
            .inflate(R.layout.third_party_component_layout, container, false)
        initView(view)
        initData()
        return view
    }


    private fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.thirdparty_recy)
    }

    private fun initData() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        val list = arrayListOf("ViewPager2")

        mAdapter = SimpleStringAdapter(context, list)
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
                    0 ->{//ViewPager2
                        val intent = Intent(activity, ViewPager2Activity::class.java)
                        startActivity(
                            intent,
                            ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
                        )
                    }
                }

            }

        })

    }
}