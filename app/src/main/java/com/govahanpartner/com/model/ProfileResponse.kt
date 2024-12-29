package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse (

    @SerializedName("status")
    var status: Int? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("data")
    var data: ProfileResponseData? = ProfileResponseData()

    )
data class ProfileResponseData(
    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("url"          ) var url         : String? = null,
    @SerializedName("device_type"   ) var deviceType   : String? = null,
    @SerializedName("device_token"  ) var deviceToken  : String? = null,
    @SerializedName("address"       ) var address      : String? = null,
    @SerializedName("email"         ) var email        : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("profile_image" ) var profileImage : String? = null
)