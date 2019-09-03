package com.example.technologycollection.activitys

import android.app.ActivityOptions
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import android.util.Pair
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technologycollection.Utils.MyShareElementCallback
import kotlinx.android.synthetic.main.metarial_recy_item.view.*
import kotlinx.android.synthetic.main.tranisition_test_activity.*
import kotlinx.android.synthetic.main.transition_recyclerview_item.view.*


/**
 * 转场动画类
 * @author zqhcxy 2019/08/23
 */

class TransitionActivity : BaseCommonActivity(), View.OnClickListener {
   companion object{
       const val SHARE_POS_KEY:String="share_pos_key"
   }



    lateinit var mExplodeBtn: Button
    lateinit var mSlideBtn: Button
    lateinit var mFadeBtn: Button
    lateinit var mShareElementBtn: Button
    lateinit var mImageView: ImageView
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter:TransitionImageViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transition_activity)
        initToolbar()



        ActivityCompat.postponeEnterTransition(this)

        initView()
        initData()
    }

    fun initView() {
        mExplodeBtn = findViewById(R.id.transition_explode_btn)
        mSlideBtn = findViewById(R.id.transition_slide_btn)
        mFadeBtn = findViewById(R.id.transition_fade_btn)
        mShareElementBtn = findViewById(R.id.transition_sharedElement_btn)
        mImageView = findViewById(R.id.transition_shared_img)
        mRecyclerView = findViewById(R.id.transition_recy)

        mExplodeBtn.setOnClickListener(this)
        mSlideBtn.setOnClickListener(this)
        mFadeBtn.setOnClickListener(this)
        mShareElementBtn.setOnClickListener(this)

        mRecyclerView.viewTreeObserver.addOnPreDrawListener(object :ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                mRecyclerView.viewTreeObserver.removeOnDrawListener {  }
                ActivityCompat.startPostponedEnterTransition(this@TransitionActivity)

                return true
            }

        })

    }

    fun initData() {
        setTitle(getString(R.string.transition_str))
        var lists = mutableListOf<String>()
        for (i in 0 until  20){
            lists.add("position: $i")
        }
        mAdapter = TransitionImageViewAdapter(this,lists)

        //TODO 实现RecyclerView的item共享元素
        mRecyclerView.layoutManager = GridLayoutManager(this,4)
        mRecyclerView.adapter=mAdapter
        mAdapter.setAdapterinf(object : TransitionImageViewAdapter.Adapterinf{
            override fun onItemClick(view: View) {
                startShareIntent(view,view.transitionName)
            }

        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.transition_explode_btn -> {
                window.enterTransition = Explode()
                window.exitTransition = Explode()
                val intent = Intent(this, TransitionTestActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.transition_slide_btn -> {
                window.enterTransition = Slide()
                window.exitTransition = Slide()
                val intent = Intent(this, TransitionTestActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.transition_fade_btn -> {
                window.enterTransition = Fade()
                window.exitTransition = Fade()
                val intent = Intent(this, TransitionTestActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.transition_sharedElement_btn -> {
                val pairone: Pair<View, String> = Pair(mImageView, "image_tranisition")
                val pairtwo: Pair<View, String> = Pair(mShareElementBtn, "button")
                val intent = Intent(this, TransitionTestActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, pairone, pairtwo).toBundle())
            }
        }
    }

    fun startShareIntent(itemView:View,transitionName:String){
        val intent = Intent(this, TransitionTestActivity::class.java)
        val pos:Int = itemView.getTag() as Int
        intent.putExtra(SHARE_POS_KEY,pos)
        startActivityForResult(intent,112, ActivityOptions.makeSceneTransitionAnimation(this, itemView, transitionName).toBundle())
    }

//    override fun onActivityReenter(resultCode: Int, data: Intent?) {
////        super.onActivityReenter(resultCode, data)
////        ActivityCompat.postponeEnterTransition(this)
//
//        var position:Int=0
//        if(data!=null){
//            position =data.getIntExtra(SHARE_POS_KEY,0)
//        }
//
//        Log.i("","onActivityReenter position: $position")
////        mRecyclerView.viewTreeObserver.addOnPreDrawListener(object :ViewTreeObserver.OnPreDrawListener{
////            override fun onPreDraw(): Boolean {
////                mRecyclerView.viewTreeObserver.removeOnDrawListener {  }
////                ActivityCompat.postponeEnterTransition(this@TransitionActivity)
////
////                return true
////            }
////
////        })
//
//        var itemView=mAdapter.getTransitionView("image_tranisition_$position")
//        val sharedElementCallback = MyShareElementCallback(itemView)
//        setEnterSharedElementCallback(sharedElementCallback)
//
//
//    }



    class TransitionImageViewAdapter : RecyclerView.Adapter<TransitionImageViewAdapter.ViewHolder> {

        var context: Context
        var inflater: LayoutInflater
        var list: List<String>

//        var mItemViews=LinkedHashMap<String,View>()

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
            holder.itemView.transition_recy_item_tv.setImageResource(R.mipmap.pic1)

            val transitionName="image_tranisition_$position"
            holder.itemView.transition_recy_item_tv.transitionName=transitionName
            holder.itemView.transition_recy_item_tv.setTag(position)
            holder.itemView.transition_recy_item_tv.setOnClickListener {
                mInf.onItemClick(it)
            }
//            mItemViews.put(transitionName,holder.itemView.transition_recy_item_tv)
        }

//        fun getTransitionView(key:String): View? {
//            return mItemViews.get(key)
//        }

        lateinit var mInf:Adapterinf

        fun setAdapterinf(inf:Adapterinf){
            mInf = inf
        }


         interface Adapterinf {
            fun onItemClick(view:View)

        }
        class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
    }
}