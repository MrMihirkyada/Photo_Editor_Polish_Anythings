package com.example.photoeditorpolishanything.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.Api.Groupes
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.StoreFragment.LightFxBottomSheetDialogFragment

class LightFx_Adapter(private var data: MutableList<Groupes>) : RecyclerView.Adapter<LightFx_Adapter.LightFxViewHolder>() {
    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/lightfx/"

    fun updateData(newData: MutableList<Groupes>) {
        data = newData
        Log.d("Adapter", "Updated data size: ${data.size}")
        data.forEachIndexed { index, item ->
            Log.d("Adapter", "Item $index: ${item.javaClass.simpleName}, value: $item")
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightFxViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lightfx, parent, false)
        return LightFxViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LightFxViewHolder, position: Int) {
        val lightFxItem = data.getOrNull(position)

        if (lightFxItem is Groupes) {
            val imageUrl = lightFxItem.mainImageUrl?.firstOrNull()
            if (imageUrl != null) {
                Glide.with(holder.itemView.context)
                    .load(baseUrl + imageUrl)
                    .fitCenter()
                    .centerCrop()
                    .into(holder.imageView)
            } else {
                holder.imageView.setImageResource(R.drawable.cross) // Placeholder image
            }

            holder.txtName.text = lightFxItem.textCategory
            holder.txtNumber.text = "${lightFxItem.subImageUrl?.size ?: 0} Light Fx"

            holder.itemView.setOnClickListener {
                val activity = it.context as? FragmentActivity
                activity?.let {
                    val bottomSheetDialogFragment = LightFxBottomSheetDialogFragment.newInstance(lightFxItem.subImageUrl, R.color.black,
                        lightFxItem.textCategory.toString()
                    )
                    bottomSheetDialogFragment.show(it.supportFragmentManager, bottomSheetDialogFragment.tag)
                }
            }
        } else {
            Log.e("Adapter", "Unexpected data type at position $position: ${lightFxItem?.javaClass?.simpleName}")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class LightFxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView = itemView.findViewById(R.id.imgSticker)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtNumber: TextView = itemView.findViewById(R.id.txtNumber)
    }
}