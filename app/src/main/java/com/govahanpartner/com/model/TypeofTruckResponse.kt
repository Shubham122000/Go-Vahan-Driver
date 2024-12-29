package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class TypeofTruckResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : TypeofTruckResponseResult?  = TypeofTruckResponseResult()
)
data class TypeofTruckResponseResult(
    @SerializedName("data" ) var data : ArrayList<TypeofTruckResponseData> = arrayListOf()

)
data class TypeofTruckResponseData(
    @SerializedName("id"     ) var id    : Int?    = null,
    @SerializedName("v_type" ) var vType : String? = null
)
