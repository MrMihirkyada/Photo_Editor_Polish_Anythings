package com.example.photoeditorpolishanything

import LocaleHelper
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.BeautifyRetouch.BlemishRemover
import com.example.photoeditorpolishanything.FaceDetaction.FaceDetectionHelper
import com.example.photoeditorpolishanything.FaceDetection.JawlineEditor
import com.example.photoeditorpolishanything.databinding.ActivityBeautifyBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.isInitialized
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.isInitialized as isInitialized1


class Beautify_Activity : BaseActivity() {

    lateinit var binding: ActivityBeautifyBinding
    private var detectedFace: Face? = null
    private var faceRect: Rect? = null // Declare a variable to hold the face rectangle
    lateinit var originalBitmap: Bitmap
    lateinit var mutableBitmap: Bitmap
    var faceDetectionHelper: FaceDetectionHelper? = null
    private var detectedFaces: List<Face>? = null
    val jawlineEditor = JawlineEditor()
    private lateinit var faceBounds: Rect
    private var lastSeekBarProgress = 50 // Default value
    private lateinit var renderScript: RenderScript
    private var transformedBitmap: Bitmap? = null
    private var currentButton: Int = R.id.lnrSmooth // Default button
    private lateinit var modifiedBitmap: Bitmap
    private val width: Int by lazy { originalBitmap.width }
    private val height: Int by lazy { originalBitmap.height }

