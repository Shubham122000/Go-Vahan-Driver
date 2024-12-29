package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class VehicleNumberListMOdelCLass(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : VehicleNumberListResult?  = VehicleNumberListResult()
)

data class VehicleNumberListResult(
    @SerializedName("data" ) var data : ArrayList<VehicleNumberListData> = arrayListOf()
)
data class VehicleNumberListData(
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("vehicle_number" ) var vehicleNumber : String? = null,
    @SerializedName("capacity" ) var capacity : String? = null
)