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
import com.govahanpartner.com.passengerviewmodel.TripHistoryPassengerViewModel
import com.govahanpartner.com.utils.toast

class DriverTripDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityDriverTripDetailsBinding
    private val viewModel: TripHistoryPassengerViewModel by viewModels()
    var id=""
    var flag=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_trip_details)
        if (intent != null){
            id=intent.getStringExtra("_id").toString()
            flag=intent.getStringExtra("flag").toString()
        }
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
            viewModel.loader_trip_list_details("Bearer "+ userPref.getToken().toString(),id)
        }
        else{
            binding.llBody.visibility=View.GONE
            viewModel.passenger_trip_list_details("Bearer "+ userPref.getToken().toString(),id)
        }

        viewModel.TripDetailsResponse.observe(this) {
        if (it?.status == 1) {
            try{
                if(it.data.vehicle_no==null){
                    binding.tvNumber.text=it.data.vehicle_numbers
                }else{
                    binding.tvNumber.text=it.data.vehicle_no
                }
                if (it.data.vehicle_name==null){
                    binding.tvType.text=it.data.vehicle_type
                }
                else{
                    binding.tvType.text=it.data.vehicle_name
                }
                binding.tvDistance.text="${it.data.total_distance} KM"
                binding.tvDriver.text=it.data.name
                binding.tvFrom.text=it.data.from_trip
                binding.tvTo.text=it.data.to_trip
                binding.tvUsername.text=it.data.name
                binding.tvPhone.text=it.data.mobile_number
                binding.tvEmail.text=it.data.email
                binding.tvOwner.text=it.data.owner_name
                binding.date.text=it.data.booking_date_from
                binding.time.text=it.data.time
                binding.capacity.text=it.data.load_caring
                binding.tvTyres.text= it.data.no_of_tyers
                binding.tvBodytype.text=it.data.body_name
                binding.tvAmount.text="₹${it.data.freight_amount}"
                Glide.with(this).load(it.data.image).into(binding.tvTruckImage)

            }catch (e:Exception){
                e.printStackTrace()
            }


        } else {
            toast(it.message)
        }
    }
}

    }
