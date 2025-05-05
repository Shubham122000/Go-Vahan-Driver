package com.gvpartner.com.model

data class GetBankAcountResponse(
    val `data`: List<GetBankAcountData>,
    val message: String,
    val status: Int
)

data class GetBankAcountData(
    val account_branch: String,
    val account_holder_name: String,
    val account_no: String,
    val bank_name: String,
    val id: Int,
    val ifsc: String,
    val image: String,
    val status: Int,
    val upi_id: String,
    val v_id: Int,
    val vendor_id: Int
)