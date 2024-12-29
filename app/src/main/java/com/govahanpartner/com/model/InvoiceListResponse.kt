package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class InvoiceListResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<InvoiceListResponseData> = arrayListOf()
)
data class InvoiceListResponseData(
    @SerializedName("picup_location" ) var picupLocation : String? = null,
    @SerializedName("drop_location"  ) var dropLocation  : String? = null,
    @SerializedName("booking_id"     ) var bookingId     : String? = null,
    @SerializedName("booking_date"   ) var bookingDate   : String? = null,
    @SerializedName("booking_time"   ) var bookingTime   : String? = null,
    @SerializedName("invoice_number" ) var invoiceNumber : String? = null
)