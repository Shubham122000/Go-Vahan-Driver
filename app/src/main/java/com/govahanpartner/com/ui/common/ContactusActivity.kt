package com.govahanpartner.com.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityContactusBinding
import com.govahanpartner.com.viewmodel.StaticDataViewModel
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
            if (it.status == 1){
                binding.dummytext.text = it.data?.name
                binding.phone.text = it.data?.contactNumber
                binding.email.text = it.data?.email
                Glide.with(this).load(it.data?.image).into(binding.ivDriver)
            }
        }

    }
}