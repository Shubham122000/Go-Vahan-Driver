package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class TaxiRepositoryViewDetailsResponse(


    @SerializedName("status") val status : Int,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : TaxiRepositoryView

)

data class TaxiRepositoryView(


@SerializedName("status") val status : Int,
@SerializedName("vehicle_id") val vehicle_id : Int,
@SerializedName("owner_name") val owner_name : String,
@SerializedName("loader_name") val loader_name : String,
@SerializedName("loader_number") val loader_number : String,
@SerializedName("vendor_name") val vendor_name : String,
@SerializedName("mobile_number") val mobile_number : String,
@SerializedName("image") val image : String,
@SerializedName("capacity") val capacity : String,
@SerializedName("height") val height : String,
@SerializedName("bodyname") val bodyname : String,
@SerializedName("year") val year : Int,
@SerializedName("tyres") val tyres : String,
@SerializedName("body_type") val body_type : String,
@SerializedName("seat") val seat : Int,

)