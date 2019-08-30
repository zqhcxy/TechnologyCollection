package com.example.technologycollection

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.technologycollection.fragmnets.MetatialComponentFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

/**
 *
 * @author zqh 2019-08-06
 */
class MainActivity : BaseCommonActivity() {

    val TAG:String="MainActivitylog"

    lateinit var vp_container: ViewPager
    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var mAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        initView()
        initData()
    }

    fun initView() {
        vp_container = findViewById(R.id.vp_container)
        bottomNavigationView = findViewById(R.id.bnv_container)
    }

    fun initData() {

        toolbar.setTitle("Metarial")
        toolbar.navigationIcon=null
        var lists: List<Fragment> = arrayListOf(MetatialComponentFragment(), Fragment(), Fragment(), Fragment())
        mAdapter = MainViewPagerAdapter(supportFragmentManager, lists)
        vp_container.adapter = mAdapter

        //bottomnavigation 初始不会调用select监听，所以要手动设置选中的item
        showStabItemSelect(0)



        bottomNavigationView.setOnNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener,
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.stab_material ->{
                        showFragmentByPosition(0)
                    }
                    R.id.stab_material2 ->{
                        showFragmentByPosition(1)
                    }
                    R.id.stab_material3 ->{
                        showFragmentByPosition(2)
                    }
                    R.id.stab_material4 ->{
                        showFragmentByPosition(3)
                    }
                }

                return true
            }

        })
        vp_container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                Log.i(TAG,"onPageScrolled pos: "+position)
            }

            override fun onPageSelected(position: Int) {
                    Log.i(TAG,"onPageSelected pos: "+position)
                showStabItemSelect(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    /**
     * 显示那个fragment
     */
    fun showFragmentByPosition(pos: Int) {
        Log.i(TAG,"showFragmentByPosition : "+pos)
        vp_container.currentItem = pos
        toolbar.setTitle("Metarial $pos")
        updateItemBadgeView(pos)
    }

    /**
     * 底部分类菜单选中
     */
    fun showStabItemSelect(position: Int){
        var id:Int=R.id.stab_material
        when(position){
            0 ->{
                id= R.id.stab_material
            }
            1->{
                id= R.id.stab_material2
            }
            2->{
                id= R.id.stab_material3
            }
            3->{
                id= R.id.stab_material4
            }
        }
        bottomNavigationView.selectedItemId = id
    }

    /**
     * 底部菜单 添加角标
     */
    fun updateItemBadgeView( pos:Int){
        var view=bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
        var count =view.childCount

        var childView = view.getChildAt(pos)
        val iconView =childView.findViewById<ImageView>(R.id.icon)
        val iconWidth=iconView.width
        val stabWidth= childView.width/2
        val spacWidth =stabWidth-iconWidth

        Log.i(TAG,"updateItemBadgeView count: $count iconWidth: $iconWidth stabWidth: $stabWidth spacWidth: $spacWidth")
    }


    /**
     * viewpager 的适配器
     */
    class MainViewPagerAdapter : FragmentPagerAdapter {

        lateinit var fragment: List<Fragment>

        constructor(fm: FragmentManager, lists: List<Fragment>) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            fragment = lists
        }


        override fun getItemId(position: Int): Long {
            return fragment.get(position).hashCode().toLong()
        }

        override fun getItem(position: Int): Fragment {
            return fragment.get(position)
        }

        override fun getCount(): Int {
            return fragment.size
        }

    }


}
