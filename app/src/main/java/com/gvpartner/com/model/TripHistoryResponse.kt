package com.gvpartner.com.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripHistoryResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : Result?  = Result()
): Parcelable
@Parcelize
data class TripHistoryResponseData(
    @SerializedName("id"              ) var id             : Int?                      = null,
    @SerializedName("booking_id"      ) var bookingId      : String?                   = null,
    @SerializedName("user_id"         ) var userId         : Int?                      = null,
    @SerializedName("booking_for"     ) var bookingFor     : Int?                      = null,
    @SerializedName("trip_id"         ) var tripId         : Int?                      = null,
    @SerializedName("status"          ) var status         : Int?                      = null,
    @SerializedName("booking_time"    ) var bookingTime    : String?                   = null,
    @SerializedName("ride_code"       ) var rideCode       : String?                   = null,
    @SerializedName("created_at"      ) var createdAt      : String?                   = null,
    @SerializedName("updated_at"      ) var updatedAt      : String?                   = null,
    @SerializedName("is_doc_upload"   ) var isDocUpload      : Boolean?                   = null,
    @SerializedName("payment_details" ) var paymentDetails : ArrayList<PaymentDetails> = arrayListOf(),
    @SerializedName("trip_details"    ) var tripDetails    : TripDetails?              = TripDetails()
): Parcelable
@Parcelize
data class Result (

    @SerializedName("data"  ) var data  : ArrayList<TripHistoryResponseData> = arrayListOf(),
    @SerializedName("page"  ) var page  : Int?            = null,
    @SerializedName("limit" ) var limit : Int?            = null,
    @SerializedName("total" ) var total : Int?            = null

): Parcelable
@Parcelize
data class TripDetails (

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
    @SerializedName("percent_amount"    ) var percentAmount   : String?  = null,
    @SerializedName("remaining_amount"  ) var remainingAmount  : String?  = null,
    @SerializedName("trip_status"       ) var tripStatus      : Int?     = null,
    @SerializedName("booking_date_from" ) var bookingDateFrom : String?  = null,
    @SerializedName("booking_date_to"   ) var bookingDateTo   : String?  = null,
    @SerializedName("time"              ) var time            : String?  = null,
    @SerializedName("fuel_charge"       ) var fuelCharge      : Int?     = null,
    @SerializedName("toll_tax"          ) var tollTax         : Int?     = null,
    @SerializedName("driver_fee"        ) var driverFee       : Int?     = null,
    @SerializedName("vehicle_image"     ) var vehicleImage    : String?  = null,
    @SerializedName("year_of_model"     ) var yearOfModel     : String?  = null,
    @SerializedName("user"              ) var user            : User?    = User(),
    @SerializedName("driver"            ) var driver          : Driver?  = Driver(),
    @SerializedName("vehicle"           ) var vehicle         : Vehicle? = Vehicle()

): Parcelable