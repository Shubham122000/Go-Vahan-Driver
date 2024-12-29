package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class DownloadPdfResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("url"     ) var url     : String? = null
)
