package com.govahanpartner.com.ui.vendor.passenger

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityAddPassengerVehicleBinding
import com.govahanpartner.com.model.*
import com.govahanpartner.com.passengerviewmodel.AddTripPassengerViewModels
import com.govahanpartner.com.permission.RequestPermission
import com.govahanpartner.com.utils.toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddPassengerVehicleActivity : BaseActivity() {

    private lateinit var binding: ActivityAddPassengerVehicleBinding
    private val viewModel: AddTripPassengerViewModels by viewModels()
    var truckdata: ArrayList<TypeofTruckResponseData> = ArrayList()
    var wheels: ArrayList<vehicalwheelsResponseData> = ArrayList()
    var colordata: ArrayList<ColorResponseData> = ArrayList()
    var yeardata: ArrayList<YearResponseData> = ArrayList()
    var seatdata: ArrayList<SeatResponseData> = ArrayList()
    var nametype: ArrayList<String> = ArrayList()
    var Bodytype: ArrayList<String> = ArrayList()
    var vehicalwheel: ArrayList<String> = ArrayList()
    var colors: ArrayList<String> = ArrayList()
    var year: ArrayList<String> = ArrayList()
    var seat: ArrayList<String> = ArrayList()
    var seat_id: ArrayList<String> = ArrayList()
    var id_type: ArrayList<String> = ArrayList()
    var datePicker: DatePickerDialog? = null
    var id_wheels: ArrayList<String> = ArrayList()
    var id_color: ArrayList<String> = ArrayList()
    var id_year: ArrayList<String> = ArrayList()
    val CAMERA_PERM_CODE_ID_Front = 101
    var imageFile: File? = null
    var imagePath = ""
    var photoURI: Uri? = null
    lateinit var imageprats: MultipartBody.Part
    private val GALLERY_ID_FRONT = 1
    private var CAMERA_ID_FRONT: Int = 2
    lateinit var image: Uri
    var flag: String = ""
    var flagforpdf: String = ""

    //    private lateinit var dialog: Dialog
    var imagetruck1: MultipartBody.Part? = null
    var imagetruck2: MultipartBody.Part? = null
    var imagetruck3: MultipartBody.Part? = null
    var imagetruck4: MultipartBody.Part? = null

    val CAMERA_PERM_CODE_ID_BACK = 102

    //    var imageFile: File? = null
//    var imagePath = ""
//    var photoURI: Uri? = null
//    lateinit var imageparts: MultipartBody.Part
    private val GALLERY_ID_BACK = 3
    private var CAMERA_ID_BACK: Int = 4
//    lateinit var image: Uri
//    var requestImage: String = ""

    val CAMERA_PERM_CODE_LICENSE_FRONT = 103
    private val GALLERY_LICENSE_FRONT = 5
    private var CAMERA_LICENSE_FRONT: Int = 6

    val CAMERA_PERM_CODE_LICENSE_BACK = 104
    private val GALLERY_LICENSE_BACK = 7
    private var CAMERA_LICENSE_BACK: Int = 8
    var selectedTruckTypeId = ""
    var selectedBodyId = ""
    var selectedHightId = ""
    var selectedWheelsId = ""
    var selectedColorId = ""
    var selectedYearId = ""
    var vehicleid: String = ""
    var Capacity: ArrayList<LoadCarryingData> = ArrayList()
    var Capacitytype: ArrayList<String> = ArrayList()
    var id_Capacity: ArrayList<String> = ArrayList()
    var selectedCapacityId = ""
    var selectedSeatId = ""

    //For Pdf Upload
    var pdfFile: MultipartBody.Part? = null
    var pdfFile1: MultipartBody.Part? = null
    var pdfFile2: MultipartBody.Part? = null
    var pdfFile3: MultipartBody.Part? = null
    var pdfFile4: MultipartBody.Part? = null
    var pdfFile5: MultipartBody.Part? = null
    private val pickPdf = 9
    private val pickPdf1 = 10
    private val pickPdf2 = 11
    private val pickPdf3 = 12
    private val pickPdf4 = 13
    private val pickPdf5 = 14
    var datepickerflag = ""
    val FILE_BROWSER_CACHE_DIR = "doc"

    private var file: File? = null
    var uri: Uri? = null
    private var mUri: Uri? = null
    private var requestCodeForLegacy: Int? = null
    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<Intent>
    val imageParts = ArrayList<MultipartBody.Part>()
    val docParts = ArrayList<MultipartBody.Part>()
    val docNames = ArrayList<String>()
    val docexpiry = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_passenger_vehicle)
        binding.etTruckownername.setText(userPref.getName().toString())


        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
