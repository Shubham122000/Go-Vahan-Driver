package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class AddloaderResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : AddloaderResult?  = AddloaderResult()


//    @SerializedName("status"  ) var status  : Int?     = null,
//    @SerializedName("message" ) var message : String?  = null,
//    @SerializedName("data"    ) var data    : Boolean? = null,
//    @SerializedName("id") var id     : String? = null,

)
data class AddloaderResult(
    @SerializedName("user_id"          ) var userId          : Int?    = null,
    @SerializedName("vehicle_name"     ) var vehicleName     : String? = null,
    @SerializedName("year_of_model"    ) var yearOfModel     : String? = null,
    @SerializedName("vehicle_number"   ) var vehicleNumber   : String? = null,
    @SerializedName("vehicle_category" ) var vehicleCategory : String? = null,
    @SerializedName("capacity"         ) var capacity        : String? = null,
    @SerializedName("height"           ) var height          : String? = null,
    @SerializedName("no_of_tyres"      ) var noOfTyres       : String? = null,
    @SerializedName("body_type"        ) var bodyType        : String? = null,
    @SerializedName("updated_at"       ) var updatedAt       : String? = null,
    @SerializedName("created_at"       ) var createdAt       : String? = null,
    @SerializedName("id"               ) var id              : Int?    = null
)