package com.example.photoeditorpolishanything.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.R

//
//class SelectedImagesAdapter(private val selectedImages: MutableList<Uri>, private val onItemCloseClick: (Int) -> Unit) : RecyclerView.Adapter<SelectedImagesAdapter.SelectedImageViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selected_image, parent, false)
//        return SelectedImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SelectedImageViewHolder, position: Int) {
//        val imageUri = selectedImages[position]
//
//        Glide.with(holder.itemView.context)
//            .load(imageUri)
//            .into(holder.imageView)
//
//
//        // Adjust the close button's layout parameters
//        val params = holder.closeButton.layoutParams as ConstraintLayout.LayoutParams
//        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
//        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
//        params.setMargins(0, 16, 16, 0) // Adjust margins as needed
//        holder.closeButton.layoutParams = params
//
//        // Set click listener if needed
//        holder.closeButton.setOnClickListener {
//            // Handle close button click, e.g., remove the item
//            onItemCloseClick(position)
//        }
//    }
//
//    override fun getItemCount(): Int = selectedImages.size
//
//    class SelectedImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.selectedImageView)
//
//        val closeButton = itemView.findViewById<ImageView>(R.id.crossButton)
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageView)
//
//        init {
//            itemView.findViewById<ImageView>(R.id.crossButton)?.setOnClickListener {
//                onItemCloseClick(adapterPosition)
//            }
//        }
//    }
//
//    fun removeItem(position: Int)
//    {
//        if (position >= 0 && position < selectedImages.size)
//        {
//            selectedImages.removeAt(position)
//            notifyItemRemoved(position)
//        }
//    }
//
//}


class SelectedImagesAdapter(private val selectedImages: MutableList<Uri>, private val onItemCloseClick: (Int) -> Unit)
    : RecyclerView.Adapter<SelectedImagesAdapter.SelectedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedImageViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selected_image, parent, false)
        return SelectedImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedImageViewHolder, position: Int)
    {
        val imageUri = selectedImages[position]

        Glide.with(holder.itemView.context)
            .load(imageUri)
            .into(holder.imageView)

        holder.closeButton.setOnClickListener {
            onItemCloseClick(holder.adapterPosition)  // Use holder.adapterPosition for the correct position
        }
    }

    override fun getItemCount(): Int = selectedImages.size

    class SelectedImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.selectedImageView)
        val closeButton: ImageView = itemView.findViewById(R.id.crossButton)
    }

    // Helper method to remove the item
    fun removeItem(position: Int)
    {
        if (position >= 0 && position < selectedImages.size)
        {
            selectedImages.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, selectedImages.size)  // This helps to update subsequent items
        }
    }
}
