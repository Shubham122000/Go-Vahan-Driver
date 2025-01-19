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

import com.govahanpartner.com.databinding.FragmentCompletedTripHistoryBinding
import com.govahanpartner.com.adapter.CompletedTripAdapter
import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.utils.Bookingid
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CompletedTripHistoryFragment : BaseFragment(), Bookingid {
    private lateinit var binding : FragmentCompletedTripHistoryBinding
    private val viewModel: TripHistoryViewModel by viewModels()
    var Listdata:ArrayList <TripHistoryResponseData> = ArrayList()
    lateinit var adapter : CompletedTripAdapter
    var flags :String = "AdapterforLoader"
    lateinit var Bookingid :String
    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed_trip_history, container, false)

        mContext=this.requireContext()

        viewModel.progressBarStatus.observe(viewLifecycleOwner) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"1","1","4"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","1","4"
            )
        }
//        viewModel.UpComingsTripHistoryApi(
//            "Bearer "+ userPref.getToken().toString(),"0","1","4"
//        )
        viewModel.TripHistoryResponse.observe(viewLifecycleOwner) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                if (Listdata.size>0){
                    binding.rvCompleted.layoutManager = LinearLayoutManager(requireContext())
                    adapter = CompletedTripAdapter(requireContext(),Listdata,this,flags)
                    binding.rvCompleted.adapter =adapter
                }
                else{
                    toast(mContext,"No data Found")
                }

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

    override fun onResume() {
        super.onResume()
        if (userPref.getRole() == "2" || userPref.getRole() == "3") {
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"1","1","4"
            )
        }else{
            viewModel.UpComingsTripHistoryApi(
                "Bearer "+ userPref.getToken().toString(),"0","1","4"
            )
        }
    }

}