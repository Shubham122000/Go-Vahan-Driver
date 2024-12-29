package com.govahanpartner.com.model

data class VendorWalletActivity(
    var `data`: List<VendorWalletData>,
    var message: String,
    var status: Int,  var TotalAmount: String
)

data class VendorWalletData(
    var amount: String,
    var name: String,
    var transaction_date: String,
    var transaction_type: String, var driver_name: String,var referal_type: Int,
    var user_id: Int, var custom_key_one: Int, var custom_key: Int,var transaction_id:String,var create_at:String
)