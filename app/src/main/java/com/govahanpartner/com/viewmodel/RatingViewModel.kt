package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.govahanpartner.com.model.RatingResponse
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel() {
    val progressBarStatus = MutableLiveData<Boolean>()
    val RatingResponse = MutableLiveData<RatingResponse>()

    fun RatingAPI(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.Rating(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                RatingResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
}