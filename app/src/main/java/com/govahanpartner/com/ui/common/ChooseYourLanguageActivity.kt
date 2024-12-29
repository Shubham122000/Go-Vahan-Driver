package com.govahanpartner.com.ui.common

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityChooseYourLanguageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseYourLanguageActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseYourLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_your_language)

            binding.llEnglish.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, LoginAsActivity::class.java)
            startActivity(intent)

        })

        binding.llHindi.setOnClickListener(View.OnClickListener {
            binding.llEnglish.setBackgroundResource(R.drawable.edt_round_corner_white_fill)
            binding.llHindi.setBackgroundResource(R.drawable.edt_round_corner_yellow)
            binding.hinditext.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.englishtext.setTextColor(Color.parseColor("#eb8900"))

        })

        binding.llEnglish.setOnClickListener(View.OnClickListener {
            binding.llHindi.setBackgroundResource(R.drawable.edt_round_corner_white_fill)
            binding.llEnglish.setBackgroundResource(R.drawable.edt_round_corner_yellow)
            binding.englishtext.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.hinditext.setTextColor(Color.parseColor("#eb8900"))
        })
        binding.btnProceed.setOnClickListener {
            val intent = Intent(this, LoginAsActivity::class.java)
            startActivity(intent)
        }
    }
}