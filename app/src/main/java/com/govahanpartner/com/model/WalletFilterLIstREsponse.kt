package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class WalletFilterListResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : WalletFilterListResult?  = WalletFilterListResult()
)

data class WalletFilterListResult (

    @SerializedName("total_amount" ) var totalAmount : Double?         = null,
    @SerializedName("data"         ) var data        : ArrayList<WalletFilterListData> = arrayListOf()

)
data class WalletFilterListData(
    @SerializedName("id"              ) var id             : Int?            = null,
    @SerializedName("booking_id"      ) var bookingId      : String?         = null,
    @SerializedName("user_id"         ) var userId         : Int?            = null,
    @SerializedName("user"            ) var user           : User?           = User(),
    @SerializedName("payment_details" ) var paymentDetails : PaymentDetails? = PaymentDetails()
)
