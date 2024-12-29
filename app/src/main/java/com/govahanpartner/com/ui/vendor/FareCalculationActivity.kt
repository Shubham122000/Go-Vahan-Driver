package com.govahanpartner.com.ui.vendor

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityFareCalculationActivityBinding
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.network.DirectionsJSONParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.util.ArrayList
import java.util.HashMap

class FareCalculationActivity : BaseActivity(),OnMapReadyCallback{

    private lateinit var binding : ActivityFareCalculationActivityBinding
    var distance :String = ""
    var amount :String = ""
    var etFrom :String = ""
    var etTO :String = ""
    private  var etfromlat = 0.0
    private  var etfromlong = 0.0
    private  var ettolat = 0.0
    private  var ettolong = 0.0
    private var supportMapFragment: SupportMapFragment? = null
    private var client: FusedLocationProviderClient? = null
    private lateinit var mMap: GoogleMap

//    companion object{
//        internal var mMap: GoogleMap? = null
//        var mPolyline:Polyline?=null
//        var distance: String =""
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_fare_calculation_activity)
        client = LocationServices.getFusedLocationProviderClient(this)
        if (intent!=null){
            distance = intent.getStringExtra("distance").toString()
            amount = intent.getStringExtra("amount").toString()
            etFrom = intent.getStringExtra("etfrom").toString()
            etTO = intent.getStringExtra("etto").toString()
            etfromlat = intent.getDoubleExtra("piclat",0.0)
            etfromlong = intent.getDoubleExtra("piclong",0.0)
            ettolat = intent.getDoubleExtra("droplat",0.0)
            ettolong = intent.getDoubleExtra("droplong",0.0)

        }
        Log.d("TAG", "onCreate: "+etfromlat+etfromlong+ettolat+ettolong)
        binding.ivBack.setOnClickListener{
           finish()
        }
        binding.etFrom.text = etFrom
        binding.etDrop.text = etTO
        binding.tvdistance.text = distance +"Km"


        binding.tvPaymentmode.text = "₹"+addNumberValueTwoDegit(amount)
        binding.tvEstimatefare.text = "₹"+addNumberValueTwoDegit(amount)
        val ai: ApplicationInfo = this.packageManager
            .getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["api_key"]
        val apiKey = value.toString()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
