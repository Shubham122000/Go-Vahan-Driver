package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class DeiselPrice(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : DeiselPriceData = DeiselPriceData()
)
data class DeiselPriceData(
    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("state_id"     ) var stateId     : String? = null,
    @SerializedName("diesel_price" ) var dieselPrice : String? = null,
    @SerializedName("petrol_price" ) var petrol_price : String? = null
)
