package com.govahanpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityTripDetailsOfAssignedDriverPactivityBinding
import com.govahanpartner.com.base.BaseActivity

class TripDetailsOfAssignedDriverPActivity : BaseActivity() {

    private lateinit var binding : ActivityTripDetailsOfAssignedDriverPactivityBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details_of_assigned_driver_pactivity)

            binding.ivBack.setOnClickListener(View.OnClickListener {
                finish()

            })

        }
    }