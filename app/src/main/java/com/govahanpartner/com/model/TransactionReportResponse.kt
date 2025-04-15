package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class TransactionReportResponse(
    @SerializedName("error"       ) var error      : Boolean?          = null,
    @SerializedName("status_code" ) var statusCode : Int?              = null,
    @SerializedName("message"     ) var message    : String?           = null,
    @SerializedName("result"      ) var result     : ArrayList<TransactionReportResponseData> = arrayListOf()
)
data class TransactionReportResponseData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("amount"     ) var amount    : Int?    = null,
    @SerializedName("status"     ) var status    : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null
)
