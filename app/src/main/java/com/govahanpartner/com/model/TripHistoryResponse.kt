package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class TripHistoryResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<TripHistoryResponseData> = arrayListOf()
)
data class TripHistoryResponseData(
    @SerializedName("booking_id"     ) var bookingId     : String? = null,
    @SerializedName("vechicle_id"    ) var vechicleId    : String? = null,
    @SerializedName("picup_location" ) var picupLocation : String? = null,
    @SerializedName("drop_location"  ) var dropLocation  : String? = null,
    @SerializedName("booking_date"   ) var bookingDate   : String? = null,
    @SerializedName("capacity"       ) var capacity      : String? = null,
    @SerializedName("booking_time"   ) var bookingTime   : String? = null,
    @SerializedName("vehicle_image"  ) var vehicleImage  : String? = null,
    @SerializedName("party_name"     ) var partyName     : String? = null
)