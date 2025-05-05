package com.gvpartner.com.ui.common

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityLoginAsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginAsActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginAsBinding
    var flag:String = ""
    var flag2:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_as)

        binding.llIndividual.setOnClickListener {
            flag2 = true
            flag = "INDIVIDUAL"
            binding.Individualyellow.visibility = View.VISIBLE
            binding.Venderyellow.visibility = View.GONE
            binding.Venderyellow1.visibility=View.GONE
        }
        binding.llVendor.setOnClickListener {
            flag2 = true
            flag = "VENDER"
            binding.Venderyellow.visibility = View.VISIBLE
            binding.Individualyellow.visibility = View.GONE
            binding.Venderyellow1.visibility=View.GONE
        }

        binding.llVendorDriver.setOnClickListener {
            flag2 = true
            flag = "VENDORDR"
            binding.Venderyellow1.visibility = View.VISIBLE
            binding.Venderyellow.visibility = View.GONE
            binding.Individualyellow.visibility = View.GONE
        }

        binding.btnContinue.setOnClickListener(View.OnClickListener {
            if (flag2 == false){
                snackbar("Please select atleast one option.")
            }else{
                val intent = Intent(this@LoginAsActivity, LoginActivity::class.java)
                intent.putExtra("flag",flag)
                startActivity(intent)
            }
        })


    }
}