package com.gvpartner.com.ui.common

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
import com.gvpartner.com.R
import com.gvpartner.com.adapter.CancelReasonAdapter
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityTripDetailsBinding
import com.gvpartner.com.databinding.DialogCancelTripBinding
import com.gvpartner.com.model.Loader_cancel_ReasonList_ResponseData
import com.gvpartner.com.ui.vendor.BookingDetailsActivity
import com.gvpartner.com.utils.Canceldata
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.InvoiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.gvpartner.com.model.TripHistoryResponseData

@AndroidEntryPoint
class TripDetailsActivity : BaseActivity(),Canceldata {
    private lateinit var binding : ActivityTripDetailsBinding
    private val viewModel: InvoiceViewModel by viewModels()
    lateinit var booking : TripHistoryResponseData
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
//            Booking = intent.getStringExtra("bookingid").toString()
            flag = intent.getStringExtra("flag").toString()
        }
        val data = intent.extras
        booking = data?.getParcelable<TripHistoryResponseData>("booking")!!
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
//        if (flag == "upcomingpassenger"){
//            viewModel.CompletedtrippassengerdetailsAPI(
//                "Bearer "+ userPref.getToken().toString(),
//                Bookingid
////        ,"Book21530"
//            )
//        }else if(flag == "upcomingloader"){
//            viewModel.CompletedtripdetailsAPI(
//                "Bearer "+ userPref.getToken().toString(),
//                Bookingid
////        ,"Book21530"
//            )
//        }




//        viewModel.CompleteDriverDetailsResponse.observe(this) {
//            if (it?.status == 1) {
//                try {
//                    try {
//                        if (it.userDetails != null) {
//                            if (it.userDetails?.name==null){
//                                binding.tvName.text = ""
//                            }else{
                                binding.tvName.text = booking.tripDetails?.user?.name
//                            }

//                            if (it.userDetails?.email==null){
//                                binding.tvEmail.text =""
//                            }else{
                                binding.tvEmail.text = booking.tripDetails?.user?.email
//                            }

//                            binding.tvName.text = it.userDetails?.name.toString()
                            binding.tvPhone.text = booking.tripDetails?.user?.mobileNumber
//                            binding.tvEmail.text = it.userDetails?.email.toString()
//                        }
//                        if (it.data != null) {
                            binding.tvTruckname.text = booking.tripDetails?.vehicle?.vehicleName.toString()
                            binding.tvNumber.text = booking.tripDetails?.vehicle?.vehicleNumber.toString()
                            binding.tvType.text = booking.tripDetails?.vehicle?.bodyType?.name.toString()
                            binding.tvCapacity.text = booking.tripDetails?.vehicle?.capacity.toString()
                            binding.tvWheeler.text = "${booking.tripDetails?.vehicle?.noOfTyres.toString()}"+" Wheelers"
                            binding.tvHead.text = "Booking id: " + booking.bookingId
                            binding.tvDistance.text = booking.tripDetails?.totalDistance.toString()
                            binding.tvFrom.text = booking.tripDetails?.fromTrip
                            binding.tvTo.text = booking.tripDetails?.toTrip
                            binding.tvAmount.text = "₹" + booking.tripDetails?.freightAmount
                            binding.tvDrivername.text = booking.tripDetails?.driver?.name.toString()
                            binding.tvDriverphone.text = booking.tripDetails?.driver?.mobileNumber.toString()
                            binding.tvLicno.text = booking.tripDetails?.driver?.licenceNumber.toString()
//                            invoicenumber = it.data?.invoice_number.toString()
                            Bookingstatus = booking.tripDetails?.tripStatus.toString()
                            var picLatitude = booking.tripDetails?.pickupLat?.toDouble()
                            var picLongitude =  booking.tripDetails?.pickupLong?.toDouble()
                            var dropLatitude = booking.tripDetails?.dropupLat?.toDouble()
                            var dropLongitude = booking.tripDetails?.dropupLong?.toDouble()
                            binding.ivNavigation.setOnClickListener {
                                picLatitude?.toDouble()?.let { it1 ->
                                    picLongitude?.toDouble()?.let { it2 ->
                                        openGoogleMaps(this,lat.toDouble(),long.toDouble(),
                                            it1, it2
                                        )
                                    }
                                }
                            }
                            Glide.with(this).load(booking.tripDetails?.driver?.profileImage).placeholder(R.drawable.image_placeholder).into(binding.imgUser)
//                            if (it.data.height==null){
//                                binding.tvHeight.text =""
//                            }else{
//                                binding.tvHeight.text =it.data?.height.toString()
//                            }
                            if (booking.paymentDetails.get(0)?.paymentMode == 1) {
                                binding.tvPaymentmode.text = "Online"
                            } else if (booking.paymentDetails.get(0)?.paymentMode == 2) {
                                binding.tvPaymentmode.text = "Wallet"
                            }
//                            else if (booking.paymentDetails.get(0)?.paymentMode == 3) {
//                                binding.tvPaymentmode.text = "Wallet"
//                            }
                            etfromlat = booking.tripDetails?.pickupLat?.toDouble()!!
                            etfromlong = booking.tripDetails?.pickupLong?.toDouble()!!
                            ettolat = booking.tripDetails?.dropupLat?.toDouble()!!
                            ettolong = booking.tripDetails?.dropupLong?.toDouble()!!

//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//
//                }catch (e:Exception){
//                    e.printStackTrace()
//                }
//            } else {
//
//            }
//        }

        binding.btnAccepttrip.setOnClickListener(View.OnClickListener {
            if (binding.etStartRide.text.toString().isNullOrEmpty()){
                snackbar("Please enter start ride code")
            }else{
                viewModel.updateBookingStatus(
                    "Bearer "+ userPref.getToken().toString(),
                    booking.id.toString(),
                    binding.etStartRide.text.toString(),
                    "2",""
                )
            }
        })
        viewModel.acceptRide.observe(this) {
            if (it?.error == false) {
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
                    intent.putExtra("bookingid",booking.id.toString())
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

            viewModel.updateBookingStatus(
                "Bearer "+ userPref.getToken().toString(),
                booking.id.toString(),
                binding.etStartRide.text.toString(),
                "3",cancelreason
            )
//            if (flag=="upcomingloader"){
//                viewModel.LoaderDriverTripCancelAPI(
//                    "Bearer "+ userPref.getToken().toString(),
//                    booking.id.toString(),
//                    cancelid,
//                    cancelreason
//                )
//
//            }else{
//                viewModel.passenger_driver_trip_cancelApi(
//                    "Bearer "+ userPref.getToken().toString(),
//                    booking.id.toString(),
//                    cancelid,
//                    cancelreason
//                )
//
//            }

//            viewModel.LoaderdrivertripcancelResponse.observe(this) {
//                if (it?.status == 1) {
//                    toast(it.message)
//                    cDialog.dismiss()
//                } else {
//                toast(it.message)
//                }
//            }

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