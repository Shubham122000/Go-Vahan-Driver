package com.govahanpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityVehicleRepositoryBinding
import com.govahanpartner.com.base.BaseActivity

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