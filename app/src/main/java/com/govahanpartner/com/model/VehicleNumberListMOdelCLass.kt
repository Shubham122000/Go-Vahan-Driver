package com.govahanpartner.com.model

data class VehicleNumberListMOdelCLass(
    val `data`: ArrayList<VehicleNumberListData>,
    val message: String,
    val status: Int
)

data class VehicleNumberListData(
    val vehicle_number: String,
    val id:String
)