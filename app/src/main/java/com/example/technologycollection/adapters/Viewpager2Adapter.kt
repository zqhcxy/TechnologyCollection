package com.example.technologycollection.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.technologycollection.R

/**
 *
 *create by zqh 2019-12-18
 */
class Viewpager2Adapter : RecyclerView.Adapter<ViewPager2ViewHolder> {

    private lateinit var mContext: Context
    private lateinit var mlist: List<String>

    constructor(context: Context) {
        mContext = context
    }

    fun setData(list: List<String>) {
        mlist = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.simple_viewpager2_item_layout, parent, false)
       val params= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        view.layoutParams = params
        return ViewPager2ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    override fun onBindViewHolder(holder: ViewPager2ViewHolder, position: Int) {
        holder.bindData(position)
    }

}

class ViewPager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.viewpager2_item_tv)
    private var colors = arrayOf("#CCFF99", "#41F1E5", "#8D41F1", "#FF99CC")

    fun bindData(pos: Int) {
        textView.setBackgroundColor(Color.parseColor(colors[pos]))
        textView.setText("$pos")
    }


}