//          supportMapFragment =
//            fragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
    }
    fun addNumberValueTwoDegit(totalAmount: String): String {
        val amount: Double = totalAmount.toDouble()
        val formatter = DecimalFormat("####.00")
        val formatted: String = formatter.format(amount)
        return formatted
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
         val pickupLocation = LatLng(etfromlat,etfromlong) // Example pickup location
         val dropoffLocation = LatLng(ettolat,ettolong)
        // Add markers for pickup and drop-off locations
        mMap!!.addMarker(MarkerOptions().position(pickupLocation).title("Pickup Location"))
        mMap!!.addMarker(MarkerOptions().position(dropoffLocation).title("Drop-off Location"))

        // Move camera to show both markers
        val bounds = LatLngBounds.builder().include(pickupLocation).include(dropoffLocation).build()
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }
/*
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.isTrafficEnabled = false;
        mMap!!.isIndoorEnabled = true;
        mMap!!.isBuildingsEnabled = true;
        mMap!!.uiSettings.isZoomControlsEnabled = false;
        mMap!!.uiSettings.isMyLocationButtonEnabled = true;
        mMap!!.uiSettings.isIndoorLevelPickerEnabled = true;
        mMap!!.isMyLocationEnabled = true
        // declare bounds object to fit whole route in screen
        val LatLongB = LatLngBounds.Builder()
        val location=Location("")
        location.latitude=etfromlat
        location.longitude=etfromlong
        // Add markers
        val sydneyUser = LatLng(etfromlat,etfromlong)
        val operaDriver = LatLng(ettolat,ettolong)
        val opt= BitmapFactory.Options();
        opt.inMutable = true;
        val bitmap=BitmapFactory.decodeResource(resources,R.drawable.locationicongreen,opt)
        val pinBitmap= Bitmap.createScaledBitmap(bitmap,50,100,true)
        val optDriver= BitmapFactory.Options();
        optDriver.inMutable = true;
        val bitmapDriver=BitmapFactory.decodeResource(resources,R.drawable.location_icon,opt)
        val pinDriverBitmap= Bitmap.createScaledBitmap(bitmapDriver,50,100,true)
        mMap?.addMarker(MarkerOptions().position(sydneyUser).icon(BitmapDescriptorFactory.fromBitmap(pinBitmap)).title("Pickup House"))
        mMap?.addMarker(MarkerOptions().position(operaDriver).icon(BitmapDescriptorFactory.fromBitmap(pinDriverBitmap)))
        val camPos = CameraPosition
            .builder(
                mMap!!.cameraPosition // current Camera
            ).target(sydneyUser)
            .zoom(12f)
            .tilt(25f)
            .bearing(location.bearing)
            .build()
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(camPos))
        drawRoute(sydneyUser,operaDriver)
        val urll = getDirectionURL(sydneyUser,operaDriver, getString(R.string.api_key))
        GetDirection(urll).execute()
    }
*/


  /*  private fun getDirectionURL(origin:LatLng, dest:LatLng, secret: String) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }
    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            val result =  ArrayList<List<LatLng>>()
//            try{
//                val  gson=Gson().toJson(data)
//                val jsonData=JSONObject(gson)
//                val jsArray=jsonData.optJSONArray("route")
//                for (i in 0 until jsArray.length()){
//                    val jsonObject=JSONObject()
//                    Log.d("RRSTTSTS",Gson().toJson(jsonObject))
//                }
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
            return result
        }

    }



    private fun drawRoute(mOrigin: LatLng, mDestination: LatLng) {
        // Getting URL to the Google Directions API
        // Getting URL to the Google Directions API
        val url: String = getDirectionsUrl(mOrigin, mDestination)!!

        val downloadTask = DownloadTask()

        // Start downloading json data from Google Directions API

        // Start downloading json data from Google Directions API
        downloadTask.execute(url)
    }
    private fun getDirectionsUrl(origin: LatLng, dest: LatLng): String? {

        // Origin of route
        val str_origin = "origin=" + origin.latitude.toString() + "," + origin.longitude

        // Destination of route
        val str_dest =
            "destination=" + dest.latitude.toString() + "," + dest.longitude

        // Key
        val key = "key=AIzaSyCHl8Ff_ghqPjWqlT2BXJH5BOYH1q-sw0E"

        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$key"

        // Output format
        val output = "json"

        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
    }
    *//** A class to download data from Google Directions URL  *//*
    private class DownloadTask : AsyncTask<String?, Void?, String>() {
        // Downloading data in non-ui thread
        override fun doInBackground(vararg url: String?): String? {

            // For storing data from web service
            var data = ""
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]!!)!!
                Log.d("DownloadTask", "DownloadTask : $data")
            } catch (e: java.lang.Exception) {
                Log.d("Background Task", e.toString())
            }
            return data
        }


        // Executes in UI thread, after the execution of
        // doInBackground()
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val parserTask = ParserTask()

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result)
        }
        fun downloadUrl(strUrl: String): String? {
            var data = ""
            var iStream: InputStream? = null
            var urlConnection: HttpURLConnection? = null
            try {
                val url = URL(strUrl)

                // Creating an http connection to communicate with url
                urlConnection = url.openConnection() as HttpURLConnection

                // Connecting to url
                urlConnection.connect()

                // Reading data from url
                iStream = urlConnection.getInputStream()
                val br = BufferedReader(InputStreamReader(iStream))
                val sb = StringBuffer()
                var line: String? = ""
                while (br.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                data = sb.toString()
                br.close()
            } catch (e: java.lang.Exception) {
                Log.d("Exception on download", e.toString())
            } finally {
                iStream!!.close()
                urlConnection!!.disconnect()
            }
            return data
        }
    }

    *//** A class to parse the Google Directions in JSON format  *//*
    private class ParserTask :
        AsyncTask<String?, Int?, List<List<HashMap<String, String>>>?>() {
        // Parsing the data in non-ui thread
        protected override fun doInBackground(vararg jsonData: String?): List<List<HashMap<String, String>>>? {
            val jObject: JSONObject
            var routes: List<List<HashMap<String, String>>>? = null
            try {
                jObject = JSONObject(jsonData[0]!!)

                val parser = DirectionsJSONParser()
                // Starts parsing data
                routes = parser.parse(jObject)


            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return routes
        }


        // Executes in UI thread, after the parsing process
        override fun onPostExecute(result: List<List<HashMap<String, String>>>?) {
            var points: ArrayList<LatLng?>? = null
            var lineOptions: PolylineOptions? = null
            // Traversing through all the routes
            for (i in result!!.indices) {
                points = ArrayList()
                lineOptions = PolylineOptions()

                // Fetching i-th route
                val path = result[i]

                // Fetching all the points in i-th route
                for (j in path.indices) {
                    val point = path[j]
                    val lat = point["lat"]!!.toDouble()
                    val lng = point["lng"]!!.toDouble()
                    val position = LatLng(lat, lng)
                    points.add(position)
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points)
                lineOptions.width(15f)
                lineOptions.color(Color.BLUE)
                lineOptions.geodesic(true)

            }
            if (lineOptions != null) {
                if (mPolyline != null) {
                    mPolyline!!.remove()
                    Log.d("RemovePolyLine","Remove")
                }
                mPolyline = mMap!!.addPolyline(lineOptions)
                Log.d("AddPolyLine",""+ mPolyline)
            }

        }

    }*/

    override fun onBackPressed() {
        super.onBackPressed()
       finish()
    }
}