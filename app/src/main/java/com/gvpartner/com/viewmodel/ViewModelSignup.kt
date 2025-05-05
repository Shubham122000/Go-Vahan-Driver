package com.gvpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.gvpartner.com.model.RegisterResponseModel
import com.gvpartner.com.model.TruckTypeResponse
import com.gvpartner.com.model.TruckTypeResponseData
import com.gvpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSignup @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    var signUpResponse = MutableLiveData<RegisterResponseModel>()
    var truckTypeResponse = MutableLiveData<TruckTypeResponse>()
    val progressBarStatus = MutableLiveData<Boolean>()
    val typeOfVehical = MutableLiveData<ArrayList<TruckTypeResponseData>>()
    private var userMutableLiveData : MutableLiveData<RegisterResponseModel> ? = null
    val error = MutableLiveData<Int> ()
    val errorString = MutableLiveData<String>()
    val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

//    fun driverSignupApi(
//        name: String,
//        mobile: String,
//        email: String,
//        gst_number: String,
//        password: String,
////        type_vehicle: String,
//        device_type: String,
//        device_name: String,
//        device_token: String,
//        device_id: String,
//    ) {
//        progressBarStatus.value = true
//        viewModelScope.launch {
//            val response = mainRepository.driverSignupApi(
//                name,
//                mobile,
//                email,
//                gst_number,
//                password,
////                        type_vehicle,
//                device_type,
//                device_name,
//                device_token,
//                device_id,
//            )
//                progressBarStatus.value = false
//        }
//    }

    fun getRegisteredUser(): LiveData<RegisterResponseModel?>?{

        if(userMutableLiveData == null){
            userMutableLiveData = MutableLiveData()
        }
        return userMutableLiveData
    }
    fun driverRegistration(
        name: String,
        countryCode:String,
        mobile: String,
        email: String,
        gst: String,
        password: String,
        referal_code: String,
        role: String

    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.driverSignupApi(name ,countryCode,mobile,
                    email,
                    gst,
                    password,referal_code,
                    role,
                    "Android","Android","Android","Android")
            if (response.isSuccessful) {
                progressBarStatus.value = false
                signUpResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun truckTypeApi(): MutableLiveData<TruckTypeResponse> {
        if (truckTypeResponse == null) {
            truckTypeResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.truckTypeApi()

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    truckTypeResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return truckTypeResponse
    }
}