package com.govahanpartner.com.ui.driver

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.TripAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.customclick.deleteVehicle
import com.govahanpartner.com.customclick.tripclick
import com.govahanpartner.com.customclick.tripdelete
import com.govahanpartner.com.databinding.ActivityTriplistBinding
import com.govahanpartner.com.dialogs.DeleteTripDialog
import com.govahanpartner.com.model.TripListResponseData
import com.govahanpartner.com.ui.DriverTripDetailsActivity
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class TriplistActivity : BaseActivity(),tripdelete, deleteVehicle,tripclick {
    private lateinit var binding : ActivityTriplistBinding
    lateinit var adapter : TripAdapter
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripListResponseData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_triplist)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.pulltorefresh.setOnRefreshListener {
            if (userPref.getRole().toString().equals("3")){
                viewModel.driver_loadertrip_list(
                    "Bearer "+ userPref.getToken().toString(),"1"
                )

            }else{
                viewModel.TriplistApi(
                    "Bearer "+ userPref.getToken().toString(),
                )
            }
            binding.pulltorefresh.isRefreshing=false
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        if (userPref.getRole().equals("3")){
            viewModel.driver_loadertrip_list(
                "Bearer "+ userPref.getToken().toString(),"1"
            )

        }else{
            viewModel.TriplistApi(
                "Bearer "+ userPref.getToken().toString(),
            )
        }

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
            if (it?.status == 1) {
                if (userPref.getRole().equals("3")){
                    viewModel.driver_loadertrip_list(
                        "Bearer "+ userPref.getToken().toString(),"1"
                    )

                }else{
                    viewModel.TriplistApi(
                        "Bearer "+ userPref.getToken().toString(),
                    )
                }

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

    override fun tripclick(id: String?) {
        val intent = Intent(this, DriverTripDetailsActivity::class.java)
        intent.putExtra("_id",id)
        intent.putExtra("flag","loader")
        startActivity(intent)
    }
}