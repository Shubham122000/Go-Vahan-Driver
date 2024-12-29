package com.govahanpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefers.UserPref
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.TransactionAdapter
import com.govahanpartner.com.databinding.ActivityTransactionReportBinding
import java.util.*
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.model.TransactionReportResponseData
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionReportActivity : BaseActivity() {
    private lateinit var binding : ActivityTransactionReportBinding
    private val viewModel: WalletViewModel by viewModels()
    var Listdata:ArrayList<TransactionReportResponseData> = ArrayList()
    lateinit var adapter: TransactionAdapter
    lateinit var userPref1: UserPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        userPref1= UserPref(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_report)


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        viewModel.TransactionReport(
            "Bearer "+userPref.getToken().toString(),
        )
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            }
            else {
                hideProgressDialog()
            }
        }

        viewModel.TransactionReportResponse.observe(this) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                binding.rvReport.layoutManager = LinearLayoutManager(this)
                adapter = TransactionAdapter(this, Listdata,userPref1)
                binding.rvReport.adapter =adapter

            } else {
                toast(it.message)
            }
        }
    }
}