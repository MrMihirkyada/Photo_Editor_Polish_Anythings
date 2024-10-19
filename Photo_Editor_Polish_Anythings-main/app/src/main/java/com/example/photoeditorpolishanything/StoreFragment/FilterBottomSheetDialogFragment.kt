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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.ImageSizeFetcher
import com.example.photoeditorpolishanything.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtName : TextView
    private lateinit var txtSize : TextView
    private lateinit var adapter: Filter_Sub_Image_Adapter
    private var data: List<String?>? = null
    private val imageSizeFetcher = ImageSizeFetcher()


    companion object {
        private const val ARG_DATA = "data"
        private const val ARG_COLOR = "navigation_bar_color"
        private const val ARG_MAIN_IMAGE_URL = "main_image_url"

        fun newInstance(data: List<String?>?, navigationBarColor: Int, mainImageUrl: String): FilterBottomSheetDialogFragment {
            val fragment = FilterBottomSheetDialogFragment()
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
        val view = inflater.inflate(R.layout.lightfx_bottom_sheet_dialog, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewe)
        txtName = view.findViewById(R.id.txtName)
        txtSize = view.findViewById(R.id.txtSize)

        val mainImageUrl = arguments?.getString(ARG_MAIN_IMAGE_URL) ?: ""
        txtName.text = mainImageUrl
        txtSize.text = data!!.size.toString() + " Filter"

        val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/filter/"
//        Glide.with(requireContext()).load(baseUrl + mainImageUrl).into(imgBackgrounds)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = Filter_Sub_Image_Adapter(data ?: emptyList())
        recyclerView.adapter = adapter


//        CoroutineScope(Dispatchers.Main).launch {
//            val sizeText = imageSizeFetcher.fetchImageSizes(baseUrl + mainImageUrl, data)
//            txtName.text = "Size: $sizeText"
//        }

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
