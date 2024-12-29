package com.govahanpartner.com.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanpartner.com.model.BannerResponse
import com.govahanpartner.com.network.MainRepository
import com.govahanpartner.com.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(private val mainRepository: MainRepository, private val utils : Utils, @ApplicationContext val context: Context): ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val homeBannerResponseModel = MutableLiveData<BannerResponse>()


    fun getDashboardBannertApi(token: String, vehicle_type: String) {
        progressBarStatus.value = true
        try {
            viewModelScope.launch {
                val response = mainRepository.getDashboardBannertApi(
                    token, vehicle_type
                )
                if (response.isSuccessful) {
                    progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
                    homeBannerResponseModel.postValue(response.body())
                } else {
                    progressBarStatus.value = false
                    Log.d("TAG", response.body().toString())
                }

            }

        } catch (e: Exception) {
            progressBarStatus.value = false
            e.printStackTrace()
            if (e is ConnectException) {
                utils.simpleAlert(
                    context, "Error", "Please check your Internet connection"
                )
            } else {
                utils.simpleAlert(
                    context,
                    "Some Error Occurred",
                    "Please check your Internet connection"
                )
            }

        }
    }
}
