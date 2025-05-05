package com.gvpartner.com

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityMyBusinessCardBinding

import com.gvpartner.com.base.BaseActivity

class MyBusinessCard : BaseActivity() {

    lateinit var binding: ActivityMyBusinessCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_business_card)


    }
}