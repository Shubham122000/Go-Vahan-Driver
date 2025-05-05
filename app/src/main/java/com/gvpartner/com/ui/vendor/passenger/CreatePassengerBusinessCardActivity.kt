package com.gvpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityCreatePassengerBusinessCardBinding

class CreatePassengerBusinessCardActivity : BaseActivity() {
    private lateinit var binding : ActivityCreatePassengerBusinessCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_passenger_business_card)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}