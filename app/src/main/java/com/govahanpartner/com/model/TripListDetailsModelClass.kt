package com.govahanpartner.com.model

data class TripListDetailsModelClass(
    var `data`: TripListDetailsData,
    var message: String,
    var status: Int
)

data class TripListDetailsData(
    var billing_type: Any,
    var body_name: String,
    var created_at: String,
    var dropup_lat: String,
    var dropup_long: String,
    var email: String,
    var owner_name: String,
    var vendor_name: String,
    var vendor_email: String,
    var vendor_mobile: String,
    var freight_amount: String,
    var from_trip: String,
    var load_caring: String,
    var id: Int,
    var image: String,
    var loader_trip_list: String,
    var time: String,
    var mobile_number: String,
    var booking_date_from: String,
    var name: String,
    var no_of_tyers: String,
    var pickup_lat: String,
    var pickup_long: String,
    var tip_task: String,
    var to_trip: String,
    var total_distance: String,
    var updated_at: Any,
    var vehicle_category: Any,
    var vehicle_name: String,
    var vehicle_no: String,
    var vehicle_numbers: String,
    var vehicle_type: String
)