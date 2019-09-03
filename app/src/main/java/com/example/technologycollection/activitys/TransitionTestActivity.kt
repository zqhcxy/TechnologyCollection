package com.example.technologycollection.activitys

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import kotlinx.android.synthetic.main.tranisition_test_activity.*
import java.util.*

/**
 * 界面跳转过渡动画的界面
 * @author zqhcxy 2019/08/30
 */
class TransitionTestActivity : BaseCommonActivity() {

    var selectPos:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tranisition_test_activity)

//        setEnterSharedElementCallback(mShareCallback)

        selectPos = intent.getIntExtra(TransitionActivity.SHARE_POS_KEY,0)
        transition_ts_iv.transitionName="image_tranisition_$selectPos"
    }

//    var mShareCallback: SharedElementCallback = object : SharedElementCallback() {
//        override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
//            val name= names?.get(0)
//            transition_ts_iv.transitionName=name
//        }
//    }

    override fun onBackPressed() {

        val intent=Intent()
        intent.putExtra(TransitionActivity.SHARE_POS_KEY,selectPos)
        setResult(Activity.RESULT_OK,intent)
        finishAfterTransition()
        super.onBackPressed()
    }

}