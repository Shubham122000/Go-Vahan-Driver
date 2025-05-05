package com.gvpartner.com.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.gvpartner.com.model.DeiselPrice
import com.gvpartner.com.model.StateListResponse
import com.gvpartner.com.model.StateListResponseData
import com.gvpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeiselViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    val _progressBarVisibility = MutableLiveData<Int>()
    var statlistResponse = MutableLiveData<StateListResponse>()
    var deiselPriceResponse = MutableLiveData<DeiselPrice>()
    var stateListData = MutableLiveData<ArrayList<StateListResponseData>>()


    fun StateListAPI(): MutableLiveData<StateListResponse> {
        if (statlistResponse == null) {
            statlistResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.StateList()

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    statlistResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return statlistResponse
    }

    fun DeiselPriceAPI(id:String): MutableLiveData<DeiselPrice> {
        if (deiselPriceResponse == null) {
            deiselPriceResponse = MutableLiveData()
        }
        progressBarStatus.value = true
        viewModelScope.launch {
            try {
                val response = mainRepository.DeiselList(id)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    deiselPriceResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                progressBarStatus.value = false

                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return deiselPriceResponse
    }




}