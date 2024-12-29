package com.govahanpartner.com.ui.vendor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.govahanpartner.com.databinding.ActivityFareCalculatorBinding
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.model.TypeofTruckResponseData
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.LoaderFareViewModel

class FareCalculatorActivity : BaseActivity() {
    private lateinit var binding : ActivityFareCalculatorBinding
    private val viewModel : LoaderFareViewModel by viewModels()
    var from=""
    var selectedTruckTypeId=""
    // Google Place API Variables
    var placesClient: PlacesClient? = null
    private val AUTOCOMPLETE_FROM_REQUEST_CODE = 1
    private val AUTOCOMPLETE_TO_REQUEST_CODE = 2
    var id_type :ArrayList<String> = ArrayList()
    var latLng: LatLng? = null
    var pickupLongitude = 0.0
    var pickupLatitude = 0.0
    var dropLongitude = 0.0
    var dropLatitude = 0.0
    var sourceLatLong: LatLng? = null
    var destLatLong: LatLng? = null
    var distance: Double? = null
    var distanceString: String? = null
    var nametype :ArrayList<String> = ArrayList()
    var truckdata : ArrayList<TypeofTruckResponseData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fare_calculator)
        if (intent!=null){
            from=intent.getStringExtra("from").toString()
        }
        binding.ivBack.setOnClickListener {
            finish()
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

        binding.spinnerVehiclenumber.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedTruckTypeId=id_type[p2]
                Log.d("YearId",selectedTruckTypeId)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        if (from.equals("passenger")){
            viewModel.TypeofTruckApi(
                "Bearer "+userPref.getToken().toString(),"2"
            ).observe(this) {
                if (it!!.error == false) {
                    truckdata.clear()
                    nametype.clear()
                    it.result?.data?.let { it1 -> truckdata.addAll(it1) }
                    viewModel.truckTypeData.value = it.result?.data
                    for (i in 0 until it.result?.data?.size!!) {
                        nametype.add(it.result?.data?.get(i)?.vType.toString())
                        id_type.add(it.result?.data?.get(i)?.id.toString())
                    }
                    val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        nametype
                    )
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerVehiclenumber.adapter = spinnerArrayAdapter
                }
            }
        }else{
            viewModel.TypeofTruckApi(
                "Bearer "+userPref.getToken().toString(),"1"
            ).observe(this) {
                if (it!!.error == false) {
                    truckdata.clear()
                    nametype.clear()
                    it.result?.data?.let { it1 -> truckdata.addAll(it1) }
                    viewModel.truckTypeData.value = it.result?.data
                    for (i in 0 until it.result?.data?.size!!) {
                        nametype.add(it.result?.data?.get(i)?.vType.toString())
                        id_type.add(it.result?.data?.get(i)?.id.toString())
                    }
                    val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        nametype
                    )
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerVehiclenumber.adapter = spinnerArrayAdapter
                }
            }
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

         binding.btnCalculate.setOnClickListener {
            if (from.equals("passenger")) {
                viewModel.passenger_fare_calculatorApi(
                    "Bearer "+userPref.getToken().toString(),
                    pickupLatitude.toString(),
                    pickupLongitude.toString(),
                    dropLatitude.toString(),
                    dropLongitude.toString(),
                    binding.etMileage.text.toString(),
                    selectedTruckTypeId,binding.etOil.text.toString())
            }
            else{
                viewModel.farecalculatorApi(
                    "Bearer "+userPref.getToken().toString(),
                    pickupLatitude.toString(),
                    pickupLongitude.toString(),
                    dropLatitude.toString(),
                    dropLongitude.toString(),
                    binding.etMileage.text.toString(),
                    selectedTruckTypeId,binding.etOil.text.toString())
            }

        }
//            val intent = Intent(this, FareCalculationActivity::class.java)
//            startActivity(intent)
            viewModel.farecalculationResponse.observe(this){
            if (it.status == 1){
                val intent = Intent(this, FareCalculationActivity::class.java)
                intent.putExtra("distance",it.data?.distance)
                intent.putExtra("amount",it.data?.amount)
                intent.putExtra("etfrom",binding.etFrom.text.toString())
                intent.putExtra("etto",binding.etTo.text.toString())
                intent.putExtra("piclat",pickupLatitude.toDouble())
                intent.putExtra("piclong",pickupLongitude.toDouble())
                intent.putExtra("droplat",dropLatitude.toDouble())
                intent.putExtra("droplong",dropLongitude.toDouble())
                startActivity(intent)
                var distance = it.data?.distance
                var amount = it.data?.amount
                toast(it.message)
            }else{
                toast(it.message)
//                if (from.equals("passenger")) {
////                val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java).putExtra("buysubscription","buysubscription").putExtra("flag1","passenger")
////                startActivity(intent)
//                } else{
//                    val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java).putExtra("buysubscription","buysubscription").putExtra("flag1","loader")
//                    startActivity(intent)
//                }
            }
        }
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
        distanceString = (m / 1000).toInt().toString()
    }

    override fun onResume() {
        super.onResume()
    }
}