package com.gvpartner.com.model

data class TruckDocumentsDetailsResponse(
    var `data`: ArrayList<TruckDocumentsData>,
    var message: String,
    var status: Int
)

data class TruckDocumentsData(
    var doc: String,
    var doc_name: String,
    var exp_date: String
)