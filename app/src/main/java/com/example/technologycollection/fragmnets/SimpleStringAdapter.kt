package com.example.technologycollection.fragmnets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technologycollection.R
import kotlinx.android.synthetic.main.metarial_recy_item.view.*

/**
 * simple list<String> addpter
 */
class SimpleStringAdapter : RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {

    var context: Context
    var inflater: LayoutInflater
    var list: List<String>

    constructor(cnt: Context?, list: List<String>) : super() {
        context = cnt!!
        inflater = LayoutInflater.from(cnt)
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.metarial_recy_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.metarial_item_title_tv.text = list.get(position)
    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
}