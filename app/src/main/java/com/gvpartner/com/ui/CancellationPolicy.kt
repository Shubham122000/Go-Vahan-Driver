package com.gvpartner.com.ui

import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityCancellationPolicyBinding
import com.gvpartner.com.viewmodel.StaticDataViewModel
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