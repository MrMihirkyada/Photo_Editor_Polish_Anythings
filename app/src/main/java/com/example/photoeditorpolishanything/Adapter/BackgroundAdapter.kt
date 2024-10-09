package com.example.photoeditorpolishanything.Adapter

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
import com.example.photoeditorpolishanything.Api.Groups
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.StoreFragment.BackgroundBottomSheetDialogFragment

class BackgroundAdapter(var activity: Activity, private var data: MutableList<Groups>,/*var groupm : Map<String, Groups?>*/) : RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder>()
{
    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/background/"

    fun updateData(newData: MutableList<Groups>)
    {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sticker, parent, false)
        return BackgroundViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BackgroundViewHolder, position: Int)
    {
        val backgroundUrl = data[position]

        val item = data.getOrNull(position) ?: return

        val imageUrlList = item.mainImageUrl
        if (imageUrlList.isNullOrEmpty())
        {
            Log.e("BackgroundAdapter", "No image URLs available for position $position")
            holder.imageView.setImageResource(R.drawable.cross) // Placeholder image
            return
        }

        val imageUrl = backgroundUrl.mainImageUrl!!.firstOrNull() // Take the first URL from the list if available

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
                    val bottomSheetDialogFragment = BackgroundBottomSheetDialogFragment.newInstance(backgroundUrl.subImageUrl, R.color.black,backgroundUrl.textCategory.toString())
                    Log.e("PassedData", "Sub Image URLs: ${backgroundUrl.subImageUrl}")
                    bottomSheetDialogFragment.show(activity.supportFragmentManager, bottomSheetDialogFragment.tag)
                }
            }
        }
        else
        {
            // Handle case where imageUrl is null
            Log.e("ImageLoading", "No image URL available for position $position")
        }

        Log.e("backgroundUrl", "onBindViewHolder: "+baseUrl + backgroundUrl.subImageUrl)
        holder.txtName.text = backgroundUrl.textCategory
        holder.txtNumber.text = backgroundUrl.subImageUrl!!.size.toString() + " Backgrounds"
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    class BackgroundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.imgSticker)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtNumber: TextView = itemView.findViewById(R.id.txtNumber)
    }
}
