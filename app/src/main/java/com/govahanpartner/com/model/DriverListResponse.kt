package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class DriverListResponse (

    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : DriverListResult?  = DriverListResult()
 )
data class DriverListResult (
    @SerializedName("data"  ) var data  : ArrayList<DriverListResponseData> = arrayListOf(),
    @SerializedName("page"  ) var page  : Int?            = null,
    @SerializedName("limit" ) var limit : Int?            = null,
    @SerializedName("total" ) var total : Int?            = null
)
data class DriverListResponseData(

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("email"         ) var email        : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("country_code"  ) var countryCode  : String? = null,
    @SerializedName("status"        ) var status       : Int?    = null,
    @SerializedName("created_at"    ) var createdAt    : String? = null,
    @SerializedName("vehicle_number"    ) var vehicleNumber    : String? = null,
    @SerializedName("is_approved"             ) var is_approved           : Int? = null,
    @SerializedName("profile_image"     ) var profileImage    : String? = null,
    @SerializedName("licence_number"    ) var licenceNumber   : String? = null,

)