package com.govahanpartner.com.ui.vendor.passenger

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityTripDetailsAssignPactivityBinding
import com.govahanpartner.com.base.BaseActivity

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