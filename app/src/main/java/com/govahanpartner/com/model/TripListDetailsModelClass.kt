package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TripListDetailsModelClass(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : TripListDetailsResult?  = TripListDetailsResult()
): Serializable

data class TripListDetailsResult(
    @SerializedName("trips" ) var trips : ArrayList<TripListDetailsData> = arrayListOf(),
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("page"  ) var page  : Int?             = null,
    @SerializedName("limit" ) var limit : Int?             = null
): Serializable
data class TripListDetailsData(
    @SerializedName("id"                ) var id              : Int?     = null,
    @SerializedName("loader_type"       ) var loaderType      : Int?     = null,
    @SerializedName("user_id"           ) var userId          : Int?     = null,
    @SerializedName("tip_task"          ) var tipTask         : String?  = null,
    @SerializedName("load_caring"       ) var loadCaring      : String?  = null,
    @SerializedName("from_trip"         ) var fromTrip        : String?  = null,
    @SerializedName("pickup_lat"        ) var pickupLat       : String?  = null,
    @SerializedName("pickup_long"       ) var pickupLong      : String?  = null,
    @SerializedName("dropup_lat"        ) var dropupLat       : String?  = null,
    @SerializedName("dropup_long"       ) var dropupLong      : String?  = null,
    @SerializedName("to_trip"           ) var toTrip          : String?  = null,
    @SerializedName("vehicle_id"        ) var vehicleId       : Int?     = null,
    @SerializedName("assign_driver"     ) var assignDriver    : Int?     = null,
    @SerializedName("total_distance"    ) var totalDistance   : String?  = null,
    @SerializedName("billing_type"      ) var billingType     : String?  = null,
    @SerializedName("freight_amount"    ) var freightAmount   : String?  = null,
    @SerializedName("trip_status"       ) var tripStatus      : Int?     = null,
    @SerializedName("booking_date_from" ) var bookingDateFrom : String?  = null,
    @SerializedName("booking_date_to"   ) var bookingDateTo   : String?  = null,
    @SerializedName("time"              ) var time            : String?  = null,
    @SerializedName("driver_amount"     ) var driverAmount    : String?  = null,
    @SerializedName("fuel_charge"       ) var fuelCharge      : Int?     = null,
    @SerializedName("toll_tax"          ) var tollTax         : Int?     = null,
    @SerializedName("driver_fee"        ) var driverFee       : Int?     = null,
    @SerializedName("created_at"        ) var createdAt       : String?  = null,
    @SerializedName("updated_at"        ) var updatedAt       : String?  = null,
    @SerializedName("vehicle_image"     ) var vehicleImage    : String?  = null,
    @SerializedName("year_of_model"     ) var yearOfModel     : String?  = null,
    @SerializedName("user"              ) var user            : User?    = User(),
    @SerializedName("vehicle"           ) var vehicle         : Vehicle? = Vehicle()
): Serializable
data class Vehicle (
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("vehicle_name"   ) var vehicleName   : String? = null,
    @SerializedName("vehicle_number" ) var vehicleNumber : String? = null,
    @SerializedName("category"       ) var category      : String? = null,
    @SerializedName("body_type"      ) var bodyType      : String? = null,
    @SerializedName("wheels"         ) var wheels        : String? = null,
    @SerializedName("model_year"     ) var modelYear     : String? = null
): Serializable