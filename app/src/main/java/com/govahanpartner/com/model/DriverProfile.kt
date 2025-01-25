package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class DriverProfile(
    @SerializedName("error"  ) var error  : Boolean?    = null,
    @SerializedName("status_code"  ) var statusCode  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : DriverProfileData?   = DriverProfileData()
)
data class DriverProfileData(
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("device_type"    ) var deviceType    : String? = null,
    @SerializedName("device_token"   ) var deviceToken   : String? = null,
    @SerializedName("address"        ) var address       : String? = null,
    @SerializedName("email"          ) var email         : String? = null,
    @SerializedName("password"          ) var password         : String? = null,
    @SerializedName("mobile_number"  ) var mobileNumber  : String? = null,
    @SerializedName("experience"     ) var experience    : String? = null,
    @SerializedName("licence_number" ) var licenceNumber : String? = null,
    @SerializedName("profile_image"  ) var profileImage  : String? = null,
    @SerializedName("user_type"  ) var user_type  : Int? = null
)
