package com.example.photoeditorpolishanything.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.photoeditorpolishanything.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBlurDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.editing_result_fedback_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lnrGood = view.findViewById<LinearLayout>(R.id.lnrGood)
        lnrGood.setOnClickListener {
            dismiss()
        }

        val lnrNotreally = view.findViewById<LinearLayout>(R.id.lnrNotreally)
        lnrNotreally.setOnClickListener {
            dismiss()
        }
    }
}