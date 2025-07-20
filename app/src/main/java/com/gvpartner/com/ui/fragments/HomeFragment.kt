package com.gvpartner.com.ui.fragments


import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gvpartner.com.R

import com.gvpartner.com.databinding.FragmentHomeBinding
import com.gvpartner.com.databinding.UiDialogDeviceLocationBinding
import com.gvpartner.com.base.BaseFragment
import com.gvpartner.com.ui.vendor.CreateBusinessCard
import com.gvpartner.com.ui.vendor.VendorLoaderVehicleActivity
import com.gvpartner.com.ui.vendor.passenger.PassengerVehicleActivity
import com.gvpartner.com.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
//        viewModel.VisitingCardUrl("Bearer " + userPref.getToken() )
        viewModel.VisitingCardPdf("Bearer " + userPref.getToken() )
        viewModel.VisitingCardResponse.observe(viewLifecycleOwner) {
            if (it!!.status == 1) {
                url = it.url.toString()
            }
        }
        viewModel.VisitingPdfResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.status == 1 && !it.url.isNullOrEmpty()) {
                    try {
                        val urlSplit = it.url!!.split("/public")
                        if (urlSplit.size >= 2) {
                            val url1 = urlSplit[0]
                            val url2 = urlSplit[1]
                            newurl = url1 + url2
                            Log.d("PDF", "Processed PDF URL: $newurl")

                            // Load or download PDF safely on a background thread
                            lifecycleScope.launch(Dispatchers.IO) {
                                try {
                                    // Example placeholder: replace with actual PDF loading logic
//                                    downloadOrOpenPdf(newurl)
                                } catch (e: Exception) {
                                    Log.e("PDF", "Error loading PDF", e)
                                }
                            }
                        } else {
                            Log.e("PDF", "Unexpected PDF URL format: ${it.url}")
                        }
                    } catch (e: Exception) {
                        Log.e("PDF", "Error parsing PDF URL", e)
                    }
                } else {
                    Log.e("PDF", "Invalid or empty VisitingPdf URL")
                }
            }
        }

//        if(isAdded){
//            val manager : LocationManager =requireContext(). getSystemService( Context.LOCATION_SERVICE ) as LocationManager
//            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//                gpsDialog()
//            }
//        }

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