//package com.example.photoeditorpolishanything
//
//import android.annotation.SuppressLint
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.graphics.Path
//import android.graphics.PorterDuff
//import android.graphics.PorterDuffXfermode
//import android.graphics.drawable.GradientDrawable
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.view.MotionEvent
//import android.view.View
//import android.view.WindowManager
//import android.widget.ImageView
//import android.widget.SeekBar
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import com.bumptech.glide.Glide
//import com.example.photoeditorpolishanything.databinding.ActivityDrawBinding
//import yuku.ambilwarna.AmbilWarnaDialog
//
//class Draw_Activity : AppCompatActivity() {
//    lateinit var binding: ActivityDrawBinding
//    private var currentColor: Int = Color.WHITE
//    private var mDefaultColor = 0
//
//    private lateinit var originalBitmap: Bitmap
//    private lateinit var drawingBitmap: Bitmap
//    private lateinit var combinedBitmap: Bitmap
//    private lateinit var canvas: Canvas
//    private lateinit var paint: Paint
//    private lateinit var path: Path
//    private val touchTolerance = 4f
//    private var lastX = 0f
//    private var lastY = 0f
//
//    private var isEraserActive = false
//
//
//    private var selectedColorView: View? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDrawBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            val window = this.window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.statusBarColor = this.resources.getColor(R.color.black)
//        }
//        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
//
//        initView()
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun initView() {
//        mDefaultColor = 0
//
//        binding.imgback.setOnClickListener {
//            onBackPressed()
//        }
//
//        binding.lnrColor.setOnClickListener {
//            openColorPickerDialog()
//        }
//
//        // Preset color click listeners
//        binding.civWhite.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.white), it) }
//        binding.civGrey.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.lightgrey), it) }
//        binding.civDarkGrey.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.DarkGrey), it) }
//        binding.civBlack.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.black), it) }
//        binding.civBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.Brown), it) }
//        binding.civDarkBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.DarkBrown), it) }
//        binding.civLightBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.LightBrown), it) }
//        binding.civRed.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.Red), it) }
//        binding.civBlue.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.blue), it) }
//
//        paint = Paint().apply {
//            color = currentColor
//            strokeWidth = 5f
//            style = Paint.Style.STROKE
//            isAntiAlias = true
//            strokeJoin = Paint.Join.ROUND
//            strokeCap = Paint.Cap.ROUND
//        }
//
//        setColor(ContextCompat.getColor(this, R.color.white), binding.civWhite)
//
//        val imageUriString = intent.getStringExtra("selected_image_uri")
//        if (imageUriString != null) {
//            val imageUri = Uri.parse(imageUriString)
//            val imageView = findViewById<ImageView>(R.id.imgDrawSelectImage)
//
//            if (imageView != null) {
//                try {
//                    Glide.with(this)
//                        .load(imageUri)
//                        .into(imageView)
//                } catch (e: Exception) {
//                    logErrorAndFinish("Glide error: ${e.message}")
//                }
//            } else {
//                logErrorAndFinish("ImageView not found in layout")
//            }
//        } else {
//            logErrorAndFinish("Image URI string is null")
//        }
//
//        binding.imgDrawSelectImage.post {
//            val width = binding.imgDrawSelectImage.width
//            val height = binding.imgDrawSelectImage.height
//
//            if (width > 0 && height > 0) {
//                drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                canvas = Canvas(drawingBitmap)
//                path = Path()
//                combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                binding.imgDrawSelectImage.setOnTouchListener(MyTouchListener())
//            } else {
//                logErrorAndFinish("Invalid dimensions for bitmap creation")
//            }
//        }
//
//        binding.switchEraser.setOnCheckedChangeListener { _, isChecked ->
//            isEraserActive = isChecked
//            updatePaint()
//        }
//
//        binding.skbEraser.max = 100
//        binding.skbEraser.progress = 5
//        binding.skbEraser.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
//        {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                paint.strokeWidth = progress.toFloat()
//                findViewById<TextView>(R.id.txtProgress).text = progress.toString()
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                // Do nothing
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                // Do nothing
//            }
//        })
//    }
//
//    private fun updatePaint() {
//        if (isEraserActive) {
//            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
//            paint.color = Color.TRANSPARENT
//        } else {
//            paint.xfermode = null
//            paint.color = currentColor
//        }
//    }
//
//    private fun setColor(color: Int, selectedView: View)
//    {
//        // Check if the selected color is already the current color
//        if (currentColor == color) {
//            return // No need to change if it's already selected
//        }
//
//        // Reset previously selected view
//        selectedColorView?.background = null
//
//        // Update current color and view
//        currentColor = color
//        selectedColorView = selectedView
//
//        // Apply the new color to the selected view
//        val drawable = ContextCompat.getDrawable(this, R.drawable.ring) as GradientDrawable
//        drawable.setColor(color)
//
//        // Apply the drawable to the background of the selected view
//        selectedView.background = drawable
//
//        // Apply the color to your drawing paint or other relevant parts of your activity
//        paint.color = color
//        // Optionally, update any other UI components or logic related to color selection
//    }
//
//    inner class MyTouchListener : View.OnTouchListener {
//        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//            event ?: return false
//
//            val x = event.x
//            val y = event.y
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    path.reset()
//                    path.moveTo(x, y)
//                    lastX = x
//                    lastY = y
//                    return true
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    val dx = Math.abs(x - lastX)
//                    val dy = Math.abs(y - lastY)
//                    if (dx >= touchTolerance || dy >= touchTolerance) {
//                        path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2)
//                        lastX = x
//                        lastY = y
//                    }
//                    canvas.drawPath(path, paint)
//                    updateCombinedBitmap()
//                    return true
//                }
//
//                MotionEvent.ACTION_UP -> {
//                    path.lineTo(lastX, lastY)
//                    canvas.drawPath(path, paint)
//                    path.reset()
//                    updateCombinedBitmap()
//                    return true
//                }
//            }
//            return false
//        }
//    }
//
//    private fun updateCombinedBitmap()
//    {
//        val imageView = findViewById<ImageView>(R.id.imgDrawSelectImage)
//        imageView.isDrawingCacheEnabled = true
//        originalBitmap = imageView.drawingCache
//        val combinedCanvas = Canvas(combinedBitmap)
//        combinedCanvas.drawBitmap(originalBitmap, 0f, 0f, null)
//        combinedCanvas.drawBitmap(drawingBitmap, 0f, 0f, null)
//        binding.imgDrawSelectImage.setImageBitmap(combinedBitmap)
//        imageView.isDrawingCacheEnabled = false
//    }
//
//    private fun openColorPickerDialog() {
//        val colorPickerDialog = AmbilWarnaDialog(this, mDefaultColor,
//            object : AmbilWarnaDialog.OnAmbilWarnaListener {
//                override fun onCancel(dialog: AmbilWarnaDialog?) {}
//
//                override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
//                    mDefaultColor = color
//                    setColor(color, binding.lnrColor)
//                }
//            })
//        colorPickerDialog.show()
//    }
//
//    private fun logErrorAndFinish(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//        finish()
//    }
//}








//package com.example.photoeditorpolishanything
//
//import android.annotation.SuppressLint
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.graphics.Path
//import android.graphics.PorterDuff
//import android.graphics.PorterDuffXfermode
//import android.graphics.drawable.GradientDrawable
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.view.MotionEvent
//import android.view.View
//import android.view.WindowManager
//import android.widget.ImageView
//import android.widget.SeekBar
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import com.bumptech.glide.Glide
//import com.example.photoeditorpolishanything.databinding.ActivityDrawBinding
//import yuku.ambilwarna.AmbilWarnaDialog
//
//class Draw_Activity : AppCompatActivity() {
//    lateinit var binding: ActivityDrawBinding
//    private var currentColor: Int = Color.WHITE
//    private var mDefaultColor = 0
//
//    private lateinit var originalBitmap: Bitmap
//    private lateinit var drawingBitmap: Bitmap
//    private lateinit var combinedBitmap: Bitmap
//    private lateinit var canvas: Canvas
//    private lateinit var paint: Paint
//    private lateinit var path: Path
//    private val touchTolerance = 4f
//    private var lastX = 0f
//    private var lastY = 0f
//
//    private var selectedColorView: View? = null
//    private var isEraserActive = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDrawBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            val window = this.window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.statusBarColor = this.resources.getColor(R.color.black)
//        }
//        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
//
//        initView()
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun initView() {
//        mDefaultColor = 0
//
//        binding.imgback.setOnClickListener {
//            onBackPressed()
//        }
//
//        binding.lnrColor.setOnClickListener {
//            openColorPickerDialog()
//        }
//
//        // Preset color click listeners
//        binding.civWhite.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.white), it) }
//        binding.civGrey.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.lightgrey), it) }
//        binding.civDarkGrey.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.DarkGrey), it) }
//        binding.civBlack.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.black), it) }
//        binding.civBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.Brown), it) }
//        binding.civDarkBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.DarkBrown), it) }
//        binding.civLightBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.LightBrown), it) }
//        binding.civRed.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.Red), it) }
//        binding.civBlue.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.blue), it) }
//
//        paint = Paint().apply {
//            color = currentColor
//            strokeWidth = 5f
//            style = Paint.Style.STROKE
//            isAntiAlias = true
//            strokeJoin = Paint.Join.ROUND
//            strokeCap = Paint.Cap.ROUND
//        }
//
//        setColor(ContextCompat.getColor(this, R.color.white), binding.civWhite)
//
//        val imageUriString = intent.getStringExtra("selected_image_uri")
//        if (imageUriString != null) {
//            val imageUri = Uri.parse(imageUriString)
//            val imageView = findViewById<ImageView>(R.id.imgDrawSelectImage)
//
//            if (imageView != null) {
//                try {
//                    Glide.with(this)
//                        .load(imageUri)
//                        .into(imageView)
//                } catch (e: Exception) {
//                    logErrorAndFinish("Glide error: ${e.message}")
//                }
//            } else {
//                logErrorAndFinish("ImageView not found in layout")
//            }
//        } else {
//            logErrorAndFinish("Image URI string is null")
//        }
//
//        binding.imgDrawSelectImage.post {
//            val width = binding.imgDrawSelectImage.width
//            val height = binding.imgDrawSelectImage.height
//
//            if (width > 0 && height > 0) {
//                drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                canvas = Canvas(drawingBitmap)
//                path = Path()
//                combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                binding.imgDrawSelectImage.setOnTouchListener(MyTouchListener())
//            } else {
//                logErrorAndFinish("Invalid dimensions for bitmap creation")
//            }
//        }
//
//        binding.skbEraser.max = 100
//        binding.skbEraser.progress = 5
//        binding.skbEraser.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                paint.strokeWidth = progress.toFloat()
//                findViewById<TextView>(R.id.txtProgress).text = progress.toString()
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                // Do nothing
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                // Do nothing
//            }
//        })
//
//        // Add eraser button click listener
//        binding.lnrEraser.setOnClickListener {
//            isEraserActive = true
//            updatePaint()
//        }
//    }
//
//    private fun setColor(color: Int, selectedView: View) {
//        if (currentColor == color && !isEraserActive) {
//            return
//        }
//
//        selectedColorView?.background = null
//
//        currentColor = color
//        selectedColorView = selectedView
//
//        val drawable = ContextCompat.getDrawable(this, R.drawable.ring) as GradientDrawable
//        drawable.setColor(color)
//
//        selectedView.background = drawable
//
//        isEraserActive = false
//        updatePaint()
//    }
//
//    private fun updatePaint() {
//        if (isEraserActive) {
//            // Use a thicker stroke width for the eraser
//            paint.strokeWidth = 20f  // Adjust this value as needed for your app
//            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
//            paint.color = Color.TRANSPARENT
//            paint.alpha = 0
//        } else {
//            // Reset to normal drawing mode
//            paint.strokeWidth = binding.skbEraser.progress.toFloat()  // Use the current seekbar progress or default value
//            paint.xfermode = null
//            paint.alpha = 255
//            paint.color = currentColor
//        }
//    }
//
//    inner class MyTouchListener : View.OnTouchListener {
//        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//            event ?: return false
//
//            val x = event.x
//            val y = event.y
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    path.reset()
//                    path.moveTo(x, y)
//                    lastX = x
//                    lastY = y
//                    return true
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    val dx = Math.abs(x - lastX)
//                    val dy = Math.abs(y - lastY)
//                    if (dx >= touchTolerance || dy >= touchTolerance) {
//                        path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2)
//                        lastX = x
//                        lastY = y
//                    }
//                    canvas.drawPath(path, paint)
//                    updateCombinedBitmap()
//                    return true
//                }
//
//                MotionEvent.ACTION_UP -> {
//                    path.lineTo(lastX, lastY)
//                    canvas.drawPath(path, paint)
//                    path.reset()
//                    updateCombinedBitmap()
//                    return true
//                }
//            }
//            return false
//        }
//    }
//
//    private fun updateCombinedBitmap() {
//        val imageView = findViewById<ImageView>(R.id.imgDrawSelectImage)
//        imageView.isDrawingCacheEnabled = true
//        originalBitmap = imageView.drawingCache
//        val combinedCanvas = Canvas(combinedBitmap)
//        combinedCanvas.drawBitmap(originalBitmap, 0f, 0f, null)
//        combinedCanvas.drawBitmap(drawingBitmap, 0f, 0f, null)
//        binding.imgDrawSelectImage.setImageBitmap(combinedBitmap)
//        imageView.isDrawingCacheEnabled = false
//    }
//
//    private fun openColorPickerDialog() {
//        val colorPickerDialog = AmbilWarnaDialog(this, mDefaultColor,
//            object : AmbilWarnaDialog.OnAmbilWarnaListener {
//                override fun onCancel(dialog: AmbilWarnaDialog?) {}
//
//                override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
//                    mDefaultColor = color
//                    setColor(color, binding.lnrColor)
//                }
//            })
//        colorPickerDialog.show()
//    }
//
//    private fun logErrorAndFinish(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//        finish()
//    }
//}


package com.example.photoeditorpolishanything

import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.photoeditorpolishanything.databinding.ActivityDrawBinding
import yuku.ambilwarna.AmbilWarnaDialog

class Draw_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawBinding
    private var mDefaultColor = 0

    private lateinit var drawingView: DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawBinding.inflate(layoutInflater)
        setContentView(binding.root) // Ensure binding is initialized before using it

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initView()
    }

    private fun initView() {
        mDefaultColor = ContextCompat.getColor(this, R.color.white)

        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        binding.lnrColor.setOnClickListener {
            openColorPickerDialog()
        }

        binding.civWhite.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.white), it) }
        binding.civGrey.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.lightgrey), it) }
        binding.civDarkGrey.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.DarkGrey), it) }
        binding.civBlack.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.black), it) }
        binding.civBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.Brown), it) }
        binding.civDarkBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.DarkBrown), it) }
        binding.civLightBrown.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.LightBrown), it) }
        binding.civRed.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.Red), it) }
        binding.civBlue.setOnClickListener { setColor(ContextCompat.getColor(this, R.color.blue), it) }

        drawingView = findViewById(R.id.drawing_view) // Make sure to find drawingView after setContentView
        setColor(ContextCompat.getColor(this, R.color.white), binding.civWhite)

        val imageUriString = intent.getStringExtra("selected_image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            drawingView.setBackgroundImage(imageUri)
        } else {
            logErrorAndFinish("Image URI string is null")
        }

        binding.skbEraser.max = 100
        binding.skbEraser.progress = 5
        binding.skbEraser.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                drawingView.setStrokeWidth(progress.toFloat())
                binding.txtProgress.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })

        binding.lnrEraser.setOnClickListener {
            drawingView.setEraser(true)
        }
    }

    private fun setColor(color: Int, selectedView: View) {
        drawingView.setColor(color)
        resetColorSelection()
        selectedView.setBackgroundResource(R.drawable.ring)

        // Reset the eraser mode when selecting a new color
        drawingView.setEraser(false)
    }

    private fun resetColorSelection() {
        binding.civWhite.setBackgroundResource(0)
        binding.civGrey.setBackgroundResource(0)
        binding.civDarkGrey.setBackgroundResource(0)
        binding.civBlack.setBackgroundResource(0)
        binding.civBrown.setBackgroundResource(0)
        binding.civDarkBrown.setBackgroundResource(0)
        binding.civLightBrown.setBackgroundResource(0)
        binding.civRed.setBackgroundResource(0)
        binding.civBlue.setBackgroundResource(0)
        binding.lnrColor.setBackgroundResource(0)
    }

    private fun openColorPickerDialog() {
        val colorPickerDialog = AmbilWarnaDialog(this, mDefaultColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog?) {}

                override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                    mDefaultColor = color

                    setColor(color, binding.lnrColor)

                    val customBackgroundDrawable = ContextCompat.getDrawable(this@Draw_Activity, R.drawable.custom_background)
                    customBackgroundDrawable?.setColorFilter(mDefaultColor, PorterDuff.Mode.SRC_ATOP)
                    binding.lnrColor.background = customBackgroundDrawable                }
            })
        colorPickerDialog.show()
    }

    private fun logErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }
}