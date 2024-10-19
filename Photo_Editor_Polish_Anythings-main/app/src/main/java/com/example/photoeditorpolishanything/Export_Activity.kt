package com.example.photoeditorpolishanything

import LocaleHelper
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.photoeditorpolishanything.databinding.ActivityExportBinding

class Export_Activity : BaseActivity() {

    lateinit var binding: ActivityExportBinding
    private var overlayView: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)


        intiView()
    }

    override fun updateUIForSelectedLanguage()
    {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtSaved.text = resources.getString(R.string.Saved)
        binding.txtMakeAnother.text = resources.getString(R.string.MakeAnother)
        binding.txtMore.text = resources.getString(R.string.More)
        binding.txtInstagram.text = resources.getString(R.string.Instagram)
        binding.txtWhatsApp.text = resources.getString(R.string.WhatsApp)
        binding.txtFacebook.text = resources.getString(R.string.Facebook)
        binding.txtMessenger.text = resources.getString(R.string.Messenger)
        binding.txtTwitter.text = resources.getString(R.string.Twitter)
        binding.txtPrint.text = resources.getString(R.string.Print)
        binding.txtEmail.text = resources.getString(R.string.Email)
    }

    private fun updateBottomSheetTextViews(view: View) {
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        view.findViewById<TextView>(R.id.txtAreYouEditing).text = resources.getString(R.string.AreYouEditing)
        view.findViewById<TextView>(R.id.txtGood).text = resources.getString(R.string.Good)
        view.findViewById<TextView>(R.id.txtNotReally).text = resources.getString(R.string.NotReally)
    }

    private fun intiView()
    {
        val editedImageUriString = intent.getStringExtra("selected_image_uri")
        if (editedImageUriString != null) {
            val editedImageUri = Uri.parse(editedImageUriString)
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(editedImageUri))
            binding.lnrExport.setImageBitmap(bitmap) // Assuming you have an ImageView with id lnrExport
        }

        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        binding.lnrMakeAnother.setOnClickListener {
            showCustomDialog()
        }
    }

    private fun showCustomDialog() {
        // Inflate the custom dialog layout
        val dialogView = layoutInflater.inflate(R.layout.editing_result_fedback_dialog, null)

        // Create the AlertDialog builder and set the custom view
        val builder = AlertDialog.Builder(this,R.style.CustomDialogTheme)
        builder.setView(dialogView)

        updateBottomSheetTextViews(dialogView)


        // Create and show the dialog
        val dialog = builder.create()

//        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
//        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
//        dialog.window?.setLayout(width, height)

        // Show the overlay
        showOverlay()

        dialog.setOnDismissListener {
            // Remove the overlay when the dialog is dismissed
            removeOverlay()
        }

        dialog.show()

        // Find and set up the views in the custom dialog
        val Good = dialogView.findViewById<LinearLayout>(R.id.lnrGood)
        val NotReally = dialogView.findViewById<LinearLayout>(R.id.lnrNotreally)

        Good.setOnClickListener {
            dialog.dismiss()
        }

        NotReally.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showOverlay() {
        if (overlayView == null) {
            overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_view, null) as ViewGroup
            val decorView = window.decorView as ViewGroup
            decorView.addView(overlayView)
        }
    }

    private fun removeOverlay() {
        overlayView?.let {
            val decorView = window.decorView as ViewGroup
            decorView.removeView(it)
            overlayView = null
        }
    }
}