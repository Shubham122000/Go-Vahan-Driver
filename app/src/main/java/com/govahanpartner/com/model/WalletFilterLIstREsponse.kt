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
    @SerializedName("id"               ) var id              : Int?    = null,
    @SerializedName("type"             ) var type            : Int?    = null,
    @SerializedName("user_id"          ) var userId          : Int?    = null,
    @SerializedName("amount"           ) var amount          : String? = null,
    @SerializedName("transaction_type" ) var transactionType : String? = null,
    @SerializedName("booking_id"       ) var bookingId       : String? = null,
    @SerializedName("referal_type"     ) var referalType     : Int?    = null,
    @SerializedName("transaction_date" ) var transactionDate : String? = null,
    @SerializedName("transaction_id"   ) var transactionId   : String? = null,
    @SerializedName("invoice"          ) var invoice         : String? = null,
    @SerializedName("status"           ) var status          : Int?    = null,
    @SerializedName("created_at"       ) var createdAt       : String? = null,
    @SerializedName("updated_at"       ) var updatedAt       : String? = null,
    @SerializedName("to_id"            ) var toId            : String? = null,
    @SerializedName("from_id"          ) var fromId          : String? = null,
    @SerializedName("from_user"        ) var fromUser        : User? = User(),
    @SerializedName("to_user"          ) var toUser          : User?   = User()
)
