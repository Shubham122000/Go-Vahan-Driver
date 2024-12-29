package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.govahanpartner.com.model.FareCaluculationResponse
import com.govahanpartner.com.model.TypeofTruckResponse
import com.govahanpartner.com.model.TypeofTruckResponseData
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoaderFareViewModel @Inject constructor(private val mainRepository: MainRepository) :ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    val farecalculationResponse = MutableLiveData<FareCaluculationResponse>()
    var truckTypeResponse = MutableLiveData<TypeofTruckResponse>()
    val _progressBarVisibility = MutableLiveData<Int>()
    var truckTypeData = MutableLiveData<ArrayList<TypeofTruckResponseData>>()

    fun TypeofTruckApi(token :String,type:String): MutableLiveData<TypeofTruckResponse> {
        if (truckTypeResponse == null) {
            truckTypeResponse = MutableLiveData()
        }
        progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.TypeofTruckApi(token,type)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    truckTypeResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                progressBarStatus.value = false

                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return truckTypeResponse
    }
    fun farecalculatorApi(
        token: String,
        pickup_lat: String,
        pickup_long: String,
        dropup_lat: String,
        dropup_long: String,
        mileage: String,
        vehicle_type: String,oil_price:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.loderfarecalculator(token,pickup_lat,pickup_long,dropup_lat,dropup_long,mileage,vehicle_type,oil_price)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                farecalculationResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun passenger_fare_calculatorApi(
        token: String,
        pickup_lat: String,
        pickup_long: String,
        dropup_lat: String,
        dropup_long: String,
        mileage: String,
        vehicle_type: String,oil_price:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.passenger_fare_calculatorApi(token,pickup_lat,pickup_long,dropup_lat,dropup_long,mileage,vehicle_type,oil_price)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                farecalculationResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
}