package com.govahanpartner.com.model

import com.google.gson.annotations.SerializedName

data class ProfileEditResponse(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ProfileEditResponseData?   = ProfileEditResponseData()
)
data class ProfileEditResponseData(

    @SerializedName("id"                ) var id              : Int?    = null,
    @SerializedName("role"              ) var role            : Int?    = null,
    @SerializedName("user_type"         ) var userType        : Int?    = null,
    @SerializedName("v_id"              ) var vId             : Int?    = null,
    @SerializedName("service_id"        ) var serviceId       : String? = null,
    @SerializedName("vendor_driver"     ) var vendorDriver    : String? = null,
    @SerializedName("name"              ) var name            : String? = null,
    @SerializedName("email"             ) var email           : String? = null,
    @SerializedName("mobile_number"     ) var mobileNumber    : String? = null,
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
    @SerializedName("is_deleted"        ) var isDeleted       : Int?    = null,
    @SerializedName("is_approved"       ) var isApproved      : Int?    = null,
    @SerializedName("last_logedin_date" ) var lastLogedinDate : String? = null,
    @SerializedName("login_status"      ) var loginStatus     : Int?    = null,
    @SerializedName("latitude"          ) var latitude        : String? = null,
    @SerializedName("longtitude"        ) var longtitude      : String? = null,
    @SerializedName("gst_number"        ) var gstNumber       : String? = null,
    @SerializedName("licence_number"    ) var licenceNumber   : String? = null,
    @SerializedName("experience"        ) var experience      : String? = null,
    @SerializedName("id_proof_doc"      ) var idProofDoc      : String? = null,
    @SerializedName("invoice_filename"  ) var invoiceFilename : String? = null,
    @SerializedName("whatsapp_status"   ) var whatsappStatus  : Int?    = null,
    @SerializedName("sms_email"         ) var smsEmail        : Int?    = null,
    @SerializedName("created_at"        ) var createdAt       : String? = null,
    @SerializedName("updated_at"        ) var updatedAt       : String? = null
)
