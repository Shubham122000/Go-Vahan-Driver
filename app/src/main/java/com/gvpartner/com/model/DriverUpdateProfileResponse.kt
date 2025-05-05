package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class DriverUpdateProfileResponse(

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : DriverUpdateProfileResponseData?   = DriverUpdateProfileResponseData()
)
data class DriverUpdateProfileResponseData(
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("device_type"    ) var deviceType    : String? = null,
    @SerializedName("device_token"   ) var deviceToken   : String? = null,
    @SerializedName("address"        ) var address       : String? = null,
    @SerializedName("email"          ) var email         : String? = null,
    @SerializedName("mobile_number"  ) var mobileNumber  : String? = null,
    @SerializedName("experience"     ) var experience    : String? = null,
    @SerializedName("licence_number" ) var licenceNumber : String? = null,
    @SerializedName("profile_image"  ) var profileImage  : String? = null
)