//        binding.btnSubmit.setOnClickListener {
//            val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java)
//            startActivity(intent)
//        }

        pickSingleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                Log.d("TAG", "@@onActivityResult:.....")
                if (it.resultCode != Activity.RESULT_OK) {
                    Toast.makeText(this, "Failed picking media.", Toast.LENGTH_SHORT).show()
                } else {
                    val uri = it.data?.data
//                    Glide.with(this).load(uri).placeholder(R.drawable.profile).into(binding.ivTruck1).toString()
                    val path = getPathFromURI(uri)

                    if (path != null) {
                        file = File(path)
                        //   images.add(imageFile!!.absolutePath.toString())
                    }



//                    flag = true
                    if (requestCodeForLegacy == GALLERY_ID_FRONT){
                        Glide.with(this).load(file).into(binding.ivTruck1)
                        imagetruck1 = MultipartBody.Part.createFormData(
                            "image_1",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                        imageParts.add(imagetruck1!!)
                    }else if (requestCodeForLegacy == GALLERY_ID_BACK){
                        Glide.with(this).load(file).into(binding.ivTruck2)
                        imagetruck2 = MultipartBody.Part.createFormData(
                            "image_2",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                        imageParts.add(imagetruck2!!)
                    }else if (requestCodeForLegacy == GALLERY_LICENSE_FRONT){
                        Glide.with(this).load(file).into(binding.ivTruck3)
                        imagetruck3 = MultipartBody.Part.createFormData(
                            "image_3",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                        imageParts.add(imagetruck3!!)
                    }else if (requestCodeForLegacy == GALLERY_LICENSE_BACK){
                        Glide.with(this).load(file).into(binding.ivTruck4)
                        imagetruck4 = MultipartBody.Part.createFormData(
                            "image_4",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                        imageParts.add(imagetruck4!!)
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
        binding.spinnerSeat.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedSeatId = seat_id[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
//        binding.llRcbook.setOnClickListener {
//            if (binding.tvRcbook.text.equals("")) {
//                toast("Please select Rcbook")
//            } else if (binding.tvDate.equals("")) {
//                toast("Please select Expiry Date")
//            } else if (vehicleid.equals("")) {
//                toast("Fill vehicle detail first.")
//            } else {
//                viewModel.PassengerdocAPI(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid,
//                    "Rc-book", binding.tvDate.text.toString(),
//                    pdfFile
//                )
//            }
//
//        }
//
//
//
//        binding.llPollutiondoc.setOnClickListener {
//            if (binding.tvPollutiondoc.text.equals("")) {
//                toast("Please select Pollution-Document.")
//            } else if (binding.date2.equals("")) {
//                toast("Please select Expiry Date")
//            } else if (vehicleid.equals("")) {
//                toast("Fill vehicle detail first.")
//            } else {
//                viewModel.PassengerdocAPI(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid,
//                    "Pollution-Document", binding.date2.text.toString(),
//                    pdfFile2
//                )
//            }
//        }
//        binding.lyFitnessdoc.setOnClickListener {
//            if (binding.tvFitnessdoc.text.equals("")) {
//                toast("Please select Pollution-Document.")
//            } else if (binding.date3.equals("")) {
//                toast("Please select Expiry Date")
//            } else if (vehicleid.equals("")) {
//                toast("Fill vehicle detail first.")
//            } else {
//                viewModel.PassengerdocAPI(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid,
//                    "Fitness-Papers", binding.date3.text.toString(),
//                    pdfFile3
//                )
//            }
//        }
//        binding.lyInsurancedoc.setOnClickListener {
//            if (binding.tvInsurancedoc.text.equals("")) {
//                toast("Please select Insurance-Document.")
//            } else if (binding.date1.equals("")) {
//                toast("Please select Expiry Date")
//            } else if (vehicleid.equals("")) {
//                toast("Fill vehicle detail first.")
//            } else {
//                viewModel.PassengerdocAPI(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid,
//                    "Insurance-Document", binding.date1.text.toString(),
//                    pdfFile1
//                )
//            }
//        }
//        binding.lyRtodoc.setOnClickListener {
//            if (binding.tvRtodoc.text.equals("")) {
//                toast("Please select RTO-Document.")
//            } else if (binding.date4.equals("")) {
//                toast("Please select Expiry Date")
//            } else if (vehicleid.equals("")) {
//                toast("Fill vehicle detail first.")
//            } else {
//                viewModel.PassengerdocAPI(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid,
//                    "RTO Documents", binding.date4.text.toString(),
//                    pdfFile4
//                )
//            }
//        }
//
//
//        binding.lyOther.setOnClickListener {
//            if (binding.tvOther.text.equals("")) {
//                toast("Please select Other Document.")
//            } else if (binding.date5.equals("")) {
//                toast("Please select Expiry Date")
//            } else if (vehicleid.equals("")) {
//                toast("Fill vehicle detail first.")
//            } else {
//                viewModel.PassengerdocAPI(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid,
//                    "Other", binding.date4.text.toString(),
//                    pdfFile5
//                )
//            }
//        }

        viewModel.TypeofTruckApi(
            "Bearer " + userPref.getToken().toString(), "2"
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
                binding.spinnerTrucktype.adapter = spinnerArrayAdapter
            }
        }
        viewModel.Vehicalwheels(
            "Bearer " + userPref.getToken().toString(), "2"
        ).observe(this) {

            if (it.error == false) {
                wheels.clear()
                vehicalwheel.clear()
                it.result?.data?.let { it1 -> wheels.addAll(it1) }
                viewModel.VehicalwheelsData.value = it.result?.data
                for (i in 0 until it.result?.data?.size!!) {
                    vehicalwheel.add(it.result?.data?.get(i)?.wheel.toString())
                    id_wheels.add(it.result?.data?.get(i)?.id.toString())
                }
                val spinnerArrayAdapter4: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    vehicalwheel
                )
                spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerTyrenumber.adapter = spinnerArrayAdapter4
            }
        }
//        viewModel.Color(
//            "Bearer " + userPref.getToken().toString(),
//        ).observe(this) {
//            if (it!!.status == 1) {
//                colordata.clear()
//                colors.clear()
//                colordata.addAll(it.data)
//                viewModel.Colordata.value = it.data
//                for (i in 0 until it.data.size) {
//                    colors.add(it.data[i].vColor.toString())
//                    id_color.add(it.data[i].id.toString())
//                }
//                val spinnerArrayAdapter5: ArrayAdapter<String> = ArrayAdapter<String>(
//                    this,
//                    android.R.layout.simple_spinner_dropdown_item,
//                    colors
//                )
//                spinnerArrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                binding.spinnerColor.adapter = spinnerArrayAdapter5
//            }
//        }
        viewModel.Year(
            "Bearer " + userPref.getToken().toString(),"2"
        ).observe(this) {
            if (it.error == false) {
                yeardata.clear()
                year.clear()
                it.result?.data?.let { it1 -> yeardata.addAll(it1) }
                viewModel.Yearresponsedata.value = it.result?.data
                for (i in 0 until it.result?.data?.size!!) {
                    year.add(it.result?.data?.get(i)?.year.toString())
                    id_year.add(it.result?.data?.get(i)?.id.toString())
                }
                val spinnerArrayAdapter6: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    year
                )
                spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerYearofmodel.adapter = spinnerArrayAdapter6
            }
        }

        viewModel.SeatAPI(
            "Bearer " + userPref.getToken().toString(),
        ).observe(this) {
            if (it!!.status == 1) {
                seatdata.clear()
                seat.clear()
                seatdata.addAll(it.data)
                viewModel.SeatResponseData.value = it.data
                for (i in 0 until it.data.size) {
                    seat.add(it.data[i].seat.toString())
                    seat_id.add(it.data[i].id.toString())
                }
                val spinnerArrayAdapter6: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    seat
                )
                spinnerArrayAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerSeat.adapter = spinnerArrayAdapter6
            }
        }



        binding.spinnerTrucktype.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedTruckTypeId = id_type[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        binding.spinnerYearofmodel.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedYearId = id_year[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedColorId = id_color[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        binding.spinnerTyrenumber.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedWheelsId = id_wheels[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        binding.cal1.setOnClickListener {
            datepickerflag = "Rc"
            Selectdate()
        }
        binding.cal2.setOnClickListener {
            datepickerflag = "Insurance"
            Selectdate()
        }
        binding.cal3.setOnClickListener {
            datepickerflag = "pollution"
            Selectdate()
        }
        binding.cal4.setOnClickListener {
            datepickerflag = "fitness"
            Selectdate()
        }
        binding.cal5.setOnClickListener {
            datepickerflag = "Rto"
            Selectdate()
        }
        binding.cal6.setOnClickListener {
            datepickerflag = "Other"
            Selectdate()
        }
        binding.btnSubmit.setOnClickListener {

            if (userPref.getRole().equals("2")) {
                if (binding.etTruckownername.text.isNullOrEmpty()) {
                    toast("Please enter Username.")
                } else if (binding.etVehivalnumber.text.toString().isNullOrEmpty()) {
                    toast("Please enter Vehical Number")
                } else {
                    viewModel.indi_add_passenger_vehicle(
                        "Bearer " + userPref.getToken().toString(),
                        userPref.getid().toString(),
                        binding.etTruckownername.text.toString(),
                        binding.spinnerTrucktype.selectedItem.toString(),
                        binding.spinnerYearofmodel.selectedItem.toString(),
                        binding.etVehivalnumber.text.toString(),
                        selectedTruckTypeId,
                        selectedCapacityId,
                        selectedHightId,
                        selectedColorId,
                        selectedWheelsId,
                        selectedBodyId,
                        binding.spinnerSeat.selectedItem.toString(),
                        imagetruck1!!,
                        imagetruck2!!,
                        imagetruck3!!,
                        imagetruck4!!,
                        pdfFile!!,pdfFile1!!,pdfFile2!!,pdfFile3!!,pdfFile4!!,pdfFile5!!,
                        binding.tvDate.text.toString(),
                        binding.date1.text.toString(),
                        binding.date2.text.toString(),
                        binding.date3.text.toString(),
                        binding.date4.text.toString(),
                        binding.date5.text.toString(),
                        "Rc-book",
                        "Pollution-Document",
                        "Fitness-Papers",
                        "Insurance-Document",
                        "RTO Documents",
                        "Others",
                    )
                    viewModel.AddloaderResponse.observe(this) {
                        if (it?.status == 1) {
                            Toast.makeText(this, "Vehical added Successfully...", Toast.LENGTH_LONG)
                                .show()
                            finish()
                            vehicleid = it.id.toString()
                        } else {
                            snackbar(it?.message!!)
                        }
                    }
                }
            } else {
                if (binding.etTruckownername.text.isNullOrEmpty()) {
                    toast("Please enter Username.")
                } else if (binding.etVehivalnumber.text.toString().isNullOrEmpty()) {
                    toast("Please enter Vehical Number")
                } else {
                    viewModel.Addpassengervehical(
                        "Bearer " + userPref.getToken().toString(),
                        userPref.getid().toString(),
                        binding.etTruckownername.text.toString(),
//                    binding.etTruckname.text.toString(),
                        binding.spinnerTrucktype.selectedItem.toString(),
                        binding.spinnerYearofmodel.selectedItem.toString(),
                        binding.etVehivalnumber.text.toString(),
                        selectedTruckTypeId,
                        selectedCapacityId,
                        selectedHightId,
                        selectedColorId,
                        selectedWheelsId,
                        selectedBodyId,
                        binding.spinnerSeat.selectedItem.toString(),
                        imagetruck1!!,
                        imagetruck2!!,
                        imagetruck3!!,
                        imagetruck4!!,
                        pdfFile!!,pdfFile1!!,pdfFile2!!,pdfFile3!!,pdfFile4!!,pdfFile5!!,
                        binding.tvDate.text.toString(),
                        binding.date1.text.toString(),
                        binding.date2.text.toString(),
                        binding.date3.text.toString(),
                        binding.date4.text.toString(),
                        binding.date5.text.toString(),
                        "Rc-book",
                        "Pollution-Document",
                        "Fitness-Papers",
                        "Insurance-Document",
                        "RTO Documents",
                        "Others",
                    )
                    viewModel.AddloaderResponse.observe(this) {
                        if (it?.status == 1) {
                            Toast.makeText(this, "Vehical added Successfully...", Toast.LENGTH_LONG)
                                .show()
                            finish()
                            vehicleid = it.id.toString()
                        } else {
                            snackbar(it?.message!!)
                        }
                    }
                }
            }


        }
//    binding.spinnerBadytype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//            selectedBodyId=id_body[p2]
//        }
//
//        override fun onNothingSelected(p0: AdapterView<*>?) {
//
//        }
//
//    }

        binding.ivTruck1.setOnClickListener {
            flag = "firstone"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE_ID_Front
                )
            } else {
                selectImage()
                requestCodeForLegacy = GALLERY_ID_FRONT
            }
        }
        binding.ivTruck2.setOnClickListener {
            flag = "secondone"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE_ID_BACK
                )
            } else {
                selectImage()
                requestCodeForLegacy = GALLERY_ID_BACK
            }
        }
        binding.ivTruck3.setOnClickListener {
            flag = "thirdone"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE_LICENSE_FRONT
                )
            } else {
                selectImage()
                requestCodeForLegacy = GALLERY_LICENSE_FRONT

            }
        }
        binding.ivTruck4.setOnClickListener {
            flag = "fourthone"
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERM_CODE_LICENSE_BACK
                )
            } else {
                selectImage()
                requestCodeForLegacy = GALLERY_LICENSE_BACK

            }
        }

        binding.tvRcbook.setOnClickListener {
            if (imagetruck1==null){
                toast("Please add four vehicle photos.")
            }else if (imagetruck2==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck3==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck4==null){
                toast("Please add four vehicle photos.")
            }
            else {
                flagforpdf = "firstone"
                selectPdf()
            }
        }
        binding.tvInsurancedoc.setOnClickListener {
            if (imagetruck1==null){
                toast("Please add four vehicle photos.")
            }else if (imagetruck2==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck3==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck4==null){
                toast("Please add four vehicle photos.")
            }else {
                flagforpdf = "secondone"
                selectPdf()
            }
        }
        binding.tvPollutiondoc.setOnClickListener {

            if (imagetruck1==null){
                toast("Please add four vehicle photos.")
            }else if (imagetruck2==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck3==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck4==null){
                toast("Please add four vehicle photos.")
            }else {
                flagforpdf = "thirdone"
                selectPdf()
            }
        }
        binding.tvFitnessdoc.setOnClickListener {

            if (imagetruck1==null){
                toast("Please add four vehicle photos.")
            }else if (imagetruck2==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck3==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck4==null){
                toast("Please add four vehicle photos.")
            }else {
                flagforpdf = "fourthone"
                selectPdf()
            }
        }
        binding.tvRtodoc.setOnClickListener {

            if (imagetruck1==null){
                toast("Please add four vehicle photos.")
            }else if (imagetruck2==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck3==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck4==null){
                toast("Please add four vehicle photos.")
            }else {
                flagforpdf = "fifthone"
                selectPdf()
            }

        }

        binding.tvOther.setOnClickListener {

            if (imagetruck1==null){
                toast("Please add four vehicle photos.")
            }else if (imagetruck2==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck3==null){
                toast("Please add four vehicle photos.")
            }
            else if (imagetruck4==null){
                toast("Please add four vehicle photos.")
            }else {
                flagforpdf = "sixone"
                selectPdf()
            }

        }

        viewModel.AddloaderResponse.observe(this) {
            if (it.status == 1) {
                finish()
                toast(it.message)
            } else {
                toast(it.message)
            }
        }
        viewModel.Loaderimage.observe(this) {
            if (it.status == 1) {
                toast(it.message)
            } else {
                toast(it.message)
            }
        }

