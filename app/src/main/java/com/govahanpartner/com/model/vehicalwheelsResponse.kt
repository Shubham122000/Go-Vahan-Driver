package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class vehicalwheelsResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : vehicalwheelsResponseResult?  = vehicalwheelsResponseResult()
)
data class vehicalwheelsResponseResult(
    @SerializedName("data"    ) var data    : ArrayList<vehicalwheelsResponseData> = arrayListOf()
)
data class vehicalwheelsResponseData(

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("type"       ) var type      : Int?    = null,
    @SerializedName("wheel"      ) var wheel     : String? = null,
    @SerializedName("status"     ) var status    : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null
){
    override fun toString(): String {
        return wheel.toString()
    }
}
