package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("count" ) var count : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<NotificationResponseData> = arrayListOf()
)
data class NotificationResponseData(
    @SerializedName("id"        ) var id       : Int?    = null,
    @SerializedName("user_id"   ) var userId   : Int?    = null,
    @SerializedName("driver_id" ) var driverId : Int?    = null,
    @SerializedName("title"     ) var title    : String? = null,
    @SerializedName("message"   ) var message  : String? = null,
    @SerializedName("create_at" ) var createAt : String? = null,
    @SerializedName("update_at" ) var updateAt : String? = null
)
