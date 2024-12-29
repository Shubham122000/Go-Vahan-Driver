package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanpartner.com.model.*
import com.govahanpartner.com.model.AboutUs
import com.govahanpartner.com.model.ContactUSRsponse
import com.govahanpartner.com.model.NotificationResponse
import com.govahanpartner.com.model.PrivacyPolicy
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaticDataViewModel @Inject constructor(private val mainRepository: MainRepository) :ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    val ContactUSRsponse = MutableLiveData<ContactUSRsponse>()
    val AboutUSRsponse = MutableLiveData<AboutUs>()
    val PrivacyPolicyRsponse = MutableLiveData<PrivacyPolicy>()
    val cancellationRsponse = MutableLiveData<PrivacyPolicyModel>()
    val NotificationResponse = MutableLiveData<NotificationResponse>()


    fun ContactUsAPI(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.ContactUs(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                ContactUSRsponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun AboutUsAPI(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Aboutus(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AboutUSRsponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun PrivacyPolicyAPI(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.PrivacyPolicy(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                PrivacyPolicyRsponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun calcelation_refund_policy(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.calcelation_refund_policy(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                cancellationRsponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun NotificationAPI(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Notification(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                NotificationResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

}