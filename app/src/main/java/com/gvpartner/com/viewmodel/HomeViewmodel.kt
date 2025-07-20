package com.gvpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.gvpartner.com.model.BusinessListResponse
import com.gvpartner.com.model.DownloadPdfResponse
import com.gvpartner.com.model.VisitingCardUrlResponse
import com.gvpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

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

//    fun VisitingCardPdf(
//        token: String,
//    ) {
//        progressBarStatus.value = true
//        viewModelScope.launch {
//            val response =
//                mainRepository.VisitingPdf(token)
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                VisitingPdfResponse.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//    }
//fun VisitingCardPdf(token: String) {
//    progressBarStatus.value = true
//
//    viewModelScope.launch {
//        try {
//            val response = withContext(Dispatchers.IO) {
//                mainRepository.VisitingPdf(token)
//            }
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    VisitingPdfResponse.postValue(it)
//                    Log.d("PDF", "Response Success: $it")
//                } ?: run {
//                    Log.e("PDF", "Response Body is null")
//                }
//            } else {
//                Log.e("PDF", "Response Failed: ${response.code()} - ${response.errorBody()?.string()}")
//            }
//        } catch (e: Exception) {
//            Log.e("PDF", "Exception occurred: ${e.message}", e)
//        } finally {
//            progressBarStatus.postValue(false)
//        }
//    }
//}

    fun VisitingCardPdf(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    mainRepository.VisitingPdf(token)
                if (response.isSuccessful) {
                    VisitingPdfResponse.postValue(response.body())
                } else {
                    Log.d("PDF", response.body().toString())
                }
            }catch (e:Exception){
                Log.e("PDF", "Exception occurred: ${e.message}", e)
            }
        }
        progressBarStatus.value = false
    }


}