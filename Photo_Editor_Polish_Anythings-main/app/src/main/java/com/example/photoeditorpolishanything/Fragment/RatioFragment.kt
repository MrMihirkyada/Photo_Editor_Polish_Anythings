package com.example.photoeditorpolishanything.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.databinding.FragmentRatioBinding

class RatioFragment : Fragment() {

    private lateinit var binding: FragmentRatioBinding

    var listener: OnLayoutSelectedListener? = null

    interface OnLayoutSelectedListener {
        fun onLayoutSelected(aspectX: Int, aspectY: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState == null) {
            binding = FragmentRatioBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUriString = arguments?.getString(ARG_IMAGE_URI)


        val availableWidth = binding.root.width
        val availableHeight = binding.root.height

        binding.lnrCustom.setOnClickListener {
            // Example: User selects Custom
            listener?.onLayoutSelected(0, 0) // Replace with actual dimension
        }

        binding.lnrLG11.setOnClickListener {
            // Example: User selects LG 1:1
            listener?.onLayoutSelected(1, 1) // Replace with actual dimensions
        }

        binding.lnrLG45.setOnClickListener {
            // Example: User selects LG 4:5
            listener?.onLayoutSelected(4, 5) // Replace with actual dimensions
        }

        binding.img54.setOnClickListener {
            // Example: User selects 5:4
            listener?.onLayoutSelected(5,4) // Replace with actual dimensions
        }

        binding.img34.setOnClickListener {
            // Example: User selects 3:4
            listener?.onLayoutSelected(3, 4) // Replace with actual dimensions
        }

        binding.img43.setOnClickListener {
            // Example: User selects 4:3
            listener?.onLayoutSelected(4, 3) // Replace with actual dimensions
        }

        binding.imgA4.setOnClickListener {
            // Example: User selects A4
            listener?.onLayoutSelected(2,3) // Replace with actual dimensions
        }

        binding.lnrCover.setOnClickListener {
            // Example: User selects Cover
            listener?.onLayoutSelected(16, 9) // Replace with actual dimensions
        }

        binding.lnrDevice.setOnClickListener {
            // Example: User selects Device
            listener?.onLayoutSelected(9, 16) // Replace with actual dimensions
        }

        binding.img23.setOnClickListener {
            // Example: User selects 2 : 3
            listener?.onLayoutSelected(2, 3) // Replace with actual dimensions
        }

        binding.img32.setOnClickListener {
            // Example: User selects 3 : 2
            listener?.onLayoutSelected(3, 2) // Replace with actual dimensions
        }

        binding.img12.setOnClickListener {
            // Example: User selects 1 : 2
            listener?.onLayoutSelected(1, 2) // Replace with actual dimensions
        }

        binding.lnrPost.setOnClickListener {
            // Example: User selects Post
            listener?.onLayoutSelected(16, 9) // Replace with actual dimensions
        }

        binding.lnrHeader.setOnClickListener {
            // Example: User selects Header
            listener?.onLayoutSelected(3, 1) // Replace with actual dimensions
        }

        updateUIForSelectedLanguage()
    }

    fun updateUIForSelectedLanguage() {
        binding.txtCustom.text = getString(R.string.Custom)
        binding.txtlG11.text = getString(R.string.lG11)
        binding.txtlG45.text = getString(R.string.lG45)
        binding.txtA4.text = getString(R.string.A4)
        binding.txtCover.text = getString(R.string.Cover)
        binding.txtDevice.text = getString(R.string.Device)
        binding.txtPost.text = getString(R.string.Post)
        binding.txtHeader.text = getString(R.string.Header)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLayoutSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnLayoutSelectedListener")
        }
    }
    override fun onDetach()
    {
        super.onDetach()
        listener = null
    }

    companion object {
        private const val ARG_IMAGE_URI = "image_uri"

        const val CROP_IMAGE_ACTIVITY_REQUEST_CODE = 203
        private const val REQUEST_CODE = 1234

        fun newInstance(imageUriString: String?): RatioFragment {
            val fragment = RatioFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URI, imageUriString)
            fragment.arguments = args
            return fragment
        }
    }
}