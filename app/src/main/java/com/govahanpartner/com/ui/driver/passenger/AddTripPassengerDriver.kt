package com.govahanpartner.com.ui.driver.passenger

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
import com.govahanpartner.com.databinding.ActivityAddTripPassengerDriverBinding
import com.govahanpartner.com.model.*
import com.govahanpartner.com.passengerviewmodel.AddTripPassengerViewModels
import com.govahanpartner.com.utils.toast
import java.util.*
import kotlin.collections.ArrayList

class AddTripPassengerDriver : BaseActivity() {
    private lateinit var binding : ActivityAddTripPassengerDriverBinding
    private val viewModel : AddTripPassengerViewModels by viewModels()
    var datePicker: DatePickerDialog? = null
    var driver=""
    var fuelcharge=""
    var tollcharge=""
    var driverfee=""
    var freightamount=0
    var taxadd=0.0
    var tax=0.0
    var tax2=0.0
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
    var vehicletype=""
    var loadcarry=""
    var bodytype=""
    var vehicle_number=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip_passenger_driver)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.lltextdate.setOnClickListener {
            Selectdate()
        }

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
        }
        placesClient = Places.createClient(this)
        binding.etFrom.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_FROM_REQUEST_CODE)
        }
        viewModel.AddTripResponse.observe(this) {
            if (it?.status == 1) {
                toast(it.message)
                finish()
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
//                val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java).putExtra("buysubscription","buysubscription").putExtra("flag1","passenger")
//                startActivity(intent)
            }
        }
        binding.etTo.setOnClickListener {
            placesAPiCall(AUTOCOMPLETE_TO_REQUEST_CODE)
        }
        viewModel.self_driver_passenger_trip(
            "Bearer "+userPref.getToken().toString(),
        ).observe(this) {

            if (it!!.status == 1) {
                try {
                    binding.driverassign.text = it.name.driver_name
                    binding.spinnerLoadcarring.text = "${it.data.load_caring} Ton"
                    binding.spinnerVehiclename.text = it.data.vehicle_name
                    binding.spinnerVehicletype.text = it.data.vehicle_type
                    binding.spinnerVehiclenumber.text = it.data.vehicle_number
                    binding.spinnerNooftyres.text = it.data.no_of_tyers
                    vehicletype=it.data.vehicle_type_id.toString()
                    vehicle_number= it.data.vehicle_number_id.toString()
//                    loadcarry=it.data.load_caring
                    driver=it.name.driver_id
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

        binding.btnAddthistrip.setOnClickListener {

            if (binding.etTriptask.text.toString().isEmpty()){
                toast("Please enter trip Task.")
            }else if (binding.etFrom.text.toString().isEmpty()){
                toast("Please enter from location.")
            }else if (binding.etTo.text.toString().isEmpty()){
                toast("Please enter to location.")
            }else if (binding.spinnerTimeslots.selectedItem.equals("Select Time Slots")){
                    toast("Please select time slot.")
            }
            else if (binding.etFuelcharge.text.toString().isEmpty()){
                toast("Please enter Fuel charge.")
            }
            else if (binding.etToll.text.toString().isEmpty()){
                toast("Please enter Toll tax.")
            }
           else{
                fuelcharge=binding.etFuelcharge.text.toString()
                tollcharge=binding.etToll.text.toString()
                driverfee=binding.drivercharge.text.toString()
                freightamount=fuelcharge.toInt() + tollcharge.toInt() +driverfee.toInt()
                viewModel.AddTripApi(
                    "Bearer "+userPref.getToken().toString(),
                    binding.etTriptask.text.toString(),
                    binding.etFrom.text.toString(),
                    binding.etTo.text.toString(),
                    vehicletype,
                    binding.spinnerVehiclenumber.text.toString() ,
                    binding.spinnerNooftyres.text.toString(),
                   "",
                    driver,
                    distanceString.toString(),
                    taxadd.toString(),
                    pickupLatitude.toString(),
                    pickupLongitude.toString(),
                    dropLatitude.toString(),
                    dropLongitude.toString(),
                    vehicle_number,
                    binding.tvDate.text.toString(),
                    binding.spinnerTimeslots.selectedItem.toString(),
                    binding.etFuelcharge.text.toString(),
                    binding.etToll.text.toString(),
                    binding.drivercharge.text.toString()
                )
            }
        }
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
//        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()

    }
}