package com.gvpartner.com.ui.common

import com.google.gson.annotations.SerializedName

data class ReferNEarnResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : ReferNEarnResult?  = ReferNEarnResult()
)
data class ReferNEarnResult (
    @SerializedName("referal_code"         ) var referalCode        : String? = null,
    @SerializedName("referal_link"         ) var referalLink        : String? = null

)
//data class ReferNEarnData(
//    val referal_code: String,
//    val referal_link: String,
//    val user_id: Int
//)