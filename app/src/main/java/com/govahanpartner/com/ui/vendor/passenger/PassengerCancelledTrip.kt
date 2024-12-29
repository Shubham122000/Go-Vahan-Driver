package com.govahanpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R

import com.govahanpartner.com.databinding.FragmentPassengerCancelledTripBinding
import com.govahanpartner.com.adapter.CancelTripAdapter
import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PassengerCancelledTrip : BaseFragment() {
    private lateinit var binding: FragmentPassengerCancelledTripBinding
    val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter: CancelTripAdapter
    var flags :String = "Adapterforpassenger"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_passenger_cancelled_trip,
            container,
            false
        )
        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.cnacel_booking_passengers(
            "Bearer " + userPref.getToken().toString(),
        )
        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                binding.rvCancelled.layoutManager = LinearLayoutManager(requireContext())
                adapter = CancelTripAdapter(requireContext(), Listdata,flags)
                binding.rvCancelled.adapter = adapter

////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
//                toast(.message)
            }



        }
        return binding.root
    }

}