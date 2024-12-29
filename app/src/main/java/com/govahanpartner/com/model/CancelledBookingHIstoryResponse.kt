package com.govahanpartner.com.model

data class CancelledBookingHIstoryResponse(
    var `data`: List<CancelledBookingHIstoryData>,
    var message: String,
    var status: Int
)

data class CancelledBookingHIstoryData(
    var booking_date: String,
    var booking_id: String,
    var booking_time: Any,
    var capacity: String,
    var drop_location: String,
    var picup_location: String,
    var vechicle_id: String,
    var vehicle_image: String
)