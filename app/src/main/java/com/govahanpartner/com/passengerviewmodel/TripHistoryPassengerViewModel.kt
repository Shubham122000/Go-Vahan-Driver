package com.govahanpartner.com.passengerviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.govahanpartner.com.model.TripHistoryResponse
import com.govahanpartner.com.model.TripListDetailsModelClass
import com.govahanpartner.com.model.TripListResponse
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripHistoryPassengerViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel() {
    val TripHistoryResponse = MutableLiveData<TripHistoryResponse>()
    val TripDetailsResponse = MutableLiveData<TripListDetailsModelClass>()
    val TriplistResponse = MutableLiveData<TripListResponse>()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun UpComingsTripHistoryApi(
        token: String, hitFromDriver :String, forPassenger :String, bookingStatus :String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.UpcomingsTripHistory(token, hitFromDriver, forPassenger, bookingStatus)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun vendor_upcooming_booking_passengers(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.vendor_upcooming_booking_passengers(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
//    fun loader_trip_list_details(
//        token: String,
//        loader_id:String
//    ) {
//        progressBarStatus.value = true
//        viewModelScope.launch {
//
//            val response =
//                mainRepository.loader_trip_list_details(token,loader_id)
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                TripDetailsResponse.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//    }
    fun passenger_trip_list_details(
        token: String,
        loader_id:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passenger_trip_list_details(token,loader_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripDetailsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
}