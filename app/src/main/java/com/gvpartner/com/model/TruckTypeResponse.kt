package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class TruckTypeResponse(
    @SerializedName("status"  ) var status  : Int?            = 0,
    @SerializedName("message" ) var message : String?         = "",
    @SerializedName("data"    ) var data    : ArrayList<TruckTypeResponseData> = arrayListOf()
)
data class TruckTypeResponseData(

    @SerializedName("id"         ) var id        : Int?    = 0,
    @SerializedName("service"    ) var service   : String? = "",
    @SerializedName("created_at" ) var createdAt : String? = "",
    @SerializedName("updated_at" ) var updatedAt : String? = ""
){
    override fun toString(): String {
        return service.toString()
    }
}