//        binding.btnFinalsubmit.setOnClickListener {
//            if (binding.tvRcbook.text.equals("")) {
//                toast("Please select Rc-book.")
//            } else if (binding.tvInsurancedoc.text.equals("")) {
//                toast("Please select Insurance-Document.")
//            } else if (binding.tvPollutiondoc.text.equals("")) {
//                toast("Please select Pollution-Document.")
//            } else if (binding.tvFitnessdoc.text.equals("")) {
//                toast("Please select fitness-document")
//            } else if (binding.tvRtodoc.text.equals("")) {
//                toast("Please select Rto-document.")
//            }else if (binding.tvOther.text.equals("")) {
//                toast("Please select Other document.")
//            } else {
//                viewModel.AddpassengerVehicalfinalApi(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid
//                )
//            }
//        }
//        viewModel.AddVehicalfinalResponse.observe(this) {
//            if (it.status == 1) {
//                finish()
////            val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java)
////                .putExtra("flag1","passenger")
////                .putExtra("vehicle_owner_name1", binding.etTruckownername.text.toString())
////
////                .putExtra("vehicle_id",vehicleid)
////                .putExtra("vehicle_name1",binding.spinnerTrucktype.selectedItem.toString())
////                .putExtra("model_year1",selectedYearId)
////                .putExtra("vehicle_number1",binding.etVehivalnumber.text.toString())
////                .putExtra("vehicle_type1",selectedTruckTypeId)
////                .putExtra("capacity1",selectedCapacityId)
////                .putExtra("height1",selectedHightId)
////                .putExtra("seat",binding.spinnerSeat.selectedItem.toString())
////                .putExtra("color",binding.spinnerColor.selectedItem.toString())
////                .putExtra("number_of_tyres1",selectedWheelsId)
////                .putExtra("bodytype1",selectedBodyId)
////            startActivity(intent)
//            } else {
//                toast(it.message)
//            }
//        }
    }

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "*/*"
        if (flagforpdf == "firstone") {
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf)
        } else if (flagforpdf == "secondone") {
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf1)
        } else if (flagforpdf == "thirdone") {
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf2)
        } else if (flagforpdf == "fourthone") {
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf3)
        } else if (flagforpdf == "fifthone") {
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf4)

        } else if (flagforpdf == "sixone") {
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf5)

        }
    }

    private fun selectImage() {
        // on below line we are creating a new bottom sheet dialog.
        val dialog = BottomSheetDialog(this)
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.bottom_drawer, null)

        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        val CameraButton = view.findViewById<TextView>(R.id.camera_open)
        CameraButton.setOnClickListener {
//            val values = ContentValues()
//            values.put(MediaStore.Images.Media.TITLE, "New Picture")
//            values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//            photoURI =
//                this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//            if (flag == "firstone") {
//                startActivityForResult(takePictureIntent, CAMERA_ID_FRONT)
//            } else if (flag == "secondone") {
//                startActivityForResult(takePictureIntent, CAMERA_ID_BACK)
//            } else if (flag == "thirdone") {
//                startActivityForResult(takePictureIntent, CAMERA_LICENSE_FRONT)
//            } else if (flag == "fourthone") {
//                startActivityForResult(takePictureIntent, CAMERA_LICENSE_BACK)
//            }
            captureImage()
            dialog.dismiss()

        }

        val GalleryButton = view.findViewById<TextView>(R.id.gallery_open)
        GalleryButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pickSingleMediaLauncher.launch(Intent(MediaStore.ACTION_PICK_IMAGES))
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                if (flag == "firstone") {
                    startActivityForResult(intent, GALLERY_ID_FRONT)
                } else if (flag == "secondone") {
                    startActivityForResult(intent, GALLERY_ID_BACK)
                } else if (flag == "thirdone") {
                    startActivityForResult(intent, GALLERY_LICENSE_FRONT)
                } else if (flag == "fourthone") {
                    startActivityForResult(intent, GALLERY_LICENSE_BACK)
                }
            }
