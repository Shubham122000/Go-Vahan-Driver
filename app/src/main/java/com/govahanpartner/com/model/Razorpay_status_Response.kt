package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class Razorpay_status_Response(
    @SerializedName("status"  ) var status  : String?            = null,
    @SerializedName("message" ) var message : String?         = null,
)
