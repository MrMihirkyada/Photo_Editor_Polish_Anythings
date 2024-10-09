package com.example.photoeditorpolishanything.Adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.R

class Background_Sub_Image_Adapter(private val subImageUrls: List<String?>) : RecyclerView.Adapter<Background_Sub_Image_Adapter.SubImageViewHolder>() {

    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/background/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.background_sub_image_item, parent, false)
        return SubImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubImageViewHolder, position: Int) {
        val subImageUrl = subImageUrls[position]
        val fullUrl = baseUrl + subImageUrl
        Log.e("ImageLoading", "Loading image from URL: $fullUrl")
        Glide.with(holder.itemView.context).load(fullUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return subImageUrls.size
    }

    class SubImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgSubSticker)
    }
}
