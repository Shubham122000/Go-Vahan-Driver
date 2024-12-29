package com.govahanpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityTripDetailOfAssignedDriverBinding
import com.govahanpartner.com.base.BaseActivity

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