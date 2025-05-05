package com.gvpartner.com.ui.vendor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivitySignUpVendorBinding
import com.gvpartner.com.model.SignupModel
import com.gvpartner.com.model.TruckTypeResponseData
import com.gvpartner.com.ui.common.LoginActivity
import com.gvpartner.com.utils.CommonUtils
import com.gvpartner.com.viewmodel.ViewModelSignup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpAsVendor : BaseActivity(), View.OnClickListener {

    private lateinit var binding : ActivitySignUpVendorBinding
    private val viewModel: ViewModelSignup by viewModels()
    var truckdata : ArrayList<TruckTypeResponseData> = ArrayList()
    var nametype :ArrayList<String> = ArrayList()
    var id_type :ArrayList<String> = ArrayList()
    private var isVisible2 = false
    private var isVisible1 = false
    var signupModel: SignupModel?=null
    var name=""
    val vehicalNo:String?=null
    var vehicleRegistration : EditText?= null
    var allEds = ArrayList<EditText>()
    var newString: String? = null
    var flag: String = ""
    var role :String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_vendor)
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        if (intent != null){
            flag = intent.getStringExtra("flag").toString()
        }
        if (flag == "INDIVIDUAL"){
            role = "1"
            binding.typesofservices.visibility = View.GONE
        }else{
            role = "2"
            binding.typesofservices.visibility = View.GONE
        }
        binding.lifecycleOwner = this
        binding.btnSubmitSign.setOnClickListener(this)
        binding.alreadyhaveAnAccount.setOnClickListener(this)
        viewModel.progressBarVisibility.observe(this) {
            if (it == 1) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
         }
        viewModel.truckTypeApi().observe(this) {

            if (it!!.status == 1) {
                truckdata.clear()
                nametype.clear()
                nametype.clear()
                truckdata.addAll(it!!.data)
                viewModel.typeOfVehical.value = it.data
                for (i in 0 until it.data.size) {
                    nametype.add(it.data[i].service.toString())
                    id_type.add(it.data[i].id.toString())
                }
                val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    nametype
                )
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spState.adapter = spinnerArrayAdapter
            }
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.signUpResponse.observe(this) {
            if (it?.error == false) {

                if (flag=="INDIVIDUAL"){
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("flag",flag)
                    intent.putExtra("flag1","signup")
                    intent.putExtra("id",it.result?.user?.id.toString())
                    startActivity(intent)
                }
//                userPref.setUserId(it!!.data!!.Id.toString())E
                else{
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("flag",flag)
                    startActivity(intent)
                    finishAffinity()
                }
                Toast.makeText(this, "Signup successfully", Toast.LENGTH_LONG).show()


            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }

//        binding.btnSubmit.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        })
        viewModel.getRegisteredUser()?.observe(this) {

            if (it!!.error == false) {
                Intent(this@SignUpAsVendor, LoginActivity::class.java).putExtra("flag",flag).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            } else {
                snackbar(it.message!!)
            }
        }
        binding.lytVisiblePasssignup.setOnClickListener {
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
          binding.lytVisibleConfirmPasssignup.setOnClickListener {
            if (isVisible2) {
                binding.edtConfirmpassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisibleConfirmPass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_hide
                        )
                    )
                    binding.edtConfirmpassword.setSelection(binding.edtConfirmpassword.text.length)
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


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_submit_sign -> {
                if (binding.etFullName.text.toString().isNullOrEmpty()) {
                    snackbar("Please enter name.")
                } else if (binding.edtMobile.text.toString().isNullOrEmpty()) {
                    snackbar("Please enter mobile number")
                } else if (binding.edtMobile.text.toString().length < 10) {
                    snackbar("Please enter valid mobile number")
                } else if (binding.edtEmail.text.toString().isNullOrEmpty()) {
                    snackbar("Please enter email")
                }else if (!CommonUtils.isValidMail(binding.edtEmail.text.toString().trim())) {
                    snackbar("Please enter valid email")
                } else if (binding.edtGstNo.text.toString().isNullOrEmpty()) {
                    snackbar("Please enter gst number.")
                } else if (binding.edtPassword.text.toString().isNullOrEmpty()) {
                    snackbar("Please enter password.")
                } else if (binding.edtPassword.text.length < 5) {
                    snackbar("Please enter valid password.")
                }  else if (!CommonUtils.isPasswordConfirmPasswordSame(binding.edtPassword.text.toString().trim(),binding.edtConfirmpassword.text.toString().trim())) {
                    snackbar("Confirm password doesn't match.")
                } else {
                    viewModel.driverRegistration(
                        binding.etFullName.text.toString(),
                        binding.ccp.selectedCountryCode.toString(),
                        binding.edtMobile.text.toString(),
                        binding.edtEmail.text.toString(),
                        binding.edtGstNo.text.toString(),
                        binding.edtPassword.text.toString(),
                        binding.referralCode.text.toString(),
                        role
                    )
                }
            }
            R.id.alreadyhave_an_account -> {
                finish()
            }
        }

    }
}