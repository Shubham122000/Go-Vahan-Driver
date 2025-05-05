package com.gvpartner.com.ui.vendor

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.SphericalUtil
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityAddTripVactivityBinding
import com.gvpartner.com.model.*
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.TypeOfTruckViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import java.util.*

@AndroidEntryPoint
class AddTripVActivity : BaseActivity() {
    private lateinit var binding : ActivityAddTripVactivityBinding
    private val viewModel : TypeOfTruckViewModel by viewModels()
    var tax=0.0
    var tax2=0.0
    var datePicker: DatePickerDialog? = null
    var tyre=""
    var truckdata : ArrayList<TypeofTruckResponseData> = ArrayList()
    var wheels : ArrayList<vehicalwheelsResponseData> = ArrayList()
    var vehicle :ArrayList<VehicleListData> = ArrayList()
    var vehiclenumber :ArrayList<VehicleNumberListData> = ArrayList()
    var Vehicletype :ArrayList<String> = ArrayList()
    var VehicleIDtype :ArrayList<String> = ArrayList()
    var vehicle_number: ArrayList<String> = ArrayList()
    var vehicle_id: ArrayList<String> = ArrayList()
    var Capacity : ArrayList<String> = ArrayList()
    var nametype :ArrayList<String> = ArrayList()
    var vehicalwheel :ArrayList<String> = ArrayList()
//    var Capacity : ArrayList<LoadCarryingData> = ArrayList()
    var Capacitytype :ArrayList<String> = ArrayList()
    var id_Capacity :ArrayList<String> = ArrayList()
    var id_type :ArrayList<String> = ArrayList()
    var id_wheels :ArrayList<String> = ArrayList()
    var Bodydata : ArrayList<BodyTypeData> = ArrayList()
    var Bodytype :ArrayList<String> = ArrayList()
    var id_body :ArrayList<String> = ArrayList()
    var tripvalue=""
    var selectedDateFormat2 = ""
    var assigneddriver :ArrayList<DriverListResponseData> = ArrayList()
    var driver :ArrayList<String> = ArrayList()
    var id_driver :ArrayList<String> = ArrayList()
    var value=""
    var selectedTruckTypeId=""
    var selectedWheelsId=""
    var selectedBodyId=""
    var selecteddriverId=""
    var selectedVehicleName=""
    var selectedVehicleNumber=""
    var vehicletype=""
    var bodytype=""
    var fuelcharge=""
    var tollcharge=""
    var driverfee=""
    var driverfee1=""
    var job: Job? = null
    var taxadd=0.0
    // Google Place API Variables
    var assigndriver=""
    var placesClient: PlacesClient? = null
    private val AUTOCOMPLETE_FROM_REQUEST_CODE = 1
    private val AUTOCOMPLETE_TO_REQUEST_CODE = 2
    var freightamount=0
    var totalamount=""
    var latLng: LatLng? = null
    var pickupLongitude = 0.0
    var pickupLatitude = 0.0
    var dropLongitude = 0.0
    var dropLatitude = 0.0
    var sourceLatLong: LatLng? = null
    var destLatLong: LatLng? = null
    var distance: Double? = null
    var  distanceString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip_vactivity)
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.lltextdate.setOnClickListener {
            Selectdate()
        }
//        driver1=binding.driverassign.selectedItem.toString()
        if (userPref.getRole().equals("2")|| userPref.getRole().equals("3")){
            binding.driverassign.visibility=View.GONE
            binding.textDriver.visibility=View.GONE
            binding.idView.visibility=View.GONE
            binding.tvdriverassign.visibility=View.GONE
            binding.driverAssignedView.visibility=View.GONE
            binding.tvdriverassign.text=userPref.getName()
//            assigndriver=userPref.getName().toString()
        }
