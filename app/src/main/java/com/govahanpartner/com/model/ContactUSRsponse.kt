package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class ContactUSRsponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ContactUSRsponseData?   = ContactUSRsponseData()
)
data class ContactUSRsponseData(
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("type"           ) var type          : String? = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("email"          ) var email         : String? = null,
    @SerializedName("contact_number" ) var contactNumber : String? = null,
    @SerializedName("image"          ) var image         : String? = null,
    @SerializedName("address"        ) var address       : String? = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("updated_at"     ) var updatedAt     : String? = null
)