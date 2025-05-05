package com.gvpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.gvpartner.com.model.RegisterResponseModel
import com.gvpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val loginResponse = MutableLiveData<RegisterResponseModel>()
    val accountVerificationResponse = MutableLiveData<RegisterResponseModel>()
    val changePasswordResponse = MutableLiveData<RegisterResponseModel>()
    val verifyOtpResponse = MutableLiveData<RegisterResponseModel>()
    val changepassword = MutableLiveData<RegisterResponseModel>()
    val progressBarStatus = MutableLiveData<Boolean>()
    var error = MutableLiveData<Int>()

    fun ForgotPassword(
        mobile: String,
        password: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.ForgotPassword(mobile, password)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                changepassword.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun driverLogin(
        email: String,
        password: String,
        device_token: String,
        device_type: String,
        type: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.driverLogin(email, password, device_token,device_type,type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                loginResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun add_driving_licence(
        id: String,
        driving_licence: MultipartBody.Part
    ) {
        val id: RequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())

        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_driving_licence(id,driving_licence)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                loginResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun driverSendOtp(
        mobile: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response = mainRepository.driverSendOtpApi(mobile)
            if (response.isSuccessful) {

                progressBarStatus.value = false
                Log.d("TAG", "success")
                accountVerificationResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", "Failed")
            }
        }
    }

    fun driverVerifyOtp(
        otp: String,
        mobile: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response = mainRepository.driverVerifyOtp(otp,mobile)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                Log.d("TAG", "success")
                verifyOtpResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", "Failed")
            }
        }
    }
    fun driverChangePassword(
        token:String,
        password: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response = mainRepository.driverChangePwd(token,password)
            if (response.isSuccessful) {

                progressBarStatus.value = false
                Log.d("TAG", "success")
                changePasswordResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", "Failed")
            }
        }
    }
}