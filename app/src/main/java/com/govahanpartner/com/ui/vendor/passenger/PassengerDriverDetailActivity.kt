package com.govahanpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityPassengerDriverDetailBinding
import com.govahanpartner.com.base.BaseActivity

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