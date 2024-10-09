package com.example.photoeditorpolishanything

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class Editing_Fedback_Dialog(context: Context) : Dialog(context)
{
    private var userrate = 0f

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editing_result_fedback_dialog)

        initview()
    }

    private fun initview()
    {
        val Good = findViewById<LinearLayout>(R.id.lnrGood)
        val NotReally = findViewById<LinearLayout>(R.id.lnrgallery)

        Good.setOnClickListener {
            dismiss()
        }

        NotReally.setOnClickListener {
            dismiss()
        }
    }
}