//        else{
//            binding.driverassign.visibility=View.VISIBLE
//            binding.tvdriverassign.visibility=View.GONE
//            binding.llDrivercharge.visibility=View.VISIBLE
//
////            assigndriver=binding.driverassign.selectedItem.toString()
//        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey)
            Places.initialize(this, apiKey)
        }
        placesClient = Places.createClient(this)
        binding.etFrom.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_FROM_REQUEST_CODE)
        }
        binding.etTo.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_TO_REQUEST_CODE)
        }

        driverlist()
                binding.driverassign.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selecteddriverId=id_driver[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            binding.spinnerVehiclenumber.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedVehicleNumber = vehicle_id[p2]
//                    binding.etEnterloadcarring.text= Capacity[p2]
//                    if (binding.spinnerVehiclenumber.selectedItem.equals("SELECT")) {
//                        toast("Please select vehicle number.")
//                    } else {
//                        viewModel.get_loader_vehicleno_details(
//                            "Bearer " + userPref.getToken().toString(), selectedVehicleNumber
//                        )
//
//                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
           binding.spinnerTimeslots.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                selectedVehicleNumber=vehicle_number[p2]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

//        viewModel.VehicleDataResponse.observe(this) {
//            if (it!!.status == 1) {
//                binding.spinnerVehiclename.text=it.data[0].vehicle_name
//                binding.spinnerBodytype.text=it.data[0].body_type
//                binding.etEnterloadcarring.text=it.data[0].capacity
//                binding.spinnerNooftyres.text= it.data[0].no_of_tyers.toString()
//                vehicletype=it.data[0].vehicle_type.toString()
//                bodytype=it.data[0].body_type_id.toString()
//                tyre=it.data[0].no_tyres.toString()
//            }else{
//
//            }
//        }

            viewModel.VehicleNumberLIst(
                "Bearer "+userPref.getToken().toString(),
                "1"
            ).observe(this) {

                if (it!!.error == false) {
                    vehiclenumber.clear()
                    vehicle_number.clear()
                    it.result?.data?.let { it1 -> vehiclenumber.addAll(it1) }
                    viewModel.VehiclenumberData.value = it.result?.data

                    for (i in 0 until it.result?.data!!.size) {
                        it.result?.data?.get(i)?.vehicleNumber?.let { it1 -> vehicle_number.add(it1) }
                        vehicle_id.add(it.result?.data?.get(i)?.id.toString())
                        Capacity.add(it.result?.data?.get(i)?.capacity.toString())
                    }
                    val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        vehicle_number
                    )
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerVehiclenumber.adapter = spinnerArrayAdapter
//                    viewModel.get_loader_vehicleno_details(
//                        "Bearer " + userPref.getToken().toString(), selectedVehicleNumber
//                    )
                }
            }

                viewModel.DriverlistResponse.observe(this) {
                if (it?.error == false) {
                    assigneddriver.clear()
                    driver.clear()
                    it.result?.data?.let { it1 -> assigneddriver.addAll(it1) }
                    viewModel.DriverlistResponseData.value = it.result?.data
                    for (i in 0 until it.result?.data?.size!!) {
                        driver.add(it.result?.data!![i].name.toString())
                        id_driver.add(it.result?.data!![i].id.toString())
                    }
                    val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        driver
                    )
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.driverassign.adapter = spinnerArrayAdapter
                }
                else {
//                    snackbar(it?.message!!)
                }
            }

