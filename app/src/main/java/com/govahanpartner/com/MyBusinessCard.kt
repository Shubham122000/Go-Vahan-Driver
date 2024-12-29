package com.govahanpartner.com

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityMyBusinessCardBinding

import com.govahanpartner.com.base.BaseActivity

class MyBusinessCard : BaseActivity() {

    lateinit var binding: ActivityMyBusinessCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_business_card)


    }
}