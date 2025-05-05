package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class InvoiceurldownloadResponse (
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("url" ) var url : String? = null,
)
