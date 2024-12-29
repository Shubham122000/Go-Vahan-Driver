package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class AboutUs(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : AboutUsData?   = AboutUsData()
)
data class AboutUsData(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("created_at"  ) var createdAt   : String? = null,
    @SerializedName("updated_at"  ) var updatedAt   : String? = null
)
