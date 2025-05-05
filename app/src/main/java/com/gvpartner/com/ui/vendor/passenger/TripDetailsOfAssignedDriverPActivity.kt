package com.gvpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityTripDetailsOfAssignedDriverPactivityBinding
import com.gvpartner.com.base.BaseActivity

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