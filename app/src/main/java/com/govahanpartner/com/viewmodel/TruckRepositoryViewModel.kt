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
class TruckRepositoryViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val TruckrepositoryList = MutableLiveData<LoaderTruckRepositoryListResponse> ()
    val TruckrepositoryDelete = MutableLiveData<DriverProfile> ()
    val truckviewResponse = MutableLiveData<TaxiRepositoryViewDetailsResponse> ()
    val truckImagesResponse = MutableLiveData<TruckImagesModelCLass> ()
    val truckDocumentsResponse = MutableLiveData<TruckDocumentsDetailsResponse> ()
    val TruckrepositorymodelClass = MutableLiveData<DriverProfile> ()
    val progressBarStatus = MutableLiveData<Boolean>()
    var addloaderResponse = MutableLiveData<AddloaderResponse>()

    fun TruckrepositoryListApi(
        token:String,
        isFromPassenger: String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Loadertruckrepositorylist(token,isFromPassenger)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TruckrepositoryList.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun TruckrepositoryPassengerListApi(
        token:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Loadertruckrepositorypassengerlist(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TruckrepositoryList.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun loader_truck_repository_list_deleteListApi(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.loader_truck_repository_list_delete(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TruckrepositoryDelete.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun getVehicleDetails(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.getVehicleDetails(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                truckviewResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
//    fun loader_truck_repository_image_list_details(
//        token:String,
//        id:String
//    ){
//        progressBarStatus.value = true
//        viewModelScope.launch {
//
//            val response =
//                mainRepository.loader_truck_repository_image_list_details(token,id)
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                truckImagesResponse.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//
//    }
    fun passengers_truck_repository_image_list(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passengers_truck_repository_image_list(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                truckImagesResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
//    fun loader_truck_repository_documents(
//        token:String,
//        id:String
//    ){
//        progressBarStatus.value = true
//        viewModelScope.launch {
//
//            val response =
//                mainRepository.loader_truck_repository_doc_list_details(token,id)
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                truckDocumentsResponse.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//
//    }
    fun passengers_truck_repository_list_details(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passengers_truck_repository_list_details(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                truckviewResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun passengers_truck_repository_doc_list(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passengers_truck_repository_doc_list(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                truckDocumentsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun passengers_truck_repository_list_delete(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passengers_truck_repository_list_delete(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                TruckrepositorymodelClass.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }



    fun editVehicle(
        token: String,
//        driver_id: String,
//        vehicle_owner_name: String,
//        vehicle_name: String,
//        year_of_model: String,
//        vehicle_number: String,
//        vehicle_type: String,
//        vehicle_category: String,
//        capacity: String,
//        height: String,
//        color: String,
//        no_of_tyres: String,
//        body_type: String,
        isFromPassenger: String,
//        seat: String,
        images1: MultipartBody.Part?,
        images2: MultipartBody.Part?,
        images3: MultipartBody.Part?,
        images4: MultipartBody.Part?,
        doc1: MultipartBody.Part?,
        doc2: MultipartBody.Part?,
        doc3: MultipartBody.Part?,
        doc4: MultipartBody.Part?,
        doc5: MultipartBody.Part?,
        doc6: MultipartBody.Part?,
        exp_date_1: String,
        exp_date_2: String,
        exp_date_3: String,
        exp_date_4: String,
        exp_date_5: String,
        other_exp_date: String,
        other_doc_name: String,
    ) {
//        val driver_id: RequestBody = driver_id.toRequestBody("text/plain".toMediaTypeOrNull())
//        val vehicle_owner_name: RequestBody = vehicle_owner_name.toRequestBody("text/plain".toMediaTypeOrNull())
//        val vehicle_name: RequestBody = vehicle_name.toRequestBody("text/plain".toMediaTypeOrNull())
//        val year_of_model: RequestBody = year_of_model.toRequestBody("text/plain".toMediaTypeOrNull())
//        val vehicle_number: RequestBody = vehicle_number.toRequestBody("text/plain".toMediaTypeOrNull())
//        val vehicle_type: RequestBody = vehicle_type.toRequestBody("text/plain".toMediaTypeOrNull())
//        val color: RequestBody = color.toRequestBody("text/plain".toMediaTypeOrNull())
//        val capacity: RequestBody = capacity.toRequestBody("text/plain".toMediaTypeOrNull())
//        val height: RequestBody = height.toRequestBody("text/plain".toMediaTypeOrNull())
//        val no_of_tyres: RequestBody = no_of_tyres.toRequestBody("text/plain".toMediaTypeOrNull())
//        val body_type: RequestBody = body_type.toRequestBody("text/plain".toMediaTypeOrNull())
//        val isFromPassenger: RequestBody = isFromPassenger.toRequestBody("text/plain".toMediaTypeOrNull())
//        val seat: RequestBody = seat.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_1: RequestBody = exp_date_1.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_2: RequestBody = exp_date_2.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_3: RequestBody = exp_date_3.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_4: RequestBody = exp_date_4.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_5: RequestBody = exp_date_5.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_exp_date: RequestBody = other_exp_date.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_doc_name: RequestBody = other_doc_name.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.editVehicle(
                    token,
//                    driver_id,
//                    vehicle_owner_name,
//                    vehicle_name,
//                    year_of_model,
//                    vehicle_number,
//                    vehicle_type,
//                    vehicle_category,
//                    capacity,
//                    height,
//                    color,
//                    no_of_tyres,
//                    body_type,
//                    isFromPassenger,
//                    seat,
                    images1, images2, images3, images4, doc1, doc2, doc3, doc4, doc5,doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4,exp_date_5, other_exp_date, other_doc_name
                )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                addloaderResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }


}