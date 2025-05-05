package com.gvpartner.com.model


data class WalletListResponse(
    var TotalAmount: String,
    var `data`: List<WalletListData>,
    var message: String,
    var status: Int
)

data class WalletListData(
    var amount: String,
    var credit: String,
    var name: String,var fare:String,
    var debit:String,val transaction_type:String,
    var transaction_date: String,var transaction_id:String

)