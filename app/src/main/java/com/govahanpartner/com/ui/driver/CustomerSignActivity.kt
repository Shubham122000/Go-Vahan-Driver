package com.govahanpartner.com.ui.driver

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityCustomerSignBinding

class CustomerSignActivity : BaseActivity() {
    private lateinit var binding : ActivityCustomerSignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_sign)

    }
}