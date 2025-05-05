package com.gvpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityTrackTruckDriverBinding
import com.gvpartner.com.base.BaseActivity

class TrackTruckDriverActivity : BaseActivity() {
    private lateinit var binding : ActivityTrackTruckDriverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_track_truck_driver)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

        binding.llTrack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TrackVehicleActivity::class.java)
            startActivity(intent)

        })

    }
}