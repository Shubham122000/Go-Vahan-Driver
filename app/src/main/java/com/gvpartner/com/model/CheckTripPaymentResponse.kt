package com.gvpartner.com.model

data class CheckTripPaymentResponse(
    val `data`: CheckTripPaymentData,
    val message: String,
    val status: Int,
    val totalAmount: String
)

data class CheckTripPaymentData(
    val fare: String,
    val freight_amount: String
)