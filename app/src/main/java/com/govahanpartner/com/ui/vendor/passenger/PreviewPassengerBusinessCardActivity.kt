package com.govahanpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityPreviewPassengerBusinessCardBinding
import com.govahanpartner.com.base.BaseActivity

class PreviewPassengerBusinessCardActivity : BaseActivity() {
    private lateinit var binding : ActivityPreviewPassengerBusinessCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview_passenger_business_card)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}