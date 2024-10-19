package com.example.photoeditorpolishanything

interface OnStickerClickListener {
    fun onStickerClicked(imageUrls: List<String>)
}

interface StickerClickListener {
    fun onStickerSelected(imageUrl: String)
}

