package com.example.photoeditorpolishanything.StoreFragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.Adapter.Background_Sub_Image_Adapter
import com.example.photoeditorpolishanything.ImageSizeFetcher
import com.example.photoeditorpolishanything.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BackgroundBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtName: TextView
    private lateinit var txtNumber : TextView
//    private lateinit var imgMain : ImageView
    private lateinit var adapter: Background_Sub_Image_Adapter
    private var data: List<String?>? = null
    private val imageSizeFetcher = ImageSizeFetcher()

    companion object {
        private const val ARG_DATA = "data"
        private const val ARG_COLOR = "navigation_bar_color"
        private const val ARG_MAIN_IMAGE_URL = "main_image_url"

        fun newInstance(data: List<String?>?, navigationBarColor: Int, mainImageUrl: String): BackgroundBottomSheetDialogFragment {
            val fragment = BackgroundBottomSheetDialogFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_DATA, ArrayList(data))
            args.putInt(ARG_COLOR, navigationBarColor)
            args.putString(ARG_MAIN_IMAGE_URL, mainImageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = arguments?.getStringArrayList(ARG_DATA)
        Log.e("FragmentData", "Sub Image URLs in Fragment: $data")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.background_bottom_sheet_dialog, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewes)
        txtName = view.findViewById(R.id.txtNames)
        txtNumber = view.findViewById(R.id.txtNumbers)
//        imgMain = view.findViewById(R.id.imgMain)

        val mainImageUrl = arguments?.getString(ARG_MAIN_IMAGE_URL) ?: ""
        val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/background/"
//        Glide.with(requireContext()).load(baseUrl + mainImageUrl).into(imgMain)

        txtName.text = mainImageUrl
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        adapter = Background_Sub_Image_Adapter(data ?: emptyList())
        recyclerView.adapter = adapter

//        var subImage : BackgroundApi? = null

        CoroutineScope(Dispatchers.IO).launch {
            val sizeText = imageSizeFetcher.fetchImageSizes(baseUrl, data)
            withContext(Dispatchers.Main) {
                txtNumber.text = "Size :- $sizeText"
            }
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.isHideable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet.requestLayout()

            dialog.window?.navigationBarColor = ContextCompat.getColor(requireContext(), arguments?.getInt(ARG_COLOR) ?: R.color.black)
        }
        return dialog
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }
}