    //    private lateinit var sharpenFilter: SharpenFilter
    private var leftEye: PointF? = null
    private var rightEye: PointF? = null
    private var noseBase: PointF? = null
    private var leftMouth: PointF? = null
    private var rightMouth: PointF? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private lateinit var ringView: ImageView
    private var touchX = 0
    private var touchY = 0
    private var drawCanvas: Canvas? = null
    private val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
    }
    private lateinit var drawingBitmap: Bitmap
    private lateinit var ringCanvas: Canvas
    private lateinit var drawingCanvas: Canvas
    var context : Context? = null
    lateinit var rs : RenderScript
    private val blemishRemover = BlemishRemover()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeautifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        if (Build.VERSION.SDK_INT >= 21)
        {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)
        }

        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)


        faceDetectionHelper = FaceDetectionHelper()
        ::bitmap.isInitialized
        ::originalBitmap.isInitialized
        rs = RenderScript.create(this)


        initView()
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtOpen.text = resources.getString(R.string.Open)
        binding.txtBeautify.text = resources.getString(R.string.Beautify)
        binding.txtFace.text = resources.getString(R.string.Face)
        binding.txtRetouch.text = resources.getString(R.string.Retouch)
        binding.txtMakeup.text = resources.getString(R.string.Makeup)
        binding.txtBlemish.text = resources.getString(R.string.Blemish)
        binding.txtWrinkle.text = resources.getString(R.string.Wrinkle)
        binding.txtDarkCircles.text = resources.getString(R.string.DarkCircles)
        binding.txtReshape.text = resources.getString(R.string.Reshape)


        binding.txtThin.text = resources.getString(R.string.Thin)
        binding.txtWidth.text = resources.getString(R.string.Width)
        binding.txtJaw.text = resources.getString(R.string.Jaw)
        binding.txtForehead.text = resources.getString(R.string.Forehead)
        binding.txtSizes.text = resources.getString(R.string.Size)
        binding.txtWidths.text = resources.getString(R.string.Width)
        binding.txtDistance.text = resources.getString(R.string.Distance)
        binding.txtHeights.text = resources.getString(R.string.Height)
        binding.txtTilt.text = resources.getString(R.string.Tilt)
        binding.txtSize.text = resources.getString(R.string.Size)
        binding.txtWidthes.text = resources.getString(R.string.Width)
        binding.txtHeightes.text = resources.getString(R.string.Height)
        binding.txtMThickness.text = resources.getString(R.string.Thickness)
        binding.txtNThickness.text = resources.getString(R.string.Thickness)
        binding.txtWidthess.text = resources.getString(R.string.Width)
        binding.txtTip.text = resources.getString(R.string.Tip)
        binding.txtLift.text = resources.getString(R.string.Lift)
        binding.txtThickness.text = resources.getString(R.string.Thickness)
        binding.txtDistances.text = resources.getString(R.string.Distance)
        binding.txtLifts.text = resources.getString(R.string.Lift)
        binding.txtTilts.text = resources.getString(R.string.Tilt)

         
        binding.txtSmooth.text = resources.getString(R.string.Smooth)
        binding.txtBrighten.text = resources.getString(R.string.Brighten)
        binding.txtSharpen.text = resources.getString(R.string.Sharpen)


        binding.txtSets.text = resources.getString(R.string.Sets)
        binding.txtLipColor.text = resources.getString(R.string.LipColor)
        binding.txtBlush.text = resources.getString(R.string.Blush)
        binding.txtContour.text = resources.getString(R.string.Contour)


        binding.txtOriginal.text = resources.getString(R.string.Original)
        binding.txtBright.text = resources.getString(R.string.Bright)
        binding.txtStory.text = resources.getString(R.string.Story)
        binding.txtNatural.text = resources.getString(R.string.Natural)
        binding.txtWarm.text = resources.getString(R.string.Warm)
        binding.txtDew.text = resources.getString(R.string.Dew)
        binding.txtGold.text = resources.getString(R.string.Gold)
        binding.txtLomo.text = resources.getString(R.string.Lomo)
        binding.txtPink.text = resources.getString(R.string.Pink)


        binding.txtMa01.text = resources.getString(R.string.Ma01)
        binding.txtMa02.text = resources.getString(R.string.Ma02)
        binding.txtMa03.text = resources.getString(R.string.Ma03)
        binding.txtMa04.text = resources.getString(R.string.Ma04)
        binding.txtMa05.text = resources.getString(R.string.Ma05)
        binding.txtMa06.text = resources.getString(R.string.Ma06)
        binding.txtMa07.text = resources.getString(R.string.Ma07)
        binding.txtMa08.text = resources.getString(R.string.Ma08)
        binding.txtMa09.text = resources.getString(R.string.Ma09)



        binding.txtFlush.text = resources.getString(R.string.Flush)
        binding.txtSquare.text = resources.getString(R.string.Square)
        binding.txtPear.text = resources.getString(R.string.Pear)
        binding.txtOval.text = resources.getString(R.string.Oval)
        binding.txtSunburns.text = resources.getString(R.string.Sunburn)
        binding.txtAngled.text = resources.getString(R.string.Angled)
        binding.txtNormals.text = resources.getString(R.string.Normal)
        binding.txtApple.text = resources.getString(R.string.Apple)
        binding.txtFull.text = resources.getString(R.string.Full)



        binding.txtFlushs.text = resources.getString(R.string.Flush)
        binding.txtSquares.text = resources.getString(R.string.Square)
        binding.txtPears.text = resources.getString(R.string.Pear)
        binding.txtOvals.text = resources.getString(R.string.Oval)
        binding.txtSunburn.text = resources.getString(R.string.Sunburn)
        binding.txtAngleds.text = resources.getString(R.string.Angled)
        binding.txtNormal.text = resources.getString(R.string.Normal)


        binding.txtTapareastoremoveblemishes.text =
            resources.getString(R.string.Tapareastoremoveblemishes)


        binding.txtManual.text = resources.getString(R.string.Manual)
        binding.txtEraser.text = resources.getString(R.string.Eraser)


        binding.txtReshapes.text = resources.getString(R.string.Reshape)
        binding.txtDetail.text = resources.getString(R.string.Detail)
        binding.txtResize.text = resources.getString(R.string.Resize)
        binding.txtRestore.text = resources.getString(R.string.Restore)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        val imageUriString = intent.getStringExtra("selected_image_uri")
        val imageUri = Uri.parse(imageUriString)

        ::originalBitmap.isInitialized1
        ::mutableBitmap.isInitialized1
        ::bitmap.isInitialized1
        ::drawingBitmap.isInitialized1
        ::ringCanvas.isInitialized1
        ::ringCanvas.isInitialized1

        CoroutineScope(Dispatchers.Main).launch {
            try {
                originalBitmap = getBitmapFromUri(imageUri)
                bitmap = getBitmapFromUri(imageUri)
                mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                binding.imgEditBeautifySelectImage.setImageBitmap(originalBitmap)
                detectFaceInImage(originalBitmap)
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                Toast.makeText(this@Beautify_Activity, "Failed to load image", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        val getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    binding.imgEditBeautifySelectImage.setImageURI(imageUri)
                }
            }

        if (imageUriString != null)
        {
            val imageUri = Uri.parse(imageUriString)
            loadImageAsync(imageUri)
        }
        else
        {
            logErrorAndFinish("Image URI string is null")
        }

        val renderScript = RenderScript.create(this)


        binding.lnrskbface.setOnSeekBarChangeListener(object :
            CustomSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(progress: Int)
            {

//                originalBitmap?.let { bitmap ->
//                    if (leftEye != null && rightEye != null && noseBase != null && leftMouth != null && rightMouth != null) {
//                        // Update face reshaping based on the progress
//                        val reshapedBitmap = applyReshapingEffect(bitmap, progress)
//                        // Ensure UI updates happen on the main thread
//                        runOnUiThread {
//                            binding.imgEditBeautifySelectImage.setImageBitmap(reshapedBitmap)
//                        }
//                    }
//                }

//                val scaleFactor = progress / 100.0f
//                adjustThinness(scaleFactor)

//                val scale = progress / 50f // Scale from 0.5 to 2.0
//                updateFaceTransformation(scale)

                val scaleFactor = 1.0f + (progress - 50) / 100.0f

                detectFace(
                    InputImage.fromBitmap(originalBitmap, 0),
                    object : FaceDetectionCallback {
                        override fun onSuccess(faces: List<Face>)
                        {
                            if (faces.isNotEmpty())
                            {
                                val face = faces[0] // Assuming only one face for simplicity
                                val reshapedBitmap = reshapeFace(originalBitmap, face, scaleX = scaleFactor)
                                binding.imgEditBeautifySelectImage.setImageBitmap(reshapedBitmap)
                            }
                        }

                        override fun onFailure(e: Exception) {
                            // Handle the error
                        }
                    })

//              binding.imgEditBeautifySelectImage.setImageBitmap(adjustedBitmap)
            }

            override fun onStartTrackingTouch() {}

            override fun onStopTrackingTouch() {}
        })


        binding.lnrSmooth.setOnClickListener {
            binding.skbSmooth.visibility = View.VISIBLE


            binding.skbBrighten.visibility = View.GONE
            binding.skbSharpen.visibility = View.GONE
            smoothfilter()
        }

        binding.lnrBrighten.setOnClickListener {
            binding.skbBrighten.visibility = View.VISIBLE


            binding.skbSharpen.visibility = View.GONE
            binding.skbSmooth.visibility = View.GONE

            Brightenfilter()
        }

        binding.lnrSharpen.setOnClickListener {
            binding.skbSharpen.visibility = View.VISIBLE


            binding.skbBrighten.visibility = View.GONE
            binding.skbSmooth.visibility = View.GONE

            Sharpenfilter()
        }


        binding.imgcross.setOnClickListener {
            binding.lnrMainlayoutBeautify.visibility = View.VISIBLE
            binding.txtOpen.visibility = View.VISIBLE

            binding.rtlVisibilitycrossright.visibility = View.GONE
            binding.rtlVisibilityPro.visibility = View.GONE

            //---------------face View---------------//
            binding.lnrskbface.visibility = View.GONE
            binding.lnrFaceLayout.visibility = View.GONE
            //---------------face View---------------//


            //---------------Retouch View---------------//
            binding.lnrskbRetouch.visibility = View.GONE
            binding.lnrRetouchLayout.visibility = View.GONE
            //---------------Retouch View---------------//


            //---------------Make up View---------------//

            binding.rtlVisibilitycrossright.visibility = View.GONE
            binding.lnrvisibility.visibility = View.GONE

            //------------Setting View---------//
            binding.lnrskbSettingvisibility.visibility = View.GONE
            binding.lnrSettingvisibility.visibility = View.GONE
            //------------Setting View---------//

            //------------Lip View---------//
            binding.lnrskbvisibilityLip.visibility = View.GONE
            binding.lnrvisibilityLipcolor.visibility = View.GONE
            //------------Lip View---------//

            //------------Blush View---------//
            binding.lnrskbvisibilityBlush.visibility = View.GONE
            binding.lnrvisibilityBlush.visibility = View.GONE
            //------------Blush View---------//


            //------------Blush View---------//
            binding.lnrskbvisibilityContour.visibility = View.GONE
            binding.lnrvisibilityContour.visibility = View.GONE
            //------------Blush View---------//

            //---------------Make up View---------------//


            //---------------Blemish View---------------//
            binding.lnrBlemishLayout.visibility = View.GONE
            binding.blemishButton.visibility = View.GONE
            //---------------Blemish View---------------//


            //---------------Wrinkle View---------------//
            binding.lnrWrinkleReturn.visibility = View.GONE
            binding.lnrWrinkleLayout.visibility = View.GONE
            binding.rtlVisibilityPro.visibility = View.GONE
            //---------------Wrinkle View---------------//


            //---------------Reshape View---------------//
            binding.lnrReshapeView.visibility = View.GONE
            //---------------Reshape View---------------//

        }


        binding.imgcrosss.setOnClickListener {
            binding.lnrMainlayoutBeautify.visibility = View.VISIBLE
            binding.txtOpen.visibility = View.VISIBLE

            //---------------Wrinkle View---------------//
            binding.lnrWrinkleReturn.visibility = View.GONE
            binding.lnrWrinkleLayout.visibility = View.GONE
            binding.rtlVisibilityPro.visibility = View.GONE
            //---------------Wrinkle View---------------//
        }

        binding.lnrPro.setOnClickListener {
            var i = Intent(this@Beautify_Activity, Polish_Pro_PaymentActivity::class.java)
            startActivity(i)
        }

        binding.lnrFace.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,FaceActivity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrskbfaces.visibility = View.VISIBLE
            binding.lnrskbface.visibility = View.VISIBLE
            binding.lnrFaceLayout.visibility = View.VISIBLE
        }

        binding.lnrRetouch.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Retouch_Activity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrskbRetouch.visibility = View.VISIBLE
            binding.lnrRetouchLayout.visibility = View.VISIBLE
        }

        binding.lnrMakeup.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Makeup_Activity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrvisibility.visibility = View.VISIBLE
        }

    //---------------------------------------MAKE UP VIEW-----------------------------------------//
        binding.lnrSetting.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE

            binding.lnrskbSettingvisibility.visibility = View.VISIBLE
            binding.lnrSettingvisibility.visibility = View.VISIBLE
        }

        binding.lnrLipColor.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE

            binding.lnrskbvisibilityLip.visibility = View.VISIBLE
            binding.lnrvisibilityLipcolor.visibility = View.VISIBLE
        }

        binding.lnrBlush.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE

            binding.lnrskbvisibilityBlush.visibility = View.VISIBLE
            binding.lnrvisibilityBlush.visibility = View.VISIBLE
        }

        binding.lnrContour.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE

            binding.lnrskbvisibilityContour.visibility = View.VISIBLE
            binding.lnrvisibilityContour.visibility = View.VISIBLE
        }
    //---------------------------------------MAKE UP VIEW-----------------------------------------//

        binding.lnrBlemish.setOnClickListener {
//          var i = Intent(this@Beautify_Activity,Blemish_Activity::class.java)
//          startActivity(i)
            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrBlemishLayout.visibility = View.VISIBLE
            binding.blemishButton.visibility = View.VISIBLE
        }

        binding.lnrWrinkle.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Wrinkle_DarkCircle_Activity::class.java)
