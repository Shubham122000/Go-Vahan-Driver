package com.gvpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityPaymentMethodsVactivityBinding
import com.gvpartner.com.base.BaseActivity

class PaymentMethodsVActivity : BaseActivity() {
    private lateinit var binding : ActivityPaymentMethodsVactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_methods_vactivity)


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })
    }
}