package com.govahanpartner.com.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityCreateBusinessCard2Binding

class CreateBusinessCard2 : BaseActivity() {

    private lateinit var binding : ActivityCreateBusinessCard2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_create_business_card2)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_business_card2)

        binding.btnDownload.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddDriverActivity::class.java)
            startActivity(intent)

        })


    }
}