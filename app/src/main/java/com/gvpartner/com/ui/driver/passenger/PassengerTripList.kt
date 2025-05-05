package com.gvpartner.com.ui.driver.passenger

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvpartner.com.R
import com.gvpartner.com.adapter.PassengerTripListAdapter
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.customclick.deleteVehicle
import com.gvpartner.com.customclick.tripclick
import com.gvpartner.com.customclick.tripdelete
import com.gvpartner.com.databinding.ActivityPassengerTripListBinding
import com.gvpartner.com.dialogs.DeleteTripDialog
import com.gvpartner.com.model.TripListResponseData
import com.gvpartner.com.ui.DriverTripDetailsActivity
import com.gvpartner.com.viewmodel.TripHistoryViewModel
import java.util.ArrayList

class PassengerTripList : BaseActivity() ,tripdelete,deleteVehicle,tripclick{
    lateinit var binding : ActivityPassengerTripListBinding
    lateinit var adapter : PassengerTripListAdapter
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripListResponseData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_passenger_trip_list)

        binding.ivBack.setOnClickListener {
            finish()
        }
//        if (userPref.getRole().equals("3")){
//            viewModel.driver_loadertrip_list(
//                "Bearer "+ userPref.getToken().toString(),"2"
//            )
//
//        }else{
//            viewModel.PassengerTriplistApi(
//                "Bearer "+ userPref.getToken().toString(),
//            )
//        }

        viewModel.TriplistApi(
            "Bearer "+ userPref.getToken().toString(),"2"
        )

        binding.pulltorefresh.setOnRefreshListener {
//            if (userPref.getRole().equals("3")){
//                viewModel.driver_loadertrip_list(
//                    "Bearer "+ userPref.getToken().toString(),"2"
//                )
//
//            }else{
//                viewModel.PassengerTriplistApi(
//                    "Bearer "+ userPref.getToken().toString(),
//                )
//            }
            viewModel.TriplistApi(
                "Bearer "+ userPref.getToken().toString(),"2"
            )
            binding.pulltorefresh.isRefreshing=false
        }
        viewModel.TriplistResponse.observe(this) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.let { it1 -> Listdata.addAll(it1.trips) }
                binding.rvtriplit.layoutManager = LinearLayoutManager(this)
                adapter = PassengerTripListAdapter(this, Listdata,this,this)
                binding.rvtriplit.adapter = adapter

////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
//                toast(.message)
            }
        }
        viewModel.DeleteTRipComplete.observe(this) {
            if (it?.error == false) {
//                if (userPref.getRole().equals("3")){
//                    viewModel.driver_loadertrip_list(
//                        "Bearer "+ userPref.getToken().toString(),"2"
//                    )
//
//                }else{
//                    viewModel.PassengerTriplistApi(
//                        "Bearer "+ userPref.getToken().toString(),
//                    )
//                }
                viewModel.TriplistApi(
                    "Bearer "+ userPref.getToken().toString(),"2"
                )

            } else {
            }

        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
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
//        intent.putExtra("_id",id)
//        intent.putExtra("flag","passenger")
        startActivity(intent)
    }
}