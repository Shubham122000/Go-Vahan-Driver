package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class ChecksumResponse (
    @SerializedName("success") val success : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : ChecksumData

)
data class ChecksumData (

    @SerializedName("merchantId") val merchantId : String,
    @SerializedName("merchantTransactionId") val merchantTransactionId : String,
    @SerializedName("instrumentResponse") val instrumentResponse : InstrumentResponse
)
data class InstrumentResponse (

    @SerializedName("type") val type : String,
    @SerializedName("redirectInfo") val redirectInfo : RedirectInfo
)
data class RedirectInfo (

    @SerializedName("url") val url : String,
    @SerializedName("method") val method : String
)