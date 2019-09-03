package com.example.technologycollection.Utils

import android.app.SharedElementCallback
import android.text.TextUtils
import android.view.View

class MyShareElementCallback : SharedElementCallback {

     var sharedElementView: View?

    constructor(shareView: View?) {
        sharedElementView = shareView
    }

    override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
//        super.onMapSharedElements(names, sharedElements)
        if(sharedElementView ==null) return


        val tranisitiongName= sharedElementView!!.transitionName
        if(TextUtils.isEmpty(tranisitiongName)) return

        names?.clear()
        sharedElements?.clear()
        names?.add(tranisitiongName)
        sharedElements?.put(tranisitiongName, sharedElementView!!)
    }


}