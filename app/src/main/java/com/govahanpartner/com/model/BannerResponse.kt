package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class BannerResponse(
@SerializedName("status"  ) var status  : Int?            = null,
@SerializedName("message" ) var message : String?         = null,
@SerializedName("data"    ) var data    : ArrayList<HomeBannerData> = arrayListOf()

)

data class HomeBannerData (

    @SerializedName("user_id"    ) var userId    : Int?    = null,
    @SerializedName("banner_img" ) var bannerImg : String? = null

)