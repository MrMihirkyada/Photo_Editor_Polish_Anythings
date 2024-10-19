package com.example.photoeditorpolishanything

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

// Fade out and shrink animation
class CustomImageRemoveAnimator : DefaultItemAnimator() {
    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        val itemView = holder.itemView

        // Fade out and shrink animation
        itemView.animate()
            .alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .setDuration(300)
            .withEndAction {
                dispatchRemoveFinished(holder)
                itemView.alpha = 1f
                itemView.scaleX = 1f
                itemView.scaleY = 1f
            }

        return true
    }
}


// Zoom out animation
//class CustomImageRemoveAnimator : DefaultItemAnimator() {
//    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
//        val itemView = holder.itemView
//
//        // Zoom out animation
//        itemView.animate()
//            .scaleX(0.5f)
//            .scaleY(0.5f)
//            .alpha(0f)
//            .setDuration(300)
//            .withEndAction {
//                dispatchRemoveFinished(holder)
//                itemView.scaleX = 1f
//                itemView.scaleY = 1f
//                itemView.alpha = 1f
//            }
//
//        return true
//    }
//}



// Flip out animation
//class CustomImageRemoveAnimator : DefaultItemAnimator() {
//    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
//        val itemView = holder.itemView
//
//        // Flip out animation
//        itemView.animate()
//            .rotationY(90f)
//            .alpha(0f)
//            .setDuration(300)
//            .withEndAction {
//                dispatchRemoveFinished(holder)
//                itemView.rotationY = 0f
//                itemView.alpha = 1f
//            }
//
//        return true
//    }
//}