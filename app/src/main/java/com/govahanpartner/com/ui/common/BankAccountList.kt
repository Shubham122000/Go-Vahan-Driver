package com.govahanpartner.com.ui.common

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.BankAccountAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.customclick.bankclick
import com.govahanpartner.com.databinding.ActivityBankAccountListBinding
import com.govahanpartner.com.model.BankAccountListData
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.WalletViewModel
import java.util.ArrayList

class BankAccountList : BaseActivity() , bankclick {
    private lateinit var binding: ActivityBankAccountListBinding
    private val viewModel: WalletViewModel by viewModels()
    var Listdata: ArrayList<BankAccountListData> = ArrayList()
    lateinit var adapter: BankAccountAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_account_list)
        viewModel.bank_account_list(
            "Bearer " + userPref.getToken().toString()
        )

        binding.ivBack.setOnClickListener {
         finish()
        }
        binding.addbank.setOnClickListener {
            val intent = Intent(this, AddAccountActivity::class.java)
            startActivity(intent)
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.bank_account_listResponse.observe(this)
        {
            if (it.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                binding.rvList.layoutManager = LinearLayoutManager(this)
                adapter = BankAccountAdapter(this, Listdata, this)
                binding.rvList.adapter = adapter

            } else {
                toast(it.message!!)
            }
        }

        viewModel.selectbankaccount.observe(this)
        {
            if (it.status == 1) {
             toast("Bank selected successfully.")
             finish()
            } else {
                toast(it.message!!)
            }
        }
    }


    override fun bankclick(id: Int) {
        viewModel.bank_account_list_id(
            "Bearer " + userPref.getToken().toString(),id.toString()
        )
    }
}