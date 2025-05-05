package com.gvpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityCreateBusinessCardv2Binding

class CreateBusinessCardV2Activity : BaseActivity() {

    private lateinit var binding : ActivityCreateBusinessCardv2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_business_cardv2)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })
    }
}