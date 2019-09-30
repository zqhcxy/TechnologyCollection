package com.example.technologycollection.activitys

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.technologycollection.BaseCommonActivity
import com.example.technologycollection.R
import java.io.File
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit


private const val TAG = "CameraXActivity"
private const val REQUESET_CODE_PERMISSIONS = 10
private val REQUEST_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)

/**
 * CameraX 相机功能类(官方API 未确定，暂时只实现拍照)
 * @author zqhcxy 2019-09-26
 */
class CameraXActivity : BaseCommonActivity() {


    private val ALL_PATH = Environment.getExternalStorageDirectory().toString() + "/testpic/"

    private lateinit var mTextureView: TextureView
    private lateinit var mCatPhotoImageView: ImageButton
    private lateinit var mImageCapture:ImageCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camerax_activity)
        mTextureView = findViewById(R.id.camerax_ttv)
        mCatPhotoImageView = findViewById(R.id.camerax_cat_iv)

        if (allPermissionGranted()) {//有权限
            Log.i(TAG, "has permission")
            mTextureView.post { startCamera() }
        } else {//没有权限，去请求
            Log.i(TAG, "no permission,go request")
            ActivityCompat.requestPermissions(this, REQUEST_PERMISSIONS, REQUESET_CODE_PERMISSIONS)
        }
    }

    private fun startCamera() {

        /***
         * 预览
         */

        val config = PreviewConfig.Builder().apply {
            //等比高
            setTargetAspectRatio(Rational(9, 16))
            //分辨率
//            setTargetResolution(Size(mTextureView.width, mTextureView.height))
            //前/后摄像头
            setLensFacing(CameraX.LensFacing.BACK)
        }.build()

        val preview = Preview(config)

        preview.setOnPreviewOutputUpdateListener {
            val parent = mTextureView.parent as ViewGroup
            parent.removeView(mTextureView)
            parent.addView(mTextureView, 0)

            mTextureView.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        /**
         * 拍照
         */
        val imagecaptConfig = ImageCaptureConfig.Builder().apply {
//            setTargetAspectRatio(Rational(16,9))
//            setTargetRotation(Surface.ROTATION_90)
            setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
        }.build()

        mImageCapture = ImageCapture(imagecaptConfig)

        /**
         * 解析
         */

        val analysisConfig = ImageAnalysisConfig.Builder().apply {
            //使用线程来解析图片
            val analyzerThread = HandlerThread("LuminosityAnalysis").apply { start() }
            setCallbackHandler(Handler(analyzerThread.looper))
            //解析最新图片，而不是解析每张图片
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
        }.build()
        //实例化图片解析，并用自定义的解析器
        val analyzerUseCase = ImageAnalysis(analysisConfig).apply {
            analyzer = LuminosityAnalyzer()
        }
//        analyzerUseCase.setAnalyzer { image, rotationDegrees ->
//            val rect = image.cropRect
//            val format = image.format
//            val width = image.width
//            val height = image.height
//            val planes = image.planes
//
//            Log.i(TAG,"image data-- rect: $rect \n format: $format \n width: $width \n height: $height \n planes: $planes")
//        }


        CameraX.bindToLifecycle(this, preview,mImageCapture,analyzerUseCase)

    }

    /**
     * 更新预览的旋转角度
     */
    private fun updateTransform() {

        val matrix = Matrix()

        //获取预览控件的中心坐标
        val centerX = mTextureView.width / 2f
        val centerY = mTextureView.height / 2f

        val rotationDegrees = when (mTextureView.display.rotation) {

            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270

            else -> return
        }

        matrix.postRotate(-rotationDegrees.toFloat(),centerX,centerY)

        //给TextureView 应用旋转
        mTextureView.setTransform(matrix)

        mCatPhotoImageView.setOnClickListener{
            takePicture()
        }

    }

    private fun takePicture(){
        val fileName  = ALL_PATH+System.currentTimeMillis().toString()
        val fileFormat= ".jpg"
//        val file =createTempFile(fileName,fileFormat)
        val file = File(fileName+fileFormat)
        if (!file.parentFile.exists() && !file.parentFile.mkdirs()) {
            Log.i("CameraKitActivity", "file no fund")
            return
        }

        mImageCapture.takePicture(file,object : ImageCapture.OnImageSavedListener{
            override fun onImageSaved(file: File) {
                Log.i(TAG,"onImageSaved")
            }

            override fun onError(
                useCaseError: ImageCapture.UseCaseError,
                message: String,
                cause: Throwable?
            ) {
                Log.i(TAG,"onError: $message")
            }

        })
    }

    /**
     * 检查权限
     */
    private fun allPermissionGranted() = REQUEST_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 权限请求回调
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUESET_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {//权限完成
                mTextureView.post { startCamera() }
                Log.i(TAG, "request permission success")
            } else {//权限请求失败
                Log.i(TAG, "request permission fail")
            }
        }
    }

    /**
     * 图片(亮度)解析器
     */
    private class LuminosityAnalyzer :ImageAnalysis.Analyzer{
        private var lastAnalyzedTimestamp=0L

        /**
         * Helper extension function used to extract a byte array from an
         * image plane buffer
         */
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }


        override fun analyze(image: ImageProxy, rotationDegrees: Int) {
            val currentTimestamp  = System.currentTimeMillis()
            //计算平均亮度的频率（每秒）
            if(currentTimestamp-lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)){

                val buffer = image.planes[0].buffer
                //从回调数据中获取图像数据
                val data = buffer.toByteArray()
                //将数据转换为像素数据
                val pixels = data.map { it.toInt() and 0xFF }
                //计算图像的平均亮度
                val luma = pixels.average()
                Log.i(TAG,"Average luminosity: $luma")
                lastAnalyzedTimestamp = currentTimestamp

            }


        }

    }


}