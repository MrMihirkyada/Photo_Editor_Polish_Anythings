package com.example.photoeditorpolishanything.Model

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.photoeditorpolishanything.Api.Groupas
import kotlinx.coroutines.launch

data class ImageItem(val uri: Uri)

data class Sticker(val id: String, val url: String)

data class StickerResponse(val baseUrl: String, val data: Map<String, Groupas>)

class ImageViewModel(application: Application) : AndroidViewModel(application) {

    fun getAllImagesFromStorage(): LiveData<List<Uri>> {
        val imageList = MutableLiveData<List<Uri>>()

        viewModelScope.launch {
            val imageUris = mutableListOf<Uri>()
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = getApplication<Application>().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )

            cursor?.use {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                while (it.moveToNext()) {
                    val imagePath = it.getString(columnIndex)
                    imageUris.add(Uri.parse(imagePath))
                }
            }

            imageList.postValue(imageUris)
        }

        return imageList
    }
}