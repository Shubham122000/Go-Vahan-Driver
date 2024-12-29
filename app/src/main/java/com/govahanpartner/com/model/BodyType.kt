package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class BodyType(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : BodyTypeResult?  = BodyTypeResult()
//    @SerializedName("status"  ) var status  : Int?            = null,
//    @SerializedName("message" ) var message : String?         = null,
//    @SerializedName("data"    ) var data    : ArrayList<BodyTypeData> = arrayListOf()
)
data class BodyTypeResult (

    @SerializedName("data" ) var data : ArrayList<BodyTypeData> = arrayListOf()

)
data class BodyTypeData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("status"     ) var status    : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null
){
    override fun toString(): String {
        return name.toString()
    }
}
