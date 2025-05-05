package com.gvpartner.com.ui.vendor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvpartner.com.R
import com.gvpartner.com.adapter.InvoiceListAdapter
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.customclick.Click
import com.gvpartner.com.databinding.ActivityLoaderInvoiceListBinding
import com.gvpartner.com.model.InvoiceListResponseData
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.InvoiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class Loader_invoice_List : BaseActivity() ,Click{
    lateinit var binding : ActivityLoaderInvoiceListBinding
    lateinit var adapter : InvoiceListAdapter
    var flag=""
    private val viewModel: InvoiceViewModel by viewModels()
    var Listdata: ArrayList<InvoiceListResponseData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_loader_invoice_list)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_loader_invoice_list)
        if (intent!=null){
            flag= intent.getStringExtra("flag").toString()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        if (flag.equals("loader")){
            viewModel.InvoicelistAPI(
                "Bearer "+ userPref.getToken().toString(),"1"
            )

        }else{

            viewModel.InvoicelistAPI(
                "Bearer "+ userPref.getToken().toString(),"2"
            )
        }

        viewModel.Invoiceurldownload.observe(this){
            if (it.status==1){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(browserIntent)
            }else{
                toast(it.message)
            }
        }
        viewModel.InvoiceListResponse.observe(this) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                binding.rvinvoice.layoutManager = LinearLayoutManager(this)
                adapter = InvoiceListAdapter(this, Listdata,this)
                binding.rvinvoice.adapter = adapter

////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
//                toast(.message)
            }
        }

    }

    override fun click(data: InvoiceListResponseData?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data?.pdfUrl))
        startActivity(browserIntent)
//        if (flag.equals("loader")) {
//            viewModel.loader_driver_invoice_url("Bearer " + userPref.getToken().toString(), id!!, "1")
//        }
//        else{
//            viewModel.loader_driver_invoice_url("Bearer " + userPref.getToken().toString(), id!!, "2")
//        }
    }
}