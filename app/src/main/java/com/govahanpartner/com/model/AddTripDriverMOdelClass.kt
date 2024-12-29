package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class AddTripDriverMOdelClass(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : AddTripDriverMOdelClassResult?  = AddTripDriverMOdelClassResult()
)

//data class AddTripDriverData(
//    val body_name: String,
//    val no_of_tyers: String,
//    val pickup_lat: String,
//    val vehicle_category: Any,
//    val vehicle_name: String,
//    val vehicle_number: String,
//    val vehicle_type: String,
//    val load_caring: String,
//    val capacity: String,
//    val body_type: Int,
//    val vehicle_number_id: Int,
//    val vehicle_type_id: Int
//)

data class AddTripDriverMOdelClassResult(
    @SerializedName("trips"        ) var trips       : ArrayList<String> = arrayListOf(),
    @SerializedName("total"        ) var total       : Int?              = null,
    @SerializedName("current_page" ) var currentPage : Int?              = null,
    @SerializedName("limit"        ) var limit       : Int?              = null
)