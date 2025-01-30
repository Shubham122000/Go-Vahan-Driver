package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class InvoiceListResponse(
//    @SerializedName("status"  ) var status  : Int?            = null,
//    @SerializedName("message" ) var message : String?         = null,
//    @SerializedName("data"    ) var data    : ArrayList<InvoiceListResponseData> = arrayListOf()

    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : InvoiceListResponseResult?  = InvoiceListResponseResult()
)
data class InvoiceListResponseResult (

    @SerializedName("data" ) var data : ArrayList<InvoiceListResponseData> = arrayListOf()

)
data class InvoiceListResponseData(
    @SerializedName("id"              ) var id             : Int?                      = null,
    @SerializedName("booking_id"      ) var bookingId      : String?                   = null,
    @SerializedName("trip_id"         ) var tripId         : Int?                      = null,
    @SerializedName("created_at"      ) var createdAt      : String?                   = null,
    @SerializedName("booking_time"    ) var bookingTime    : String?                   = null,
    @SerializedName("payment_details" ) var paymentDetails : ArrayList<PaymentDetails> = arrayListOf(),
    @SerializedName("is_doc_upload"   ) var isDocUpload    : Boolean?                  = null,
    @SerializedName("pdf_url"         ) var pdfUrl         : String?                   = null,
    @SerializedName("trip_details"    ) var tripDetails    : TripDetails?              = TripDetails()
)