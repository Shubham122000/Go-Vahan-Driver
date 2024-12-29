package com.govahanpartner.com.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.FragmentCancelledBookingHistoryBinding
import com.govahanpartner.com.adapter.CancelTripAdapter
import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.viewmodel.TripHistoryViewModel

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

        viewModel.cancel_booking_history_loader(
            "Bearer " + userPref.getToken().toString(),
        )
        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
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
        viewModel.cancel_booking_history_loader(
            "Bearer " + userPref.getToken().toString(),
        )
    }
}