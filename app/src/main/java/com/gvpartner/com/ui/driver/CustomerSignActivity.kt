package com.gvpartner.com.ui.driver

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityCustomerSignBinding

class CustomerSignActivity : BaseActivity() {
    private lateinit var binding : ActivityCustomerSignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_sign)

    }
}