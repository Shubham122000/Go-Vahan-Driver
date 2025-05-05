package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class ColorResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ColorResponseData> = arrayListOf()
)
data class ColorResponseData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("v_color"    ) var vColor    : String? = null,
    @SerializedName("status"     ) var status    : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null
)
