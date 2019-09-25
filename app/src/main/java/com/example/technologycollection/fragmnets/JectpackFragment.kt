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
import com.example.technologycollection.activitys.LifeCyclerActivity

/**
 * jectpack 官方框架
 * @author zqhcxy 2019/09/23
 */
class JectpackFragment : Fragment {


    lateinit var mRecyclerView: BaseRecyclerView
    lateinit var mAdapter: SimpleStringAdapter

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

    fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.thirdparty_recy)

    }

    fun initData() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        val list = arrayListOf("LifeCycle")

        mAdapter = SimpleStringAdapter(context, list)
        mRecyclerView.adapter = mAdapter

        mRecyclerView.setOnItemClickListener(object : BaseRecyclerView.ItemOnClickListener {
            override fun onItemClick(
                view: View,
                position: Int,
                adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
            ) {
                when (position) {
                    0 -> {
                        val intent = Intent(activity, LifeCyclerActivity::class.java)
                        startActivity(
                            intent,
                            ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
                        )
                    }

                }

            }

            override fun onItemLongClick(
                view: View,
                position: Int,
                adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
            ) {


            }

        })


    }


}