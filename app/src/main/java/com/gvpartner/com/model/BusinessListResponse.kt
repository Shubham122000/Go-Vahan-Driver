package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class BusinessListResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : BusinessListResponseData?   = BusinessListResponseData()
)
data class BusinessListResponseData(

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("vendor_id"    ) var vendorId    : Int?    = null,
    @SerializedName("company_name" ) var companyName : String? = null,
    @SerializedName("full_name"    ) var fullName    : String? = null,
    @SerializedName("role"    ) var role    : String? = null,
    @SerializedName("mobile"       ) var mobile      : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("address"      ) var address     : String? = null,
    @SerializedName("image"        ) var image       : String? = null,
    @SerializedName("created_at"   ) var createdAt   : String? = null,
    @SerializedName("updated_at"   ) var updatedAt   : String? = null

)