//            startActivityForResult(intent, GALLERY)
            dialog.dismiss()
        }
        val cancel = view.findViewById<TextView>(R.id.cancel)
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()
    }
    private fun captureImage() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                try {
                    file = createImageFile()
                    // Continue only if the File was successfully created
                    if (file != null) {
                        mUri = FileProvider.getUriForFile(
                            this,
                            "com.govahanpartner.com.fileprovider.unique",
                            file!!
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)

                        if (flag == "firstone") {
                            startActivityForResult(takePictureIntent, CAMERA_ID_FRONT)
                        } else if (flag == "secondone") {
                            startActivityForResult(takePictureIntent, CAMERA_ID_BACK)
                        } else if (flag == "thirdone") {
                            startActivityForResult(takePictureIntent, CAMERA_LICENSE_FRONT)
                        } else if (flag == "fourthone") {
                            startActivityForResult(takePictureIntent, CAMERA_LICENSE_BACK)
                        }                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
//                    displayMessage(baseContext, ex.message.toString())
                }

            } else {
//                displayMessage(baseContext, "Null")
            }
        }

    }
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )

        imagePath = image.absolutePath
        return image
    }

    private fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            this.contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
        if (requestCode == GALLERY_ID_FRONT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)

                    if (path != null) {
                        imageFile = File(path)
                        Glide.with(this).load(imageFile).into(binding.ivTruck1)
                        //   images.add(imageFile!!.absolutePath.toString())
                    }
                    //   images.add(path.toString())
                    val requestFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck1 = MultipartBody.Part.createFormData(
                        "image",
                        imageFile?.name,
                        requestFile
                    )
                    viewModel.PassengerimageAPI(
                        "Bearer " + userPref.getToken().toString(),
                        vehicleid,
                        imagetruck1
                    )
                }


            }
        } else if (requestCode == CAMERA_ID_FRONT) {
//            if (resultCode == RESULT_OK) {
//
//                try {
//
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck1)
//                    //   images.add(imageFile!!.absolutePath.toString())
//                    val requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())
//
//                    imagetruck1 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
//                    viewModel.PassengerimageAPI(
//                        "Bearer "+userPref.getToken().toString(),
//                        vehicleid,
//                        imagetruck1
//                    )
//
//                } catch (e: java.lang.Exception) {
//                }
//
//            }

            val bitmap = BitmapFactory.decodeStream(
                contentResolver.openInputStream(photoURI!!)
            )
            Glide.with(this).load(bitmap!!)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder))
                .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
                .into(binding.ivTruck1)

            imageFile = File(getPathFromURI(photoURI))
            val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
