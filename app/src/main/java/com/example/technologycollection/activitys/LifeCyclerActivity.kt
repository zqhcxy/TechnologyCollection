package com.example.technologycollection.activitys

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import kotlinx.android.synthetic.main.lifecycler_activity.*

/**
 * @author zqhcxy 2019/09/23
 */
class LifeCyclerActivity : BaseCommonActivity() {

    val TAG = "LifeCyclerActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lifecycler_activity)
        initView()
        initData()
    }

    fun initView() {

    }

    fun initData() {

        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                Log.i(TAG, "onCreate-ON_CREATE")
                lifecycle_state_tv.setText("ON_CREATE")
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                Log.i(TAG, "onStart-ON_START")
                lifecycle_state_tv.setText("ON_START")
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun connectListener() {
                Log.i(TAG, "connectListener-ON_RESUME")
                lifecycle_state_tv.setText("ON_RESUME")
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun disconnectListener() {
                Log.i(TAG, "disconnectListener-ON_PAUSE")
                lifecycle_state_tv.setText("ON_PAUSE")
            }
            
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun destroy() {
                Log.i(TAG, "destroy-ON_DESTROY")
                lifecycle_state_tv.setText("ON_DESTROY")
            }

        })
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    class MyLifecycleObserver : LifecycleObserver
}