package com.govahanpartner.com.ui.fragments


import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.govahanpartner.com.R

import com.govahanpartner.com.databinding.FragmentHomeBinding
import com.govahanpartner.com.databinding.UiDialogDeviceLocationBinding
import com.govahanpartner.com.base.BaseFragment
import com.govahanpartner.com.ui.driver.AddDrivingLicenseActivity
import com.govahanpartner.com.ui.vendor.CreateBusinessCard
import com.govahanpartner.com.ui.vendor.VendorLoaderVehicleActivity
import com.govahanpartner.com.ui.vendor.passenger.PassengerVehicleActivity
import com.govahanpartner.com.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    lateinit var binding : FragmentHomeBinding
    private val viewModel: HomeViewmodel by viewModels()
    var url :String =""
    var downlloadpdf :String = ""
    var newurl :String = ""
    var flag=false
    lateinit var mContext:Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mContext=this.requireContext()
        binding.btnCreatebusinesscard.setOnClickListener {
        if (flag==true){
            toast(mContext,"You can create Business Card only one time.")
            binding.btnCreatebusinesscard.isEnabled=false
        }
        else{
                binding.btnCreatebusinesscard.isEnabled=true
                val intent = Intent(requireContext(), CreateBusinessCard::class.java)
            intent.putExtra("flag","add")
                startActivity(intent)
            }
        }
//        binding.card.setOnClickListener {
//            binding.btnCreatebusinesscard.isEnabled=true
//            val intent = Intent(requireContext(), CreateBusinessCard::class.java)
//            intent.putExtra("flag","edit")
//            startActivity(intent)
//        }

        if (isAdded) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                    )
                }
            }
        }

        binding.llLoadervehicle.setOnClickListener {
            val intent = Intent(requireContext(), VendorLoaderVehicleActivity::class.java)
            startActivity(intent)
        }

        binding.llPassangervehicle.setOnClickListener {
            val intent = Intent(requireContext(), PassengerVehicleActivity::class.java)
            startActivity(intent)
        }

        binding.btnDownload.setOnClickListener {
            if (newurl.isNotEmpty()) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            newurl
                        )
                    )
                )
            }else{
                toast(requireContext(),"Please add card first.")
            }
        }

        binding.btnShare.setOnClickListener {

            if (newurl.isNotEmpty()) {
                val i = Intent(Intent.ACTION_SEND)
                i.setType("text/plain")
                var shareBody: String = newurl
                var shareSubject: String = "Share link:-"
                i.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                i.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(i, "Sharing using"))
            }else{
                toast(requireContext(),"Please add card first.")
            }
        }
        viewModel.progressBarVisibility.observe(viewLifecycleOwner) {
            if (it == 1) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.GetBusinessdetails("Bearer " + userPref.getToken())
        viewModel.BusinessListResponse.observe(viewLifecycleOwner) {
            if (it!!.status == 1) {

                if (it.data!=null){
                    Glide.with(requireContext()).load(it.data?.image)
                        .apply(RequestOptions.placeholderOf(R.drawable.transportlogo))
                        .apply(RequestOptions.errorOf(R.drawable.transportlogo))
                        .into(binding.ivDriver)
                    binding.tvCompanyname.text = it.data?.companyName.toString()
                    binding.tvPhone.text = it.data?.mobile.toString()
                    binding.tvEmail.text = it.data?.email.toString()
                    binding.tvAddress.text = it.data?.address.toString()
                    binding.tvName.text = it.data?.fullName.toString()
                    flag=true
                }
                else{
                    flag=false
                }
            }
        }
        viewModel.VisitingCardUrl("Bearer " + userPref.getToken() )
        viewModel.VisitingCardPdf("Bearer " + userPref.getToken() )
        viewModel.VisitingCardResponse.observe(viewLifecycleOwner) {
            if (it!!.status == 1) {
                url = it.url.toString()
            }
        }
        viewModel.VisitingPdfResponse.observe(viewLifecycleOwner) {
            if (it!!.status == 1){
                downlloadpdf = it.url.toString()
                var urlsplit = downlloadpdf.split("/public")!!.toTypedArray()
                var url1 = urlsplit[0]
                var url2 = urlsplit[1]
                newurl = url1+url2
            }
        }
        if(isAdded){
            val manager : LocationManager =requireContext(). getSystemService( Context.LOCATION_SERVICE ) as LocationManager
            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                gpsDialog()
            }
        }

//        if (userPref.getdriver_license().toString() == "null"){
//            binding.addDrivingLicence.visibility = View.VISIBLE
//        }else{
//            binding.addDrivingLicence.visibility = View.GONE
//        }
//
//            binding.addDrivingLicence.setOnClickListener {
//            if (userPref.getdriver_license().isNullOrEmpty()){
//                val intent = Intent(requireContext(), AddDrivingLicenseActivity::class.java)
//                startActivity(intent)
//            }
//        }




        return binding.root
    }
    override fun onResume() {
        super.onResume()
        viewModel.GetBusinessdetails("Bearer " + userPref.getToken() )
        viewModel.VisitingCardUrl("Bearer " + userPref.getToken() )
        viewModel.VisitingCardPdf("Bearer " + userPref.getToken() )
    }

    private fun gpsDialog() {
        val cDialog = Dialog(requireContext(), R.style.Theme_Tasker_Dialog)
        val bindingDialog: UiDialogDeviceLocationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.ui_dialog_device_location,
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
        bindingDialog.btnTurnOn.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Permission Denied")
                .setMessage("Permission is denied, Please allow permissions from App Settings.")
                .setPositiveButton("App Settings",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        cDialog.dismiss()
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                    } )
                .setNegativeButton("Cancel", null)
                .show()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if (isAdded) {
                        if ((ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) ==
                                    PackageManager.PERMISSION_GRANTED)
                        ) {
                            Toast.makeText(
                                requireContext(),
                                "Permission Granted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                return
            }
        }
    }


}