//            viewModel.self_driver_trip(
//                "Bearer "+userPref.getToken().toString(),
//            ).observe(this) {
//                if (it!!.status == 1) {
//                    Capacity.clear()
//                    Capacitytype.clear()
//
//                    viewModel.AddTripDriverData.value = it.data
//
////                    binding.driverassignTv.text=it.name.driver_name
////                binding.tvLoadcarring.text=it.data.l
////                    binding.tvVehiclename.text=it.data.vehicle_name
////                    binding.tvVehicletype.text=it.data.vehicle_type
////                    binding.tvVehiclenumber.text=it.data.vehicle_number
////                    binding.tvNooftyres.text=it.data.no_of_tyers
////                    binding.tvBodytype.text=it.data.body_name
//
//                }
//            }

        binding.etToll.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (binding.drivercharge.text.equals("")){
//                    binding.totalamount.text=""
//                }
            }

            override fun afterTextChanged(p0: Editable?) {
//                if (binding.drivercharge.text.equals("")){
//                    binding.totalamount.text=""
//                }else{
                    fuelcharge=binding.etFuelcharge.text.toString()
                    tollcharge=binding.etToll.text.toString()
//                    driverfee=binding.drivercharge.text.toString()

                    if (driverfee.equals("")){
                        freightamount= fuelcharge.toInt() + tollcharge.toInt()
                        tax=freightamount.toDouble()*5
                        tax2=tax/100
                        binding.tax.text="₹${tax2.toString()}"
                        taxadd=tax2+freightamount
                        binding.totalamount.text="₹${taxadd.toString()}"
                    }else{
                        freightamount=fuelcharge.toInt() + tollcharge.toInt() +driverfee.toInt()
                        tax=freightamount.toDouble()*5
                        tax2=tax/100
                        binding.tax.text="₹${tax2.toString()}"
                        taxadd=tax2+freightamount
                        binding.totalamount.text="₹${taxadd.toString()}"
                    }

//                }

            }
        })
        binding.drivercharge.addTextChangedListener(object : TextWatcher {



            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.drivercharge.text.equals("")){
                    binding.totalamount.text=""
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.drivercharge.text.equals("")){
                    binding.totalamount.text=""
                }else{
                    fuelcharge=binding.etFuelcharge.text.toString()
                    tollcharge=binding.etToll.text.toString()
                    driverfee=binding.drivercharge.text.toString()

                    if (driverfee.equals("")){
                         freightamount= fuelcharge.toInt() + tollcharge.toInt()
                         tax=freightamount.toDouble()*5
                         tax2=tax/100
                         binding.tax.text="₹${tax2.toString()}"
                         taxadd=tax2+freightamount
                         binding.totalamount.text="₹${taxadd.toString()}"
                    }else{
                        freightamount=fuelcharge.toInt() + tollcharge.toInt() +driverfee.toInt()
                        tax=freightamount.toDouble()*5
                        tax2=tax/100
                        binding.tax.text="₹${tax2.toString()}"
                        taxadd=tax2+freightamount
                        binding.totalamount.text="₹${taxadd.toString()}"
                    }

                }

            }
        })


        viewModel.AddTripResponse.observe(this) {
            if (it?.error == false) {
                toast("Trip Added Successfully")
                finish()
            } else {
//                snackbar(it?.message!!)
//                val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java).putExtra("buysubscription","buysubscription").putExtra("flag1","loader")
//                startActivity(intent)
            }
        }
        binding.btnAddthistrip.setOnClickListener {

//            if (binding.etTriptask.text.toString().isEmpty()){
//                toast("Please enter trip Task.")
//            }else if(binding.etEnterloadcarring.text.toString().isEmpty()){
//                toast("Please enter Load carring.")
//            }
            if (binding.etFrom.text.toString().isEmpty()){
                toast("Please enter from location.")
            }else if (binding.etTo.text.toString().isEmpty()){
                toast("Please enter to location.")
            }
            else if (binding.etFuelcharge.text.toString().isEmpty()){
                toast("Please enter Fuel charge.")
            }
            else if (binding.etToll.text.toString().isEmpty()){
                toast("Please enter Toll tax.")
            }
            else{

                if (userPref.getRole().equals("2")|| userPref.getRole().equals("3")){
                    assigndriver=userPref.getuserid().toString()
                }else{
                    assigndriver=selecteddriverId
                }
                if(binding.drivercharge.text.toString().equals("")){
                    driverfee1="0"
                }else{
                    driverfee1= binding.drivercharge.text.toString()

                }
//                viewModel.AddTripVendor(
//                    "Bearer "+userPref.getToken().toString(),
//                    binding.etTriptask.text.toString(),
//                    binding.etEnterloadcarring.text.toString(),
//                    binding.etFrom.text.toString(),
//                    binding.etTo.text.toString(),
//                    vehicletype,
//                    binding.spinnerVehiclenumber.selectedItem.toString(),
//                    tyre,
//                    bodytype,
//                    assigndriver,
//                    distanceString.toString(),
//                    taxadd.toString(),
//                    pickupLatitude.toString(),
//                    pickupLongitude.toString(),
//                    dropLatitude.toString(),
//                    dropLongitude.toString(),
//                    selectedVehicleNumber,
//                    binding.tvDate.text.toString(),
//                    binding.spinnerTimeslots.selectedItem.toString(),binding.etFuelcharge.text.toString(),binding.etToll.text.toString(),driverfee1,
//                )
                viewModel.AddTripApi(
                    "Bearer " + userPref.getToken().toString(),
                    binding.etFrom.text.toString(),
                    binding.etTo.text.toString(),
                    assigndriver,
                    distanceString.toString(),
                    taxadd.toString(),
                    pickupLatitude.toString(),
                    pickupLongitude.toString(),
                    dropLatitude.toString(),
                    dropLongitude.toString(),
                    selectedVehicleNumber,
                    binding.tvDate.text.toString(),
                    binding.spinnerTimeslots.selectedItem.toString(),
                    binding.etFuelcharge.text.toString(),
                    binding.etToll.text.toString(),
                    tax2.toString(),
                    binding.drivercharge.text.toString()
                )
            }
        }
    }


    fun driverlist(){
        viewModel.driverListApi(
            "Bearer "+userPref.getToken().toString(),
//            userPref.getid().toString()
        )
    }
    fun math(f: Float): Int {
        val c = (f + 0.5f).toInt()
        val n = f + 0.5f
        return if ((n - c) % 2 == 0f) f.toInt() else c
    }


    private fun placesAPiCall(requestCode: Int) {
        val fields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, requestCode)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_FROM_REQUEST_CODE) {
            when (resultCode) {     //binding.etAmount.text = it.data!!.rate
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    Log.i("TAG", "Place: " + place.name + ", " + place.id)
                    latLng = place.latLng
                    pickupLongitude = latLng!!.longitude
                    pickupLatitude = latLng!!.latitude

                    binding.etFrom.text = place.name
                    sourceLatLong = LatLng(pickupLatitude, pickupLongitude)
                    Log.e("@@pickupLatitude", pickupLatitude.toString())
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("TAG", status.statusMessage!!)
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        } else if (requestCode == AUTOCOMPLETE_TO_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    Log.i("TAG", "Place: " + place.name + ", " + place.id)
                    latLng = place.latLng
                    dropLongitude = latLng!!.longitude
                    dropLatitude = latLng!!.latitude
                    //  binding.moveFrom.text =  place.name
                    binding.etTo.text = place.name
                    destLatLong = LatLng(dropLatitude, dropLongitude)
                    distance = SphericalUtil.computeDistanceBetween(sourceLatLong, destLatLong)
                    fromMeterToKM(distance!!)
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.i("TAG", status.statusMessage!!)
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun Selectdate(){
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        cal.timeZone = TimeZone.getTimeZone("UTC")

        val datePickerDialog = DatePickerDialog(
            this,R.style.DatePickerTheme, { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
                binding.tvDate.text = simpleDateFormat.format(cal.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
    fun fromMeterToKM(m: Double) {
        Log.e("@@1", (m / 1000).toInt().toString())
        distanceString = (m / 1000).toInt().toString()
    }
}