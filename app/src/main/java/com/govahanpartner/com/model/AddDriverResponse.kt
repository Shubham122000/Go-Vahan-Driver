package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class AddDriverResponse (
    @SerializedName("error"  ) var error  : Boolean?    = null,
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
//    @SerializedName("data"    ) var data    : String? = null
)