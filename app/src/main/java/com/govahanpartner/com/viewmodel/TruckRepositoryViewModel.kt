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
class TruckRepositoryViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val TruckrepositoryList = MutableLiveData<LoaderTruckRepositoryListResponse> ()
    val TruckrepositoryDelete = MutableLiveData<DriverProfile> ()
    val truckviewResponse = MutableLiveData<TaxiRepositoryViewDetailsResponse> ()
    val truckImagesResponse = MutableLiveData<TruckImagesModelCLass> ()
    val truckDocumentsResponse = MutableLiveData<TruckDocumentsDetailsResponse> ()
    val TruckrepositorymodelClass = MutableLiveData<DriverProfile> ()
    val progressBarStatus = MutableLiveData<Boolean>()

    fun TruckrepositoryListApi(
        token:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Loadertruckrepositorylist(token)
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
    fun loader_truck_repository_list_details(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.loader_truck_repository_list_details(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                truckviewResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun loader_truck_repository_image_list_details(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.loader_truck_repository_image_list_details(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                truckImagesResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
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
    fun loader_truck_repository_documents(
        token:String,
        id:String
    ){
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.loader_truck_repository_doc_list_details(token,id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                truckDocumentsResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
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


}