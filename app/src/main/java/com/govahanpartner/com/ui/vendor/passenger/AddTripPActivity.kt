package com.govahanpartner.com.ui.vendor.passenger

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
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityAddTripPactivityBinding
import com.govahanpartner.com.model.*
import com.govahanpartner.com.passengerviewmodel.AddTripPassengerViewModels
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.TypeOfTruckViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddTripPActivity : BaseActivity() {
    private lateinit var binding : ActivityAddTripPactivityBinding
    private val viewModel : AddTripPassengerViewModels by viewModels()
    private val viewModel1 : TypeOfTruckViewModel by viewModels()
    var tax=0.0
    var tax2=0.0
    var driverfee1=""
    var tyres=""
    var taxadd=0.0
    var datePicker: DatePickerDialog? = null
    var truckdata : ArrayList<TypeofTruckResponseData> = ArrayList()
    var wheels : ArrayList<vehicalwheelsResponseData> = ArrayList()
    var vehiclenumber :ArrayList<VehicleNumberData> = ArrayList()
    var vehicle_no: ArrayList<String> = ArrayList()
    var vehicle_id: ArrayList<String> = ArrayList()
    var nametype :ArrayList<String> = ArrayList()
    var vehicalwheel :ArrayList<String> = ArrayList()
    var Capacity : ArrayList<LoadCarryingData> = ArrayList()
    var id_type :ArrayList<String> = ArrayList()
    var id_wheels :ArrayList<String> = ArrayList()
    var Bodydata : ArrayList<BodyTypeData> = ArrayList()
    var Bodytype :ArrayList<String> = ArrayList()
    var id_body :ArrayList<String> = ArrayList()
    var assigneddriver :ArrayList<DriverListResponseData> = ArrayList()
    var driver :ArrayList<String> = ArrayList()
    var id_driver :ArrayList<String> = ArrayList()
    var id_vehicle :ArrayList<String> = ArrayList()
    var selectedTruckTypeId=""
    var selectedCapacityId=""
    var selectedWheelsId=""
    var selectedBodyId=""
    var value=""
    var selecteddriverId=""
    var vehicleId=""
    // Google Place API Variables
    var placesClient: PlacesClient? = null
    private val AUTOCOMPLETE_FROM_REQUEST_CODE = 1
    private val AUTOCOMPLETE_TO_REQUEST_CODE = 2
    var vehicle :ArrayList<VehicleListData> = ArrayList()
    var Vehicletype :ArrayList<String> = ArrayList()
    var VehicleIDtype :ArrayList<String> = ArrayList()
    var latLng: LatLng? = null
    var pickupLongitude = 0.0
    var pickupLatitude = 0.0
    var dropLongitude = 0.0
    var dropLatitude = 0.0
    var sourceLatLong: LatLng? = null
    var destLatLong: LatLng? = null
    var distance: Double? = null
    var  distanceString: String? = null
    var selectedVehicleName=""
    var tripvalue=""
    var assigndriver=""
    var vehicletype=""
    var fuelcharge=""
    var tollcharge=""
    var driverfee=""
    var freightamount=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip_pactivity)
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        if (userPref.getRole().equals("2")){
            binding.driverassign.visibility=View.GONE
            binding.tvdriverassign.visibility=View.VISIBLE
            binding.llDrivercharge.visibility=View.GONE
            binding.tvdriverassign.text=userPref.getName()
        }
        else{
            binding.llDrivercharge.visibility=View.VISIBLE
            binding.driverassign.visibility=View.VISIBLE
            binding.tvdriverassign.visibility=View.GONE
        }

        binding.lltextdate.setOnClickListener {
            Selectdate()
        }

        viewModel1.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey)
        }
        placesClient = Places.createClient(this)
        binding.etFrom.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_FROM_REQUEST_CODE)
        }
        binding.etTo.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_TO_REQUEST_CODE)
        }


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
//                vehicleId=id_vehicle[p2]
                vehicleId = id_vehicle[p2]

                if (binding.spinnerVehiclenumber.selectedItem.equals("Select")){

                }else{
                    viewModel.get_passenger_vehicleno_details("Bearer "+userPref.getToken().toString(),vehicleId)

                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        viewModel.VehicleNumberLIst(
            "Bearer "+userPref.getToken().toString(),
        ).observe(this) {

            if (it!!.status == 1) {
                vehiclenumber.clear()
                vehicle_no.clear()
                vehiclenumber.addAll(it!!.data)
                viewModel.VehiclenumberData.value = it.data
                for (i in 0 until it.data.size) {
                    vehicle_no.add(it.data[i].vehicle_no)
                    id_vehicle.add(it.data[i].id.toString())
                }
                val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    vehicle_no
                )
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerVehiclenumber.adapter = spinnerArrayAdapter
            }
        }

       viewModel.VehicleDataResponse.observe(this) {
           if (it!!.status == 1) {
               binding.spinnerVehiclename.text=it.data[0].vehicle_name
               binding.spinnerVehicletype.text=it.data[0].v_type
               tyres=it.data[0].no_tyres.toString()
               binding.spinnerNooftyres.text= it.data[0].no_of_tyers.toString()
               binding.etEnterloadcarring.text= it.data[0].seat.toString()
               vehicletype=it.data[0].vehicle_type.toString()
           }else{

           }
       }

        viewModel.driverListApi(
            "Bearer "+userPref.getToken().toString(),
            userPref.getid().toString()
        )
        viewModel.DriverlistResponse.observe(this) {
            if (it?.status == 1) {
                assigneddriver.clear()
                driver.clear()
                assigneddriver.addAll(it.data)
                viewModel.DriverlistResponseData.value = it.data
                for (i in 0 until it.data.size) {
                    driver.add(it.data[i].name.toString())
                    id_driver.add(it.data[i].id.toString())
                }
                val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    driver
                )
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.driverassign.adapter = spinnerArrayAdapter
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
        viewModel1.AddTripResponse.observe(this) {
            if (it?.error == false) {
                toast("Trip Added successfully.")
                finish()
            } else {
                snackbar(it?.message!!)
//                val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java)
//                    .putExtra("buysubscription","buysubscription")
//                    .putExtra("flag1","passenger")
//                startActivity(intent)
            }
        }
        binding.btnAddthistrip.setOnClickListener {

            if (binding.etTriptask.text.toString().isEmpty()){
                toast("Please enter trip Task.")
            }else if (binding.etFrom.text.toString().isEmpty()){
                toast("Please enter from location.")
            }else if (binding.etTo.text.toString().isEmpty()){
                toast("Please enter to location.")
            } else if (binding.spinnerTimeslots.selectedItem.equals("Select Time Slots")){
                toast("Please select time slot.")
            } else if (binding.etFuelcharge.text.toString().isEmpty()){
                toast("Please enter Fuel charge.")
            } else if (binding.etToll.text.toString().isEmpty()){
                toast("Please enter Toll tax.")
            } else{
                if (userPref.getRole().equals("2")){
                    assigndriver=userPref.getuserid().toString()
                }
                else{
                    assigndriver=selecteddriverId
                }
                if(binding.drivercharge.text.toString().equals("")){
                    driverfee1="0"
                }else{
                    driverfee1= binding.drivercharge.text.toString()
                }
                fuelcharge=binding.etFuelcharge.text.toString()
                tollcharge=binding.etToll.text.toString()
//                driverfee=binding.drivercharge.text.toString()
                freightamount=fuelcharge.toInt() + tollcharge.toInt() +driverfee1.toInt()

                viewModel1.add_passenger_vendor_trip(
                    "Bearer "+userPref.getToken().toString(),
                    binding.etTriptask.text.toString(),
                    binding.etEnterloadcarring.text.toString(),
                    binding.etFrom.text.toString(),
                    binding.etTo.text.toString(),vehicletype,
                    binding.spinnerVehiclenumber.selectedItem.toString(),
                    tyres,
                    "1",
                    assigndriver,
                    distanceString.toString(),
                    taxadd.toString(),
                    pickupLatitude.toString(),
                    pickupLongitude.toString(),
                    dropLatitude.toString(),
                    dropLongitude.toString(),
                    vehicleId,
                    binding.tvDate.text.toString(),
                    binding.spinnerTimeslots.selectedItem.toString(),
                    binding.etFuelcharge.text.toString(),binding.etToll.text.toString(),driverfee1
                )
            }
        }
        binding.etToll.addTextChangedListener(object : TextWatcher {


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
//                    totalamount=freightamount.toString()
//                    binding.totalamount.text="₹${totalamount}"
                }

            }
        })

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
    fun fromMeterToKM(m: Double) {

        Log.e("@@1", (m / 1000).toInt().toString())

        distanceString = (m / 1000).toInt().toString()
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

}

