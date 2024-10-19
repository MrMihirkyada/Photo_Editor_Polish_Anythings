package com.example.photoeditorpolishanything.FaceDetaction

//fun detectFaces(bitmap: Bitmap) {
//    val image = InputImage.fromBitmap(bitmap, 0)
//
//    faceDetector.process(image)
//        .addOnSuccessListener { faces ->
//            for (face in faces) {
//                // Handle detected faces
//                val bounds = face.boundingBox
//                val rotY = face.headEulerAngleY
//                val rotZ = face.headEulerAngleZ
//                val smilingProbability = face.smilingProbability
//                val rightEyeOpenProbability = face.rightEyeOpenProbability
//                val leftEyeOpenProbability = face.leftEyeOpenProbability
//
//                // Access landmarks
//                val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)
//                val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)
//                val noseBase = face.getLandmark(FaceLandmark.NOSE_BASE)
//                val leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK)
//                val rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)
//
//                // Do something with the face data
//            }
//        }
//        .addOnFailureListener { e ->
//            // Handle error
//        }
//}
