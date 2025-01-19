package com.govahanpartner.com.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.OngoingTripAdapter
import com.govahanpartner.com.adapter.PendingTripAdapter
import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.databinding.FragmentOngoingTripHistoryBinding
import com.govahanpartner.com.databinding.FragmentPendingBookingHistoryBinding
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PendingBookingHistory : BaseFragment() {
    private lateinit var binding : FragmentPendingBookingHistoryBinding
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter : PendingTripAdapter
    lateinit var mContext: Context
    var vehicleType:String=""
    var flags:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending_booking_history, container, false)
        mContext=this.requireContext()

        vehicleType = arguments?.getString("VehicleType").toString()
        vehicleType?.let {
            // Use vehicleType as needed
            if (it == "Passenger"){
                flags = "PendingPassenger"
                if (userPref.getRole() == "2" || userPref.getRole() == "3") {
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "2", "1"
                    )
                }else{
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "0", "2", "1"
                    )
                }

            }else{
                flags = "PendingLoader"
                if (userPref.getRole() == "2" || userPref.getRole() == "3") {
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "1", "1"
                    )
                }else{
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "0", "1", "1"
                    )
                }
            }
        }

        // Inflate the layout for this fragment
        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                if (Listdata.size>0){
                    binding.rvPending.layoutManager = LinearLayoutManager(requireContext())
                    adapter = PendingTripAdapter(requireContext(), Listdata,flags)
                    binding.rvPending.adapter =adapter

                }
                else{
                    toast(mContext,"No Data Found")
                }
            } else {
//                toast(.message)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        vehicleType?.let {
            // Use vehicleType as needed
            if (it == "Passenger"){
                flags = "PendingPassenger"
                if (userPref.getRole() == "2" || userPref.getRole() == "3") {
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "2", "1"
                    )
                }else{
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "0", "2", "1"
                    )
                }

            }else{
                flags = "PendingLoader"
                if (userPref.getRole() == "2" || userPref.getRole() == "3") {
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "1", "1", "1"
                    )
                }else{
                    viewModel.UpComingsTripHistoryApi(
                        "Bearer " + userPref.getToken().toString(), "0", "1", "1"
                    )
                }
            }
        }

    }


}