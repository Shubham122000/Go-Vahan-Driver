package com.gvpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityPaymentMethodsPactivityBinding
import com.gvpartner.com.base.BaseActivity

class PaymentMethodsPActivity : BaseActivity() {
    private lateinit var binding : ActivityPaymentMethodsPactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_methods_pactivity)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}