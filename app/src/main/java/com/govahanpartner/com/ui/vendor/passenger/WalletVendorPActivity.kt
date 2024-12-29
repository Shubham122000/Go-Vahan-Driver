package com.govahanpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityWalletVendorPactivityBinding
import com.govahanpartner.com.base.BaseActivity

class WalletVendorPActivity : BaseActivity() {
    private lateinit var binding : ActivityWalletVendorPactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_vendor_pactivity)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

    }
}