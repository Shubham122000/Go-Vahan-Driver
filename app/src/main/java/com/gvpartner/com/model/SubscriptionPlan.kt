package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class SubscriptionPlan(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : SubscriptionPlanResult?  = SubscriptionPlanResult()

//    @SerializedName("status"  ) var status  : Int?            = null,
//    @SerializedName("message" ) var message : String?         = null,
//    @SerializedName("data"    ) var data    : ArrayList<SubscriptionPlanData> = arrayListOf()
)
data class SubscriptionPlanResult(
    @SerializedName("data" ) var data : ArrayList<SubscriptionPlanData> = arrayListOf()
)
data class SubscriptionPlanData(
    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("plan_name"     ) var planName     : String? = null,
    @SerializedName("description"   ) var description  : String? = null,
    @SerializedName("amount"        ) var amount       : String? = null,
    @SerializedName("validity"      ) var validity     : Int?    = null,
    @SerializedName("status"        ) var status       : Int?    = null,
    @SerializedName("for_loader"    ) var forLoader    : Int?    = null,
    @SerializedName("for_passenger" ) var forPassenger : Int?    = null
)

