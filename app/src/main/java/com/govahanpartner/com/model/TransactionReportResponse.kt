package com.govahanpartner.com.model

data class TransactionReportResponse(
    val message: String,
    val status: Int,
    val data: ArrayList<TransactionReportResponseData>,
)
data class TransactionReportResponseData(
    val booking_date: Any,
    val created_at: String,
    val fare: String,
    val name: String,
    val transaction_id: String

)
