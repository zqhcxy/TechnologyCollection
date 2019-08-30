package com.example.technologycollection.activitys

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import android.util.Pair



/**
 * 转场动画类
 * @author zqhcxy 2019/08/23
 */
class TransitionActivity : BaseCommonActivity(), View.OnClickListener {


    lateinit var mExplodeBtn: Button
    lateinit var mSlideBtn: Button
    lateinit var mFadeBtn: Button
    lateinit var mShareElementBtn: Button
    lateinit var mImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transition_activity)
        initToolbar()


        initView()
        initData()
    }

    fun initView() {
        mExplodeBtn = findViewById(R.id.transition_explode_btn)
        mSlideBtn = findViewById(R.id.transition_slide_btn)
        mFadeBtn = findViewById(R.id.transition_fade_btn)
        mShareElementBtn = findViewById(R.id.transition_sharedElement_btn)
        mImageView = findViewById(R.id.transition_shared_img)

        mExplodeBtn.setOnClickListener(this)
        mSlideBtn.setOnClickListener(this)
        mFadeBtn.setOnClickListener(this)
        mShareElementBtn.setOnClickListener(this)

    }

    fun initData() {
        setTitle(getString(R.string.transition_str))


    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.transition_explode_btn ->{
                window.enterTransition=Explode()
                window.exitTransition = Explode()
                val intent= Intent(this,TransitionTestActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.transition_slide_btn ->{
                window.enterTransition=Slide()
                window.exitTransition = Slide()
                val intent= Intent(this,TransitionTestActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.transition_fade_btn ->{
                window.enterTransition=Fade()
                window.exitTransition = Fade()
                val intent= Intent(this,TransitionTestActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            R.id.transition_sharedElement_btn ->{
                val pairone :Pair<View,String> = Pair(mImageView,"imageview")
                val pairtwo :Pair<View,String> = Pair(mShareElementBtn,"button")
                val intent= Intent(this,TransitionTestActivity::class.java)
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this,pairone,pairtwo).toBundle())
            }
        }
    }
}