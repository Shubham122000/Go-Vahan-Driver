package com.govahanpartner.com.ui.common

import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityTermsConditionsBinding
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.PrivancyPolicyViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TermsConditionsActivity : BaseActivity() {
    private val viewModel : PrivancyPolicyViewModel by viewModels()
    lateinit var binding:ActivityTermsConditionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_conditions)


        viewModel.termsAndConditions("Bearer " + userPref.user.apiToken)
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.privacyPolicyResponse.observe(this) {
            if (it.status == 1) {

                binding.termsandcondition.text = Html.fromHtml(it.data!!.description)

            } else {
                toast(it.message!!)
            }
        }




    }
}