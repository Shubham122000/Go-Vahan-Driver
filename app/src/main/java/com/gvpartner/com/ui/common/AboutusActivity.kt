package com.gvpartner.com.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityAboutusBinding
import com.gvpartner.com.viewmodel.StaticDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutusActivity : BaseActivity() {
    private lateinit var binding : ActivityAboutusBinding
    private val viewModel: StaticDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus)

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

        viewModel.AboutUsAPI(
            "Bearer " + userPref.getToken().toString()
        )
        viewModel.AboutUSRsponse.observe(this){
            if (it.status == 1){
                binding.aboutustext.text = it.data?.description

            }
        }

    }
}