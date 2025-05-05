package com.gvpartner.com.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class LoaderTruckRepositoryListResponse(

    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : LoaderTruckRepositoryListResult?  = LoaderTruckRepositoryListResult()
): Serializable, Parcelable
@Parcelize
data class LoaderTruckRepositoryListResult(
    @SerializedName("vehicles" ) var vehicles : ArrayList<Vehicles> = arrayListOf(),
    @SerializedName("total"    ) var total    : Int?                = null,
    @SerializedName("page"     ) var page     : Int?                = null,
    @SerializedName("limit"    ) var limit    : Int?                = null
): Serializable, Parcelable
@Parcelize
data class Vehicles (

    @SerializedName("id"                     ) var id                   : Int?       = null,
    @SerializedName("user_id"                ) var userId               : Int?       = null,
    @SerializedName("vehicle_name"           ) var vehicleName          : String?    = null,
    @SerializedName("vehicle_number"         ) var vehicleNumber        : String?    = null,
    @SerializedName("vehicle_category"       ) var vehicleCategory      : Int?       = null,
    @SerializedName("year_of_model"          ) var yearOfModel          : Int?       = null,
    @SerializedName("document_status"        ) var documentStatus       : Int?       = null,
    @SerializedName("document_reject_reason" ) var documentRejectReason : String?    = null,
    @SerializedName("status"                 ) var status               : Int?       = null,
    @SerializedName("document_verified_at"   ) var documentVerifiedAt   : String?    = null,
    @SerializedName("is_from_passenger"      ) var isFromPassenger      : Int?       = null,
    @SerializedName("is_subscribe"           ) var isSubscribe          : Int?       = null,
    @SerializedName("height"                 ) var height               : Int?       = null,
    @SerializedName("no_of_tyres"            ) var noOfTyres            : Int?       = null,
    @SerializedName("body_type"              ) var bodyType             : BodyType?  = BodyType(),
    @SerializedName("seat"                   ) var seat                 : String?    = null,
    @SerializedName("capacity"               ) var capacity             : String?    = null,
    @SerializedName("created_at"             ) var createdAt            : String?    = null,
    @SerializedName("updated_at"             ) var updatedAt            : String?    = null,
    @SerializedName("payment_mode"           ) var payment_mode: String?    = null,
    @SerializedName("subscription_start"     ) var subscription_start: String?    = null,
    @SerializedName("subscription_end"       ) var subscription_end            : String?    = null,
    @SerializedName("payment_status"         ) var paymentStatus        : Int?       = null,
    @SerializedName("main_image"             ) var mainImage         : String?    = null,
    @SerializedName("is_subscription_valid"  ) var isSubscriptionValid  : String?    = null,
    @SerializedName("user"                   ) var user                 : User?      = User(),
    @SerializedName("category"               ) var category             : Category?  = Category(),
    @SerializedName("wheels"                 ) var wheels               : Wheels?    = Wheels(),
    @SerializedName("model_year"             ) var modelYear            : ModelYear? = ModelYear(),
    @SerializedName("vehicle_images"         ) var vehicleImages        : VehicleImages?              = VehicleImages(),
    @SerializedName("vehicle_documents"      ) var vehicleDocuments     : ArrayList<VehicleDocuments> = arrayListOf(),

):  Parcelable
@Parcelize
data class ModelYear (
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("year" ) var year : String? = null
): Parcelable
@Parcelize
data class Wheels (
    @SerializedName("id"    ) var id    : Int? = null,
    @SerializedName("wheel" ) var wheel : Int? = null
): Parcelable
@Parcelize
data class Category (

    @SerializedName("id"     ) var id    : Int?    = null,
    @SerializedName("v_type" ) var vType : String? = null

): Parcelable
@Parcelize
data class VehicleDocuments (

    @SerializedName("doc_type"        ) var docType       : Int?    = null,
    @SerializedName("file_url"        ) var fileUrl       : String? = null,
    @SerializedName("doc_expiry_date" ) var docExpiryDate : String? = null,
    @SerializedName("other_doc_name"  ) var otherDocName  : String? = null

): Parcelable
@Parcelize
data class User (
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("email" ) var email : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("image" ) var image : String? = null
):Parcelable

@Parcelize
data class VehicleImages (

    @SerializedName("image_1" ) var image1 : String? = null,
    @SerializedName("image_2" ) var image2 : String? = null,
    @SerializedName("image_3" ) var image3 : String? = null,
    @SerializedName("image_4" ) var image4 : String? = null

): Parcelable

//data class LoaderTruckRepositoryListResponseData(
//    @SerializedName("vehicle_id"    ) var vehicleId    : Int?    = null,
//    @SerializedName("loader_name"   ) var loaderName   : String? = null,
//    @SerializedName("loader_number" ) var loaderNumber : String? = null,
//    @SerializedName("name"          ) var name         : String? = null,
//    @SerializedName("owner_name"          ) var owner_name         : String? = null,
//    @SerializedName("vaild"          ) var vaild         : String? = null,
//    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
//    @SerializedName("image"         ) var image        : String? = null,
//    @SerializedName("status"         ) var status        : String? = null,
//    @SerializedName("payment_status"         ) var payment_status        : String? = null,
//    @SerializedName("expired_date"         ) var expired_date        : String? = null,
//
//)
