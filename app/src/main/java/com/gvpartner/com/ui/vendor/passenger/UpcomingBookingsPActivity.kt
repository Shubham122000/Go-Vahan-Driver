package com.gvpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefers.UserPref
import com.gvpartner.com.R
import com.gvpartner.com.adapter.UpcomingBookingsAdapter
import com.gvpartner.com.databinding.ActivityUpcomingBookingsPactivityBinding
import java.util.*
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.model.TripHistoryResponseData
import com.gvpartner.com.passengerviewmodel.TripHistoryPassengerViewModel
import com.gvpartner.com.utils.toast


class UpcomingBookingsPActivity : BaseActivity() {
    private lateinit var binding : ActivityUpcomingBookingsPactivityBinding
    private val viewModel: TripHistoryPassengerViewModel by viewModels()
    var Listdata:ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter : UpcomingBookingsAdapter
    var flag:String ="upcomingpassenger"
    lateinit var prefrence : UserPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_upcoming_bookings_pactivity)
        prefrence= UserPref(this)
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
//        if (userPref.getRole().equals("3")){
//        viewModel.UpComingsTripHistoryApi(
//            "Bearer "+ userPref.getToken().toString(),
//        )} else if(userPref.getRole().equals("2")){
//                viewModel.UpComingsTripHistoryApi(
//                    "Bearer "+ userPref.getToken().toString(),
//                )
//        } else{
//            viewModel.vendor_upcooming_booking_passengers("Bearer "+ userPref.getToken().toString())
//        }

        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),
                "1","2","1"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","2","1"
            )
        }

        viewModel.TripHistoryResponse.observe(this) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                binding.rvUpcomingbookings.layoutManager = LinearLayoutManager(this)
                adapter = UpcomingBookingsAdapter(this,prefrence, Listdata,flag)
                binding.rvUpcomingbookings.adapter =adapter

            } else {
                toast(it.message)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),
                "1","2","1"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","2","1"
            )
        }
    }
}