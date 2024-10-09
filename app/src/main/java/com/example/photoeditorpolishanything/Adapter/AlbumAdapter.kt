package com.example.photoeditorpolishanything.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.DashboardActivity
import com.example.photoeditorpolishanything.R

class AlbumAdapter(private val albums: List<DashboardActivity.Album>, private val onClick: (DashboardActivity.Album) -> Unit) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val albumName: TextView = view.findViewById(R.id.albumName)
        val albumThumbnail: ImageView = view.findViewById(R.id.albumThumbnail)
        val photoCount: TextView = view.findViewById(R.id.numberimages)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.albumName.text = album.name
        holder.photoCount.text = album.photoCount.toString()
        holder.albumThumbnail.setImageURI(album.thumbnailUri)

        holder.itemView.setOnClickListener {
            onClick(album)
        }
    }

    override fun getItemCount() = albums.size
}
