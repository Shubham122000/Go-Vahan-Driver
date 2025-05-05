package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

class VisitingCardUrlResponse {

    @SerializedName("status"  ) var status  : Int?    = 0
    @SerializedName("message" ) var message : String? = ""
    @SerializedName("url"     ) var url     : String? = ""

}