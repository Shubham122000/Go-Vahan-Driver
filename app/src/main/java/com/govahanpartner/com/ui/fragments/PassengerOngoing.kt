package com.govahanpartner.com.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.FragmentPassengerOngoingBinding
import com.govahanpartner.com.adapter.OngoingTripAdapter

import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PassengerOngoing : BaseFragment() {
    private lateinit var binding : FragmentPassengerOngoingBinding
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter : OngoingTripAdapter
    var flags:String="OngoingPassenger"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passenger_ongoing, container, false)
        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.OngoinTripHistoryPassengerApi(
            "Bearer "+ userPref.getToken().toString(),
        )
        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                binding.rvOngoing.layoutManager = LinearLayoutManager(requireContext())
                adapter = OngoingTripAdapter(requireContext(), Listdata,flags)
                binding.rvOngoing.adapter =adapter

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