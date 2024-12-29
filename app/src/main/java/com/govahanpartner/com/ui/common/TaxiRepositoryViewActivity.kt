package com.govahanpartner.com.ui.common

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.TruckDocumentsAdapter
import com.govahanpartner.com.adapter.TruckImagesAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityTaxiRepositoryViewBinding
import com.govahanpartner.com.model.TruckDocumentsData
import com.govahanpartner.com.model.TruckImagesData
import com.govahanpartner.com.ui.vendor.VendorsSubscriptionPlanActivity
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.TruckRepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxiRepositoryViewActivity : BaseActivity() /*, click*/ {
    private lateinit var binding : ActivityTaxiRepositoryViewBinding
    private val viewModel: TruckRepositoryViewModel by viewModels()
    var Listdata:ArrayList<TruckImagesData> = ArrayList()
    var Listdata1:ArrayList<TruckDocumentsData> = ArrayList()
    var newurl :String = ""
    var downlloadpdf :String = ""
    var url :String =""
    lateinit var adapter : TruckImagesAdapter
    lateinit var adapter1 : TruckDocumentsAdapter
    var vehicle_id=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_taxi_repository_view)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btn.setOnClickListener {
            val intent =  Intent(this, VendorsSubscriptionPlanActivity::class.java).putExtra("vehicle_id", vehicle_id).putExtra("flag1","passenger")
            startActivity(intent)
        }
        if (intent!=null){
            vehicle_id= intent.getStringExtra("vehicle_id").toString()
        }
        viewModel.truckviewResponse.observe(this) {
            if (it?.status == 1) {
                binding.etTruckownername.text=it.data.owner_name
                binding.spinnerTrucktype.text=it.data.loader_name
                binding.etVehivalnumber.text=it.data.loader_number
                binding.spinnerTyrenumber.text= it.data.tyres.toString()
                binding.spinnerYearofmodel.text=it.data.year.toString()
                binding.seat.text=it.data.seat.toString()
                if (it.data.status==1){
                    binding.btn.visibility= View.VISIBLE
                    binding.btn.text="Proceed Payment"
                }
                else{
                    binding.btn.visibility= View.GONE
                }
            } else {
                toast(it.message)
            }
        }

        viewModel.passengers_truck_repository_list_details(
            "Bearer "+ userPref.getToken().toString(),
            vehicle_id
        )
        viewModel.passengers_truck_repository_image_list(
            "Bearer "+ userPref.getToken().toString(),vehicle_id
        )
        viewModel.truckImagesResponse.observe(this) {
            if (it?.status == 1) {
                if (it.data.image3!=null || it.data.image4!=null){
                    binding.ll2.visibility=View.VISIBLE
                }
                else{
                    binding.ll2.visibility=View.GONE
                }
                if (it.data.image1==null){
                    binding.ivTruck1.visibility=View.GONE
                }else{
                    Glide.with(this).load(it.data.image1).into(binding.ivTruck1)
                }
                if (it.data.image2==null){
                    binding.ivTruck2.visibility=View.GONE
                }else{
                    Glide.with(this).load(it.data.image2).into(binding.ivTruck2)
                }
                if (it.data.image3==null){
                    binding.ivTruck3.visibility=View.GONE
                }else{
                    Glide.with(this).load(it.data.image3).into(binding.ivTruck3)
                }
                if (it.data.image4==null){
                    binding.ivTruck4.visibility=View.GONE
                }else{
                    Glide.with(this).load(it.data.image4).into(binding.ivTruck4)
                }
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
        viewModel.passengers_truck_repository_doc_list(
            "Bearer "+ userPref.getToken().toString(),vehicle_id
        )
        viewModel.truckDocumentsResponse.observe(this) {
            if (it?.status == 1) {
                Listdata1.clear()
                Listdata1.addAll(it.data)

                binding.rvDocuments.layoutManager = LinearLayoutManager(this)
                adapter1 = TruckDocumentsAdapter(this, Listdata1)
                binding.rvDocuments.adapter =adapter1
            } else {
                snackbar(it?.message!!)
            }
        }
    }


//    override fun Click(position: Int, url: String) {
//        val i = Intent(Intent.ACTION_VIEW)
//        i.data = Uri.parse(url)
//        startActivity(i)
//
//
////        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newurl))
////        startActivity(browserIntent)
//
////        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newurl)))
//        downlloadpdf = url
//        var urlsplit = downlloadpdf.split("/public")!!.toTypedArray()
//
//        var url1 = urlsplit[0]
//        var url2 = urlsplit[1]
//        newurl = url1+url2
//    }
}