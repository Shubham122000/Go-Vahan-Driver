package com.govahanpartner.com.ui.common

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityOtpactivityBinding
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class OTPActivity : BaseActivity() {
    private val mLoginViewModel: LoginViewModel by viewModels()
    private lateinit var binding : ActivityOtpactivityBinding
    var mobile :String =""
    var flag=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpactivity)
        if (intent != null){
            mobile = intent.getStringExtra("mobile_number").toString()
            flag = intent.getStringExtra("flag").toString()

        }

        binding.ivBack.setOnClickListener {
            finish()
        }
        mLoginViewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        mLoginViewModel.accountVerificationResponse.observe(this) {
            if (it.error == false) {
//                Toast.makeText(this, "OTP Sent Successfully...${it.otp}", Toast.LENGTH_LONG).show()
            } else {
                toast(it.message)
            }
        }
        mLoginViewModel.verifyOtpResponse.observe(this) {
            if (it?.error == false) {
                val intent = Intent(this, CreatePasswordActivity::class.java)
                intent.putExtra("mobile",mobile)
                intent.putExtra("flag",flag)
                startActivity(intent)
                finish()
            } else {
                snackbar(it?.message!!)
            }
        }
        binding.tvResend.setOnClickListener {
            mLoginViewModel.driverSendOtp(
                mobile,
            )
        }


        binding.btnNext.setOnClickListener {
            mLoginViewModel.driverVerifyOtp(
                binding.otpView.text.toString(),
                mobile
            )
        }

    }
}