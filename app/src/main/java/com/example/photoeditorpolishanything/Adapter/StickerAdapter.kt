package com.example.photoeditorpolishanything.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Api.Groupas
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.StoreFragment.StickerBottomSheetDialogFragment

class StickerAdapter(var activity: Activity, var data: MutableList<Groupas>) : RecyclerView.Adapter<StickerAdapter.StickerViewHolder>()
{

    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/sticker/"

    fun updateData(newData: MutableList<Groupas>)
    {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sticker, parent, false)
        Log.e("StoreFragment", "onCreateViewHolder: " + itemView)
        return StickerViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StickerViewHolder, position: Int)
    {
        val stickerUrl = data[position]

        val item = data.getOrNull(position) ?: return

        val imageUrlList = item.mainImageUrl
        if (imageUrlList.isNullOrEmpty())
        {
            Log.e("StickerAdapter", "No image URLs available for position $position")
            holder.imageView.setImageResource(R.drawable.cross) // Placeholder image
            return
        }

        val imageUrl = stickerUrl.mainImageUrl!!.firstOrNull() // Take the first URL from the list if available

        if (imageUrl != null)
        {
            // Load image using Glide
            Glide.with(holder.itemView.context)
                .load(baseUrl + imageUrl)
                .fitCenter()
                .centerCrop()
                .into(holder.imageView)

            holder.itemView.setOnClickListener {
                val activity = it.context as? FragmentActivity
                if (activity != null)
                {
                    val bottomSheetDialogFragment = StickerBottomSheetDialogFragment.newInstance(stickerUrl.subImageUrl, R.color.black,imageUrl,stickerUrl.textCategory)
                    bottomSheetDialogFragment.show(activity.supportFragmentManager, bottomSheetDialogFragment.tag)
                }
            }
        }
        else
        {
            // Handle case where imageUrl is null
            Log.e("ImageLoading", "No image URL available for position $position")
        }

        holder.textView.text = stickerUrl.textCategory
        holder.textNumber.text = stickerUrl.subImageUrl!!.size.toString() + " Stickers"
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    class StickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.imgSticker)
        val textView: TextView = itemView.findViewById(R.id.txtName)
        val textNumber: TextView = itemView.findViewById(R.id.txtNumber)
    }
}
