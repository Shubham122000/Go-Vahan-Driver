package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName


data class PrivacyPolicyModel (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ProvacyPolicyData?   = ProvacyPolicyData()

)

data class ProvacyPolicyData (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("type"        ) var type        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("created_at"  ) var createdAt   : String? = null,
    @SerializedName("updated_at"  ) var updatedAt   : String? = null

)