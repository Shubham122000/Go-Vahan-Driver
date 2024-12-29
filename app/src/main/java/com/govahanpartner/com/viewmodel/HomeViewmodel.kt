package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.govahanpartner.com.model.BusinessListResponse
import com.govahanpartner.com.model.DownloadPdfResponse
import com.govahanpartner.com.model.VisitingCardUrlResponse
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    var BusinessListResponse = MutableLiveData<BusinessListResponse>()
    var VisitingCardResponse = MutableLiveData<VisitingCardUrlResponse>()
    var VisitingPdfResponse = MutableLiveData<DownloadPdfResponse>()
    val progressBarStatus = MutableLiveData<Boolean>()
    val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

        fun GetBusinessdetails(token :String){
    //        if (BusinessListResponse == null) {
    //            BusinessListResponse = MutableLiveData()
    //        }
            viewModelScope.launch {
                try {
                    val response = mainRepository.Businessdata(token)

                    if (response.isSuccessful) {
                        progressBarStatus.value = false
                        BusinessListResponse.postValue(response.body())
                    }
                }catch (e:Exception) {
                    _progressBarVisibility.postValue(0)
                    e.printStackTrace()
                }
            }
    //        return BusinessListResponse
        }

    fun VisitingCardUrl(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.VisitingCard(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                VisitingCardResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun VisitingCardPdf(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.VisitingPdf(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                VisitingPdfResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
}