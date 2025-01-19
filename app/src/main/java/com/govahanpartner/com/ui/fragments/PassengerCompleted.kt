package com.govahanpartner.com.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R

import com.govahanpartner.com.databinding.FragmentPassengerCompletedBinding
import com.govahanpartner.com.adapter.CompletedTripAdapter
import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.utils.Bookingid
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PassengerCompleted : BaseFragment(), Bookingid {
    lateinit var binding: FragmentPassengerCompletedBinding
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter : CompletedTripAdapter
    var flags :String = "AdapterforPassenger"
    lateinit var Bookingid :String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_passenger_completed, container, false)
        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.CompletedTripHistoryApiPassenger(
            "Bearer "+ userPref.getToken().toString(),
        )
        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                binding.rvCompleted.layoutManager = LinearLayoutManager(requireContext())
                adapter = CompletedTripAdapter(requireContext(), Listdata,this,flags)
                binding.rvCompleted.adapter =adapter

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

    override fun getbooking(bookingid: String) {
        Bookingid = bookingid
    }

}