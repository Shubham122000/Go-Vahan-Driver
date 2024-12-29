package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanpartner.com.model.*
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel()  {
    val progressBarStatus = MutableLiveData<Boolean>()
    val walletListResponse = MutableLiveData<VendorWalletActivity>()
    val walletData = MutableLiveData<VendorWalletActivity>()
    val walletListfilterResponse = MutableLiveData<WalletFilterLIstREsponse>()
    val walletDownload = MutableLiveData<ProfileResponse>()
    val TransactionReportResponse = MutableLiveData<TransactionReportResponse>()
    val AddwalletResponse = MutableLiveData<Addmoneywallet>()
    val addmoneytowalletresponse = MutableLiveData<DriverProfile>()
    val selectbankaccount= MutableLiveData<BankAccountListResponse>()
    val bank_account_listResponse= MutableLiveData<BankAccountListResponse>()
    var razorpayStatusResponse= MutableLiveData<Razorpay_status_Response> ()

    fun WalletListApi(
        header: String,date : String,transaction_type : String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.WalletList(header,date,transaction_type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                walletListResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun vendor_wallet_list(
        header: String,date : String,transaction_type : String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.vendor_wallet_list(header,date,transaction_type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                walletData.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun individual_payment_list(
        header: String,date : String,transaction_type : String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.individual_payment_list(header,date,transaction_type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                walletData.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun payment_status_check(token: String,
                             transaction_id: String
    ): MutableLiveData<Razorpay_status_Response> {
        if (razorpayStatusResponse == null) {
            razorpayStatusResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.payment_status_check(token, transaction_id
                )

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    razorpayStatusResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                progressBarStatus.value = false
                e.printStackTrace()
            }
        }
        return razorpayStatusResponse
    }

    fun my_wallet_payment(
        token: String,
        type: String,
        transaction_id: String,
        amount: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.my_wallet_payment(token,type,transaction_id,amount)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                addmoneytowalletresponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun add_bank_account(
        token: String,
        account_no: String,
        name: String,
        ifsc: String,
        bank_name: String,
        account_branch: String,upi_id:String,
       image: MultipartBody.Part
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val account_no: RequestBody = account_no.toRequestBody("text/plain".toMediaTypeOrNull())
            val name: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val ifsc: RequestBody = ifsc.toRequestBody("text/plain".toMediaTypeOrNull())
            val bank_name: RequestBody = bank_name.toRequestBody("text/plain".toMediaTypeOrNull())
            val account_branch: RequestBody = account_branch.toRequestBody("text/plain".toMediaTypeOrNull())
            val upi_id: RequestBody = upi_id.toRequestBody("text/plain".toMediaTypeOrNull())


            val response =
                mainRepository.add_bank_account(token,account_no,name,ifsc,bank_name,account_branch,upi_id,image)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                addmoneytowalletresponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
 fun bank_account_list(
        token: String,

    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.bank_account_list(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                bank_account_listResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
 fun bank_account_list_id(
     token: String, id: String

    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.bank_account_list_id(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                selectbankaccount.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun withdrawapi(
        token: String, amount: String

    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.withdrawapi(token,amount)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                bank_account_listResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun vendor_driver_transection(
        token: String, amount: String

    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.vendor_driver_transection(token,amount)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                bank_account_listResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun TransactionReport(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.TransactionReport(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TransactionReportResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun addmoneywalletapi(
        token: String,
        amount:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Addmoneywallet(token,amount)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddwalletResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun purchase_plan_from_walletApi(
        token: String,
        amount:String,truck_id:String,transaction_type:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.purchase_plan_from_walletApi(token,amount,truck_id,transaction_type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddwalletResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun buySubscriptionWalletApi(
        token: String,
        plantype:String,transaction_type:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.buySubscriptionWalletApi(token,plantype,transaction_type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddwalletResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun buySubscriptionOnlineApi(
        token: String,
        plantype:String,transaction_type:String,transection:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.buySubscriptionOnlineApi(token,plantype,transaction_type, transection )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddwalletResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun purchase_plan_from_walletApi_to_truck(
        token: String,
        amount:String,truck_id:String,transaction_type:String,validity:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.purchase_plan_from_walletApi_to_truck(token,amount,truck_id,transaction_type, validity)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddwalletResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun purchase_plan_from_walletApi_to_passenger(
        token: String,
        amount:String,truck_id:String,transaction_type:String,validity: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.purchase_plan_from_walletApi_to_passenger(token,amount,truck_id,transaction_type,validity)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddwalletResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun Filteredwalletapi(
        token: String,
        date:String,transaction_type:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Filteredwallet(token,date,transaction_type)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                walletListfilterResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun my_driver_wallet_list_donload(
        token: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.my_driver_wallet_list_donload(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                walletDownload.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

}