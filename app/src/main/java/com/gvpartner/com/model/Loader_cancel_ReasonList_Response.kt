package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class Loader_cancel_ReasonList_Response(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Loader_cancel_ReasonList_ResponseData> = arrayListOf()
)
data class Loader_cancel_ReasonList_ResponseData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("reason"     ) var reason    : String? = null,
    @SerializedName("status"     ) var status    : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null
)
