package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class StateListResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<StateListResponseData> = arrayListOf()
)
data class StateListResponseData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("name" ) var stateName : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null
)
