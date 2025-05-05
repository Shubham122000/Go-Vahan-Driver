package com.gvpartner.com.ui.common

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.gvpartner.com.R
import com.gvpartner.com.viewmodel.LoginViewModel
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityLoginBinding
import com.gvpartner.com.ui.DashboardActivity
import com.gvpartner.com.ui.vendor.SignUpAsVendor
import com.gvpartner.com.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var isVisible1 = false
    var flag: String = ""
    var type=""
    var token = ""
    companion object{
      var role=""
         }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        Firebase.initialize(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            token = task.result
            Log.d("TAG", "onCreate: "+token)
        })
        binding.lifecycleOwner = this
        binding.btnSignup.setOnClickListener(this)
        binding.lytVisiblePass.setOnClickListener(this)
        binding.tvForgotPass.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        if (intent != null){
            flag = intent.getStringExtra("flag").toString()
        }
        if (flag.equals("INDIVIDUAL")){
            type="2"
        }
        else if (flag.equals("VENDORDR")){
            type="3"
            binding.btnSignup.visibility=View.GONE
        } else{
            type="4"
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }


        viewModel.loginResponse.observe(this) {
            if (it?.error == false) {
                Toast.makeText(this, "Logged in Successfully...", Toast.LENGTH_LONG).show()
                userPref.user = it.result?.user!!
                userPref.isLogin = true
                userPref.setid(it.result?.user?.id.toString())
                userPref.setdriver_license(it.result?.user?.drivingLicence.toString())
                role=it.result?.user?.role.toString()
                userPref.setToken(it.result?.user?.apiToken)
                userPref.setusertype(it.result?.user?.userType.toString())
                userPref.setRole(it.result?.user?.role.toString())
                userPref.setName(it.result?.user?.name!!)
                userPref.setEmail(it.result?.user?.email)
                userPref.setMobile(it.result?.user?.mobileNumber.toString())
                userPref.setUserId(it.result?.user?.id!!.toString())
                it.result?.user?.profileImage?.let { it1 -> userPref.setImage(it1) }

//                if (flag == "INDIVIDUAL"){
//                    if (it.data?.driving_licence==null){
//                        val intent = Intent(this, AddDrivingLicenseActivity::class.java).putExtra("id",it.data?.id).putExtra("flag1","login")
//                        startActivity(intent)
//                    }
//                    else{
//                        val intent = Intent(this, DashboardActivity::class.java)
//                        startActivity(intent)
//                        finishAffinity()
//                    }
//                } else{
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
//                }
//                userPref.setUserId(it!!.data!!.Id.toString())

            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_signup -> {
                val intent = Intent(this, SignUpAsVendor::class.java)
                intent.putExtra("flag",flag)
                startActivity(intent)
            }
            R.id.lytVisiblePass -> {
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
            R.id.tvForgotPass -> {
                val intent = Intent(this, AccountVerificationActivity::class.java)
                intent.putExtra("flag",flag)
                startActivity(intent)
            }
            R.id.btn_login -> {
                if (binding.edtEmailLogin.text.toString().isNullOrEmpty()
                ) {
                    toast("Please enter Username.")
                } else if (binding.edtPassword.text.toString().isNullOrEmpty()) {
                    toast(" password field at least must have entered one capital letter, one special character, and varchar values and password length should be minimum of 6 letters")
                } else {
                    viewModel.driverLogin(
                        binding.edtEmailLogin.text.toString(),
                        binding.edtPassword.text.toString(),
                        token,
                        "Android",
                        type
                    )
                }
            }
        }
    }
}


