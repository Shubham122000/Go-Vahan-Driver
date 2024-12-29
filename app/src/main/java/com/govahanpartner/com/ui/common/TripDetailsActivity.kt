package com.govahanpartner.com.ui.common

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.CancelReasonAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityTripDetailsBinding
import com.govahanpartner.com.databinding.DialogCancelTripBinding
import com.govahanpartner.com.model.Loader_cancel_ReasonList_ResponseData
import com.govahanpartner.com.ui.vendor.BookingDetailsActivity
import com.govahanpartner.com.ui.vendor.passenger.DriverLocationActivity
import com.govahanpartner.com.utils.Canceldata
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.InvoiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

@AndroidEntryPoint
class TripDetailsActivity : BaseActivity(),Canceldata {
    private lateinit var binding : ActivityTripDetailsBinding
    private val viewModel: InvoiceViewModel by viewModels()
    lateinit var Bookingid :String
    lateinit var invoicenumber :String
    lateinit var Bookingstatus :String
    lateinit var canselreasonadapter :CancelReasonAdapter
    lateinit var cancelid : String
    lateinit var cancelreason : String
    var flag : String =""
    private  var etfromlat = 0.0
    private  var lat = 0.0
    private  var long = 0.0
    private  var etfromlong = 0.0
    private  var ettolat = 0.0
    private  var ettolong = 0.0
    var Listdata :ArrayList<Loader_cancel_ReasonList_ResponseData> = ArrayList()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details)

        if (intent!=null){
            Bookingid = intent.getStringExtra("bookingid").toString()
            flag = intent.getStringExtra("flag").toString()
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.btnRejecttrip.setOnClickListener(View.OnClickListener {
            cancelDialog()
        })
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        if (flag == "upcomingpassenger"){
            viewModel.CompletedtrippassengerdetailsAPI(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
//        ,"Book21530"
            )
        }else if(flag == "upcomingloader"){
            viewModel.CompletedtripdetailsAPI(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
//        ,"Book21530"
            )
        }




        viewModel.CompleteDriverDetailsResponse.observe(this) {
            if (it?.status == 1) {
                try {
                    try {
                        if (it.userDetails != null) {
                            if (it.userDetails?.name==null){
                                binding.tvName.text = ""
                            }else{
                                binding.tvName.text = it.userDetails?.name.toString()
                            }

                            if (it.userDetails?.email==null){
                                binding.tvEmail.text =""
                            }else{
                                binding.tvEmail.text = it.userDetails?.email.toString()
                            }

//                            binding.tvName.text = it.userDetails?.name.toString()
                            binding.tvPhone.text = it.userDetails?.mobile_number.toString()
//                            binding.tvEmail.text = it.userDetails?.email.toString()
                        }
                        if (it.data != null) {
                            binding.tvTruckname.text = it.data?.vehicle_name.toString()
                            binding.tvNumber.text = it.data?.vehicle_number.toString()
                            binding.tvType.text = it.data?.bodyname.toString()
                            binding.tvCapacity.text = it.data?.capacity.toString()
                            binding.tvWheeler.text = "${it.data?.no_of_tyres.toString()}"+" Wheelers"
                            binding.tvHead.text = "Booking id: " + it.data?.booking_id.toString()
                            binding.tvDistance.text = it.data?.distance.toString()
                            binding.tvFrom.text = it.data?.picup_location.toString()
                            binding.tvTo.text = it.data?.drop_location.toString()
                            binding.tvAmount.text = "₹" + it.data?.fare.toString()
                            binding.tvDrivername.text = it.ownerDetails?.name.toString()
                            binding.tvDriverphone.text = it.ownerDetails?.mobile.toString()
                            binding.tvLicno.text = it.ownerDetails?.licenseno.toString()
                            invoicenumber = it.data?.invoice_number.toString()
                            Bookingstatus = it.data?.booking_status.toString()
                            var picLatitude = it.data.picup_lat.toDouble()
                            var picLongitude = it.data.picup_long.toDouble()
                            var dropLatitude = it.data.drop_lat.toDouble()
                            var dropLongitude = it.data.drop_long.toDouble()
                            binding.ivNavigation.setOnClickListener {
                                openGoogleMaps(this,lat.toDouble(),long.toDouble(),picLatitude.toDouble(),picLongitude.toDouble())
                            }
                            Glide.with(this).load(it.ownerDetails?.image).placeholder(R.drawable.image_placeholder).into(binding.imgUser)
                            if (it.data.height==null){
                                binding.tvHeight.text =""
                            }else{
                                binding.tvHeight.text =it.data?.height.toString()
                            }
                            if (it.data?.payment_mode.equals("1")) {
                                binding.tvPaymentmode.text = "Cash"
                            } else if (it.data?.payment_mode.equals("2")) {
                                binding.tvPaymentmode.text = "Online"
                            } else if (it.data?.payment_mode.equals("3")) {
                                binding.tvPaymentmode.text = "Wallet"
                            }
                            etfromlat = it.data?.picup_lat!!.toDouble()
                            etfromlong = it.data?.picup_long!!.toDouble()
                            ettolat = it.data?.drop_lat!!.toDouble()
                            ettolong = it.data?.drop_long!!.toDouble()

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }
            } else {

            }
        }

        binding.btnAccepttrip.setOnClickListener(View.OnClickListener {
            if (binding.etStartRide.text.toString().isNullOrEmpty()){
                snackbar("Please enter start ride code")
            }else{
                viewModel.AcceptRideAPI(
                    "Bearer "+ userPref.getToken().toString(),
                    Bookingid,
                    binding.etStartRide.text.toString()
                )
            }
        })
        viewModel.acceptRide.observe(this) {
            if (it?.status == 1) {
                try {
                    toast(it.message)
                    var flag1=""
                    if (flag=="upcomingloader"){
                        flag1="OngoingLoader"
                    }else{
                        flag1="OngoingPassenger"
                    }

                    val intent = Intent(this, BookingDetailsActivity::class.java)
                    intent.putExtra("distance",binding.tvDistance.text.toString())
                    intent.putExtra("flag",flag1)
                    intent.putExtra("amount",binding.tvAmount.text.toString())
                    intent.putExtra("etfrom",binding.tvFrom.text.toString())
                    intent.putExtra("etto",binding.tvTo.text.toString())
                    intent.putExtra("piclat",etfromlat.toDouble())
                    intent.putExtra("piclong",etfromlong.toDouble())
                    intent.putExtra("droplat",ettolat.toDouble())
                    intent.putExtra("droplong",ettolong.toDouble())
                    intent.putExtra("bookingid",Bookingid)
                    startActivity(intent)
                    finish()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }else {
                toast(it.message)
            }
        }
    }
    private fun getLastLocation() {
        // Check for permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            // Request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // Request the last known location

        fusedLocationClient.lastLocation.addOnCompleteListener(this) { task: Task<Location?> ->
            // Handle the result safely
            val location: Location? = task.result
            if (task.isSuccessful && location != null) {
                lat = location.latitude
                long = location.longitude
//                println("Latitude: $latitude, Longitude: $longitude")
            } else {
                println("Failed to get location.")
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun idreason(reasonid: String) {
        cancelid = reasonid
//        cancelreason = reason
    }

    private fun cancelDialog() {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        val bindingDialog: DialogCancelTripBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_cancel_trip,
            null,
            false
        )

        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()


        viewModel.Loader_cancel_ReasonList_Response.observe(this) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                bindingDialog.rvReasons.layoutManager = LinearLayoutManager(this)
                canselreasonadapter = CancelReasonAdapter(this, Listdata,this)
                bindingDialog.rvReasons.adapter =canselreasonadapter

////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
//                toast(.message)
            }
        }


        bindingDialog.ivClose.setOnClickListener(View.OnClickListener {
            cDialog.dismiss()
        })
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.LoadercancelReasonAPI(
             "Bearer "+ userPref.getToken().toString(),
        )

        bindingDialog.btnCancel.setOnClickListener {
            cancelreason = bindingDialog.etFeedback.text.toString()
            if (flag=="upcomingloader"){
                viewModel.LoaderDriverTripCancelAPI(
                    "Bearer "+ userPref.getToken().toString(),
                    Bookingid,
                    cancelid,
                    cancelreason
                )

            }else{
                viewModel.passenger_driver_trip_cancelApi(
                    "Bearer "+ userPref.getToken().toString(),
                    Bookingid,
                    cancelid,
                    cancelreason
                )

            }

            viewModel.LoaderdrivertripcancelResponse.observe(this) {
                if (it?.status == 1) {
                    toast(it.message)
                    cDialog.dismiss()
                } else {
                toast(it.message)
                }
            }

        }
    }
    fun openGoogleMaps(context: Context, startLat: Double, startLng: Double, endLat: Double, endLng: Double) {
        val uri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$startLat,$startLng&destination=$endLat,$endLng&travelmode=driving")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // Handle case where Google Maps is not installed
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

}