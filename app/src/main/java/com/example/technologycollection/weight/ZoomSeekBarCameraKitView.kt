package com.example.technologycollection.weight

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.util.AttributeSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.wonderkiln.camerakit.CameraKit
import com.wonderkiln.camerakit.CameraView

/**
 * custom CameraKitView to show Zoom change on Seelbar
 * @author zqhcxy 2019/09/03
 */
class ZoomSeekBarCameraKitView : CameraView {


    var mZoom = 1.0f
//    var cameraParameter: Camera.Parameters?=null
    var mInterface: MyCameraViewInterface? = null
    var cameraId =CameraKit.Constants.FACING_BACK

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        requestPermission(context as Activity,16)
         cameraId = CameraKit.Constants.FACING_BACK
        if (isFacingFront) {
            cameraId = CameraKit.Constants.FACING_FRONT
        }

    }

    fun requestPermission(context: Activity, requestCode:Int){
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO),requestCode)
        }
    }

    override fun onZoom(modifier: Float, start: Boolean) {
        super.onZoom(modifier, start)

        var zoom = (modifier - 1.0f) * 0.8f + 1.0f
        zoom = mZoom * zoom

        if (zoom <= 1f) zoom = 1f
        val maxZoom = getMaxZoom()
        if (zoom > maxZoom) {
            zoom = maxZoom
        }
        mZoom = zoom

        mInterface?.onZoomChange(1.0f, maxZoom, mZoom)

    }

    fun getMaxZoom(): Float {
        val cameraParameter = Camera.open(cameraId).parameters
        return cameraParameter.getZoomRatios().get(cameraParameter.getZoomRatios().size - 1)  / 100.0f
    }


    fun setMyCameraViewInterface(inf: MyCameraViewInterface) {
        mInterface = inf
    }



    interface MyCameraViewInterface {
        fun onZoomChange(minZoom: Float, maxZoom: Float, currentZoom: Float)
    }
}