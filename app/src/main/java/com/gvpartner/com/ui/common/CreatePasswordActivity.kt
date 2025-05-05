package com.gvpartner.com.ui.common

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityCreatePasswordBinding
import com.gvpartner.com.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePasswordActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()
    var mobile :String = ""
    private lateinit var binding : ActivityCreatePasswordBinding
    private var isVisible2 = false
    private var isVisible1 = false
    var flag=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_create_password)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_password)
        if (intent!=null){
            mobile = intent.getStringExtra("mobile").toString()
            flag = intent.getStringExtra("flag").toString()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnDone.setOnClickListener(View.OnClickListener {
            if (binding.edtPassword.text.toString().isNullOrEmpty()){
                snackbar("Please enter password.")
            }else if (binding.edtConfirmpassword.text.toString().isNullOrEmpty()){
                snackbar("Please enter confirm password")
            }else if (!binding.edtPassword.text.toString().equals(binding.edtConfirmpassword.text.toString())){
                snackbar("Password does not match.")
            }else{
                viewModel.ForgotPassword(
                    mobile,
                    binding.edtPassword.text.toString()
                )
            }

        })

        binding.lytVisiblePass.setOnClickListener {
            if (isVisible1) {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisiblePass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_hide
                        )
                    )
                    binding.edtPassword.setSelection(binding.edtPassword.text.length)
                }
                isVisible1 = false
            } else { //Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisiblePass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_view
                        )
                    )
                    binding.edtPassword.setSelection(binding.edtPassword.text.length)
                }
                isVisible1 = true
            }
        }
        binding.lytVisibleConfirmPass.setOnClickListener {
            if (isVisible2) {
                binding.edtConfirmpassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisiblePass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_hide
                        )
                    )
                    binding.edtPassword.setSelection(binding.edtPassword.text.length)
                }
                isVisible2 = false
            } else { //Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                binding.edtConfirmpassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisibleConfirmPass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_view
                        )
                    )
                    binding.edtConfirmpassword.setSelection(binding.edtConfirmpassword.text.length)
                }
                isVisible2 = true
            }
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.changepassword.observe(this){
            if (it.error == false){
                snackbar(it.message.toString())
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("flag",flag)
                startActivity(intent)

                finishAffinity()
            }else{
                snackbar(it.message.toString())
            }
        }
    }
}