package com.example.photoeditorpolishanything.StoreFragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Adapter.Sticker_Sub_Image_Adapter
import com.example.photoeditorpolishanything.ImageSizeFetcher
import com.example.photoeditorpolishanything.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class StickerBottomSheetDialogFragment : BottomSheetDialogFragment()
{

    private lateinit var recyclerView : RecyclerView
    private lateinit var imgStickers : ImageView
    private lateinit var txtName : TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var txtFreeDownload : TextView
    private lateinit var btnFreeDownload : LinearLayout
    private lateinit var txtNumber : TextView
    private lateinit var adapter : Sticker_Sub_Image_Adapter
    private var data : List<String?>? = null
    private val imageSizeFetcher = ImageSizeFetcher()
    private lateinit var datas : String
    private lateinit var mainImageUrl : String
    var textCategory : TextView? = null
    private val downloadedFiles = mutableListOf<File>()

    private val REQUEST_WRITE_PERMISSION = 786

    var STORAGE_DIRECTORY = "Download/TestFolder"

    companion object {
        const val ARG_DATA = "data"
        private const val ARG_COLOR = "navigation_bar_color"
        private const val ARG_MAIN_IMAGE_URL = "main_image_url"
        private const val ARG_TEXT_CATEGORY =
            "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/sticker/"

        fun newInstance(data: List<String?>?, navigationBarColor: Int, mainImageUrl: String, textCategory: String?)
        : StickerBottomSheetDialogFragment {
            val fragment = StickerBottomSheetDialogFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_DATA, ArrayList(data))
            args.putInt(ARG_COLOR, navigationBarColor)
            args.putString(ARG_MAIN_IMAGE_URL, mainImageUrl)
            args.putString(ARG_TEXT_CATEGORY, textCategory)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getStringArrayList(ARG_DATA)
        datas = arguments?.getString(ARG_TEXT_CATEGORY)!!
        mainImageUrl = arguments?.getString(ARG_MAIN_IMAGE_URL)!!
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.sticker_bottom_sheet_dialog, container, false)
        recyclerView = view.findViewById(R.id.recyclerViews)
        btnFreeDownload = view.findViewById(R.id.btnFreeDownload)
        imgStickers = view.findViewById(R.id.imgStickers)
        progressBar = view.findViewById(R.id.progressBar)
        txtName = view.findViewById(R.id.txtName)
        txtNumber = view.findViewById(R.id.txtNumbers)
        Glide.with(requireContext()).load(ARG_TEXT_CATEGORY + mainImageUrl).into(imgStickers)

        btnFreeDownload.setOnClickListener {
            checkPermissions()
        }

//        setRecyclerViewHeight(recyclerView)
        Log.e("imgStickers", "onCreateView: " + ARG_TEXT_CATEGORY + mainImageUrl)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        data = arguments?.getStringArrayList(ARG_DATA)
        adapter = Sticker_Sub_Image_Adapter(data ?: emptyList())
        recyclerView.adapter = adapter

        txtName.text = datas

        CoroutineScope(Dispatchers.IO).launch {
            val sizeText = imageSizeFetcher.fetchImageSizes(ARG_TEXT_CATEGORY, data)
            withContext(Dispatchers.Main) {
                txtNumber.text = "Size :- $sizeText"
            }
        }

        return view
    }

    private fun downloadData() {
        btnFreeDownload = requireView().findViewById(R.id.btnFreeDownload)
        txtFreeDownload = requireView().findViewById(R.id.txtFreeDownload)
        val dataToDownload = data

        // Update button text to show downloading progress
        txtFreeDownload.text = "Downloading... 0%"

        // Define directory
        val downloadDir = File(requireContext().filesDir, "Download/TestFolder")
        if (!downloadDir.exists()) {
            downloadDir.mkdirs() // Create the directory if it does not exist
        }

        // Create a coroutine to handle the download
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            dataToDownload!!.forEachIndexed { index, url ->
                val encodedUrl = ARG_TEXT_CATEGORY + url!!.replace(" ", "%20")
                Log.d("Download URL", encodedUrl)  // Log the URL for debugging

                val request = Request.Builder().url(encodedUrl).build()
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful)
                    {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Download failed: $url, Response code: ${response.code}", Toast.LENGTH_SHORT).show()
                        }
                        return@forEachIndexed  // Skip to the next URL
                    }

                    val inputStream : InputStream? = response.body?.byteStream()
                    val file = File(downloadDir, "image_${index + 1}.jpg")
                    val outputStream = FileOutputStream(file)

                    inputStream?.copyTo(outputStream)
                    outputStream.close()
                }

                // Update progress
                withContext(Dispatchers.Main) {
                    val progress = ((index + 1) * 100) / dataToDownload.size
                    txtFreeDownload.text = "Downloading... $progress%"
                }
            }

            // Update button text when download is complete
            withContext(Dispatchers.Main) {
                txtFreeDownload.text = "Download Complete"
            }
        }
    }

    private fun checkPermissions()
     {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_PERMISSION
            )
        } else {
            // Call your method to start the download
//            downloadData(baseUrl + data, data)
            downloadData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                downloadData(baseUrl + mainImageUrl, data)
                downloadData()
            } else {
                // Handle the case when permission is denied
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.isHideable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet.requestLayout()

            dialog.window?.navigationBarColor = ContextCompat.getColor(
                requireContext(),
                arguments?.getInt(ARG_COLOR) ?: R.color.black
            )
        }
        return dialog
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }
}

