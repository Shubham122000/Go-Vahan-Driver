package com.govahanpartner.com.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R

import com.govahanpartner.com.databinding.FragmentOngoingTripHistoryBinding
import com.govahanpartner.com.adapter.OngoingTripAdapter
import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
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
        viewModel.OngoinTripHistoryApi(
            "Bearer "+ userPref.getToken().toString(),
        )
        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
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
        viewModel.OngoinTripHistoryApi(
            "Bearer "+ userPref.getToken().toString(),
        )
    }

}