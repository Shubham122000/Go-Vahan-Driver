package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanpartner.com.model.*
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    var InvoiceListResponse = MutableLiveData<InvoiceListResponse>()
    var Invoiceurldownload = MutableLiveData<InvoiceurldownloadResponse>()
    var InvoiceSummeryResponse = MutableLiveData<InvoiceSummeryResponse>()
    var SendDriverLoaderInvoiceResponse = MutableLiveData<SendDriverLoaderInvoiceResponse>()
    var LoaderdrivertripcancelResponse = MutableLiveData<LoaderDriverTripCancelResponse>()
    var Loader_cancel_ReasonList_Response = MutableLiveData<Loader_cancel_ReasonList_Response>()
    var CompleteDriverDetailsResponse = MutableLiveData<CompleteDriverDetailsResponse>()
    var InvoiceResponse = MutableLiveData<InvoiceResponse>()
    var acceptRide = MutableLiveData<Addmoneywallet>()
    val progressBarStatus = MutableLiveData<Boolean>()
    var VisitingPdfResponse = MutableLiveData<DownloadPdfResponse>()

    fun InvoicelistAPI(
        token: String,type:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.InvoiceList(token,type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                InvoiceListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun loader_driver_invoice_url(
        token: String,booking_id:String,type:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.loader_driver_invoice_url(token,booking_id,type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                Invoiceurldownload.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }


    fun CompletedtripdetailsAPI(
        token: String,
        bookingid:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.Completedrivertripdetails(token,bookingid)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CompleteDriverDetailsResponse.postValue(response.body())
            }else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun cancel_booking_history_passenger_details(
        token: String,
        bookingid:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.cancel_booking_history_passenger_details(token,bookingid)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CompleteDriverDetailsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun CompletedtrippassengerdetailsAPI(
        token: String,
        bookingid:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.Completedriverpassengertripdetails(token,bookingid)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CompleteDriverDetailsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun ongoing_booking_history_loader_details(
        token: String,
        bookingid:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.ongoing_booking_history_loader_details(token,bookingid)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CompleteDriverDetailsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun ongoing_booking_history_passenger_details(
        token: String,
        bookingid:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.ongoing_booking_history_passenger_details(token,bookingid)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CompleteDriverDetailsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun cancel_booking_history_loader_details(
        token: String,
        bookingid:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.cancel_booking_history_loader_details(token,bookingid)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                CompleteDriverDetailsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }



    fun SendDriverLoaderInvoiceAPI(
        token: String,
        bookingid:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.Senddriverloaderinvoice(token,bookingid)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                SendDriverLoaderInvoiceResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun LoaderDriverTripCancelAPI(
        token: String,
        bookingid:String,
        reason_id:String,
        reason:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.LoaderDriverTripCancel(token,bookingid,reason_id,reason)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                LoaderdrivertripcancelResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun passenger_driver_trip_cancelApi(
        token: String,
        bookingid:String,
        reason_id:String,
        reason:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.passenger_driver_trip_cancelApi(token,bookingid,reason_id,reason)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                LoaderdrivertripcancelResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun LoadercancelReasonAPI(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.LoaderCancelReason(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                Loader_cancel_ReasonList_Response.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun AcceptRideAPI(
        token: String,
        bookingid: String,
        startcode: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.AcceptRide(token,bookingid,startcode)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                acceptRide.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun InvoicesummeryAPI(
        token: String,
        invoice_numbers:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.LoaderinvoiceSummery(token,invoice_numbers)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                InvoiceSummeryResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun InvoiceDownloadAPI(
        token: String,
        invoiceno:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.InvoiceDownload(token,invoiceno)
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