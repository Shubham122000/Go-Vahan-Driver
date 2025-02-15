package com.govahanpartner.com.ui.vendor.passenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.smarteist.autoimageslider.SliderView
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.SliderAdapter
import com.govahanpartner.com.databinding.ActivityPassengerVehicleBinding
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.model.HomeBannerData
import com.govahanpartner.com.model.HomeSliderModelCLass
import com.govahanpartner.com.ui.common.LoginActivity
import com.govahanpartner.com.ui.driver.passenger.AddTripPassengerDriver
import com.govahanpartner.com.ui.driver.passenger.PassengerTripList
import com.govahanpartner.com.ui.vendor.*
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.BannerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerVehicleActivity : BaseActivity() {
    private lateinit var binding : ActivityPassengerVehicleBinding
    lateinit var flag :String
    lateinit var adapter:SliderAdapter
    private val viewModel : BannerViewModel by viewModels()
    lateinit var sliderDataArrayList:ArrayList<HomeBannerData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_vehicle)
        sliderDataArrayList= ArrayList()
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        viewModel.homeBannerResponseModel.observe(this) {
            if (it.status == 1) {
                sliderDataArrayList.clear()
                //  listData.clear()
                for (i in 0 until it.data.size){
                    sliderDataArrayList.add(it.data[i])
                }
                binding.imageSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
                binding.imageSlider.scrollTimeInSec = 3
                binding.imageSlider.isAutoCycle = true
                binding.imageSlider.startAutoCycle()
                // adapter = SliderAdapter(requireContext(), response.data.imagesAll)
                adapter = SliderAdapter(sliderDataArrayList)
                binding.imageSlider.setSliderAdapter(adapter!!)
                /*listData.addAll(it.data)
                authorizedFranchiseAdapter = AuthorizedFranchiseAdapter(listData)
                binding.rvAuthorizedFranchise.apply {
                    adapter = authorizedFranchiseAdapter
                    layoutManager = LinearLayoutManager(this@AuthorizedFranchisesActivity)
                }*/

            } else  {
                Log.d("Response", it.toString())
                toast(it.message!!)
            }
        }
        viewModel.getDashboardBannertApi("Bearer " + userPref.user.apiToken,"2")

        binding.petrolPrice.setOnClickListener {
            val intent = Intent(this, DieselPriceActivity::class.java)
            startActivity(intent)
        }

        binding.llAdddriver.setOnClickListener {
            val intent = Intent(this, PassengerDriverListActivity::class.java)
            startActivity(intent)
        }
        if (userPref.getRole().equals("3") ){
            binding.llAdddriver.visibility = View.GONE
            binding.llRepository.visibility = View.INVISIBLE
            binding.llTruckdocument.visibility=View.INVISIBLE

        }
        else if (userPref.getRole().equals("2")) {
            binding.llAdddriver.visibility = View.GONE
            binding.llRepository.visibility = View.VISIBLE
            binding.llTruckdocument.visibility=View.VISIBLE

        }else if (userPref.getRole().equals("4")) {
            binding.llAdddriver.visibility = View.VISIBLE
            binding.llTruckdocument.visibility=View.VISIBLE
            binding.llAddtrip.visibility=View.GONE
        }
//        if (LoginActivity.role.equals("3") ){
//            binding.llAdddriver.visibility = View.INVISIBLE
//            binding.llTruckdocument.visibility=View.INVISIBLE
//
//        }
//        else if (LoginActivity.role.equals("2")) {
//            binding.llAdddriver.visibility = View.INVISIBLE
//            binding.llTruckdocument.visibility=View.VISIBLE
//
//        }else if (LoginActivity.role.equals("4")) {
//            binding.llAdddriver.visibility = View.VISIBLE
//            binding.llTruckdocument.visibility=View.VISIBLE
//        }
        binding.invoice.setOnClickListener {
            val intent = Intent(this, Loader_invoice_List::class.java)
            intent.putExtra("flag","passenger")
            startActivity(intent)
        }
            binding.llAddtrip.setOnClickListener {
//            if (userPref.getRole().equals("3")){
//                val intent = Intent(this, AddTripPassengerDriver::class.java)
//                startActivity(intent)
//            }
//            else if (userPref.getRole().equals("2")){
//                val intent = Intent(this,AddTripPActivity ::class.java)
//                startActivity(intent)
//            }
//            else if (userPref.getRole().equals("4")){
                val intent = Intent(this,AddTripPActivity ::class.java)
                startActivity(intent)
//            }
        }
        binding.passengerTripHistory.setOnClickListener {
            val intent = Intent(this, PassengerTripHistory::class.java)
            startActivity(intent)
        }
        binding.llUpcoming.setOnClickListener {
            val intent = Intent(this, UpcomingBookingsPActivity::class.java)
            startActivity(intent)
        }
        binding.llTruckdocument.setOnClickListener {
            val intent = Intent(this, AddPassengerVehicleActivity::class.java)
            startActivity(intent)
        }
        binding.llTripcosecal.setOnClickListener {
            val intent = Intent(this, FareCalculatorActivity::class.java).putExtra("from","passenger")
            startActivity(intent)
        }
        binding.llTripbook.setOnClickListener {
            val intent = Intent(this,PassengerTripList::class.java)
            startActivity(intent)
        }
        binding.llRepository.setOnClickListener {
            flag = "FromPassenger"
            val intent = Intent(this, TruckRepositoryActivity::class.java)
            intent.putExtra("FromLoader","FromPassenger")
            startActivity(intent)
        }


                /*listData.addAll(it.data)
                authorizedFranchiseAdapter = AuthorizedFranchiseAdapter(listData)
                binding.rvAuthorizedFranchise.apply {
                    adapter = authorizedFranchiseAdapter
                    layoutManager = LinearLayoutManager(this@AuthorizedFranchisesActivity)
                }*/

            }

    override fun onResume() {
        super.onResume()
    }
}



