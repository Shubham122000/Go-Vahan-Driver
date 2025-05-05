package com.gvpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityPassengerDriverDetailBinding
import com.gvpartner.com.base.BaseActivity

class PassengerDriverDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityPassengerDriverDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_driver_detail)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}