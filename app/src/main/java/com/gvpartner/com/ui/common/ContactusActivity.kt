package com.gvpartner.com.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityContactusBinding
import com.gvpartner.com.viewmodel.StaticDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactusActivity : BaseActivity() {
    private lateinit var binding : ActivityContactusBinding
    private val viewModel: StaticDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contactus)


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.ContactUsAPI(
            "Bearer " + userPref.getToken().toString()
        )
        viewModel.ContactUSRsponse.observe(this){
            if (it.error == false){
                binding.dummytext.text = it.result?.data?.name
                binding.phone.text = it.result?.data?.contactNumber
                binding.email.text = it.result?.data?.email
                binding.dummytext.text = it.result?.data?.address
//                Glide.with(this).load(it.data?.image).into(binding.ivDriver)
            }
        }

    }
}