package com.govahanpartner.com.ui.vendor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityBookingDetailsBinding
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.ui.DashboardActivity
import com.govahanpartner.com.ui.common.UploadDocumentsRegardingTrip
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.InvoiceViewModel
import com.govahanpartner.com.viewmodel.TripHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingDetailsActivity : BaseActivity() {
    private lateinit var binding : ActivityBookingDetailsBinding
    private val viewModel: InvoiceViewModel by viewModels()
    lateinit var booking : TripHistoryResponseData
    lateinit var flag :String
    lateinit var invoicenumber :String
    lateinit var Bookingstatus :String
    var flag1=""
    var amount=""
    var pendingAmount=""
    private val viewModel1: TripHistoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_details)

        if (intent !=null){
//            booking = intent.getStringExtra("booking").toString()
            booking = intent.extras?.getParcelable<TripHistoryResponseData>("booking")!!
            flag = intent.getStringExtra("flag").toString()
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel1.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel1.getBankAccountsApi(
            "Bearer "+ userPref.getToken().toString(),
        )
        binding.cash.setOnClickListener {
         if (binding.cash.isChecked){
             binding.cashsubmit.visibility=View.VISIBLE
             binding.onlinesubmit.visibility=View.GONE
           }
        }
        viewModel1.getBankAccounts.observe(this) {
            if (it?.status == 1) {
                toast(it.message)
                Glide.with(this).load(it.data[0].image).placeholder(R.drawable.image_placeholder).into(binding.qrscan)
                Log.d("TAG", "onCreate: "+it.data[0].image)
                binding.upiid.text="UPI Id :  ${it.data[0].upi_id}"
            } else {
                toast(it.message)
            }
        }
        binding.online.setOnClickListener {
         if (binding.online.isChecked){
             binding.onlinesubmit.visibility=View.VISIBLE
             binding.cashsubmit.visibility=View.GONE
         }
        }
//        binding.ridecomplete.setOnClickListener {
//            if (flag == "OngoingLoader") {
//                viewModel1.RideCompletedApi(
//                    "Bearer "+ userPref.getToken().toString(),
//                    Bookingid
//                )
//
//            }else{
//                viewModel1.passengers_ride_completed(
//                    "Bearer "+ userPref.getToken().toString(),
//                    Bookingid
//                )
//            }
//
//            viewModel1.RideCompletedResponse.observe(this) {
//                if (it?.status == 1) {
//                    toast(it.message)
//                    val intent = Intent(this, DashboardActivity::class.java)
//                    startActivity(intent)
//                    finishAffinity()
//                } else {
//                    toast(it.message)
//                }
//            }
//        }

        binding.BTN.setOnClickListener {
            if (binding.POD.isChecked && binding.challan.isChecked && binding.signature.isChecked){
                flag1="1"
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",booking.id.toString()).putExtra("flag",flag)
                startActivity(intent)
            }  else if (binding.POD.isChecked&&binding.challan.isChecked) {
                flag1="1"
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",booking.id.toString()).putExtra("flag",flag)
                startActivity(intent)
            } else if (binding.POD.isChecked&&binding.signature.isChecked) {
                flag1="1"
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",booking.id.toString()).putExtra("flag",flag)
                startActivity(intent)
            }else if (binding.challan.isChecked&&binding.signature.isChecked){
                flag1="1"
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",booking.id.toString()).putExtra("flag",flag)
                startActivity(intent)
            }
            else{
                flag1="0"
                !binding.BTN.isEnabled
            }
         }

        binding.cashsubit.setOnClickListener {
            if (flag == "OngoingLoader") {
//                viewModel1.checkTripPaymentsApi(
//                    "Bearer " + userPref.getToken().toString(),pendingAmount ,
//                    booking.bookingId.toString(),
//                )
                if (userPref.getRole() == "2" || userPref.getRole() == "3") {
                    viewModel.updateBookingStatus(
                        "Bearer "+ userPref.getToken().toString(),booking.id.toString(),"","4",""
                    )
                }else{
                    viewModel.updateBookingStatus(
                        "Bearer "+ userPref.getToken().toString(),booking.id.toString(),"","4",""
                    )
                }
                viewModel.acceptRide.observe(this) {
                    if (it?.error == false) {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        toast(it.message)
                    }
                }
                } else {
                if (userPref.getRole() == "2" || userPref.getRole() == "3") {
                    viewModel.updateBookingStatus(
                        "Bearer "+ userPref.getToken().toString(),booking.id.toString(),"","4",""
                    )
                }else{
                    viewModel.updateBookingStatus(
                        "Bearer "+ userPref.getToken().toString(),booking.id.toString(),"","4",""
                    )
                }
                viewModel.acceptRide.observe(this) {
                    if (it?.error == false) {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        toast(it.message)
//                        binding.ridecomplete.visibility = View.GONE
                    }
                }
            }
        }
        binding.onlineamount.setOnClickListener {
//            if (binding.edCash.text.toString().isEmpty()){
//                amount=binding.edOnline.text.toString()
//            }else {
//                amount=binding.edCash.text.toString()
//            }
            if (flag == "OngoingLoader") {
                viewModel1.checkTripPaymentsApi(
                    "Bearer " + userPref.getToken().toString(), pendingAmount,
                    booking.bookingId.toString()
                )
                viewModel1.CheckTripPayment.observe(this) {
                    if (it?.status == 1) {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
//                        binding.ridecomplete.visibility = View.GONE
                        toast(it.message)
                    }
                }
            }else{
                viewModel1.passenger_Payments_checkApi(
                    "Bearer " + userPref.getToken().toString(), amount,
                    booking.bookingId.toString()
                )
                viewModel1.CheckTripPayment2.observe(this) {
                    if (it?.status == 1) {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        toast(it.message)
                    }
                }
            }
        }
        if (flag == "CancelLoader"){
            binding.uploaddocument.visibility=View.GONE

//            viewModel.cancel_booking_history_loader_details(
//                "Bearer "+ userPref.getToken().toString(),
//                booking.bookingId.toString()
//            )
        }
        else if (flag == "CancelPassenger"){
            binding.uploaddocument.visibility=View.GONE

//            viewModel.cancel_booking_history_passenger_details(
//                "Bearer "+ userPref.getToken().toString(),
//                booking.bookingId.toString()
//            )
        }
        else if (flag == "AdapterforPassenger"){
            binding.uploaddocument.visibility=View.GONE
//            viewModel.CompletedtrippassengerdetailsAPI(
//                "Bearer "+ userPref.getToken().toString(),
//                booking.bookingId.toString()
//            )
        }
        else if (flag == "AdapterforLoader"){
            binding.uploaddocument.visibility=View.GONE
            viewModel.CompletedtripdetailsAPI(
                "Bearer "+ userPref.getToken().toString(),
                booking.bookingId.toString()
            )
        }else if (flag == "OngoingLoader"){
            binding.uploaddocument.visibility=View.VISIBLE
//            viewModel.ongoing_booking_history_loader_details(
//                "Bearer "+ userPref.getToken().toString(),
//                booking.bookingId.toString()
//            )
        }else if (flag == "OngoingPassenger"){
            binding.uploaddocument.visibility=View.VISIBLE
//            viewModel.ongoing_booking_history_passenger_details(
//                "Bearer "+ userPref.getToken().toString(),
//                booking.bookingId.toString()
//            )
        }
//        viewModel.CompleteDriverDetailsResponse.observe(this) {
//            if (it?.status == 1) {
                try {
//                    Log.d("TAG", "onCreate: "+it.data)

//                    if (it.userDetails !=  null) {
//                        if (it.userDetails?.name==null){
//                            binding.tvName.text = ""
//                        }else{
                            binding.tvName.text = booking.tripDetails?.user?.name.toString()
//                        }

//                        if (it.userDetails?.email==null){
//                            binding.tvEmail.text =""
//                        }else{
                            binding.tvEmail.text = booking.tripDetails?.user?.email.toString()
//                        }
                        binding.tvPhone.text = booking.tripDetails?.user?.mobileNumber.toString()
//                    }
//                    if (it.data != null) {
//                        if (it.data.height==null){
//                            binding.tvHeight.text =""
//                        }else{
//                            binding.tvHeight.text =booking.tripDetails?.user?.name.toString()
//                        }
                        binding.textviewTo.text = booking.tripDetails?.toTrip
                        binding.tvCapacity.text = booking.tripDetails?.vehicle?.capacity.toString()
                        binding.tvTruckname.text = booking.tripDetails?.vehicle?.vehicleName.toString()
                        binding.tvNumber.text = booking.tripDetails?.vehicle?.vehicleNumber.toString()
                        binding.tvWheeler.text = "${booking.tripDetails?.vehicle?.wheels?.wheel.toString()}"+" Wheelers"
                        binding.tvHead.text = "Booking id: "+booking.bookingId.toString()
//                      binding.tvHeight.text = it.data.get(0).h
                        binding.tvDistance.text = booking.tripDetails?.totalDistance.toString()
                        binding.tvFrom.text = booking.tripDetails?.fromTrip.toString()
                        binding.tvAmount.text =  "₹${booking.tripDetails?.percentAmount.toString()}"
                        binding.tvPending.text =  "₹${booking.tripDetails?.remainingAmount.toString()}"
                        pendingAmount = booking.tripDetails?.remainingAmount.toString()
                        invoicenumber = booking.paymentDetails.get(0).invoice.toString()
                        Bookingstatus = booking.tripDetails?.tripStatus.toString()
                        Glide.with(this).load(booking.tripDetails?.user?.image).placeholder(R.drawable.image_placeholder).into(binding.imgUser)
                        if (Bookingstatus == "4"){
                            binding.tvStatus.text = "Completed"
                            binding.finalAmount.text = "Paid Amount"
                            binding.ivNavigation.visibility = View.GONE
                        }else if (Bookingstatus == "2"){
                            binding.tvStatus.text = "Ongoing"
                            binding.ivNavigation.visibility = View.VISIBLE
                        }else{
                            binding.tvStatus.text = "Cancelled"
                            binding.ivNavigation.visibility = View.GONE
                        }
                        binding.tvDrivername.text = booking.tripDetails?.driver?.name.toString()
                        binding.tvDriverphone.text = booking.tripDetails?.driver?.mobileNumber.toString()
                        binding.tvLicno.text = booking.tripDetails?.driver?.licenceNumber.toString()
                        binding.tvWheeler.text = "${booking.tripDetails?.vehicle?.wheels?.wheel.toString()}"+" Wheelers"
                    if (booking.paymentDetails.isNotEmpty()) {
                        if (booking.paymentDetails.get(0).paymentMode == 1) {
                            binding.tvPaymentmode.text = "Online"
                        } else if (booking.paymentDetails.get(0).paymentMode == 2) {
                            binding.tvPaymentmode.text = "Wallet"
                        }
                    }
//                        else if (booking.paymentDetails.get(0).paymentMode?.equals("3") == true){
//                            binding.tvPaymentmode.text ="Wallet"
//                        }
                        binding.tvType.text = booking.tripDetails?.vehicle?.bodyType?.name.toString()
                        var picLatitude = booking.tripDetails?.pickupLat?.toDouble()
                        var picLongitude = booking.tripDetails?.pickupLong?.toDouble()
                        var dropLatitude = booking.tripDetails?.dropupLat?.toDouble()
                        var dropLongitude = booking.tripDetails?.dropupLong?.toDouble()
                        binding.ivNavigation.setOnClickListener {
                            if (picLatitude != null) {
                                if (picLongitude != null) {
                                    if (dropLatitude != null) {
                                        if (dropLongitude != null) {
                                            openGoogleMaps(this@BookingDetailsActivity,picLatitude,picLongitude,dropLatitude,dropLongitude)
                                        }
                                    }
                                }
                            }
                        }

                        if (booking.isDocUpload == true){
                            binding.POD.isChecked = true
                            binding.challan.isChecked = true
                            binding.signature.isChecked = true
                            binding.BTN.visibility = View.GONE
                        }
//                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                toast(it.message)
//            }
//        }

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.btnViewinvoice.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, InvoiceSummaryActivity::class.java)
            intent.putExtra("invoicenumber",invoicenumber)
            startActivity(intent)

        })
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