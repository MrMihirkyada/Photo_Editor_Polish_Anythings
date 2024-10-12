package com.example.photoeditorpolishanything.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Api.Groupas
import com.example.photoeditorpolishanything.R

class  Sticker_Activity_Adapter(
    var activity: Activity,
    var data: MutableList<Groupas>,
) : RecyclerView.Adapter<Sticker_Activity_Adapter.Sticker_Activity_Adapter>()
{

    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/sticker/"
    private var filteredData: MutableList<Groupas> = data // Store the filtered data


    fun updateData(newData: MutableList<Groupas>)
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
        val stickerUrl = data[position]

        val item = data.getOrNull(position) ?: return

        val imageUrlList = item.subImageUrl
        if (imageUrlList.isNullOrEmpty())
        {
            Log.e("StickerAdapter", "No image URLs available for position $position")
            holder.imageView.setImageResource(R.drawable.cross) // Placeholder image
            return
        }

        val imageUrl = stickerUrl.subImageUrl!!.get(0)

        if (imageUrl != null)
        {
            // Load image using Glide
            Glide.with(holder.itemView.context)
                .load(baseUrl + imageUrl)
                .fitCenter()
                .centerCrop()
                .into(holder.imageView)


                holder.imageView.setOnClickListener {
    //                // Get the list of sub-image URLs associated with the clicked sticker
    //                val subImageUrls = stickerUrl.subImageUrl
    //
    //                // Check if there are any sub-image URLs
    //                if (subImageUrls != null && subImageUrls.isNotEmpty()) {
    //                    // Start the new activity and pass the sub-image URLs to it
    //                    val intent = Intent(activity, Sticker_Activity::class.java)
    //                    intent.putStringArrayListExtra("imageUrls", ArrayList(subImageUrls))
    //                    activity.startActivity(intent)
    //                } else {
    //                    Toast.makeText(holder.itemView.context, "No images available", Toast.LENGTH_SHORT).show()
    //                }

                    // Show the category as a toast
                    Toast.makeText(holder.itemView.context, stickerUrl.textCategory, Toast.LENGTH_SHORT).show()
                }

        }
        else
        {
            // Handle case where imageUrl is null
            Log.e("ImageLoading", "No image URL available for position $position")
        }

//        holder.textView.text = stickerUrl.textCategory
//        holder.textNumber.text = stickerUrl.subImageUrl!!.size.toString() + " Stickers"
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