//            imageFile = MultipartBody.Part.createFormData("image", imageFile?.name, requestFile!!)

            val requestGalleryImageFile: RequestBody =
                imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck1 =
                MultipartBody.Part.createFormData(
                    "image",
                    imageFile?.name,
                    requestGalleryImageFile
                )
            viewModel.PassengerimageAPI(
                "Bearer " + userPref.getToken().toString(),
                vehicleid,
                imagetruck1
            )


        } else if (requestCode == GALLERY_ID_BACK) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)

                    if (path != null) {
                        imageFile = File(path)
                        Glide.with(this).load(imageFile).into(binding.ivTruck2)
                        //  images.add(imageFile!!.absolutePath.toString())

                    }
                    var requestGalleryImageFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck2 =
                        MultipartBody.Part.createFormData(
                            "image",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )
                    viewModel.PassengerimageAPI(
                        "Bearer " + userPref.getToken().toString(),
                        vehicleid,
                        imagetruck2
                    )

//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_ID_BACK) {
//            if (resultCode == RESULT_OK) {
//
//                try {
//
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck2)
//                    //  images.add(imageFile!!.absolutePath.toString())
////                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())
//
//                    imagetruck2 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
//                    viewModel.PassengerimageAPI(
//                        "Bearer "+userPref.getToken().toString(),
//                        vehicleid,
//                        imagetruck2
//                    )
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                } catch (e: java.lang.Exception) {
//                }
//            }


            val bitmap = BitmapFactory.decodeStream(
                contentResolver.openInputStream(photoURI!!)
            )
            Glide.with(this).load(bitmap!!)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder))
                .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
                .into(binding.ivTruck2)

            imageFile = File(getPathFromURI(photoURI))
            val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
