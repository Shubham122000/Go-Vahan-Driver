package com.govahanpartner.com.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide

import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityDriverTripDetailsBinding
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.model.TripListResponseData
import com.govahanpartner.com.passengerviewmodel.TripHistoryPassengerViewModel
import com.govahanpartner.com.utils.toast

class DriverTripDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityDriverTripDetailsBinding
    private val viewModel: TripHistoryPassengerViewModel by viewModels()
    var modelData: TripListResponseData?=null
    var id=""
    var flag=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_trip_details)
//        if (intent != null){
//            id=intent.getStringExtra("_id").toString()
//            flag=intent.getStringExtra("flag").toString()
//        }
        val extras = intent.extras
        if (extras != null) {
            modelData = (extras.getSerializable("modelDataList") as TripListResponseData?)
        }
//        toast(modelData.toString())
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
        if (flag.equals("loader")){
            binding.llBody.visibility=View.VISIBLE
//            viewModel.loader_trip_list_details("Bearer "+ userPref.getToken().toString(),id)
        }
        else{
            binding.llBody.visibility=View.GONE
            viewModel.passenger_trip_list_details("Bearer "+ userPref.getToken().toString(),id)
        }

//        viewModel.TripDetailsResponse.observe(this) {
//        if (it?.error == false) {
//            try{
                if(modelData?.vehicle?.vehicleNumber==null){
                    binding.tvNumber.text=modelData?.vehicle?.vehicleNumber
                }else{
                    binding.tvNumber.text=modelData?.vehicle?.vehicleNumber
                }
                if (modelData?.vehicle?.vehicleName==null){
                    binding.tvType.text=modelData?.vehicle?.vehicleName
                }
                else{
                    binding.tvType.text=modelData?.vehicle?.vehicleName
                }
                binding.tvDistance.text="${modelData?.totalDistance} KM"

                binding.tvFrom.text=modelData?.fromTrip
                binding.tvTo.text=modelData?.toTrip
                binding.tvUsername.text=modelData?.user?.name
                binding.tvDriver.text=modelData?.driver?.name
        if (modelData?.driver?.countryCode == null){
            binding.tvPhone.text= modelData?.driver?.mobileNumber
        }else{
            binding.tvPhone.text= "+ ${modelData?.driver?.countryCode} ${modelData?.driver?.mobileNumber}"
        }

                binding.tvEmail.text=modelData?.driver?.email
                binding.tvOwner.text=modelData?.user?.name
                binding.date.text=modelData?.bookingDateFrom
                binding.time.text=modelData?.time
                binding.capacity.text=modelData?.loadCaring
                binding.tvTyres.text= modelData?.vehicle?.wheels?.wheel.toString()
                binding.tvBodytype.text=modelData?.vehicle?.bodyType?.name
                binding.tvAmount.text="₹${modelData?.freightAmount}"
                Glide.with(this).load(modelData?.vehicleImage).into(binding.tvTruckImage)
//
//            }catch (e:Exception){
//                e.printStackTrace()
//            }


//        } else {
//            toast(it.message)
//        }
//    }
}

    }
