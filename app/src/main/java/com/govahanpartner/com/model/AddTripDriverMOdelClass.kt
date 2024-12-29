package com.govahanpartner.com.model

data class AddTripDriverMOdelClass(
    val `data`: AddTripDriverData,
    val message: String,
    val name: Name,
    val status: Int
)

data class AddTripDriverData(
    val body_name: String,
    val no_of_tyers: String,
    val pickup_lat: String,
    val vehicle_category: Any,
    val vehicle_name: String,
    val vehicle_number: String,
    val vehicle_type: String,
    val load_caring: String,
    val capacity: String,
    val body_type: Int,
    val vehicle_number_id: Int,
    val vehicle_type_id: Int
)

data class Name(
    val driver_name: String,
    val driver_id: String
)