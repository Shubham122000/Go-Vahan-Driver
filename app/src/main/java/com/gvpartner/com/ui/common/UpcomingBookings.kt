package com.gvpartner.com.ui.common

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefers.UserPref
import com.gvpartner.com.R
import com.gvpartner.com.adapter.UpcomingBookingsAdapter
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityUpcomingBookingsBinding
import com.gvpartner.com.model.TripHistoryResponseData
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UpcomingBookings : BaseActivity(){
    private lateinit var binding : ActivityUpcomingBookingsBinding
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata:ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter : UpcomingBookingsAdapter
    lateinit var prefrence : UserPref
    var flag:String ="upcomingloader"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_upcoming_bookings)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upcoming_bookings)
        prefrence=UserPref(this)
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),
                "1","1","1"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","1","1"
            )
        }

        viewModel.TripHistoryResponse.observe(this) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                if(Listdata.size>0){
                    binding.rvUpcomingbookings.layoutManager = LinearLayoutManager(this)
                    adapter = UpcomingBookingsAdapter(this,prefrence, Listdata,flag)
                    binding.rvUpcomingbookings.adapter =adapter
                }
           else{
               toast("No Booking Found.") }
            }
            else
            { toast(it.message) }
            }


    }

    override fun onResume() {
        super.onResume()
        if (userPref.getRole() == "3"){
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),
                "1","1","1"
            )
        }else if(userPref.getRole() == "2"){
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),
                "0","1","1"
            )
        }
        else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),
                "0","1","1"
            )
        }
    }

}