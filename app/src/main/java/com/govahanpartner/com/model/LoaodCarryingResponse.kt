package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class LoaodCarryingResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<LoadCarryingData> = arrayListOf()
)
data class LoadCarryingData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("capacity"   ) var capacity  : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null
){
    override fun toString(): String {
        return capacity.toString()
    }
}
