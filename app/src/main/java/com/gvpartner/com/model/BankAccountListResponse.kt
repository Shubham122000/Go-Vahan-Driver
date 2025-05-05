package com.gvpartner.com.model

data class BankAccountListResponse(
    var `data`: List<BankAccountListData>,
    var message: String,
    var status: Int
)

data class BankAccountListData(
    var account_branch: String,
    var account_holder_name: String,
    var account_no: String,
    var bank_name: String,
    var id: Int,
    var ifsc: String,
    var status: Int,
    var vendor_id: Int
)