package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class PaymentSuccessMsgResponse (
    @SerializedName("success") val success : Boolean,
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : PaymentSuccessData
    )

data class PaymentSuccessData (

    @SerializedName("merchantId") val merchantId : String,
    @SerializedName("merchantTransactionId") val merchantTransactionId : String,
    @SerializedName("transactionId") val transactionId : String,
    @SerializedName("amount") val amount : Int,
    @SerializedName("state") val state : String,
    @SerializedName("responseCode") val responseCode : String,
    @SerializedName("paymentInstrument") val paymentInstrument : PaymentInstrument
)
data class PaymentInstrument (

    @SerializedName("type") val type : String,
//    @SerializedName("utr") val utr : Int,
    @SerializedName("cardNetwork") val cardNetwork : String,
    @SerializedName("accountType") val accountType : String
)