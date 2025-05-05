package com.gvpartner.com.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil

import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityTruckDriverRegistrationBinding
import com.gvpartner.com.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruckDriverRegistration : BaseActivity() {
    private lateinit var binding : ActivityTruckDriverRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_truck_driver_registration)



    }
}