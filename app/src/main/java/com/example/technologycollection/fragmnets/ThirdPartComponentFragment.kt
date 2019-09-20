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
import com.example.technologycollection.Utils.ActivityAndFragmentInterface
import com.example.technologycollection.activitys.CameraKitActivity

/**
 * third Component
 * @author zqhcxy 2019/09/03
 */
class ThirdPartComponentFragment : Fragment {

    lateinit var mRecyclerView: BaseRecyclerView
    lateinit var mAdapter: SimpleStringAdapter

    var mInterface: ActivityAndFragmentInterface? = null

    constructor() {

    }

    constructor(inf: ActivityAndFragmentInterface) {
        mInterface = inf
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = LayoutInflater.from(activity).inflate(R.layout.third_party_component_layout, container, false)
        initView(view)
        initData()
        return view
    }

    fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.thirdparty_recy)

    }

    fun initData() {
        activity!!.setTitle("ThirdPartComponent")
//        mInterface?.updateTitle("ThirdPartComponent")

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        val list = arrayListOf("Camera Kit")

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
                        val intent = Intent(activity, CameraKitActivity::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
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