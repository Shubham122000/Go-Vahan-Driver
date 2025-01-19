package com.govahanpartner.com.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaxiRepositoryViewDetailsResponse(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : TaxiRepositoryViewDetailsResult?  = TaxiRepositoryViewDetailsResult()

): Parcelable
@Parcelize
data class TaxiRepositoryViewDetailsResult(
    @SerializedName("id"                     ) var id                   : Int?                 = null,
    @SerializedName("user_id"                ) var userId               : Int?                 = null,
    @SerializedName("vehicle_name"           ) var vehicleName          : String?              = null,
    @SerializedName("vehicle_number"         ) var vehicleNumber        : String?              = null,
    @SerializedName("vehicle_category"       ) var vehicleCategory      : Int?                 = null,
    @SerializedName("year_of_model"          ) var yearOfModel          : Int?                 = null,
    @SerializedName("document_status"        ) var documentStatus       : Int?                 = null,
    @SerializedName("document_reject_reason" ) var documentRejectReason : String?              = null,
    @SerializedName("status"                 ) var status               : Int?                 = null,
    @SerializedName("document_verified_at"   ) var documentVerifiedAt   : String?              = null,
    @SerializedName("is_from_passenger"      ) var isFromPassenger      : Int?                 = null,
    @SerializedName("is_subscribe"           ) var isSubscribe          : Int?                 = null,
    @SerializedName("height"                 ) var height               : Int?                 = null,
    @SerializedName("no_of_tyres"            ) var noOfTyres            : Int?                 = null,
    @SerializedName("body_type"              ) var bodyType             : BodyTypeData?            = BodyTypeData(),
    @SerializedName("seat"                   ) var seat                 : String?              = null,
    @SerializedName("capacity"               ) var capacity             : String?              = null,
    @SerializedName("created_at"             ) var createdAt            : String?              = null,
    @SerializedName("updated_at"             ) var updatedAt            : String?              = null,
    @SerializedName("images"                 ) var images               : ArrayList<Images>    = arrayListOf(),
    @SerializedName("documents"              ) var documents            : ArrayList<Documents> = arrayListOf(),
    @SerializedName("payment_details"        ) var paymentDetails       : PaymentDetails?      = PaymentDetails(),
    @SerializedName("user"                   ) var user                 : User?                = User(),
    @SerializedName("category"               ) var category             : Category?            = Category(),
    @SerializedName("wheels"                 ) var wheels               : Wheels?              = Wheels(),
    @SerializedName("model_year"             ) var modelYear            : ModelYear?           = ModelYear()
): Parcelable
@Parcelize
data class PaymentDetails (
    @SerializedName("status"             ) var status            : Int?    = null,
    @SerializedName("payment_mode"       ) var paymentMode       : String? = null,
    @SerializedName("is_subscription_valid"       ) var isSubscriptionValid       : String? = null,
    @SerializedName("amount"             ) var amount            : Float?    = null,
    @SerializedName("subscription_start" ) var subscriptionStart : String? = null,
    @SerializedName("subscription_end"   ) var subscriptionEnd   : String? = null

): Parcelable
@Parcelize
data class Documents (

    @SerializedName("id"              ) var id            : Int?    = null,
    @SerializedName("doc_type"        ) var docType       : Int?    = null,
    @SerializedName("other_doc_name"  ) var otherDocName  : String? = null,
    @SerializedName("doc_expiry_date" ) var docExpiryDate : String? = null,
    @SerializedName("file_id"         ) var fileId        : Int?    = null,
    @SerializedName("document_url"    ) var documentUrl   : String? = null

): Parcelable
@Parcelize
data class Images (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("file_id"    ) var fileId    : Int?    = null,
    @SerializedName("image_view" ) var imageView : Int?    = null,
    @SerializedName("image_url"  ) var imageUrl  : String? = null

): Parcelable

//data class Category (
//
//    @SerializedName("id"     ) var id    : Int?    = null,
//    @SerializedName("v_type" ) var vType : String? = null
//
//)
//data class ModelYear (
//
//    @SerializedName("id"   ) var id   : Int?    = null,
//    @SerializedName("year" ) var year : String? = null
//
//)
//data class Wheels (
//
//    @SerializedName("id"    ) var id    : Int? = null,
//    @SerializedName("wheel" ) var wheel : Int? = null
//
//)
//data class TaxiRepositoryView(
//
//
//@SerializedName("status") val status : Int,
//@SerializedName("vehicle_id") val vehicle_id : Int,
//@SerializedName("owner_name") val owner_name : String,
//@SerializedName("loader_name") val loader_name : String,
//@SerializedName("loader_number") val loader_number : String,
//@SerializedName("vendor_name") val vendor_name : String,
//@SerializedName("mobile_number") val mobile_number : String,
//@SerializedName("image") val image : String,
//@SerializedName("capacity") val capacity : String,
//@SerializedName("height") val height : String,
//@SerializedName("bodyname") val bodyname : String,
//@SerializedName("year") val year : Int,
//@SerializedName("tyres") val tyres : String,
//@SerializedName("body_type") val body_type : String,
//@SerializedName("seat") val seat : Int,
//
//)