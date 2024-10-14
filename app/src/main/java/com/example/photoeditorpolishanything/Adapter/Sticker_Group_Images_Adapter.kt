package com.example.photoeditorpolishanything.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Adapter.Sticker_Activity_Adapter.Companion.imageUrlList
import com.example.photoeditorpolishanything.R

class Sticker_Group_Images_Adapter(var data: List<String>) :
    RecyclerView.Adapter<Sticker_Group_Images_Adapter.Sticker_Activity_Adapter>()
{

    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/sticker/"

    fun updateData(newData: List<String>)
    {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Sticker_Activity_Adapter
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sticker_activity_item, parent, false)
        Log.e("StoreFragment", "onCreateViewHolder: " + itemView)
        return Sticker_Activity_Adapter(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Sticker_Activity_Adapter, position: Int)
    {
            // Load image using Glide
            Glide.with(holder.itemView.context)
                .load(baseUrl + imageUrlList)
                .fitCenter()
                .centerCrop()
                .into(holder.imageView)
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    class Sticker_Activity_Adapter(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.imgSticker)
    }
}