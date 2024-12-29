package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class TripListResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<TripListResponseData> = arrayListOf()
)

data class TripListResponseData(
    @SerializedName("pickup_lat"       ) var pickupLat       : String? = null,
    @SerializedName("id"       ) var id       : Int? = null,
    @SerializedName("pickup_long"      ) var pickupLong      : String? = null,
    @SerializedName("dropup_lat"       ) var dropupLat       : String? = null,
    @SerializedName("dropup_long"      ) var dropupLong      : String? = null,
    @SerializedName("tip_task"         ) var tipTask         : String? = null,
    @SerializedName("load_caring"      ) var loadCaring      : String? = null,
    @SerializedName("from_trip"        ) var fromTrip        : String? = null,
    @SerializedName("to_trip"          ) var toTrip          : String? = null,
    @SerializedName("vehicle_category" ) var vehicleCategory : Int?    = null,
    @SerializedName("total_distance"   ) var totalDistance   : String? = null,
    @SerializedName("billing_type"     ) var billingType     : Int?    = null,
    @SerializedName("freight_amount"   ) var freightAmount   : String? = null,
    @SerializedName("created_at"       ) var createdAt       : String? = null,
    @SerializedName("updated_at"       ) var updatedAt       : String? = null,
    @SerializedName("name"             ) var name            : String? = null,
    @SerializedName("vehicle_name"     ) var vehicleName     : String? = null,
    @SerializedName("vehicle_number"   ) var vehicleNumber   : String? = null,
    @SerializedName("vehicle_type"     ) var vehicleType     : String? = null,
    @SerializedName("no_of_tyers"      ) var noOfTyers       : String? = null,
    @SerializedName("body_name"        ) var bodyName        : String? = null,
    @SerializedName("image"            ) var image           : String? = null
)