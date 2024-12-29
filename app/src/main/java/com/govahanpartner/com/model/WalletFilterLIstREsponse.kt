package com.govahanpartner.com.model

data class WalletFilterLIstREsponse(
    var `data`: List<WalletFilterLIstData>,
    var message: String,
    var status: Int, var TotalAmount: String
)


data class WalletFilterLIstData(
    var amount: String,
    var credit: String,
    var name: String,
    var create_at: String,
    var transaction_id:String
)
