package com.gvpartner.com.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvpartner.com.R

import com.gvpartner.com.databinding.FragmentOngoingTripHistoryBinding
import com.gvpartner.com.adapter.OngoingTripAdapter
import com.gvpartner.com.base.BaseFragment
import com.gvpartner.com.model.TripHistoryResponseData
import com.gvpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OngoingTripHistoryFragment : BaseFragment() {

    private lateinit var binding : FragmentOngoingTripHistoryBinding
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata:ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter : OngoingTripAdapter
    lateinit var mContext: Context
    var flags:String="OngoingLoader"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ongoing_trip_history, container, false)
        mContext=this.requireContext()
        // Inflate the layout for this fragment
        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"1","1","2"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","1","2"
            )
        }

        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                if (Listdata.size>0){
                    binding.rvOngoing.layoutManager = LinearLayoutManager(requireContext())
                    adapter = OngoingTripAdapter(requireContext(), Listdata,flags)
                    binding.rvOngoing.adapter =adapter

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
        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"1","1","2"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","1","2"
            )
        }
    }

}