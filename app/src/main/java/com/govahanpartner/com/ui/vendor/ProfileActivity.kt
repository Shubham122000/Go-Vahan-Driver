package com.govahanpartner.com.ui.vendor

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityProfileBinding
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.DialogLogoutBinding
import com.govahanpartner.com.ui.common.LoginActivity
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)


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
        viewModel.GetProfileAPI(
            "Bearer "+ userPref.getToken().toString(),
        )
        viewModel.getProfileResponse.observe(this) {
            if (it?.error == false) {

                binding.tvFullname.text = it.result?.user?.name.toString()
                binding.tvEmail.text = it.result?.user?.email.toString()
                binding.tvPhone.text = it.result?.user?.mobileNumber.toString()

                if (it.result?.user?.address==null){
                    binding.tvAddress.text = " "
                }else{

                    binding.tvAddress.text = it.result?.user?.address.toString()
                }

                Glide.with(this).load(it.result?.user?.profileImage).into(binding.ivDriver)
            } else {
//                toast(.message)
            }
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
        binding.btnUpdate.setOnClickListener {
            startActivity(Intent(this,EditProfileVendorActivity::class.java))
        }
    }

    private fun logout() {
        val cDialog = Dialog(this)
        val bindingDialog: DialogLogoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_logout,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }
        bindingDialog.btnLogout.setOnClickListener {
            userPref.clearPref()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
            cDialog.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.GetProfileAPI(
                "Bearer "+ userPref.getToken().toString(),
        )

    }
}