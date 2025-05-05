package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class AddVehicalfinalResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null
)
