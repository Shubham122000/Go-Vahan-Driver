package com.govahanpartner.com.model

data class VehicleNumberPassengerLIst(
    val `data`: ArrayList<VehicleNumberData>,
    val message: String,
    val status: Int
)

data class VehicleNumberData(
    val vehicle_no: String,
    val id: Int
)