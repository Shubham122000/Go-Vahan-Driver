package com.gvpartner.com.ui.driver

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityDriverLoaderVehicleBinding
import com.gvpartner.com.ui.AddDriverActivity
import com.gvpartner.com.ui.common.AddTruckDocumentsActivity
import com.gvpartner.com.ui.driver.passenger.AddTripLoaderDriver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityDriverLoaderVehicle : BaseActivity() {

    private lateinit var binding : ActivityDriverLoaderVehicleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        //setContentView(R.layout.activity_loader_vehicle)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_loader_vehicle)


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

        binding.llAddtrip.setOnClickListener(View.OnClickListener {
            if (userPref.getusertype().equals("2")){
                val intent = Intent(this, AddTripActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, AddTripLoaderDriver::class.java)
                startActivity(intent)
            }


        })

        binding.llAdddriver.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddDriverActivity::class.java)
            startActivity(intent)

        })


        binding.llTruckdocument.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddTruckDocumentsActivity::class.java)
            startActivity(intent)

        })




    }
    override fun onResume() {
        super.onResume()
    }
}