//            imageFile = MultipartBody.Part.createFormData("image", imageFile?.name, requestFile!!)

            val requestGalleryImageFile: RequestBody =
                imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck2 =
                MultipartBody.Part.createFormData(
                    "image",
                    imageFile?.name,
                    requestGalleryImageFile
                )
            viewModel.PassengerimageAPI(
                "Bearer " + userPref.getToken().toString(),
                vehicleid,
                imagetruck2
            )


        } else if (requestCode == GALLERY_LICENSE_FRONT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)

                    if (path != null) {
                        imageFile = File(path)
                        Glide.with(this).load(imageFile).into(binding.ivTruck3)
                    }
                    var requestGalleryImageFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck3 =
                        MultipartBody.Part.createFormData(
                            "image",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )

                    if (vehicleid.isNullOrEmpty()) {
                        toast("Please ")
                    }
                    viewModel.PassengerimageAPI(
                        "Bearer " + userPref.getToken().toString(),
                        vehicleid,
                        imagetruck3
                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_LICENSE_FRONT) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck3)
////                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())
//
//                    imagetruck3 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
//                    viewModel.PassengerimageAPI(
//                        "Bearer "+userPref.getToken().toString(),
//                        vehicleid,
//                        imagetruck3
//                    )
//
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                } catch (e: java.lang.Exception) {
//                }
//            }


            val bitmap = BitmapFactory.decodeStream(
                contentResolver.openInputStream(photoURI!!)
            )
            Glide.with(this).load(bitmap!!)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder))
                .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
                .into(binding.ivTruck3)

            imageFile = File(getPathFromURI(photoURI))
            val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
//            imageFile = MultipartBody.Part.createFormData("image", imageFile?.name, requestFile!!)

            val requestGalleryImageFile: RequestBody =
                imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck3 =
                MultipartBody.Part.createFormData(
                    "image",
                    imageFile?.name,
                    requestGalleryImageFile
                )
            viewModel.PassengerimageAPI(
                "Bearer " + userPref.getToken().toString(),
                vehicleid,
                imagetruck3
            )


        } else if (requestCode == GALLERY_LICENSE_BACK) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)

                    if (path != null) {
                        imageFile = File(path)
                        Glide.with(this).load(imageFile).into(binding.ivTruck4)

                    }
                    var requestGalleryImageFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck4 =
                        MultipartBody.Part.createFormData(
                            "image",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )
                    viewModel.PassengerimageAPI(
                        "Bearer " + userPref.getToken().toString(),
                        vehicleid,
                        imagetruck4
                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_LICENSE_BACK) {
//            if (resultCode == RESULT_OK) {
//
//                try {
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck4)
////                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())
//
//                    imagetruck4 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
//                    viewModel.PassengerimageAPI(
//                        "Bearer "+userPref.getToken().toString(),
//                        vehicleid,
//                        imagetruck4
//                    )
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                } catch (e: java.lang.Exception) {
//                }
//            }


            val bitmap = BitmapFactory.decodeStream(
                contentResolver.openInputStream(photoURI!!)
            )
            Glide.with(this).load(bitmap!!)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder))
                .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
                .into(binding.ivTruck4)

            imageFile = File(getPathFromURI(photoURI))
            val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
