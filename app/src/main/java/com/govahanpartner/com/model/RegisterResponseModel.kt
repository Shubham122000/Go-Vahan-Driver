package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class RegisterResponseModel(
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : RegisterResponseModelResult?  = RegisterResponseModelResult()
)

data class RegisterResponseModelResult(
    @SerializedName("user" ) var user : RegisterResponseModelUser? = RegisterResponseModelUser()
)

data class RegisterResponseModelUser(
    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("role"              ) var role            : Int?    = null,
    @SerializedName("user_type"         ) var userType        : Int?    = null,
    @SerializedName("v_id"              ) var vId             : String? = null,
    @SerializedName("service_id"        ) var serviceId       : String? = null,
    @SerializedName("vendor_driver"     ) var vendorDriver    : String? = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("franchise_name"    ) var franchiseName   : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("mobile_number"     ) var mobileNumber    : String? = null,
    @SerializedName("country_code"      ) var countryCode     : String? = null,
    @SerializedName("address"           ) var address         : String? = null,
    @SerializedName("profile_image"     ) var profileImage    : String? = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String? = null,
    @SerializedName("device_token"      ) var deviceToken     : String? = null,
    @SerializedName("device_name"       ) var deviceName      : String? = null,
    @SerializedName("device_type"       ) var deviceType      : String? = null,
    @SerializedName("device_id"         ) var deviceId        : String? = null,
    @SerializedName("otp"               ) var otp             : String? = null,
    @SerializedName("status"            ) var status          : Int?    = null,
    @SerializedName("complete_profile"  ) var completeProfile : Int?    = null,
    @SerializedName("api_token"         ) var apiToken        : String? = null,
    @SerializedName("amount"            ) var amount          : String? = null,
    @SerializedName("is_approved"       ) var isApproved      : Int?    = null,
    @SerializedName("last_login"        ) var lastLogin       : String? = null,
    @SerializedName("login_status"      ) var loginStatus     : Int?    = null,
    @SerializedName("latitude"          ) var latitude        : String? = null,
    @SerializedName("longtitude"        ) var longtitude      : String? = null,
    @SerializedName("gst_number"        ) var gstNumber       : String? = null,
    @SerializedName("licence_number"    ) var licenceNumber   : String? = null,
    @SerializedName("experience"        ) var experience      : String? = null,
    @SerializedName("referal_code"      ) var referalCode     : String? = null,
    @SerializedName("refered_by"        ) var referedBy       : String? = null,
    @SerializedName("id_proof_doc"      ) var idProofDoc      : String? = null,
    @SerializedName("driving_licence"   ) var drivingLicence  : String? = null,
    @SerializedName("whatsapp_status"   ) var whatsappStatus  : Int?    = null,
    @SerializedName("sms_email"         ) var smsEmail        : Int?    = null,
    @SerializedName("payment_status"    ) var paymentStatus   : Int?    = null,
    @SerializedName("created_at"        ) var createdAt       : String? = null,
    @SerializedName("updated_at"        ) var updatedAt       : String? = null
)

//data class Data(
//    @SerializedName("id") var id: Int? = null,
//    @SerializedName("role") var role: Int? = null,
//    @SerializedName("name") var name: String? = null,
//    @SerializedName("email") var email: String? = null,
//    @SerializedName("contact_number") var contact_number: String? = null,
//    @SerializedName("email_verified_at") var emailVerifiedAt: String? = null,
//    @SerializedName("created_at") var createdAt: String? = null,
//    @SerializedName("updated_at") var updatedAt: String? = null,
//    @SerializedName("device_token") var deviceToken: String? = null,
//    @SerializedName("mobile_number") var mobileNumber: String? = null,
//    @SerializedName("driving_licence") var driving_licence: String? = null,
//    @SerializedName("device_type") var deviceType: String? = null,
//    @SerializedName("device_id") var deviceId: String? = null,
//    @SerializedName("user_id") var userId: String? = null,
//    @SerializedName("business_name") var businessName: String? = null,
//    @SerializedName("description") var description: String? = null,
//    @SerializedName("gst_number") var gstNumber: String? = null,
//    @SerializedName("pan_number") var panNumber: String? = null,
//    @SerializedName("aadhar_number") var aadharNumber: String? = null,
//    @SerializedName("address") var address: String? = null,
//    @SerializedName("profile_image") var profileImage: String? = null,
//    @SerializedName("otp") var otp: String? = null,
//    @SerializedName("status") var status: Int? = null,
//    @SerializedName("api_token") var apiToken: String? = null,
//    @SerializedName("amount") var amount: String? = null,
//    @SerializedName("vehicle_number") var vehicleNumber: String? = null,
//    @SerializedName("total_driver") var totalDriver: String? = null,
//    @SerializedName("user_type") var user_type: Int? = null
//
//)