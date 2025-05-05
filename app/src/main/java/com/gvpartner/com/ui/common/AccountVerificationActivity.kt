package com.gvpartner.com.ui.common

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.viewmodel.LoginViewModel
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityAccountVerificationBinding
import com.gvpartner.com.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AccountVerificationActivity : BaseActivity(), View.OnClickListener {
    private val mLoginViewModel: LoginViewModel by viewModels()
    private lateinit var binding:ActivityAccountVerificationBinding
    var flag=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_verification)
        if (intent != null){
            flag = intent.getStringExtra("flag").toString()
        }

        binding.btnNext.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)

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

                val intent = Intent(this, OTPActivity :: class.java)
                intent.putExtra("id", it.result?.user?.id.toString())
                intent.putExtra("flag",flag)
                intent.putExtra("otp", it.result?.user?.otp)
                intent.putExtra("mobile_number", binding.etMobile.text.toString())
                startActivity(intent)
                finish()
            } else {
                toast(it.message)
            }
        }
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_next ->{
                if(binding.etMobile.text.toString().isNullOrEmpty()){
                    snackbar("Please enter mobile number.")
                }else if (binding.etMobile.text.toString().length<10){
                    snackbar("Please enter valid mobile number.")
                }else {
                        mLoginViewModel.driverSendOtp(
                        binding.etMobile.text.toString(),
                        )
                }
            }
            R.id.ivBack ->{
                onBackPressed()
            }


        }
    }


}