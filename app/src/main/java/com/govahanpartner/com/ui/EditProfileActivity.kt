package com.govahanpartner.com.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityEditProfileBinding
import com.govahanpartner.com.base.BaseActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : BaseActivity() {
    private lateinit var binding : ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)

    }
}