//            imageFile = MultipartBody.Part.createFormData("image", imageFile?.name, requestFile!!)

            val requestGalleryImageFile: RequestBody =
                imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck4 =
                MultipartBody.Part.createFormData(
                    "image",
                    imageFile?.name,
                    requestGalleryImageFile
                )
            viewModel.PassengerimageAPI(
                "Bearer " + userPref.getToken().toString(),
                vehicleid,
                imagetruck4
            )


        } else if (requestCode == pickPdf) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)
                    var path1 = writeFileContent(image!!)
                    var fileSelected = File(path1)
                    binding.tvRcbook.text = fileSelected.name
                    if (path != null) {
                        fileSelected = File(path)
                    }
                    if (fileSelected!!.endsWith(".jpg") || fileSelected!!.endsWith(".jpeg")|| fileSelected!!.endsWith(".png") ||
                        fileSelected!!.endsWith(".webp") || fileSelected!!.endsWith(".svg")) {

                        if (path != null) {
                            fileSelected = File(path)
//                            Glide.with(this).load(imageFile).into(binding.ivTruck4)
//                            binding.tvRcbook.text = imageFile!!.name
                        }
                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("image/*".toMediaTypeOrNull())

                        pdfFile =
                            MultipartBody.Part.createFormData(
                                "doc_1",
                                fileSelected?.name,
                                requestGalleryImageFile
                            )


//                        binding.tvRcbook.text = imageFile?.name

                    } else {
                        /*       image = data.data!!
                               val path = getPathFromURI(image)*/
                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile = MultipartBody.Part.createFormData(
                            "doc_1",
                            fileSelected?.name,
                            requestGalleryImageFile
                        )

                    }
                }


                /*        val fileUris = data?.data
            //            Glide.with(this).load(R.drawable.ic_baseline_picture_as_pdf_24).into(binding.ivPict)
                        var path = writeFileContent(fileUris!!)
                        var fileSelected = File(path)
                        binding.tvRcbook.text = fileSelected.name
                        val requestFile: RequestBody = fileSelected
                            .asRequestBody(*//* "multipart/form-data".toMediaTypeOrNull()*//*"multipart/form-data".toMediaTypeOrNull())
            pdfFile = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
*/

            }


        }
        else if (requestCode == pickPdf1) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)
                    var path1 = writeFileContent(image!!)
                    var fileSelected = File(path1)
                    binding.tvInsurancedoc.text = fileSelected.name
                    if (path != null) {
                        fileSelected = File(path)
                    }
                    if (fileSelected!!.endsWith(".jpg") || fileSelected!!.endsWith(".jpeg") || fileSelected!!.endsWith(
                            ".png"
                        ) ||
                        fileSelected!!.endsWith(".webp") || fileSelected!!.endsWith(".svg")
                    ) {

//                        binding.tvInsurancedoc.text = imageFile?.absolutePath
                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile1 = MultipartBody.Part.createFormData(
                            "doc_2",
                            fileSelected?.name,
                            requestGalleryImageFile
                        )
                    } else {
                        /*       image = data.data!!
                               val path = getPathFromURI(image)*/


                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile1 = MultipartBody.Part.createFormData(
                            "doc_2",
                            fileSelected?.name,
                            requestGalleryImageFile
                        )
                    }
                }


            }
        }else if (requestCode == pickPdf3) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)
                    var path1 = writeFileContent(image!!)
                    var fileSelected = File(path1)
                    binding.tvFitnessdoc.text = fileSelected.name
                    if (path != null) {
                        fileSelected = File(path)
                    }
                    if (fileSelected!!.endsWith(".jpg") || fileSelected!!.endsWith(".jpeg") || fileSelected!!.endsWith(
                            ".png"
                        ) ||
                        fileSelected!!.endsWith(".webp") || fileSelected!!.endsWith(".svg")) {

//                        binding.tvFitnessdoc.text = imageFile?.absolutePath
                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile3 = MultipartBody.Part.createFormData(
                            "doc_4",
                            fileSelected?.name,
                            requestGalleryImageFile
                        )
                    } else {
                        /*       image = data.data!!
                               val path = getPathFromURI(image)*/


                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile3 =

                            MultipartBody.Part.createFormData(
                                "doc_4",
                                fileSelected?.name,
                                requestGalleryImageFile
                            )

                    }
                }
            }
//            val fileUris = data?.data
//            var path = writeFileContent(fileUris!!)
//            var fileSelected = File(path)
//
//            binding.tvFitnessdoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = fileSelected
//                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            pdfFile3 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)

        } else if (requestCode == pickPdf4) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)
                    var path1 = writeFileContent(image!!)
                    var fileSelected = File(path1)
                    binding.tvRtodoc.text = fileSelected.name
                    if (path != null) {
                        fileSelected = File(path)
                    }
                    if (fileSelected!!.endsWith(".jpg") || fileSelected!!.endsWith(".jpeg") || fileSelected!!.endsWith(
                            ".png"
                        ) ||
                        fileSelected!!.endsWith(".webp") || fileSelected!!.endsWith(".svg")) {

//                        binding.tvRtodoc.text = imageFile?.absolutePath
                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile4 = MultipartBody.Part.createFormData(
                            "doc_5",
                            fileSelected?.name,
                            requestGalleryImageFile
                        )
                    } else {
                        /*       image = data.data!!
                               val path = getPathFromURI(image)*/


                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile4 =
                            MultipartBody.Part.createFormData(
                                "doc_5",
                                fileSelected?.name,
                                requestGalleryImageFile
                            )
                    }
                }
            }
//            val fileUris = data?.data
//            var path = writeFileContent(fileUris!!)
//            var fileSelected = File(path)
//
//            binding.tvRtodoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = fileSelected
//                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            pdfFile4 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
            //  viewModel.onUpdateCv(pdfFile)

        } else if (requestCode == pickPdf5) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    val path = getPathFromURI(image)
                    var path1 = writeFileContent(image!!)
                    var fileSelected = File(path1)
                    binding.tvOther.text = fileSelected.name
                    if (path != null) {
                        fileSelected = File(path)
                    }
                    if (fileSelected!!.endsWith(".jpg") || fileSelected!!.endsWith(".jpeg") || fileSelected!!.endsWith(
                            ".png"
                        ) ||
                        fileSelected!!.endsWith(".webp") || fileSelected!!.endsWith(".svg")) {

//                        binding.tvRtodoc.text = imageFile?.absolutePath
                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile5 = MultipartBody.Part.createFormData(
                            "doc_6",
                            fileSelected?.name,
                            requestGalleryImageFile
                        )
                    } else {
                        /*       image = data.data!!
                               val path = getPathFromURI(image)*/


                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile5 =
                            MultipartBody.Part.createFormData(
                                "doc_6",
                                fileSelected?.name,
                                requestGalleryImageFile
                            )
                    }
                }
            }
