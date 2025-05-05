package com.gvpartner.com.model

import com.google.gson.annotations.SerializedName

data class ListResponseData(

    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<DataResponse> = arrayListOf()

) : com.gvpartner.com.BaseModel()

data class DataResponse(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("role") var role: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("email_verified_at") var emailVerifiedAt: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("remember_token") var rememberToken: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("device_token") var deviceToken: String? = null,
    @SerializedName("mobile_number") var mobileNumber: String? = null,
    @SerializedName("device_type") var deviceType: String? = null,
    @SerializedName("device_id") var deviceId: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("business_name") var businessName: String? = null,
    @SerializedName("gst_number") var gstNumber: String? = null,
    @SerializedName("pan_number") var panNumber: String? = null,
    @SerializedName("aadhar_number") var aadharNumber: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("profile_image") var profileImage: String? = null,
    @SerializedName("otp") var otp: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("api_token") var apiToken: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("vehicle_number") var vehicleNumber: String? = null,
    @SerializedName("total_driver") var totalDriver: String? = null,
    @SerializedName("is_deleted") var isDeleted: Int? = null,
    @SerializedName("is_approved") var isApproved: Int? = null,
    @SerializedName("last_logedin_date") var lastLogedinDate: String? = null,
    @SerializedName("login_status") var loginStatus: Int? = null

): com.gvpartner.com.BaseModel()