//            startActivity(i)
            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilityPro.visibility = View.VISIBLE
            binding.lnrWrinkleReturn.visibility = View.VISIBLE
            binding.lnrWrinkleLayout.visibility = View.VISIBLE

        }

        binding.lnrDarkCircles.setOnClickListener {
//            var i = Intent(this@Beautify_Activity,Wrinkle_DarkCircle_Activity::class.java)
//            startActivity(i)
            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilityPro.visibility = View.VISIBLE
            binding.lnrWrinkleReturn.visibility = View.VISIBLE
            binding.lnrWrinkleLayout.visibility = View.VISIBLE

        }

        binding.lnrReshape.setOnClickListener {
//            var i = Intent(this@Beautify_Activity, Wrinkle_DarkCircle_Activity::class.java)
//            startActivity(i)

            binding.lnrMainlayoutBeautify.visibility = View.GONE
            binding.txtOpen.visibility = View.GONE

            binding.rtlVisibilitycrossright.visibility = View.VISIBLE
            binding.lnrReshapeView.visibility = View.VISIBLE
        }

        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageView = findViewById<ImageView>(R.id.imgEditBeautifySelectImage)

            if (imageView != null) {
                try {
                    Glide.with(this).load(imageUri).into(imageView)
                } catch (e: Exception) {
                    logErrorAndFinish("Glide error: ${e.message}")
                }
            } else {
                logErrorAndFinish("ImageView not found in layout")
            }
        } else {
            logErrorAndFinish("Image URI string is null")
        }

        originalBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

        binding.imgEditBeautifySelectImage.setImageBitmap(originalBitmap)



