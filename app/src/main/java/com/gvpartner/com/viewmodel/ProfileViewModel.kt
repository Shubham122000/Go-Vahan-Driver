package com.gvpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvpartner.com.model.PrivacyPolicyModel
import com.gvpartner.com.model.ProfileEditResponse
import com.gvpartner.com.model.ProfileResponse
import com.gvpartner.com.network.MainRepository
import com.gvpartner.com.ui.common.ReferNEarnResponse

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {
    var getProfileResponse = MutableLiveData<ProfileResponse>()
    var PrivacyPolicyModel1 = MutableLiveData<PrivacyPolicyModel>()
    var refernearnResponse = MutableLiveData<ReferNEarnResponse>()
    var profileeditResponse = MutableLiveData<ProfileEditResponse>()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun GetProfileAPI(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.GetProfile(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                getProfileResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun get_user_check_statusApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_user_check_statusApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                PrivacyPolicyModel1.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
  fun referralsApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.referralsApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                refernearnResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun UpdateProfile(
        token:String,
        name:String,
        email:String,
        mobile:String,
        address:String,
        device_token:String,
        device_type:String,
        device_id:String,
        Image: MultipartBody.Part?


    ) {
        val name: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val email: RequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val mobile: RequestBody = mobile.toRequestBody("text/plain".toMediaTypeOrNull())
        val address: RequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val device_token: RequestBody = device_token.toRequestBody("text/plain".toMediaTypeOrNull())
        val device_type: RequestBody = device_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val device_id: RequestBody = device_id.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.EditProfile(
                token,
                name,
                email,
                mobile,
                address,
                device_token,
                device_type,
                device_id,
                Image!!
            )
            if (response.isSuccessful) {
                Log.d("TAG", "UpdateProfile: "+response.isSuccessful)
                progressBarStatus.value = false
                profileeditResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

}