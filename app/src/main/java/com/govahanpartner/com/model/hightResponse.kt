package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class hightResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : hightResponseResult?  = hightResponseResult()
)
data class hightResponseResult(
    @SerializedName("data" ) var data : ArrayList<hightResponseData> = arrayListOf()
)


data class hightResponseData(
    @SerializedName("id"     ) var id     : Int? = null,
    @SerializedName("height" ) var height : Int? = null
){
    override fun toString(): String {
        return height.toString()
    }
}
