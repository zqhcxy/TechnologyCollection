package com.example.technologycollection.activitys

import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import com.example.technologycollection.fragmnets.SimpleBackgroundFragment

/**
 * 使用viewpager2 实现Fragment
 *create by zqh 2019-12-20
 */

class Viewpager2FragmentActivity :BaseCommonActivity() {

    private lateinit var mViewPager2:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_viewpager2)
        initToolbar()

        initView()
        initData()
    }


    private fun initView(){
        mViewPager2 = findViewById(R.id.viewpager2_vp)
        mViewPager2.adapter=ViewPager2FragmentAdapter(supportFragmentManager,lifecycle)
    }

    private fun initData(){


    }


    /**
     * Fragment 的Viewpager2
     */
    class ViewPager2FragmentAdapter(fragmentManager:FragmentManager,lifecycle:Lifecycle) :FragmentStateAdapter(fragmentManager,lifecycle){

        private val mFragments:SparseArray<Fragment> =SparseArray()


        init {
            mFragments.put(PAGE1, SimpleBackgroundFragment(Color.parseColor("#CCFF99")))
            mFragments.put(PAGE2,SimpleBackgroundFragment(Color.parseColor("#41F1E5")))
            mFragments.put(PAGE3,SimpleBackgroundFragment(Color.parseColor("#8D41F1")))
            mFragments.put(PAGE4,SimpleBackgroundFragment(Color.parseColor("#FF99CC")))

        }

        companion object{
            const val PAGE1=0
            const val PAGE2=1
            const val PAGE3=2
            const val PAGE4=3
        }

        override fun getItemCount(): Int {
            return mFragments.size()
        }

        override fun createFragment(position: Int): Fragment {

            return mFragments[position]
        }


    }
}