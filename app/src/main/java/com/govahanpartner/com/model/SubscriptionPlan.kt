package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class SubscriptionPlan(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<SubscriptionPlanData> = arrayListOf()
)
data class SubscriptionPlanData(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("plan_name"   ) var planName    : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("amount"      ) var amount      : String? = null,
    @SerializedName("plan_type"   ) var planType    : Int?    = null,
    @SerializedName("status"      ) var status      : Int?    = null,
    @SerializedName("created_at"  ) var createdAt   : String? = null,
    @SerializedName("updated_at"  ) var updatedAt   : String? = null
)

