package com.gvpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvpartner.com.model.*
import com.gvpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripHistoryViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel()  {

    val TripHistoryResponse = MutableLiveData<TripHistoryResponse>()
    val TriplistResponse = MutableLiveData<TripListResponse>()
    val RideCompletedResponse = MutableLiveData<RideCompletedResponse>()
    val CheckTripPayment = MutableLiveData<CheckTripPaymentResponse>()
    val CheckTripPayment2 = MutableLiveData<CheckTripPaymentResponse>()
    val getBankAccounts = MutableLiveData<GetBankAcountResponse>()
    val DeleteTRipComplete = MutableLiveData<DriverProfile>()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun RideCompletedApi(
        token: String,
        id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.RideCompleted(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                RideCompletedResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun passengers_ride_completed(
        token: String,
        id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passengers_ride_completed(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                RideCompletedResponse.postValue(response.body())
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

    fun passenger_Payments_checkApi(
        token: String,
        amount: String,booking_id:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passenger_Payments_checkApi(token,amount,booking_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CheckTripPayment2.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun checkTripPaymentsApi(
        token: String,
        amount: String,booking_id:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.checkTripPaymentsApi(token,amount,booking_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CheckTripPayment.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun getBankAccountsApi(
        token: String,

    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.getBankAccountsApi(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                getBankAccounts.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun DeleteTripHistory(
        token: String,
        id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.loader_trip_delete(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DeleteTRipComplete.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun OngoinTripHistoryApi(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.OngoinTripHistory(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun OngoinTripHistoryPassengerApi(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.OngoinTripHistoryPassenger(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun cancel_booking_history_loader(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.cancel_booking_history_loader(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun cnacel_booking_passengers(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.cnacel_booking_passengers(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



    fun CompletedTripHistoryApi(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.CompletedTripHistory(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun CompletedTripHistoryApiPassenger(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.CompletedTripHistoryPassenger(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

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
    fun vendor_upcooming_booking_loder(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.vendor_upcooming_booking_loder(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TripHistoryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }


    fun TriplistApi(
        token: String,
        isFromPassenger: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.TripList(token,isFromPassenger)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TriplistResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }   fun driver_loadertrip_list(
        token: String,type: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.driver_loadertrip_list(token,type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TriplistResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun vendor_driver_loadertrip_list(
        token: String,driver_id:String,type:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.vendor_driver_loadertrip_list(token,driver_id,type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TriplistResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun PassengerTriplistApi(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.PassengerTripList(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TriplistResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

}