package com.govahanpartner.com.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignupModel (
   var name: String,
   var email: String,
   var mobile: String,
   var gst_number: String,
   var vehicle_number: String,
   var vehicle_registration_no: String,
   val  type_vehicle: String,
   val total_driver: String,
   val driver_dl_no: String,
   val driver_name: String,
   val device_type: String,
   val  device_name: String,
   val  device_token: String,
   val device_id: String,
   val password: String,
        ) : Parcelable