//package com.example.photoeditorpolishanything.FaceDetaction
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.util.Log
//import com.google.mediapipe.solutions.facemesh.FaceMesh
//import com.google.mediapipe.solutions.facemesh.FaceMeshOptions
//import com.google.mediapipe.solutions.facemesh.FaceMeshResult
//import com.google.mediapipe.framework.Packet
//import com.google.mediapipe.framework.PacketCallback
//import com.google.mediapipe.framework.PacketGetter
//import com.google.mediapipe.framework.Frame
//import com.google.mlkit.vision.facemesh.FaceMesh
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class FaceMeshHelper(context: Context) {
//    private val faceMesh: FaceMesh = FaceMesh(
//        context,
//        FaceMeshOptions.builder()
//            .setMaxNumFaces(1)
//            .setRefineLandmarks(true)
//            .build()
//    )
//
//    init {
//        // Initialize face mesh processing
//    }
//
//    suspend fun processBitmap(bitmap: Bitmap, callback: (FaceMeshResult) -> Unit) {
//        withContext(Dispatchers.Default) {
//            faceMesh.process(bitmap)
//            faceMesh.setOnResultListener { result ->
//                callback(result)
//            }
//        }
//    }
//}