//        binding.imgEditBeautifySelectImage.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    // Get touch coordinates
//                    val x = event.x
//                    val y = event.y
//
//                    // Calculate bitmap coordinates
//                    val (bitmapX, bitmapY) = getBitmapCoordinates(x, y)
//
//                    // Draw a ring at the touched position
//                    drawRing(bitmapX, bitmapY)
//
//                    // Make the ring invisible after a delay
//                    v.postDelayed({
//                        // Redraw the image without the ring
//                        binding.imgEditBeautifySelectImage.setImageBitmap(originalBitmap)
//                    }, 500) // Adjust delay as needed
//
//                    true
//                }
//                else -> false
//            }
//        }


        binding.imgEditBeautifySelectImage.setOnTouchListener { v, event ->
            when (event.action)
            {
                MotionEvent.ACTION_DOWN -> {
                    val x = event.x
                    val y = event.y

                    val (bitmapX, bitmapY) = getBitmapCoordinates(x, y)
                    drawRing(bitmapX, bitmapY)

                    // Handle blemish removal with a delay
                    v.postDelayed({
                        val blemishRect = RectF(bitmapX - 50, bitmapY - 50, bitmapX + 50, bitmapY + 50)
                        mutableBitmap = applyBlurEffect(mutableBitmap, blemishRect)
                        binding.imgEditBeautifySelectImage.setImageBitmap(mutableBitmap)
                    }, 500)

                    true
                }
                else -> false
            }
        }
    }

    private fun getBitmapCoordinates(viewX: Float, viewY: Float): Pair<Float, Float> {

//        val imageView = binding.imgEditBeautifySelectImage
//
//        // Get and invert the image matrix
//        val matrix = imageView.imageMatrix
//        val inverseMatrix = Matrix()
//        matrix.invert(inverseMatrix)
//
//        // Map view coordinates to bitmap coordinates
//        val mappedCoords = floatArrayOf(viewX, viewY)
//        inverseMatrix.mapPoints(mappedCoords)
//
//        return Pair(mappedCoords[0], mappedCoords[1])

        val imageView = binding.imgEditBeautifySelectImage

        // Get and invert the image matrix
        val matrix = imageView.imageMatrix
        val inverseMatrix = Matrix()
        matrix.invert(inverseMatrix)

        // Map view coordinates to bitmap coordinates
        val mappedCoords = floatArrayOf(viewX, viewY)
        inverseMatrix.mapPoints(mappedCoords)

        return Pair(mappedCoords[0], mappedCoords[1])
    }



    private fun drawRing(x: Float, y: Float)
    {
//        // Create a mutable bitmap
//        mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
//        val canvas = Canvas(mutableBitmap)
//        val paint = Paint().apply {
//            color = Color.BLACK // Ring color
//            style = Paint.Style.STROKE // Only stroke, no fill
//            strokeWidth = 5f // Ring thickness
//            isAntiAlias = true
//        }
//
//        val radius = 50f // Radius of the ring
//
//        // Draw the ring
//        canvas.drawCircle(x, y, radius, paint)
//
//        // Update the ImageView
//        binding.imgEditBeautifySelectImage.setImageBitmap(mutableBitmap)

        // Create a mutable bitmap from the original
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            color = Color.BLACK // Ring color
            style = Paint.Style.STROKE // Only stroke, no fill
            strokeWidth = 5f // Ring thickness
            isAntiAlias = true
        }

        val radius = 50f // Radius of the ring

        // Draw the ring
        canvas.drawCircle(x, y, radius, paint)

        // Update the ImageView
        binding.imgEditBeautifySelectImage.setImageBitmap(mutableBitmap)
    }

    // Function to apply blur on a bitmap
    private fun blurBitmap(bitmap: Bitmap, radius: Float): Bitmap
    {
        val outputBitmap = Bitmap.createBitmap(bitmap)
        val rs = RenderScript.create(this)
        val input = Allocation.createFromBitmap(rs, bitmap)
        val output = Allocation.createTyped(rs, input.type)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setRadius(radius)
        script.setInput(input)
        script.forEach(output)
        output.copyTo(outputBitmap)
        rs.destroy()
        return outputBitmap
    }


    private fun applyBlemishEffectInRing(x: Float, y: Float)
    {
        // Create a mutable bitmap from the original
        val mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        // Define the radius for the blur area
        val blurRadius = 50f // Radius of the area to blur

        // Create a paint object with a mask filter for the blur effect
        val paint = Paint().apply {
            isAntiAlias = true
            maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
        }

        // Apply the blur in a circular shape
        canvas.drawCircle(x, y, blurRadius, paint)

        // Update the ImageView with the new bitmap
        binding.imgEditBeautifySelectImage.setImageBitmap(mutableBitmap)
    }


    private fun applyBlurEffect(bitmap: Bitmap, blemishRect: RectF): Bitmap {
        // Create a mutable bitmap to draw on
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)

        // Create a paint object for the blur effect
        val blurPaint = Paint().apply {
            val radius = 25f // Adjust as needed for blur strength
            maskFilter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
        }

        // Create a bitmap for the effect
        val effectBitmap = Bitmap.createBitmap(outputBitmap.width, outputBitmap.height, Bitmap.Config.ARGB_8888)
        val effectCanvas = Canvas(effectBitmap)

        // Draw the original bitmap onto the effectCanvas
        effectCanvas.drawBitmap(outputBitmap, 0f, 0f, null)

        // Draw a blurred circle on the effectCanvas
        effectCanvas.drawCircle(blemishRect.centerX(), blemishRect.centerY(), blemishRect.width() / 2, blurPaint)

        // Create a paint object to blend the effect
        val blendPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER) }

        // Draw the effect bitmap onto the output bitmap
        canvas.drawBitmap(effectBitmap, 0f, 0f, blendPaint)

        return outputBitmap
    }


    // Function to apply a white blur effect in a ring shape at the specified coordinates
    private fun applyWhiteBlurEffectInRing(x: Float, y: Float) {
        // Create a mutable bitmap from the original
        val mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        // Define the radius for the white blur area
        val blurRadius = 50f // Radius of the area to blur

        // Create a paint object with white color and transparency
        val paint = Paint().apply {
            color = Color.WHITE
            alpha = 150 // Adjust the alpha value for transparency (0-255)
            isAntiAlias = true
        }

        // Draw a white circle at the touched position
        canvas.drawCircle(x, y, blurRadius, paint)

        // Update the ImageView with the new bitmap
        binding.imgEditBeautifySelectImage.setImageBitmap(mutableBitmap)
    }








    private fun removeRing(x: Float, y: Float) {
        // Recreate the bitmap to "remove" the ring by redrawing the original bitmap
        mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            // Ensure the ring is not redrawn, effectively "removing" it
            color = Color.TRANSPARENT
            style = Paint.Style.STROKE
            strokeWidth = 0f
            isAntiAlias = true
        }

        // We redraw the original image without the ring by not drawing it again
        // Update the ImageView
        binding.imgEditBeautifySelectImage.setImageBitmap(mutableBitmap)
    }


    private fun initializeBitmaps(bitmap: Bitmap) {
//        originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
//        mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
//        binding.imgEditBeautifySelectImage.setImageBitmap(mutableBitmap)

        originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.ring) // Replace with your actual image
        drawingBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        ringCanvas = Canvas(drawingBitmap)
        drawingCanvas = Canvas(drawingBitmap)
        binding.imgEditBeautifySelectImage.setImageBitmap(drawingBitmap)
    }


    private fun initializeBitmaps() {
        val resourceId = R.drawable.ring
        originalBitmap = BitmapFactory.decodeResource(resources, resourceId)

        if (originalBitmap == null) {
            Log.e("Beautify_Activity", "Failed to decode resource. Bitmap is null.")
            // Handle the error, e.g., show a message to the user or use a default bitmap
            return
        }

        drawingBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        ringCanvas = Canvas(drawingBitmap)
        drawingCanvas = Canvas(drawingBitmap)
        binding.imgEditBeautifySelectImage.setImageBitmap(drawingBitmap)
    }




    fun smoothfilter() {
        binding.skbSmooth.max = 100
        binding.skbSmooth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)
            {
                val intensity = progress / 100f

                // Cancel ongoing tasks to avoid redundant processing
                coroutineScope.coroutineContext[Job]?.cancelChildren()

                // Launch a coroutine for background processing
                coroutineScope.launch {
                    val noiseReducedBitmap = withContext(Dispatchers.IO) {
                        applyGPUImageSmoothing(originalBitmap, intensity)
                    }
                    // Update the UI with the processed image
                    originalBitmap = noiseReducedBitmap
                    binding.imgEditBeautifySelectImage.setImageBitmap(originalBitmap)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun Brightenfilter() {
        binding.skbBrighten.max = 100
        binding.skbBrighten.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {


                val intensity = progress / 100f

                // Cancel ongoing tasks to avoid redundant processing
                coroutineScope.coroutineContext[Job]?.cancelChildren()

                // Launch a coroutine for background processing
                coroutineScope.launch {
                    // Your background processing logic
//                    val brightenedBitmap = withContext(Dispatchers.Default) {
                    // Do the image processing here (this runs on a background thread)
                    applyBrightFilter(intensity) // Replace with your image processing code
//                    }

//                    // Now update the ImageView on the main thread
//                    withContext(Dispatchers.Main) {
//                        binding.imgEditBeautifySelectImage.setImageBitmap(brightenedBitmap)
//                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun Sharpenfilter() {
        binding.skbSharpen.max = 100
        binding.skbSharpen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                val intensity = progress / 100f

                // Cancel ongoing tasks to avoid redundant processing
                coroutineScope.coroutineContext[Job]?.cancelChildren()

                // Launch a coroutine for background processing
                coroutineScope.launch {
                    val sharpenedBitmap = withContext(Dispatchers.IO) {
                        applySharpenWithGPUImage(originalBitmap, intensity)
                    }
                    originalBitmap = sharpenedBitmap
                    // Update the UI with the processed image
                    binding.imgEditBeautifySelectImage.setImageBitmap(originalBitmap)
                }

//                val intensity = progress / 100f // Convert to a range of 0.0 to 1.0
//                val sharpenedBitmap = applyCustomSharpenFilter(originalBitmap, intensity)
//                binding.imgEditBeautifySelectImage.setImageBitmap(sharpenedBitmap)


//                val intensity = progress / 100f
//                applyCustomSharpen(originalBitmap, intensity)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun modifyFaceInBitmap(bitmap: Bitmap, faceRect: Rect, scaleFactor: Float): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        // Ensure the faceRect is within bounds
        val (left, top, right, bottom) = listOf(
            maxOf(faceRect.left, 0),
            maxOf(faceRect.top, 0),
            minOf(faceRect.right, bitmap.width),
            minOf(faceRect.bottom, bitmap.height)
        )

        val faceWidth = right - left
        val faceHeight = bottom - top
        val newFaceWidth = (faceWidth * scaleFactor).toInt()
        val newFaceHeight = (faceHeight * scaleFactor).toInt()

        if (newFaceWidth <= 0 || newFaceHeight <= 0) {
            throw IllegalArgumentException("New face dimensions must be greater than 0")
        }

        val newLeft = maxOf(left - (newFaceWidth - faceWidth) / 2, 0)
        val newTop = maxOf(top - (newFaceHeight - faceHeight) / 2, 0)

        val adjustedRight = minOf(newLeft + newFaceWidth, bitmap.width)
        val adjustedBottom = minOf(newTop + newFaceHeight, bitmap.height)

        val faceBitmap = Bitmap.createBitmap(newFaceWidth, newFaceHeight, Bitmap.Config.ARGB_8888)
        val faceCanvas = Canvas(faceBitmap)

        val paint = Paint()
        faceCanvas.drawBitmap(
            mutableBitmap,
            Rect(left, top, right, bottom),
            Rect(0, 0, newFaceWidth, newFaceHeight),
            paint
        )
        canvas.drawBitmap(faceBitmap, newLeft.toFloat(), newTop.toFloat(), null)

        return mutableBitmap
    }

    fun scaleFaceInBitmap(bitmap: Bitmap, faceRect: Rect, scaleFactor: Float): Bitmap {
        // Validate scale factor
        if (scaleFactor <= 0) {
            throw IllegalArgumentException("Scale factor must be greater than 0")
        }

        // Create a mutable copy of the original bitmap
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        // Ensure the faceRect is within bounds
        val left = maxOf(faceRect.left, 0)
        val top = maxOf(faceRect.top, 0)
        val right = minOf(faceRect.right, bitmap.width)
        val bottom = minOf(faceRect.bottom, bitmap.height)

        val faceWidth = right - left
        val faceHeight = bottom - top

        // Calculate new dimensions and position
        val newFaceWidth = (faceWidth * scaleFactor).toInt()
        val newFaceHeight = (faceHeight * scaleFactor).toInt()

        // Ensure new dimensions are valid
        if (newFaceWidth <= 0 || newFaceHeight <= 0) {
            return mutableBitmap // Return the original bitmap if dimensions are invalid
        }

        val newLeft = left - (newFaceWidth - faceWidth) / 2
        val newTop = top - (newFaceHeight - faceHeight) / 2

        // Ensure new position is within bounds
        val adjustedLeft = maxOf(newLeft, 0)
        val adjustedTop = maxOf(newTop, 0)
        val adjustedRight = minOf(adjustedLeft + newFaceWidth, bitmap.width)
        val adjustedBottom = minOf(adjustedTop + newFaceHeight, bitmap.height)

        // Create a new bitmap to hold the scaled face
        val faceBitmap = Bitmap.createBitmap(newFaceWidth, newFaceHeight, Bitmap.Config.ARGB_8888)
        val faceCanvas = Canvas(faceBitmap)

        // Draw the face region onto the new bitmap
        val paint = Paint()
        faceCanvas.drawBitmap(
            mutableBitmap,
            Rect(left, top, right, bottom),
            Rect(0, 0, newFaceWidth, newFaceHeight),
            paint
        )

        // Replace the face region in the original bitmap
        canvas.drawBitmap(faceBitmap, adjustedLeft.toFloat(), adjustedTop.toFloat(), null)

        return mutableBitmap
    }


    private fun adjustFaceSize(scaleFactor: Double) {
        val face = detectedFace ?: return

        // Calculate the new dimensions of the face
        val newFaceWidth = (face.boundingBox.width() * scaleFactor).toInt().coerceAtLeast(1)
        val newFaceHeight = (face.boundingBox.height() * scaleFactor).toInt().coerceAtLeast(1)

        // Extract the face bitmap
        val faceBitmap = Bitmap.createBitmap(
            originalBitmap,
            face.boundingBox.left,
            face.boundingBox.top,
            face.boundingBox.width(),
            face.boundingBox.height()
        )

        // Ensure faceBitmap has valid dimensions
        if (faceBitmap.width <= 0 || faceBitmap.height <= 0) {
            return
        }

        // Scale the face bitmap
        val scaledFaceBitmap =
            Bitmap.createScaledBitmap(faceBitmap, newFaceWidth, newFaceHeight, true)

        // Create a mutable copy of the original bitmap
        val resultBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(resultBitmap)

        // Clear the original face region
        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawRect(face.boundingBox, paint)

        // Calculate the position to draw the scaled face
        val offsetX = face.boundingBox.left + (face.boundingBox.width() - newFaceWidth) / 2
        val offsetY = face.boundingBox.top + (face.boundingBox.height() - newFaceHeight) / 2

        // Draw the scaled face back onto the original bitmap
        paint.xfermode = null
        canvas.drawBitmap(scaledFaceBitmap, offsetX.toFloat(), offsetY.toFloat(), paint)

        // Update the ImageView with the modified bitmap
        binding.imgEditBeautifySelectImage.setImageBitmap(resultBitmap)
    }

    fun scaleFaceDirectly(bitmap: Bitmap, faceRect: Rect, scaleFactor: Float): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        // Create a mutable copy of the original bitmap
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Extract the face region from the mutable bitmap
        val faceBitmap = Bitmap.createBitmap(
            mutableBitmap,
            faceRect.left,
            faceRect.top,
            faceRect.width(),
            faceRect.height()
        )

        // Scale the face region
        val matrix = Matrix()
        matrix.postScale(scaleFactor, scaleFactor)

        val scaledFaceBitmap = Bitmap.createBitmap(
            faceBitmap,
            0,
            0,
            faceBitmap.width,
            faceBitmap.height,
            matrix,
            true
        )

        // Calculate the new face rectangle
        val newFaceWidth = (faceRect.width() * scaleFactor).toInt()
        val newFaceHeight = (faceRect.height() * scaleFactor).toInt()
        val newFaceRect = Rect(
            faceRect.left - (newFaceWidth - faceRect.width()) / 2,
            faceRect.top - (newFaceHeight - faceRect.height()) / 2,
            faceRect.left + newFaceWidth - (newFaceWidth - faceRect.width()) / 2,
            faceRect.top + newFaceHeight - (newFaceHeight - faceRect.height()) / 2
        )

        // Replace the face region in the original bitmap
        val canvas = android.graphics.Canvas(mutableBitmap)
        canvas.drawBitmap(scaledFaceBitmap, null, newFaceRect, null)

        return mutableBitmap
    }

//    private fun detectFaceInImage(bitmap: Bitmap) {
//        detectFaces(
//            bitmap,
//            onSuccess = { faces ->
//                if (faces.isNotEmpty()) {
//                    detectedFace = faces[0]
//                    faceBounds = detectedFace!!.boundingBox
//
//                    // Store landmarks in class-level variables
//                    leftEye = detectedFace!!.getLandmark(FaceLandmark.LEFT_EYE)?.position
//                    rightEye = detectedFace!!.getLandmark(FaceLandmark.RIGHT_EYE)?.position
//                    noseBase = detectedFace!!.getLandmark(FaceLandmark.NOSE_BASE)?.position
//                    leftMouth = detectedFace!!.getLandmark(FaceLandmark.MOUTH_LEFT)?.position
//                    rightMouth = detectedFace!!.getLandmark(FaceLandmark.MOUTH_RIGHT)?.position
//
//                    // Enable the SeekBar after detection
//                    binding.lnrskbface.isEnabled = true
//
//                    Toast.makeText(this, "Face detected", Toast.LENGTH_SHORT).show()
//                } else {
//                    detectedFaces = emptyList()
//                    Toast.makeText(this, "No face detected", Toast.LENGTH_SHORT).show()
//                }
//            },
//            onFailure = { e ->
//                e.printStackTrace()
//                detectedFaces = emptyList()
//                Toast.makeText(this, "Failed to detect face", Toast.LENGTH_SHORT).show()
//            }
//        )
//    }


    private fun adjustFaceSize(progress: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            detectedFace?.let { face ->
                val newBitmap = withContext(Dispatchers.Default) {
                    jawlineEditor.adjustJawline(originalBitmap, face, progress)
                }
                binding.imgEditBeautifySelectImage.setImageBitmap(newBitmap)
            }
        }
    }

    private fun detectFaces(
        bitmap: Bitmap,
        onSuccess: (List<Face>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        val detector = FaceDetection.getClient(options)
        val image = InputImage.fromBitmap(bitmap, 0)
        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    // Get the first detected face (assuming there's only one face in the image)
                    val face = faces[0]

                    // Extract positions of key facial landmarks
                    val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)?.position
                    val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)?.position
                    val noseBase = face.getLandmark(FaceLandmark.NOSE_BASE)?.position
                    val leftMouth = face.getLandmark(FaceLandmark.MOUTH_LEFT)?.position
                    val rightMouth = face.getLandmark(FaceLandmark.MOUTH_RIGHT)?.position

                    // Ensure all landmarks were successfully detected before applying reshaping
                    if (leftEye != null && rightEye != null && noseBase != null && leftMouth != null && rightMouth != null) {
                        // Apply the reshaping effect based on the positions of these landmarks
                        val reshapedBitmap = applyReshapingEffect(originalBitmap!!, 50)
                        binding.imgEditBeautifySelectImage.setImageBitmap(reshapedBitmap)
                    }
                }
            }
            .addOnFailureListener {
                // Handle any errors during face detection
            }
    }


    private fun adjustFaceSize(bitmap: Bitmap) {
        detectFaces(
            bitmap,
            onSuccess = { faces ->
                if (faces.isNotEmpty()) {
                    val detectedFace = faces[0]  // Assuming there's only one face
                    faceBounds = detectedFace.boundingBox

                    // Retrieve landmarks
                    leftEye = detectedFace.getLandmark(FaceLandmark.LEFT_EYE)?.position
                    rightEye = detectedFace.getLandmark(FaceLandmark.RIGHT_EYE)?.position
                    noseBase = detectedFace.getLandmark(FaceLandmark.NOSE_BASE)?.position
                    leftMouth = detectedFace.getLandmark(FaceLandmark.MOUTH_LEFT)?.position
                    rightMouth = detectedFace.getLandmark(FaceLandmark.MOUTH_RIGHT)?.position

                    // Debugging: Print the landmarks
                    Log.d(
                        "FaceReshape",
                        "Left Eye: $leftEye, Right Eye: $rightEye, Nose Base: $noseBase, Left Mouth: $leftMouth, Right Mouth: $rightMouth"
                    )

                    if (leftEye != null && rightEye != null && noseBase != null && leftMouth != null && rightMouth != null) {
                        binding.lnrskbface.isEnabled = true
                        Toast.makeText(this, "Face detected with landmarks", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Landmarks missing for reshaping", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "No face detected", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = { e ->
                e.printStackTrace()
                Toast.makeText(this, "Failed to detect face", Toast.LENGTH_SHORT).show()
            }
        )
    }


    private fun detectFaceInImage(bitmap: Bitmap) {
        detectFaces(
            bitmap,
            onSuccess = { faces ->
                if (faces.isNotEmpty()) {
                    detectedFace = faces[0]
                    faceBounds = detectedFace!!.boundingBox

                    leftEye = detectedFace!!.getLandmark(FaceLandmark.LEFT_EYE)?.position
                    rightEye = detectedFace!!.getLandmark(FaceLandmark.RIGHT_EYE)?.position
                    noseBase = detectedFace!!.getLandmark(FaceLandmark.NOSE_BASE)?.position
                    leftMouth = detectedFace!!.getLandmark(FaceLandmark.MOUTH_LEFT)?.position
                    rightMouth = detectedFace!!.getLandmark(FaceLandmark.MOUTH_RIGHT)?.position

                    // Reshape the face
//                    val reshapedBitmap = reshapeFace(bitmap)

                    // Display or use the reshaped bitmap
//                    binding.imgEditBeautifySelectImage.setImageBitmap(reshapedBitmap)

                    binding.lnrskbface.isEnabled = true
                    Toast.makeText(this, "Face detected and reshaped", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    detectedFaces = emptyList()
                    Toast.makeText(this, "No face detected", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = { e ->
                e.printStackTrace()
                detectedFaces = emptyList()
                Toast.makeText(this, "Failed to detect face", Toast.LENGTH_SHORT).show()
            }
        )
    }


    private fun distanceBetween(p1: PointF, p2: PointF): Float {
        return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))
    }

    private fun loadImageAsync(imageUri: Uri) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                originalBitmap = withContext(Dispatchers.IO) {
                    getBitmapFromUri(imageUri)
                }
                binding.imgEditBeautifySelectImage.setImageBitmap(originalBitmap)
                detectFaceInImage(originalBitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@Beautify_Activity, "Failed to load image", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private suspend fun getBitmapFromUri(uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            bitmap ?: throw IOException("Unable to decode bitmap from URI: $uri")
        }
    }

    private fun handleSeekBarProgress(progress: Int, max: Int) {
        val intensity = progress / 100f

        when (currentButton) {
            R.id.lnrSmooth -> applyFilter(progress, max)
            R.id.lnrBrighten -> applyNoise(progress, max)
            R.id.lnrSharpen -> applySharpenWithGPUImage(originalBitmap, intensity)
        }
    }

    fun scaleBitmap(bitmap: Bitmap, scaleFactor: Float): Bitmap {
        val matrix = Matrix().apply {
            setScale(scaleFactor, scaleFactor)
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun applyTransformation(progress: Int) {
        val factor = progress / 100.0f
//        val transformedBitmap = transformImage(originalBitmap, factor)
        binding.imgEditBeautifySelectImage.setImageBitmap(transformedBitmap)
    }

    private fun updateBlendedImage(progress: Int) {
        if (originalBitmap != null && transformedBitmap != null) {
            val blendedBitmap =
                blendImages(originalBitmap!!, transformedBitmap!!, progress / 100.0f)
            binding.imgEditBeautifySelectImage.setImageBitmap(blendedBitmap)
        }
    }

    private fun blendImages(originalBitmap: Bitmap, transformedBitmap: Bitmap, alpha: Float): Bitmap {
        val blendedBitmap = Bitmap.createBitmap(
            originalBitmap.width,
            originalBitmap.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(blendedBitmap)
        val paint = Paint()

        // Draw original image
        canvas.drawBitmap(originalBitmap, 0f, 0f, null)

        // Setup paint for blending
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        paint.alpha = (alpha * 255).toInt()

        // Draw transformed image
        canvas.drawBitmap(transformedBitmap, 0f, 0f, paint)

        return blendedBitmap
    }

    private fun applyFilter(progress: Int, max: Int) {
        if (originalBitmap != null) {
            val intensity = progress / 50.0f // Map SeekBar progress to 0.0 to 1.0
            val filteredBitmap = applySmoothFilter(originalBitmap!!, intensity)
            binding.imgEditBeautifySelectImage.setImageBitmap(filteredBitmap)
        }
    }

    private fun applySmoothFilter(bitmap: Bitmap, intensity: Float): Bitmap {
        val contrast = 1 + intensity // Adjust contrast
        val brightness = 0.00001f * intensity // Extremely light brightness adjustment

        // Adjust scaling factor for brightness effect
        val brightnessScalingFactor = 50f // Example of reduced scaling factor

        val cm = ColorMatrix(
            floatArrayOf(
                contrast, 0f, 0f, 0f, brightness * brightnessScalingFactor,
                0f, contrast, 0f, 0f, brightness * brightnessScalingFactor,
                0f, 0f, contrast, 0f, brightness * brightnessScalingFactor,
                0f, 0f, 0f, 1f, 0f
            )
        )

        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(cm)

        val outputBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(outputBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return outputBitmap
    }

    private fun applyNoise(progress: Int, max: Int) {
        if (originalBitmap != null) {
            val intensity = progress / 50.0f // Map SeekBar progress to 0.0 to 1.0
            val filteredBitmap = applyNoiseReduction(originalBitmap!!, intensity)
            binding.imgEditBeautifySelectImage.setImageBitmap(filteredBitmap)
        }
    }

    private fun applyNoiseReduction(bitmap: Bitmap, intensity: Float): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val effectiveIntensity = intensity.coerceIn(0f, 1f)

        for (x in 1 until width - 1) {
            for (y in 1 until height - 1) {
                val pixel = bitmap.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                var avgRed = 0
                var avgGreen = 0
                var avgBlue = 0
                var count = 0

                for (dx in -1..1) {
                    for (dy in -1..1) {
                        if (dx != 0 || dy != 0) {
                            val neighborPixel = bitmap.getPixel(x + dx, y + dy)
                            avgRed += Color.red(neighborPixel)
                            avgGreen += Color.green(neighborPixel)
                            avgBlue += Color.blue(neighborPixel)
                            count++
                        }
                    }
                }

                avgRed =
                    ((avgRed / count) * effectiveIntensity + red * (1 - effectiveIntensity)).toInt()
                avgGreen =
                    ((avgGreen / count) * effectiveIntensity + green * (1 - effectiveIntensity)).toInt()
                avgBlue =
                    ((avgBlue / count) * effectiveIntensity + blue * (1 - effectiveIntensity)).toInt()

                outputBitmap.setPixel(x, y, Color.rgb(avgRed, avgGreen, avgBlue))
            }
        }

        return outputBitmap
    }

    private fun applyBlur(bitmap: Bitmap, radius: Float): Bitmap {
        // RenderScript Blur example (you can also use custom filter implementations)
        // Assuming the blur radius is within a reasonable range
        val bitmapOut = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val renderScript = RenderScript.create(this)
        val input = Allocation.createFromBitmap(renderScript, bitmap)
        val output = Allocation.createFromBitmap(renderScript, bitmapOut)
        val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        blurScript.setRadius(radius)
        blurScript.setInput(input)
        blurScript.forEach(output)
        output.copyTo(bitmapOut)

        renderScript.destroy()

        return bitmapOut
    }

    private fun clamp(value: Int): Int {
        return value.coerceIn(0, 255)
    }

    private fun processFace(face: Face) {
        val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)?.position
        val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)?.position
        val noseBase = face.getLandmark(FaceLandmark.NOSE_BASE)?.position
        val leftMouth = face.getLandmark(FaceLandmark.MOUTH_LEFT)?.position
        val rightMouth = face.getLandmark(FaceLandmark.MOUTH_RIGHT)?.position

        if (leftEye != null && rightEye != null && noseBase != null && leftMouth != null && rightMouth != null) {
            val reshapedBitmap = applyReshapingEffect(originalBitmap!!, 50)
            binding.imgEditBeautifySelectImage.setImageBitmap(reshapedBitmap)
        }
    }


    private fun applyReshapingEffect(bitmap: Bitmap, progress: Int): Bitmap {
        val stretchFactor = 1.0f + progress / 100f

        // Use the detected landmarks instead of hardcoded values
        val leftEye = this.leftEye ?: return bitmap
        val rightEye = this.rightEye ?: return bitmap
        val noseBase = this.noseBase ?: return bitmap
        val leftMouth = this.leftMouth ?: return bitmap
        val rightMouth = this.rightMouth ?: return bitmap

        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(newBitmap)
        val paint = Paint()

        // Calculate new positions based on the stretch factor
        val newRightEye = PointF(leftEye.x + (rightEye.x - leftEye.x) * stretchFactor, rightEye.y)
        val newRightMouth =
            PointF(leftMouth.x + (rightMouth.x - leftMouth.x) * stretchFactor, rightMouth.y)

        // Apply a transformation between original and new points
        val srcPoints = floatArrayOf(
            leftEye.x, leftEye.y,
            rightEye.x, rightEye.y,
            noseBase.x, noseBase.y,
            leftMouth.x, leftMouth.y,
            rightMouth.x, rightMouth.y
        )

        val dstPoints = floatArrayOf(
            leftEye.x, leftEye.y,
            newRightEye.x, newRightEye.y,
            noseBase.x, noseBase.y,
            leftMouth.x, leftMouth.y,
            newRightMouth.x, newRightMouth.y
        )

        val matrix = Matrix()
        matrix.setPolyToPoly(srcPoints, 0, dstPoints, 0, srcPoints.size / 2)

        // Apply the transformation to the canvas
        canvas.drawBitmap(bitmap, matrix, paint)

        return newBitmap
    }

    private fun applyBrightFilter(intensity: Float) {
        // Map SeekBar intensity to a brightness scale (e.g., 0.5x to 1.5x)
        val brightness = 0.5f + intensity // Adjust the factor as needed

        // Create a color matrix for brightness adjustment
        val colorMatrix = ColorMatrix().apply {
            setScale(brightness, brightness, brightness, 1f)
        }

        // Create a color filter with the color matrix
        val colorFilter = ColorMatrixColorFilter(colorMatrix)

        // Apply the color filter to the filtered bitmap
        val filteredBitmap =
            Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)
        val canvas = Canvas(filteredBitmap)
        val paint = Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the filtered bitmap
        binding.imgEditBeautifySelectImage.setImageBitmap(filteredBitmap)
    }

    fun applySharpenWithGPUImage(bitmap: Bitmap, intensity: Float): Bitmap {
        val freshBitmap = originalBitmap.copy(originalBitmap.config, true)

        val gpuImage = GPUImage(this)
        gpuImage.setImage(freshBitmap) // Always start with a fresh copy of the original bitmap

        val sharpenFilter = GPUImageSharpenFilter()
        sharpenFilter.setSharpness(intensity)
        gpuImage.setFilter(sharpenFilter)

        return gpuImage.bitmapWithFilterApplied
    }

    fun applyGPUImageSmoothing(inputBitmap: Bitmap, intensity: Float): Bitmap {
        val freshBitmap = originalBitmap.copy(originalBitmap.config, true)

        val gpuImage = GPUImage(this)
        gpuImage.setImage(freshBitmap)

        var smooth = GPUImageGaussianBlurFilter()
        smooth.setBlurSize(intensity)
        gpuImage.setFilter(smooth)

        // Return the bitmap with filter applied
        return gpuImage.bitmapWithFilterApplied
    }


    private fun adjustThinness(scaleFactor: Float) {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val image = InputImage.fromBitmap(originalBitmap, 0)
        val detector = FaceDetection.getClient(options)

        detector.process(image)
            .addOnSuccessListener { faces ->
                for (face in faces) {
                    modifiedBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
                    val canvas = Canvas(modifiedBitmap)
                    val paint = Paint()
                    paint.isAntiAlias = true

                    val bounds = face.boundingBox

                    // Extract face region
                    val faceBitmap = Bitmap.createBitmap(
                        originalBitmap,
                        bounds.left,
                        bounds.top,
                        bounds.width(),
                        bounds.height()
                    )

                    // Transform face region
                    val faceMatrix = Matrix()
                    faceMatrix.postScale(
                        scaleFactor,
                        1.0f,
                        bounds.width() / 2f,
                        bounds.height() / 2f
                    )
                    val transformedFaceBitmap = Bitmap.createBitmap(
                        faceBitmap,
                        0,
                        0,
                        faceBitmap.width,
                        faceBitmap.height,
                        faceMatrix,
                        true
                    )

                    // Draw transformed face back onto the canvas
                    canvas.drawBitmap(
                        transformedFaceBitmap,
                        bounds.left.toFloat(),
                        bounds.top.toFloat(),
                        paint
                    )

                    binding.imgEditBeautifySelectImage.setImageBitmap(modifiedBitmap)
                }
            }
            .addOnFailureListener { e ->
                // Handle detection failure
            }
    }


    private fun updateFaceUI() {
        faceBounds?.let { bounds ->
            // Optionally, draw the detected face bounds or landmarks on a Canvas if needed
            // For example, you could overlay a rectangle or markers on the ImageView
        }
    }

    override fun onBackPressed() {
        if (binding.lnrskbSettingvisibility.visibility == View.VISIBLE ||
            binding.lnrSettingvisibility.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityLip.visibility == View.VISIBLE ||
            binding.lnrvisibilityLipcolor.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityBlush.visibility == View.VISIBLE ||
            binding.lnrvisibilityBlush.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityContour.visibility == View.VISIBLE ||
            binding.lnrvisibilityContour.visibility == View.VISIBLE
        ) {
            // Hide all the views
            binding.lnrskbSettingvisibility.visibility = View.GONE
            binding.lnrSettingvisibility.visibility = View.GONE
            binding.lnrskbvisibilityLip.visibility = View.GONE
            binding.lnrvisibilityLipcolor.visibility = View.GONE
            binding.lnrskbvisibilityBlush.visibility = View.GONE
            binding.lnrvisibilityBlush.visibility = View.GONE
            binding.lnrskbvisibilityContour.visibility = View.GONE
            binding.lnrvisibilityContour.visibility = View.GONE

            // Show the main view
            binding.lnrvisibility.visibility = View.VISIBLE
        } else {
            // If all views are already hidden, proceed with the default back button behavior
            super.onBackPressed()
        }
    }

    private fun logErrorAndFinish(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::renderScript.isInitialized1) {
            renderScript.destroy()
            Log.d("Beautify_Activity", "RenderScript destroyed")
        }
        rs.destroy()
        coroutineScope.cancel()
    }


    private fun detectFace(image: InputImage, callback: FaceDetectionCallback) {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(options)

        detector.process(image)
            .addOnSuccessListener { faces ->
                callback.onSuccess(faces)
            }
            .addOnFailureListener { e ->
                callback.onFailure(e)
            }
    }


    private fun reshapeFace(bitmap: Bitmap, face: Face, scaleX: Float): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK)?.position
        val rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)?.position
        val noseBase = face.getLandmark(FaceLandmark.NOSE_BASE)?.position

        if (leftCheek != null && rightCheek != null && noseBase != null) {
            val leftBound = leftCheek.x.toInt()
            val rightBound = rightCheek.x.toInt()
            val topBound = (leftCheek.y - 50).toInt()
            val bottomBound = (noseBase.y + 100).toInt()

            if (leftBound >= 0 && topBound >= 0 && rightBound <= bitmap.width && bottomBound <= bitmap.height) {
                val faceRegion = Rect(leftBound, topBound, rightBound, bottomBound)
                val faceBitmap = Bitmap.createBitmap(
                    bitmap,
                    faceRegion.left,
                    faceRegion.top,
                    faceRegion.width(),
                    faceRegion.height()
                )

                val matrix = Matrix().apply {
                    postScale(scaleX, 1.0f, faceBitmap.width / 2f, faceBitmap.height / 2f)
                }

                val resizedFaceBitmap = Bitmap.createBitmap(
                    faceBitmap,
                    0,
                    0,
                    faceBitmap.width,
                    faceBitmap.height,
                    matrix,
                    true
                )

                val newFaceRegion = Rect(
                    faceRegion.left - (resizedFaceBitmap.width - faceBitmap.width) / 2,
                    faceRegion.top - (resizedFaceBitmap.height - faceBitmap.height) / 2,
                    faceRegion.left - (resizedFaceBitmap.width - faceBitmap.width) / 2 + resizedFaceBitmap.width,
                    faceRegion.top - (resizedFaceBitmap.height - faceBitmap.height) / 2 + resizedFaceBitmap.height
                )

                if (newFaceRegion.left >= 0 && newFaceRegion.top >= 0 && newFaceRegion.right <= bitmap.width && newFaceRegion.bottom <= bitmap.height) {
                    canvas.drawBitmap(resizedFaceBitmap, null, newFaceRegion, paint)
                }
            }
        }

        return mutableBitmap
    }

    interface FaceDetectionCallback {
        fun onSuccess(faces: List<Face>)
        fun onFailure(e: Exception)
    }

}

data class ReshapeParams(
    var thin: Float = 0f,
    var width: Float = 0f,
    var jaw: Float = 0f,
    var forehead: Float = 0f,
    var size: Float = 0f
)