//            val fileUris = data?.data
//            var path = writeFileContent(fileUris!!)
//            var fileSelected = File(path)
//
//            binding.tvRtodoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = fileSelected
//                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            pdfFile4 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
            //  viewModel.onUpdateCv(pdfFile)

        } else if (requestCode == pickPdf2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    var path1 = writeFileContent(image!!)
                    var fileSelected = File(path1)
                    binding.tvPollutiondoc.text = fileSelected.name

                    if (fileSelected!!.endsWith(".jpg") || fileSelected!!.endsWith(".jpeg") || fileSelected!!.endsWith(
                            ".png"
                        ) ||
                        fileSelected!!.endsWith(".webp") || fileSelected!!.endsWith(".svg")) {
//                        binding.tvPollutiondoc.text = imageFile?.absolutePath
                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile2 = MultipartBody.Part.createFormData(
                            "doc_3",
                            fileSelected?.name,
                            requestGalleryImageFile
                        )
                    } else {
                        /*       image = data.data!!
                               val path = getPathFromURI(image)*/


                        var requestGalleryImageFile: RequestBody =
                            fileSelected!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        pdfFile2 =
                            MultipartBody.Part.createFormData(
                                "doc_3",
                                fileSelected?.name,
                                requestGalleryImageFile
                            )
                    }
                }
//
//            val fileUris = data?.data
//            var path = writeFileContent(fileUris!!)
//            var fileSelected = File(path)
//
//            binding.tvPollutiondoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = fileSelected
//                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            pdfFile2 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
                //  viewModel.onUpdateCv(pdfFile)

            }
        }


//            val fileUris = data?.data
//            var path = writeFileContent(fileUris!!)
//            var fileSelected = File(path)
//
//            binding.tvRtodoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = fileSelected
//                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            pdfFile4 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)
//

        }



    @Throws(IOException::class)
    private fun writeFileContent(uri: Uri): String? {
        val selectedFileInputStream = contentResolver.openInputStream(uri)
        if (selectedFileInputStream != null) {

            val mediaDir = externalMediaDirs.firstOrNull()?.let {
                File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
            }

            val certCacheDir = File(mediaDir, FILE_BROWSER_CACHE_DIR)
            var isCertCacheDirExists = certCacheDir.exists()
            if (!isCertCacheDirExists) {
                isCertCacheDirExists = certCacheDir.mkdirs()
            }
            if (isCertCacheDirExists) {
                var fileName: String? = getFileDisplayName(uri)
                fileName!!.replace("[^a-zA-Z0-9]", " ")
                val filePath = certCacheDir.absolutePath.toString() + "/" + fileName
                val selectedFileOutPutStream: OutputStream = FileOutputStream(filePath)
                val buffer = ByteArray(1024)
                var length: Int
                while (selectedFileInputStream.read(buffer).also { length = it } > 0) {
                    selectedFileOutPutStream.write(buffer, 0, length)
                }
                selectedFileOutPutStream.flush()
                selectedFileOutPutStream.close()
                return filePath
            }
            selectedFileInputStream.close()
        }
        return null
    }

    @SuppressLint("Range")
    @Nullable
    private fun getFileDisplayName(uri: Uri): String? {
        var displayName: String? = null
        contentResolver
            .query(uri, null, null, null, null, null).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    displayName =
                        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        return displayName
    }

    fun Selectdate() {
        val myCalendar = Calendar.getInstance()
        var day = myCalendar.get(Calendar.DAY_OF_MONTH)
        var year = myCalendar.get(Calendar.YEAR)
        var month = myCalendar.get(Calendar.MONTH)
        datePicker = DatePickerDialog(this)
        datePicker = DatePickerDialog(
            this, R.style.DatePickerTheme,
            { view, year, month, dayOfMonth -> // adding the selected date in the edittext
                if (datepickerflag.equals("Rc")) {
                    binding.tvDate.text = dayOfMonth.toString() + "-" + (month + 1) + "-" + year
                } else if (datepickerflag.equals("Insurance")) {
                    binding.date1.text = dayOfMonth.toString() + "-" + (month + 1) + "-" + year

                } else if (datepickerflag.equals("pollution")) {
                    binding.date2.text = dayOfMonth.toString() + "-" + (month + 1) + "-" + year

                } else if (datepickerflag.equals("fitness")) {
                    binding.date3.text = dayOfMonth.toString() + "-" + (month + 1) + "-" + year

                } else if (datepickerflag.equals("Rto")) {
                    binding.date4.text = dayOfMonth.toString() + "-" + (month + 1) + "-" + year

                } else if (datepickerflag.equals("Other")) {
                    binding.date5.text = dayOfMonth.toString() + "-" + (month + 1) + "-" + year

                }
            }, year, month, day
        )
//            date  = DateFormat.proxyDateFormat(binding.Selectdate.text.toString()).toString()
        datePicker!!.datePicker.minDate = myCalendar.timeInMillis
        // show the dialog
        datePicker!!.show()
    }
}