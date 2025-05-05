package com.gvpartner.com.ui.vendor.passenger

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityTripDetailsAssignPactivityBinding
import com.gvpartner.com.base.BaseActivity

class TripDetailsAssignPActivity : BaseActivity() {
    private lateinit var binding : ActivityTripDetailsAssignPactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details_assign_pactivity)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })


        binding.btnStarttrip.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TripAssignedToDriverPActivity::class.java)
            startActivity(intent)

        })

    }
}