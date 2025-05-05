package com.gvpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityTripDetailOfAssignedDriverBinding
import com.gvpartner.com.base.BaseActivity

class TripDetailOfAssignedDriverActivity : BaseActivity() {
    private lateinit var binding : ActivityTripDetailOfAssignedDriverBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_detail_of_assigned_driver)


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

        binding.btnSubmit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TrackTruckDriverActivity::class.java)
            startActivity(intent)

        })
    }
}