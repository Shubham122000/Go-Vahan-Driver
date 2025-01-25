package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.govahanpartner.com.model.AddDriverResponse
import com.govahanpartner.com.model.DriverListResponse
import com.govahanpartner.com.model.DriverProfile
import com.govahanpartner.com.model.DriverUpdateProfileResponse
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AddDriverViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel()  {


    val progressBarStatus = MutableLiveData<Boolean>()
    val addDriverResponse = MutableLiveData<AddDriverResponse>()
    val DriverProfileResponse = MutableLiveData<DriverProfile>()
    val DriverProfileUpdateResponse = MutableLiveData<DriverUpdateProfileResponse>()
    val DriverlistResponse = MutableLiveData<DriverListResponse>()


    fun AddDriverApi(
        token:String,
        company:String,
        name:String,
        role:String,
        countryCode: String,
        mobile:String,
        email:String,
        address:String,
        Image: MultipartBody.Part?,
        pdfFile: MultipartBody.Part?,
        vendorid: String,
//        serviceid: String

    ) {
        val company: RequestBody = company.toRequestBody("text/plain".toMediaTypeOrNull())
        val name: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val role: RequestBody = role.toRequestBody("text/plain".toMediaTypeOrNull())
        val countryCode: RequestBody = countryCode.toRequestBody("text/plain".toMediaTypeOrNull())
        val mobile: RequestBody = mobile.toRequestBody("text/plain".toMediaTypeOrNull())
        val email: RequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val address: RequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val vendorid: RequestBody = vendorid.toRequestBody("text/plain".toMediaTypeOrNull())
//        val serviceid: RequestBody = serviceid.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.AdddriverApi(
                token,
                company,
                name,
                role,
                countryCode,
                mobile,
                email,
                address,
                Image!!,
                pdfFile!!,
                vendorid,
//                serviceid,

            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                addDriverResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun DriverProfileAPI(
        token:String,
        id:String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.DriverProfile(
                token,
                id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DriverProfileResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun vendor_driver_delete(
        token:String,
        id:String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response = mainRepository.vendor_driver_delete(
                token,
                id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DriverProfileResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
  fun createBookingDocument(
        token:String,
        booking_id:String,
        pod:MultipartBody.Part?,
        signature:MultipartBody.Part?,
        builty:MultipartBody.Part?
    ) {
      val booking_id: RequestBody = booking_id.toRequestBody("text/plain".toMediaTypeOrNull())
//      val type: RequestBody = type.toRequestBody("text/plain".toMediaTypeOrNull())

      progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.createBookingDocument(
                token,
                booking_id,pod,signature,builty)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DriverProfileResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun DriverProfileUpdateAPI(
        token:String,
        id:String,
        name: String,
        mobile: String,
        experience: String,
        licence: String,
        devicetoken: String,
        devicetype: String,
        deviceid: String,
        password:String,
        profile_image: MultipartBody.Part?
        ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val id: RequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())
            val name: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val mobile: RequestBody = mobile.toRequestBody("text/plain".toMediaTypeOrNull())
            val experience: RequestBody = experience.toRequestBody("text/plain".toMediaTypeOrNull())
            val licence: RequestBody = licence.toRequestBody("text/plain".toMediaTypeOrNull())
            val devicetoken: RequestBody = devicetoken.toRequestBody("text/plain".toMediaTypeOrNull())
            val devicetype: RequestBody = devicetype.toRequestBody("text/plain".toMediaTypeOrNull())
            val deviceid: RequestBody = deviceid.toRequestBody("text/plain".toMediaTypeOrNull())
            val password: RequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
            val response = mainRepository.DriverUpdateProfile(
                token,
                id,
                name,mobile,experience,licence,devicetoken,devicetype,deviceid,password, profile_image!!
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DriverProfileUpdateResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun driverListApi(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.driverList(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DriverlistResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
}