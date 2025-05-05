package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class YearResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : YearResponseResult?  = YearResponseResult()

)
data class YearResponseResult(
    @SerializedName("data"    ) var data    : ArrayList<YearResponseData> = arrayListOf()
)
data class YearResponseData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("year"      ) var year     : String? = null,
){
    override fun toString(): String {
        return year.toString()
    }
}
