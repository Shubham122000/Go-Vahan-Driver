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
    lateinit var Bookingid :String
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
            Bookingid = intent.getStringExtra("bookingid").toString()
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
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",Bookingid).putExtra("flag",flag)
                startActivity(intent)
            }  else if (binding.POD.isChecked&&binding.challan.isChecked) {
                flag1="1"
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",Bookingid).putExtra("flag",flag)
                startActivity(intent)
            } else if (binding.POD.isChecked&&binding.signature.isChecked) {
                flag1="1"
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",Bookingid).putExtra("flag",flag)
                startActivity(intent)
            }else if (binding.challan.isChecked&&binding.signature.isChecked){
                flag1="1"
                val intent = Intent(this, UploadDocumentsRegardingTrip::class.java).putExtra("id",Bookingid).putExtra("flag",flag)
                startActivity(intent)
            }
            else{
                flag1="0"
                !binding.BTN.isEnabled
            }
         }

        binding.cashsubit.setOnClickListener {
            if (flag == "OngoingLoader") {
                viewModel1.checkTripPaymentsApi(
                    "Bearer " + userPref.getToken().toString(),pendingAmount ,
                    Bookingid,
                )
                viewModel1.CheckTripPayment.observe(this) {
                    if (it?.status == 1) {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        toast(it.message)
                    }
                }
                } else {
                viewModel1.passenger_Payments_checkApi(
                    "Bearer " + userPref.getToken().toString(), amount,
                    Bookingid
                )
                viewModel1.CheckTripPayment2.observe(this) {
                    if (it?.status == 1) {
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
                    Bookingid
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
                    Bookingid
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

            viewModel.cancel_booking_history_loader_details(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
            )
        }
        else if (flag == "CancelPassenger"){
            binding.uploaddocument.visibility=View.GONE

            viewModel.cancel_booking_history_passenger_details(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
            )
        }
        else if (flag == "AdapterforPassenger"){
            binding.uploaddocument.visibility=View.GONE
            viewModel.CompletedtrippassengerdetailsAPI(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
            )
        }
        else if (flag == "AdapterforLoader"){
            binding.uploaddocument.visibility=View.GONE
            viewModel.CompletedtripdetailsAPI(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
            )
        }else if (flag == "OngoingLoader"){
            binding.uploaddocument.visibility=View.VISIBLE
            viewModel.ongoing_booking_history_loader_details(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
            )
        }else if (flag == "OngoingPassenger"){
            binding.uploaddocument.visibility=View.VISIBLE
            viewModel.ongoing_booking_history_passenger_details(
                "Bearer "+ userPref.getToken().toString(),
                Bookingid
            )
        }
        viewModel.CompleteDriverDetailsResponse.observe(this) {
            if (it?.status == 1) {
                try {
                    Log.d("TAG", "onCreate: "+it.data)

                    if (it.userDetails !=  null) {
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
                        binding.tvPhone.text = it.userDetails?.mobile_number.toString()
                    }
                    if (it.data != null) {
                        if (it.data.height==null){
                            binding.tvHeight.text =""
                        }else{
                            binding.tvHeight.text =it.data?.height.toString()
                        }
                        binding.textviewTo.text = it.data?.drop_location
                        binding.tvCapacity.text = it.data?.capacity.toString()
                        binding.tvTruckname.text = it.data?.vehicle_name.toString()
                        binding.tvNumber.text = it.data?.vehicle_numbers.toString()
                        binding.tvWheeler.text = "${it.data?.no_tyres.toString()}"+" Wheelers"
                        binding.tvHead.text = "Booking id: "+it.data?.booking_id.toString()
//                      binding.tvHeight.text = it.data.get(0).h
                        binding.tvDistance.text = it.data?.distance.toString()
                        binding.tvFrom.text = it.data?.picup_location.toString()
                        binding.tvAmount.text =  "₹${it.data?.fare.toString()}"
                        binding.tvPending.text =  "₹${it.data?.fare_total.toString()}"
                        pendingAmount = it.data?.fare_total.toString()
                        invoicenumber = it.data?.invoice_number.toString()
                        Bookingstatus = it.data?.booking_status.toString()
                        Glide.with(this).load(it.ownerDetails?.image).placeholder(R.drawable.image_placeholder).into(binding.imgUser)
                        if (Bookingstatus == "4"){
                            binding.tvStatus.text = "Completed"
                            binding.ivNavigation.visibility = View.GONE
                        }else if (Bookingstatus == "2"){
                            binding.tvStatus.text = "Ongoing"
                            binding.ivNavigation.visibility = View.VISIBLE
                        }else{
                            binding.tvStatus.text = "Cancelled"
                            binding.ivNavigation.visibility = View.GONE
                        }
                        binding.tvDrivername.text = it.ownerDetails?.name.toString()
                        binding.tvDriverphone.text = it.ownerDetails?.mobile.toString()
                        binding.tvLicno.text = it.ownerDetails?.licenseno.toString()
                        binding.tvWheeler.text = "${it.data?.no_of_tyres.toString()}"+" Wheelers"
                        if (it.data?.payment_mode.equals("1")){
                            binding.tvPaymentmode.text ="Cash"
                        }
                        else if (it.data?.payment_mode.equals("2")){
                            binding.tvPaymentmode.text ="Online"
                        }
                        else if (it.data?.payment_mode.equals("3")){
                            binding.tvPaymentmode.text ="Wallet"
                        }
                        binding.tvType.text = it.data?.bodyname.toString()
                        var picLatitude = it.data.picup_lat.toDouble()
                        var picLongitude = it.data.picup_long.toDouble()
                        var dropLatitude = it.data.drop_lat.toDouble()
                        var dropLongitude = it.data.drop_long.toDouble()
                        binding.ivNavigation.setOnClickListener {
                            openGoogleMaps(this@BookingDetailsActivity,picLatitude,picLongitude,dropLatitude,dropLongitude)
                        }

                        if (it.data.is_doc_upload == 1){
                            binding.POD.isChecked = true
                            binding.challan.isChecked = true
                            binding.signature.isChecked = true
                            binding.BTN.visibility = View.GONE
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
                toast(it.message)
            }
        }

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