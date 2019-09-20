package com.example.technologycollection.activitys

import android.app.Activity
import android.content.Intent
import android.media.ExifInterface
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.weight.ZoomSeekBarCameraKitView
import com.wonderkiln.camerakit.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.security.MessageDigest
import android.graphics.*
import android.view.WindowManager
import android.widget.Toast
import com.example.technologycollection.R
import com.example.technologycollection.Utils.CommonUtil

/**
 * third component Camera Kit
 * @author zqhcxy 2019/09/03
 */
class CameraKitActivity : BaseCommonActivity(), View.OnClickListener {

    val PIC_TYPE = 0
    val VIDEO_TYPE = 1
    val REQUESTCODE_PERMISSION=10

    val ALL_PATH = Environment.getExternalStorageDirectory().toString() + "/testpic/"

    lateinit var mCameraView: ZoomSeekBarCameraKitView
    lateinit var mBottomLayout: ConstraintLayout
    lateinit var mGalleryImageView: ImageView
    lateinit var mTakePhotoImageView: ImageView
    lateinit var mChangeCameraImageView: ImageView
    lateinit var mPictureTextView: TextView
    lateinit var mVideoTextView: TextView

    var mTakeType: Int = PIC_TYPE
    var isVideoRecoding = false
    var lastVideoPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.camerakit_activity)

        initView()
        initData()
    }

    fun initView() {
        mCameraView = findViewById(R.id.camerakit_cv)

        mBottomLayout = findViewById(R.id.camerakit_bottom_ly)
        mGalleryImageView = findViewById(R.id.camerakit_gallery_iv)
        mTakePhotoImageView = findViewById(R.id.camerakit_take_iv)
        mChangeCameraImageView = findViewById(R.id.camerakit_change_iv)
        mPictureTextView = findViewById(R.id.camerakit_pic_tv)
        mVideoTextView = findViewById(R.id.camerakit_video_tv)

        mGalleryImageView.setOnClickListener(this)
        mTakePhotoImageView.setOnClickListener(this)
        mChangeCameraImageView.setOnClickListener(this)
        mPictureTextView.setOnClickListener(this)
        mVideoTextView.setOnClickListener(this)


    }

    fun initData() {

        CommonUtil.requestPermission(this,REQUESTCODE_PERMISSION)
        mPictureTextView.isSelected = true
        mCameraView.setMyCameraViewInterface(object : ZoomSeekBarCameraKitView.MyCameraViewInterface {
            override fun onZoomChange(minZoom: Float, maxZoom: Float, currentZoom: Float) {


            }
        })

        mCameraView.addCameraKitListener(object : CameraKitEventListener {
            override fun onVideo(p0: CameraKitVideo?) {
                Log.i("CameraKitActivity", "onVideo ()")
                galleryPhoto(lastVideoPath)

            }

            override fun onEvent(p0: CameraKitEvent?) {
//                Log.i("CameraKitActivity", "onEvent ()")
            }

            override fun onImage(imgKit: CameraKitImage?) {

                var path = ALL_PATH + System.currentTimeMillis() + ".jpg"
                val file = File(path)

                if (!file.parentFile.exists() && !file.parentFile.mkdirs()) {
                    Log.i("CameraKitActivity", "file no fund")
                    return
                }

                try {

                    //写入图片数据
                    val outsteam = FileOutputStream(file.path)
                    outsteam.write(imgKit?.jpeg)
                    outsteam.close()

                    //纠正图片角度
                    val exifInterFace = ExifInterface(file.path)
                    //照片的角度
                    val orientaction =
                        exifInterFace.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                    Log.i("CameraKitActivity", "after befor change TAG_ORIENTATION: $orientaction")
                    if (orientaction == ExifInterface.ORIENTATION_ROTATE_270 || orientaction == ExifInterface.ORIENTATION_ROTATE_90) {
                        if (mCameraView.isFacingBack()) {
                            exifInterFace.setAttribute(
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED.toString() + ""
                            )
                        } else {
                            exifInterFace.setAttribute(
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_FLIP_VERTICAL.toString() + ""
                            )
                        }
                        exifInterFace.saveAttributes()

                    }
                    val afterChangeOrient =
                        exifInterFace.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                    Log.i("CameraKitActivity", "after change TAG_ORIENTATION: $afterChangeOrient")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                galleryPhoto(path)
            }

            override fun onError(p0: CameraKitError?) {
            }

        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.camerakit_gallery_iv -> {

            }
            R.id.camerakit_take_iv -> {
                if (mTakeType == PIC_TYPE) {
                    mCameraView.captureImage()
                    mTakePhotoImageView.isSelected = false
                } else if (mTakeType == VIDEO_TYPE) {
                    if (isVideoRecoding) {
                        isVideoRecoding = false
                        mCameraView.stopVideo()
                        mTakePhotoImageView.isSelected = false
                    } else {
                        isVideoRecoding = true
                        lastVideoPath = ALL_PATH + System.currentTimeMillis() + ".mp4"
                        val file = File(lastVideoPath)
                        mCameraView.captureVideo(file)
                        mTakePhotoImageView.isSelected = true
                    }
                }

            }
            R.id.camerakit_change_iv -> {
                if (mCameraView.isFacingBack) {
                    mCameraView.facing = CameraKit.Constants.FACING_FRONT
                } else {
                    mCameraView.facing = CameraKit.Constants.FACING_BACK
                }
            }
            R.id.camerakit_pic_tv -> {
                mTakeType = PIC_TYPE
                setSelectTypeView(PIC_TYPE)
                mBottomLayout.visibility = View.VISIBLE
            }
            R.id.camerakit_video_tv -> {//
                mTakeType = VIDEO_TYPE
                setSelectTypeView(VIDEO_TYPE)
                mBottomLayout.visibility = View.GONE
            }

        }
    }

    fun setSelectTypeView(type: Int) {
        when (type) {
            PIC_TYPE -> {
                mPictureTextView.isSelected = true
                mVideoTextView.isSelected = false
            }
            VIDEO_TYPE -> {
                mVideoTextView.isSelected = true
                mPictureTextView.isSelected = false
            }
        }
    }

    fun galleryPhoto(path: String?) {
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_gallery).override(180, 180).centerCrop()

//        val bitTransitionOptions : BitmapTransitionOptions =  BitmapTransitionOptions(). crossFade( DrawableCrossFadeFactory.Builder(). setCrossFadeEnabled(true)
//            .build())
//        transition(bitTransitionOptions)
        Glide.with(this).applyDefaultRequestOptions(requestOptions).asBitmap().load(path)
            .transform(object :BitmapTransformation(){
                override fun updateDiskCacheKey(messageDigest: MessageDigest) {
                }

                override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
                    Log.i("","transform")

                    return getCirlePhoto(toTransform)
                }

            }).into(mGalleryImageView)
        val animation = ViewAnimationUtils.createCircularReveal(
            mGalleryImageView,
            mGalleryImageView.width / 2,
            mGalleryImageView.height / 2,
            0f,
            mGalleryImageView.width.toFloat()
        )
        animation.setDuration(500)
        animation.start()



//        mGalleryImageView.setImageBitmap(getCirlePhoto(g))
    }

    /**
     * 绘制圆形的图片
     */
    fun getCirlePhoto(bitmap: Bitmap): Bitmap {
        val roundConcerImage = Bitmap.createBitmap(
            mGalleryImageView.width, mGalleryImageView.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(roundConcerImage)
        val paint = Paint()
        paint.isAntiAlias = true
        val rect = RectF(0f, 0f, mGalleryImageView.width.toFloat(), mGalleryImageView.height.toFloat())
        val rectf = Rect(0, 0, bitmap.width, bitmap.height)

        canvas.drawArc(rect,0f,360f,false,  paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rectf, rect, paint)
        return roundConcerImage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= Activity.RESULT_OK) return
        if(requestCode != REQUESTCODE_PERMISSION){
            Toast.makeText(this,"requset permission fail",Toast.LENGTH_LONG).show()
        }

    }


    override fun onResume() {
        super.onResume()
        mCameraView.start()
    }

    override fun onPause() {
        super.onPause()
        mCameraView.stop()
        isVideoRecoding = false
    }

}