package com.gvpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityTripDetailsAssignBinding
import com.gvpartner.com.base.BaseActivity

class TripDetailsAssignActivity : BaseActivity() {

    private lateinit var binding : ActivityTripDetailsAssignBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details_assign)


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })
        binding.btnTripassign.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TripAssignToDriverActivity::class.java)
            startActivity(intent)

        })


    }
}