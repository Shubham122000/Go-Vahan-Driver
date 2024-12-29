package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class LoaderTruckRepositoryListResponse(
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<LoaderTruckRepositoryListResponseData> = arrayListOf()

)
data class LoaderTruckRepositoryListResponseData(
    @SerializedName("vehicle_id"    ) var vehicleId    : Int?    = null,
    @SerializedName("loader_name"   ) var loaderName   : String? = null,
    @SerializedName("loader_number" ) var loaderNumber : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("owner_name"          ) var owner_name         : String? = null,
    @SerializedName("vaild"          ) var vaild         : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("image"         ) var image        : String? = null,
    @SerializedName("status"         ) var status        : String? = null,
    @SerializedName("payment_status"         ) var payment_status        : String? = null,
    @SerializedName("expired_date"         ) var expired_date        : String? = null,

)
