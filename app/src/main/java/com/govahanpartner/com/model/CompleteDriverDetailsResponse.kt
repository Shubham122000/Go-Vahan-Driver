package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

  data class CompleteDriverDetailsResponse(
    val `data`: CompleteDriverDetailsData,
    val message: String,
    val ownerDetails: CompleteDriverDetailsOwnerDetails,
    val status: Int,
    val userDetails: CompleteDriverDetailsUserDetails
)

data class CompleteDriverDetailsData(
    val assigned_driver_id: Any,
    val body_type: String,
    val bodyname: String,
    val booking_date: String,
    val booking_id: String,
    val booking_relation_id: Int,
    val booking_status: String,
    val booking_time: Any,
    val cancelation_reason: Any,
    val cancellation_date: Any,
    val capacity: Any,
    val height: String,
    val created_at: String,
    val currency: String,
    val distance: String,
    val driver_id: Int,
    val drop_lat: String,
    val drop_location: String,
    val drop_long: String,
    val fare: Any,
    val fare_total: Any,
    val id: Int,
    val invoice_number: String,
    val is_verified_status: Int,
    val no_of_tyres: Int,
    val no_tyres: Int,
    val payment_mode: String,
    val payment_status: String,
    val picup_lat: String,
    val picup_location: String,
    val picup_long: String,
    val start_code: String,
    val transaction_id: String,
    val updated_at: String,
    val user_id: Int,
    val v_id: Int,
    val vechicle_id: String,
    val vehicle_image: String,
    val vehicle_name: String,
    val vehicle_number: String,
    val vehicle_numbers: String,
    val year_of_model: String,
    val is_doc_upload: Int
)

data class CompleteDriverDetailsOwnerDetails(
    val email: String,
    val image: String,
    val licenseno: String,
    val mobile: String,
    val name: String,
    val profile_image: String,
    val v_id: Int
)

data class CompleteDriverDetailsUserDetails(
    val email: String,
    val mobile_number: String,
    val name: String
)