package com.gvpartner.com.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvpartner.com.R
import com.gvpartner.com.databinding.FragmentCancelledBookingHistoryBinding
import com.gvpartner.com.adapter.CancelTripAdapter
import com.gvpartner.com.base.BaseFragment
import com.gvpartner.com.model.TripHistoryResponseData
import com.gvpartner.com.viewmodel.TripHistoryViewModel

import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class CancelledBookingHistory : BaseFragment() {
    private lateinit var binding: FragmentCancelledBookingHistoryBinding
    val viewModel: TripHistoryViewModel by viewModels()
    var Listdata: ArrayList<TripHistoryResponseData> = ArrayList()
    lateinit var adapter: CancelTripAdapter
    lateinit var mContext: Context
    var flags :String = "AdapterforLoader"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cancelled_booking_history,
            container,
            false
        )
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
                "Bearer "+ userPref.getToken().toString(),"1","1","3"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","1","3"
            )
        }

        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                if (Listdata.size > 0){
                    binding.rvCancelled.layoutManager = LinearLayoutManager(requireContext())
                    adapter = CancelTripAdapter(requireContext(), Listdata,flags)
                    binding.rvCancelled.adapter = adapter
                }
                else{
                toast(mContext,"No data Found")
                }
////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
                it.message?.let { it1 -> toast(mContext, it1) }
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"1","1","3"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","1","3"
            )
        }
    }
}