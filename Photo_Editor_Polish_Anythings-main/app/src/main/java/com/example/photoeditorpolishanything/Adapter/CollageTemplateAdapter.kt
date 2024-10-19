package com.example.photoeditorpolishanything.Adapter


import ImageAdapter
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Api.Group
import com.example.photoeditorpolishanything.EditActivity
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class CollageTemplateAdapter(
    private val context: Context,
    private val groups: List<Group> ) : RecyclerView.Adapter<CollageTemplateAdapter.ViewHolder>() {

//    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/template/"
    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/template/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collage_template_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]

        holder.imageView.setOnClickListener {
            showBottomSheetForImageEditUrl(group)
        }

        holder.bind(group)
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    // ViewHolder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView) // Replace with your actual ImageView ID

        fun bind(group: Group) {
            // Bind data to your views here
            Glide.with(itemView.context).load(baseUrl + group.imageUrl).into(imageView)
        }
    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    fun showBottomSheetForImageEditUrl(group: Group) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetStyle)
        val view = LayoutInflater.from(context).inflate(R.layout.camera_dialog, null)
        view.findViewById<ImageView>(R.id.imgCross)?.setOnClickListener { bottomSheetDialog.dismiss() }
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Set layout manager with 3 columns
        recyclerView.layoutManager = GridLayoutManager(context,3)

        view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView)?.let { it.visibility = View.GONE }

        view.findViewById<LinearLayout>(R.id.lnrimgDelete)?.let { it.visibility = View.GONE }

        view.findViewById<RecyclerView>(R.id.rcvSelected_Image)?.let { it.visibility = View.GONE }

        view.findViewById<RecyclerView>(R.id.recyclerView)?.let { it.visibility = View.VISIBLE }

        view.findViewById<RecyclerView>(R.id.recyclerViewAlbum)?.let { it.visibility = View.GONE }

        view.findViewById<RecyclerView>(R.id.recyclerViewImages)?.let { it.visibility = View.GONE }

        val lnrPhotos = view.findViewById<LinearLayout>(R.id.lnrPhotos)
        val lnrAlbums = view.findViewById<LinearLayout>(R.id.lnrAlbums)

        // Ensure Photos is selected by default
        lnrPhotos.setBackgroundResource(R.drawable.toggle_selected)
        lnrAlbums.setBackgroundResource(R.drawable.toggle_unselected)

        lnrPhotos.setOnClickListener {
            lnrPhotos.setBackgroundResource(R.drawable.toggle_selected)
            lnrAlbums.setBackgroundResource(R.drawable.toggle_unselected)

            view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView)?.let { it.visibility = View.GONE }

            view.findViewById<LinearLayout>(R.id.lnrimgDelete)?.let { it.visibility = View.GONE }

            view.findViewById<RecyclerView>(R.id.rcvSelected_Image)?.let { it.visibility = View.GONE }

            view.findViewById<RecyclerView>(R.id.recyclerView)?.let { it.visibility = View.VISIBLE }

            view.findViewById<RecyclerView>(R.id.recyclerViewAlbum)?.let { it.visibility = View.GONE }

            view.findViewById<RecyclerView>(R.id.recyclerViewImages)?.let { it.visibility = View.GONE }
        }

        // Fetch images using AsyncTask and populate the RecyclerView
        FetchImagesTasks(context)
        { imageList ->

            recyclerView.adapter = ImageAdapter(imageList)
            { selectedImageUri ->
                bottomSheetDialog.dismiss()
                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("selected_imageEditUrl", baseUrl + group.imageEditUrl)
                intent.putExtra("source", "adapter")
                intent.putExtra("selected_image_uri", selectedImageUri.toString())
                context.startActivity(intent)
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setOnShowListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    bottomSheetDialog.window?.navigationBarColor = ContextCompat.getColor(context, R.color.black)
                }
            }
            bottomSheetDialog.show()
        }.execute()
    }


    private class FetchImagesTasks(private val context: Context, private val callback: (List<ImageItem>) -> Unit) : AsyncTask<Void, Void, List<ImageItem>>()
    {
        override fun doInBackground(vararg params: Void?): List<ImageItem>
        {
            val images = mutableListOf<ImageItem>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val cursor = context.contentResolver.query(uri,projection,null,null,
                MediaStore.Images.Media.DATE_ADDED + " DESC")

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    images.add(ImageItem(imageUri))
                }
            }
            return images
        }

        override fun onPostExecute(result: List<ImageItem>)
        {
            callback(result)
        }
    }
}