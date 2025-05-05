package com.gvpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvpartner.com.model.AddDriverResponse
import com.gvpartner.com.model.ChecksumResponse
import com.gvpartner.com.model.PaymentSuccessMsgResponse
import com.gvpartner.com.model.Razorpay_status_Response
import com.gvpartner.com.model.SubscriptionPlan
import com.gvpartner.com.network.MainRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionPlanViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel()  {

    val subscriptionPlan= MutableLiveData<SubscriptionPlan> ()
    val subscriptionPlanPayment= MutableLiveData<AddDriverResponse> ()
    var ChecksumResponse= MutableLiveData<ChecksumResponse> ()
    var razorpayStatusResponse= MutableLiveData<Razorpay_status_Response> ()
    var Paymentsuccessmsgresponse= MutableLiveData<PaymentSuccessMsgResponse> ()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun SubscriptionApi(
        token:String,
        forPassenger:Int
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.subscriptionplan(token,forPassenger)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                subscriptionPlan.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

//    fun checkupApi(token: String,
//        amount: String,  mobile: String,
//        user_id: String,
//  ): MutableLiveData<ChecksumResponse> {
//        if (ChecksumResponse == null) {
//            ChecksumResponse = MutableLiveData()
//        }
//        viewModelScope.launch {
//            try {
//                val response = mainRepository.checksumApi(token, amount,mobile,user_id
//                 )
//
//                if (response.isSuccessful) {
//                    progressBarStatus.value = false
//                    ChecksumResponse.postValue(response.body())
//                }
//            }catch (e:Exception) {
//                progressBarStatus.value = false
//                e.printStackTrace()
//            }
//        }
//        return ChecksumResponse
//    }

//    fun payment_status_check(token: String,
//                   transaction_id: String
//    ): MutableLiveData<Razorpay_status_Response> {
//        if (razorpayStatusResponse == null) {
//            razorpayStatusResponse = MutableLiveData()
//        }
//        viewModelScope.launch {
//            try {
//                val response = mainRepository.payment_status_check(token, transaction_id
//                )
//
//                if (response.isSuccessful) {
//                    progressBarStatus.value = false
//                    razorpayStatusResponse.postValue(response.body())
//                }
//            }catch (e:Exception) {
//                progressBarStatus.value = false
//                e.printStackTrace()
//            }
//        }
//        return razorpayStatusResponse
//    }

    fun paymentcheckApi(token: String,
        transactionid: String
    ): MutableLiveData<PaymentSuccessMsgResponse> {
        if (Paymentsuccessmsgresponse == null) {
            Paymentsuccessmsgresponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.paymentcheckapi( token,transactionid
                )

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    Paymentsuccessmsgresponse.postValue(response.body())
                }
            }catch (e:Exception) {
                progressBarStatus.value = false
                e.printStackTrace()
            }
        }
        return Paymentsuccessmsgresponse
    }
//    fun subscription_plan_passengers(
//        token:String
//    ){
//        progressBarStatus.value = true
//        viewModelScope.launch {
//
//            val response =
//                mainRepository.subscription_plan_passengers(token)
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                subscriptionPlan.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//
//    }
    fun PaymentSubscriptionPlan(
        token: String,
        id: String,
        subscribe: String,
        payment_mode: String,
        transaction_id: String,
        payment_crdated: String,
        status:String

        ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.loader_vehicle_payment(token,
                    id,
                    subscribe,
                    payment_mode,
                    transaction_id,payment_crdated,status
                )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                subscriptionPlanPayment.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun paymentsSubscriptionPassenger(
        token: String,
        id: String,
        subscribe: String,
        fare: String,
        payment_mode: String,
        transaction_id: String,
        validity: String,
        payment_crdated: String,status: String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.add_passenger_vehicle_payment(token,
                    id,
                    subscribe,
                    fare,
                    payment_mode,
                    transaction_id,validity,payment_crdated,status
                    )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                subscriptionPlanPayment.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }



}