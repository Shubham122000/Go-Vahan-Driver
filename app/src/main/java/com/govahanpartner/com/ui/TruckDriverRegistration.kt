package com.govahanpartner.com.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil

import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityTruckDriverRegistrationBinding
import com.govahanpartner.com.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruckDriverRegistration : BaseActivity() {
    private lateinit var binding : ActivityTruckDriverRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_truck_driver_registration)



    }
}