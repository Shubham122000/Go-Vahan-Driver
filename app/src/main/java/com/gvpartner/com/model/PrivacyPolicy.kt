package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class PrivacyPolicy(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : PrivacyPolicyData?   = PrivacyPolicyData()

)
data class PrivacyPolicyData(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("type"        ) var type        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("created_at"  ) var createdAt   : String? = null,
    @SerializedName("updated_at"  ) var updatedAt   : String? = null
)
