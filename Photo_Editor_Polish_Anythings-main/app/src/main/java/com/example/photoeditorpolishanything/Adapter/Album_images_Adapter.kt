package com.example.photoeditorpolishanything.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.ImageDiffCallback
import com.example.photoeditorpolishanything.R

// Adapter for displaying images
class AlbumImagesAdapter(var imageList: List<Uri>) : RecyclerView.Adapter<AlbumImagesAdapter.ImageViewHolder>()
{
    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_images_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int)
    {
        val imageUri = imageList[position]
        holder.imageView.setImageURI(imageUri) // Display image from URI
    }

    override fun getItemCount() = imageList.size

    fun updateData(newImages: List<Uri>)
    {
        val diffCallback = ImageDiffCallback(imageList, newImages)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        imageList = newImages
        diffResult.dispatchUpdatesTo(this)
    }

    fun addImages(newImages: List<Uri>) {
        val updatedList = imageList.toMutableList().apply { addAll(newImages) }
        val diffCallback = ImageDiffCallback(imageList, updatedList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        imageList = updatedList
        diffResult.dispatchUpdatesTo(this)
    }
}


//package com.example.photoeditorpolishanything.Adapter
//
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.photoeditorpolishanything.R
//
//class AlbumImagesAdapter : ListAdapter<Uri, AlbumImagesAdapter.ImageViewHolder>(DiffCallback())
//{
//
//    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view)
//    {
//        val imageView: ImageView = view.findViewById(R.id.imageView)
//    }
//
//    class DiffCallback : DiffUtil.ItemCallback<Uri>()
//    {
//        override fun areItemsTheSame(oldItem: Uri, newItem: Uri) = oldItem == newItem
//        override fun areContentsTheSame(oldItem: Uri, newItem: Uri) = oldItem == newItem
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder
//    {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.album_images_item, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int)
//    {
//        val imageUri = getItem(position)
//
//        // Use Glide to load images efficiently
//        Glide.with(holder.imageView.context)
//            .load(imageUri)
//            .into(holder.imageView)
//    }
//}

