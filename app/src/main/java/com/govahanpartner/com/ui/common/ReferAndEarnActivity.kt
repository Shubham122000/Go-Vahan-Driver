package com.govahanpartner.com.ui.common

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityReferAndEarnBinding
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReferAndEarnActivity : BaseActivity() {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding:ActivityReferAndEarnBinding
    var referalcode=""
    var link=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_refer_and_earn)
        binding.ivBack.setOnClickListener {
            finish()
        }
        viewModel.referralsApi("Bearer "+userPref.getToken().toString())
        viewModel.refernearnResponse.observe(this){
            if (it.status==1){
                referalcode=it.data.referal_code
                link=it.data.referal_link
                binding.tvRefercode.text= it.data.referal_code
            }else{
                toast(it.message)
            }
        }
        binding.btnCopy.setOnClickListener {
              val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
              val clip = ClipData.newPlainText(label.toString(), binding.tvRefercode.text)
              clipboard.setPrimaryClip(clip)
        }
        binding.btnReferNow.setOnClickListener {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app: "+link + "\n" + "Referral code:" + referalcode)
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
        }
    }
}