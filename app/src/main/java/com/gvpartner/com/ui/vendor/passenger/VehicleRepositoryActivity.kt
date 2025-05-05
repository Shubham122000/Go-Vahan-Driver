package com.gvpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityVehicleRepositoryBinding
import com.gvpartner.com.base.BaseActivity

class VehicleRepositoryActivity : BaseActivity() {
    private lateinit var binding : ActivityVehicleRepositoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vehicle_repository)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}