package com.example.technologycollection.fragmnets

import android.app.ActivityOptions
import android.content.Context
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
import com.example.technologycollection.activitys.BiliBiliCoordinatorLayoutActivity
import com.example.technologycollection.activitys.CoordinatorLayoutActivity
import com.example.technologycollection.activitys.TextInPutLayoutActivity
import com.example.technologycollection.activitys.TransitionActivity
import kotlinx.android.synthetic.main.metarial_recy_item.view.*

/**
 * Metarial 控件
 */
class MetatialComponentFragment : Fragment {


    lateinit var mRecyclerView: BaseRecyclerView
    lateinit var mAdapter: SimpleStringAdapter

    var mInterface: ActivityAndFragmentInterface?=null

    constructor(){

    }

    constructor(inf:ActivityAndFragmentInterface){
        mInterface = inf
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = LayoutInflater.from(activity).inflate(R.layout.metarial_fragment_layout, container, false)
        initView(view)
        initData()
        return view
    }

    fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.metrail_recy)
    }

    fun initData() {
//        activity!!.setTitle("Metatial")

        val list = arrayListOf("TextInPutLayout","CoordinatorLayout","CoordinatorLayout-bilibili","Transition")

        mAdapter = SimpleStringAdapter(context, list)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)

        mRecyclerView.setOnItemClickListener(object : BaseRecyclerView.ItemOnClickListener {
            override fun onItemClick(view: View, position: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
                when(position){
                    0 ->{
                        val intent=Intent(activity,TextInPutLayoutActivity::class.java)
                        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                    }
                    1->{
                        val coorIntent = Intent(activity, CoordinatorLayoutActivity::class.java)
                        startActivity(coorIntent,ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())

                    }
                    2->{
                        val bilibiliIntent = Intent(activity, BiliBiliCoordinatorLayoutActivity::class.java)
                        startActivity(bilibiliIntent,ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                    }
                    3->{
                        val transitionIntent = Intent(activity, TransitionActivity::class.java)
                        startActivity(transitionIntent,ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                    }
                }
            }

            override fun onItemLongClick(view: View, position: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
            }

        })
    }


//    class MetarialAdapter : RecyclerView.Adapter<MetarialAdapter.ViewHolder> {
//
//        var context: Context
//        var inflater: LayoutInflater
//        var list: List<String>
//
//        constructor(cnt: Context?, list: List<String>) : super() {
//            context = cnt!!
//            inflater = LayoutInflater.from(cnt)
//            this.list = list
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            return ViewHolder(inflater.inflate(R.layout.metarial_recy_item, parent, false))
//        }
//
//        override fun getItemCount(): Int {
//            return list.size
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.itemView.metarial_item_title_tv.text = list.get(position)
//        }
//
//
//        class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
//    }

}