
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.R

class ImageAdapter(private val imageList: List<ImageItem>, private val onImageClick: (Uri) -> Unit) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        init
        {
            itemView.setOnClickListener {
                val selectedImageUri = imageList[adapterPosition].uri
                onImageClick(selectedImageUri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int)
    {
        val imageItem = imageList[position]
        Glide.with(holder.imageView.context)
            .load(imageItem.uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(300, 300)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int
    {
        return imageList.size
    }

    interface OnImageClickListener
    {
        fun onImageClick(uri: Uri)
    }
}