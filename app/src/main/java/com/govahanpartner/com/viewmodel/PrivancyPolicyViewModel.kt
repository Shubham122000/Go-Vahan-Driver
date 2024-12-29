package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.govahanpartner.com.model.PrivacyPolicyModel
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivancyPolicyViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

        val privacyPolicyResponse = MutableLiveData<PrivacyPolicyModel>()
        val progressBarStatus = MutableLiveData<Boolean>()


        fun termsAndConditions(token : String) {
            progressBarStatus.value = true
            viewModelScope.launch {
                val response = mainRepository.termsAndConditions(token)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    privacyPolicyResponse.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }
            }
        }
    }
