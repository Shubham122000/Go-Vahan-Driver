package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

class Addmoneywallet {
    @SerializedName("error"      ) var error      : Boolean?            = null
    @SerializedName("status_code"      ) var statusCode      : Int?            = null
    @SerializedName("message"     ) var message     : String?         = null
}