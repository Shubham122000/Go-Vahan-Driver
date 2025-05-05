package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("review"  ) var review  : Int?            = null,
    @SerializedName("data"    ) var data    : ArrayList<RatingResponseData> = arrayListOf()
)

data class RatingResponseData(
    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("user_id"       ) var userId       : Int?    = null,
    @SerializedName("driver_id"     ) var driverId     : Int?    = null,
    @SerializedName("rating"        ) var rating       : Int?    = null,
    @SerializedName("description"   ) var description  : String? = null,
    @SerializedName("created_at"    ) var createdAt    : String? = null,
    @SerializedName("updated_at"    ) var updatedAt    : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("address"       ) var address      : String? = null,
    @SerializedName("profile_image" ) var profileImage : String? = null
)