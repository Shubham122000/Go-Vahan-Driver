package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanpartner.com.model.CreateBusinesscard
import com.govahanpartner.com.network.MainRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class BusinessCard @Inject constructor(private val mainRepository: MainRepository) : ViewModel()  {
    val progressBarStatus = MutableLiveData<Boolean>()
    var createBusinesscardResponse = MutableLiveData<CreateBusinesscard>()
    val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

//    fun getBusinessUser(): LiveData<CreateBusinesscard?>?{
//        if(bisinesscardMutableLiveData == null){
//            bisinesscardMutableLiveData = MutableLiveData()
//        }
//        return bisinesscardMutableLiveData
//    }
    fun BusinessCardAPI(
        token:String,
        company:String,
        name:String,
        mobile:String,
        email:String,
        address:String,
        role:String,
        Image: MultipartBody.Part?


    ) {
    progressBarStatus.value = true
    viewModelScope.launch {
        val company: RequestBody = company.toRequestBody("text/plain".toMediaTypeOrNull())
        val name: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val mobile: RequestBody = mobile.toRequestBody("text/plain".toMediaTypeOrNull())
        val email: RequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val address: RequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val role: RequestBody = role.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = mainRepository.BusinesscardApi(
                token,
                company,
                name,
                mobile,
                email,
                address,
                role,
                Image!!
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                createBusinesscardResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun update_business_cardApi(
        token:String,
        company:String,
        name:String,
        mobile:String,
        email:String,
        address:String,
        role:String,
        Image: MultipartBody.Part?
    ) {
    progressBarStatus.value = true
    viewModelScope.launch {
        val company: RequestBody = company.toRequestBody("text/plain".toMediaTypeOrNull())
        val name: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val mobile: RequestBody = mobile.toRequestBody("text/plain".toMediaTypeOrNull())
        val email: RequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val address: RequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val role: RequestBody = role.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = mainRepository.update_business_cardApi(
                token,
                company,
                name,
                mobile,
                email,
                address,role,
                Image!!
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                createBusinesscardResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
}
