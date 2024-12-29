package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class FareCaluculationResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : FareCaluculationResponseData?   = FareCaluculationResponseData()
)

data class FareCaluculationResponseData(
    @SerializedName("distance"    ) var distance   : String? = null,
    @SerializedName("amount"      ) var amount     : String? = null,
    @SerializedName("pickup_lat"  ) var pickupLat  : String? = null,
    @SerializedName("pickup_long" ) var pickupLong : String? = null,
    @SerializedName("dropup_lat"  ) var dropupLat  : String? = null,
    @SerializedName("dropup_long" ) var dropupLong : String? = null

)
