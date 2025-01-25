package com.govahanpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityDriverDetailBinding
import com.govahanpartner.com.ui.LoderdriverViewTrip
import com.govahanpartner.com.ui.vendor.passenger.DriverProfileUpdate
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.AddDriverViewModel

class DriverDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityDriverDetailBinding
    var id : String = ""
    var driver_id : String = ""
    var user_type=""
    var type=""
    private val viewModel: AddDriverViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_detail)
        if (intent != null){
            id = intent.getIntExtra("id",0).toString()
            type=intent.getStringExtra("type").toString()
        }
        binding.profileupdatedriver.setOnClickListener {
            var intent = Intent(this,DriverProfileUpdate::class.java)
                .putExtra("driverid",driver_id)
            startActivity(intent)
        }
        viewModel.DriverProfileAPI(
            "Bearer "+userPref.getToken().toString(),
            id
        )
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.DriverProfileResponse.observe(this){
            if (it.error == false){
                driver_id = it.data?.id.toString()
                binding.tvUsername.text = it.data?.email.toString()
                binding.tvExperience.text = it.data?.experience.toString()
                binding.tvPhone.text = it.data?.mobileNumber.toString()
                binding.tvLicno.text = it.data?.licenceNumber.toString()
                binding.name.text=it.data?.name
                userPref.setusertype1( it.data?.user_type.toString())

                Glide.with(this).load(it.data?.profileImage).placeholder(R.drawable.profile).into(binding.ivDriver).toString()
            }else{
                toast(it.message)
            }
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.btnView.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoderdriverViewTrip::class.java)
            intent.putExtra("id",id)
            intent.putExtra("type",type)
          startActivity(intent)
        })
    }
}