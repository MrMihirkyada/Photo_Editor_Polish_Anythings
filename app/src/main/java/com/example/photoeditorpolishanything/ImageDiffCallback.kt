package com.example.photoeditorpolishanything

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

class ImageDiffCallback(
    private val oldList: List<Uri>,
    private val newList: List<Uri>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
