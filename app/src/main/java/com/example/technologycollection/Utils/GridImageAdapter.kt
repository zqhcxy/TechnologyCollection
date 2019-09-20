package com.example.technologycollection.Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.technologycollection.R
import kotlinx.android.synthetic.main.transition_recyclerview_item.view.*

/**
 * 九宫格 图片展示adapter
 * @author zqhcxy 2019/09/06
 */
class GridImageAdapter : RecyclerView.Adapter<GridImageAdapter.ViewHolder> {

    var context: Context
    var inflater: LayoutInflater
    var list: List<String>


    constructor(cnt: Context?, list: List<String>) : super() {
        context = cnt!!
        inflater = LayoutInflater.from(cnt)
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.transition_recyclerview_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list.get(position)).into(holder.itemView.transition_recy_item_tv)
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
}