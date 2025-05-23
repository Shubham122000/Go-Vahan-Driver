package com.gvpartner.com.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvpartner.com.R
import com.gvpartner.com.adapter.TripAdapter
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.customclick.deleteVehicle
import com.gvpartner.com.customclick.tripclick
import com.gvpartner.com.customclick.tripdelete
import com.gvpartner.com.databinding.ActivityLoderdriverViewTripBinding
import com.gvpartner.com.dialogs.DeleteTripDialog
import com.gvpartner.com.model.TripListResponseData
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class LoderdriverViewTrip : BaseActivity(), tripdelete, deleteVehicle, tripclick {
    private lateinit var binding :ActivityLoderdriverViewTripBinding
    lateinit var adapter : TripAdapter
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripListResponseData> = ArrayList()
    var id : String = ""
    var type : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_loderdriver_view_trip)
        if (intent != null){
            id = intent.getStringExtra("id").toString()
            type = intent.getStringExtra("type").toString()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.pulltorefresh.setOnRefreshListener {
            viewModel.vendor_driver_loadertrip_list(
                "Bearer "+ userPref.getToken().toString(),id,type
            )
            binding.pulltorefresh.isRefreshing=false
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.vendor_driver_loadertrip_list(
            "Bearer "+ userPref.getToken().toString(),id,type
        )
        viewModel.TriplistResponse.observe(this) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.let { it1 -> Listdata.addAll(it1.trips) }
                binding.rvtriplit.layoutManager = LinearLayoutManager(this)
                adapter = TripAdapter(this, Listdata,this,this)
                binding.rvtriplit.adapter = adapter

            } else {
                toast("No data found")
            }
        }

        viewModel.DeleteTRipComplete.observe(this) {
            if (it?.error == false) {
                viewModel.vendor_driver_loadertrip_list(
                    "Bearer "+ userPref.getToken().toString(),id,type
                )

            } else {
            }

        }
    }

    override fun tripdelete(id: Int?) {
        supportFragmentManager?.let { DeleteTripDialog(this,id).show(it, "MyCustomFragment") }

    }

    override fun deleteVehicle(id: Int?) {
        viewModel.DeleteTripHistory(
            "Bearer "+ userPref.getToken().toString(),id.toString()
        )
    }

    override fun tripclick(tripData: TripListResponseData?) {
        val intent = Intent(this, DriverTripDetailsActivity::class.java)
            .putExtra("modelDataList", tripData)
        startActivity(intent)
    }
}