package com.example.photoeditorpolishanything.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.R

class Sticker_Sub_Image_Adapter(private var subImageUrls: List<String?>) : RecyclerView.Adapter<Sticker_Sub_Image_Adapter.SubImageViewHolder>() {

    fun updateData(newImages: List<String?>)
    {
        this.subImageUrls = newImages
        notifyDataSetChanged()
    }

    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/sticker/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sticker_sub_image_item, parent, false)
        return SubImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubImageViewHolder, position: Int)
    {
        val subImageUrl = subImageUrls[position]
        Glide.with(holder.itemView.context).load(baseUrl + subImageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int
    {
        return subImageUrls.size
    }

    class SubImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgSubSticker)
    }
}
