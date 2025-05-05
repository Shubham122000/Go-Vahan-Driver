package com.gvpartner.com.model

data class TruckImagesModelCLass(
    var `data`: TruckImagesData,
    var message: String,
    var status: Int
)

data class TruckImagesData(
    var id1: Int,
    var id2: Int,
    var id3: Int,
    var id4: Int,
    var image1: String,
    var image2: String,
    var image3: String,
    var image4: String
)