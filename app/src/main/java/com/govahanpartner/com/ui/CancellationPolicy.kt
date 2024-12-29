package com.govahanpartner.com.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityCancellationPolicyBinding
import com.govahanpartner.com.databinding.ActivityPrivacyPolicyBinding
import com.govahanpartner.com.viewmodel.StaticDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancellationPolicy : BaseActivity() {
    private lateinit var binding : ActivityCancellationPolicyBinding
    private val viewModel: StaticDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cancellation_policy)
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

        viewModel.calcelation_refund_policy(
            "Bearer " + userPref.getToken().toString()
        )
        viewModel.cancellationRsponse.observe(this){
            if (it.status == 1){
                binding.privacypolicytext.text = Html.fromHtml(it.data?.message)

            }
        }
    }
}