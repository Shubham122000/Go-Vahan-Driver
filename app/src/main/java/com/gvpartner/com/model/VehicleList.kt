package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class VehicleList(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<VehicleListData> = arrayListOf()
)
data class VehicleListData(
    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("vehicle_name" ) var vehicleName : String? = null
){
    override fun toString(): String {
        return vehicleName.toString()
    }
}
