package com.example.technologycollection.fragmnets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.technologycollection.R

/**
 * 一个只有背景色的Fragment
 *create by zqh 2019-12-20
 */
class SimpleBackgroundFragment:Fragment{

    val color:Int
    constructor(cl:Int){
        color=cl
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=LayoutInflater.from(activity).inflate(R.layout.simple_fragment_activity,null,false)
        view.setBackgroundColor(color)
        return view
    }
}