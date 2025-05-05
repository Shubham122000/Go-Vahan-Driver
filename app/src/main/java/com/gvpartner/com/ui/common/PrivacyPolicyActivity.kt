package com.gvpartner.com.ui.common

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityPrivacyPolicyBinding
import com.gvpartner.com.viewmodel.StaticDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyActivity : BaseActivity() {
    private lateinit var binding : ActivityPrivacyPolicyBinding
    private val viewModel: StaticDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_privacy_policy)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy)
        binding.ivBack.setOnClickListener{
            finish()
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.PrivacyPolicyAPI(
            "Bearer " + userPref.getToken().toString()
        )
        viewModel.PrivacyPolicyRsponse.observe(this){
            if (it.status == 1){
                binding.privacypolicytext.text = it.data?.description

            }
        }
    }
}