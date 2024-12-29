package com.govahanpartner.com.ui.driver.passenger

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.PassengerTripListAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.customclick.deleteVehicle
import com.govahanpartner.com.customclick.tripclick
import com.govahanpartner.com.customclick.tripdelete
import com.govahanpartner.com.databinding.ActivityPassengerTripListBinding
import com.govahanpartner.com.dialogs.DeleteTripDialog
import com.govahanpartner.com.model.TripListResponseData
import com.govahanpartner.com.ui.DriverTripDetailsActivity
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
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
        if (userPref.getRole().equals("3")){
            viewModel.driver_loadertrip_list(
                "Bearer "+ userPref.getToken().toString(),"2"
            )

        }else{
            viewModel.PassengerTriplistApi(
                "Bearer "+ userPref.getToken().toString(),
            )
        }

        binding.pulltorefresh.setOnRefreshListener {
            if (userPref.getRole().equals("3")){
                viewModel.driver_loadertrip_list(
                    "Bearer "+ userPref.getToken().toString(),"2"
                )

            }else{
                viewModel.PassengerTriplistApi(
                    "Bearer "+ userPref.getToken().toString(),
                )
            }
            binding.pulltorefresh.isRefreshing=false
        }
        viewModel.TriplistResponse.observe(this) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
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
            if (it?.status == 1) {
                if (userPref.getRole().equals("3")){
                    viewModel.driver_loadertrip_list(
                        "Bearer "+ userPref.getToken().toString(),"2"
                    )

                }else{
                    viewModel.PassengerTriplistApi(
                        "Bearer "+ userPref.getToken().toString(),
                    )
                }

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

    override fun tripclick(id: String?) {
        val intent = Intent(this, DriverTripDetailsActivity::class.java)
        intent.putExtra("_id",id)
        intent.putExtra("flag","passenger")
        startActivity(intent)
    }
}