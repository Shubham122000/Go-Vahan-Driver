package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class LoaderDriverTripCancelResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("CRN"     ) var CRN     : String? = null
)
