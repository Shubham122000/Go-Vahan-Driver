package com.gvpartner.com.ui.common

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityEditTaxiDocumentsBinding
import com.gvpartner.com.model.*
import com.gvpartner.com.passengerviewmodel.AddTripPassengerViewModels
import com.gvpartner.com.permission.RequestPermission
import com.gvpartner.com.viewmodel.TruckRepositoryViewModel
import com.gvpartner.com.viewmodel.TypeOfTruckViewModel
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
import kotlin.collections.ArrayList

class EditTaxiDocumentsActivity : BaseActivity() {
    private lateinit var binding : ActivityEditTaxiDocumentsBinding
    var truckdata : ArrayList<TypeofTruckResponseData> = ArrayList()
    private val viewModel : AddTripPassengerViewModels by viewModels()
    private val viewModel3: TruckRepositoryViewModel by viewModels()

    private val viewModel1 : TruckRepositoryViewModel by viewModels()
    private val viewModel2 : TypeOfTruckViewModel by viewModels()
    var id1=""
    var id2=""
    var id3=""
    var id4=""
    var datePicker: DatePickerDialog? = null
    var datepickerflag=""
    var wheels : ArrayList<vehicalwheelsResponseData> = ArrayList()
    var colordata : ArrayList<ColorResponseData> = ArrayList()
    var yeardata : ArrayList<YearResponseData> = ArrayList()
    var seatdata : ArrayList<SeatResponseData> = ArrayList()
    var nametype :ArrayList<String> = ArrayList()
    var Bodytype :ArrayList<String> = ArrayList()
    var vehicalwheel :ArrayList<String> = ArrayList()
    var colors :ArrayList<String> = ArrayList()
    var year :ArrayList<String> = ArrayList()
    var seat :ArrayList<String> = ArrayList()
    var seat_id :ArrayList<String> = ArrayList()
    var id_type :ArrayList<String> = ArrayList()

    var id_wheels :ArrayList<String> = ArrayList()
    var id_color :ArrayList<String> = ArrayList()
    var id_year :ArrayList<String> = ArrayList()
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
    var selectedTruckTypeId=""
    var selectedBodyId=""
    var selectedHightId=""
    var selectedWheelsId=""
    var selectedColorId=""
    var selectedYearId=""
    var Capacity : ArrayList<LoadCarryingData> = ArrayList()
    var Capacitytype :ArrayList<String> = ArrayList()
    var id_Capacity :ArrayList<String> = ArrayList()
    var selectedCapacityId=""
    var selectedSeatId=""
    //For Pdf Upload
    var pdfFile: MultipartBody.Part? = null
    var pdfFile1: MultipartBody.Part? = null
    var pdfFile2: MultipartBody.Part? = null
    var pdfFile3: MultipartBody.Part? = null
    var pdfFile4: MultipartBody.Part? = null
    private val pickPdf = 9
    private val pickPdf1 = 10
    private val pickPdf2 = 11
    private val pickPdf3 = 12
    private val pickPdf4 = 13
    var vehicle_id=""

    val FILE_BROWSER_CACHE_DIR = "doc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_taxi_documents)
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
//        binding.btnSubmit.setOnClickListener {
//            val intent = Intent(this, VendorsSubscriptionPlanActivity::class.java)
//            startActivity(intent)
//        }
        if (intent!=null){
            vehicle_id= intent.getStringExtra("vehicle_id").toString()
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
        viewModel2.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
     viewModel3.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

      viewModel3.passengers_truck_repository_image_list("Bearer "+userPref.getToken().toString(),vehicle_id)

        viewModel3.truckImagesResponse.observe(this) {
            if (it?.status == 1) {

                Glide.with(this).load(it.data.image1).placeholder(R.drawable.image_placeholder).into(binding.ivTruck1)
                Glide.with(this).load(it.data.image2).placeholder(R.drawable.image_placeholder).into(binding.ivTruck2)
                Glide.with(this).load(it.data.image3).placeholder(R.drawable.image_placeholder).into(binding.ivTruck3)
                Glide.with(this).load(it.data.image4).placeholder(R.drawable.image_placeholder).into(binding.ivTruck4)
                id1=it.data.id1.toString()
                id2=it.data.id2.toString()
                id3=it.data.id3.toString()
                id4=it.data.id4.toString()
            } else {
                snackbar(it?.message!!)
            }
        }


        viewModel2.Loaderimage.observe(this){
            if (it!!.status == 1) {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.Loaderimage.observe(this){
            if (it!!.status == 1) {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

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
            }
        }

        binding.llRcbook.setOnClickListener {
            viewModel.passenger_vehicle_doc_update(
                "Bearer "+userPref.getToken().toString(),
                vehicle_id,
                "Rc-book",binding.tvDate.text.toString(),
                pdfFile
            )
        }




        binding.llPollutiondoc.setOnClickListener {
            viewModel.passenger_vehicle_doc_update(
                "Bearer "+userPref.getToken().toString(),
                vehicle_id,
                "Pollution-Document",binding.date2.text.toString(),
                pdfFile2
            )

        }
        binding.lyFitnessdoc.setOnClickListener {
            viewModel.passenger_vehicle_doc_update(
                "Bearer "+userPref.getToken().toString(),
                vehicle_id,
                "Fitness-Papers",binding.date3.text.toString(),
                pdfFile3
            )
        }
        binding.lyInsurancedoc.setOnClickListener {
            viewModel.loader_vehicle_doc_update(
                "Bearer "+userPref.getToken().toString(),
                vehicle_id,
                "Insurance-Document",binding.date1.text.toString(),
                pdfFile1
            )
        }
        binding.lyRtodoc.setOnClickListener {
            viewModel.loader_vehicle_doc_update(
                "Bearer "+userPref.getToken().toString(),
                vehicle_id,
                "RTO Documents",binding.date4.text.toString(),
                pdfFile4
            )

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
            }
        }

        binding.tvRcbook.setOnClickListener {

            flagforpdf = "firstone"
            selectPdf()
        }
        binding.tvInsurancedoc.setOnClickListener {

            flagforpdf = "secondone"
            selectPdf()
        }
        binding.tvPollutiondoc.setOnClickListener {

            flagforpdf = "thirdone"
            selectPdf()
        }
        binding.tvFitnessdoc.setOnClickListener {

            flagforpdf = "fourthone"
            selectPdf()
        }
        binding.tvRtodoc.setOnClickListener {

            flagforpdf = "fifthone"
            selectPdf()
        }
        binding.cal1.setOnClickListener {
            datepickerflag="Rc"
            Selectdate()
        }
        binding.cal2.setOnClickListener {
            datepickerflag="Insurance"
            Selectdate()
        }
        binding.cal3.setOnClickListener {
            datepickerflag="pollution"
            Selectdate()
        }
        binding.cal4.setOnClickListener {
            datepickerflag="fitness"
            Selectdate()
        }
        binding.cal5.setOnClickListener {
            datepickerflag="Rto"
            Selectdate()
        }
        viewModel1.passengers_truck_repository_list_details(
            "Bearer "+ userPref.getToken().toString(),
            vehicle_id

        )
    }
    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "*/*"
        if (flagforpdf == "firstone"){
            startActivityForResult(pdfIntent, pickPdf)
        }else if (flagforpdf == "secondone"){
            startActivityForResult(pdfIntent, pickPdf1)
        }else if (flagforpdf == "thirdone"){
            startActivityForResult(pdfIntent, pickPdf2)
        }else if (flagforpdf == "fourthone"){
            startActivityForResult(pdfIntent, pickPdf3)
        }else if (flagforpdf == "fifthone"){
            startActivityForResult(pdfIntent, pickPdf4)

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
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                try {
                    imageFile = createImageFile()!!
                } catch (ex: IOException) {
                }
                // Continue only if the File was successfully created
                if (imageFile != null) {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.govahanpartner.com.fileprovider.unique",
                        imageFile!!
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    if (flag == "firstone") {
                        startActivityForResult(takePictureIntent, CAMERA_ID_FRONT)
                    } else if (flag == "secondone") {
                        startActivityForResult(takePictureIntent, CAMERA_ID_BACK)
                    } else if (flag == "thirdone") {
                        startActivityForResult(takePictureIntent, CAMERA_LICENSE_FRONT)
                    } else if (flag == "fourthone") {
                        startActivityForResult(takePictureIntent, CAMERA_LICENSE_BACK)
                    }

                    dialog.dismiss()
                }
            }
        }

        val GalleryButton = view.findViewById<TextView>(R.id.gallery_open)
        GalleryButton.setOnClickListener {
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

                    imagetruck1 =MultipartBody.Part.createFormData(
                        "image",
                        imageFile?.name,
                        requestFile
                    )
                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck1,id1
                    )
                }


            }
        } else if (requestCode == CAMERA_ID_FRONT) {
            if (resultCode == RESULT_OK) {

                try {

                    imageFile = File(imagePath)
                    Glide.with(this).load(imageFile).into(binding.ivTruck1)
                    //   images.add(imageFile!!.absolutePath.toString())
                    val requestGalleryImageFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck1 =
                        MultipartBody.Part.createFormData(
                            "image",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )
                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck1,id1
                    )

                } catch (e: java.lang.Exception) {
                }

            }
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
                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck2,id2
                    )

//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_ID_BACK) {
            if (resultCode == RESULT_OK) {

                try {

                    imageFile = File(imagePath)
                    Glide.with(this).load(imageFile).into(binding.ivTruck2)
                    //  images.add(imageFile!!.absolutePath.toString())
//                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
                    var requestGalleryImageFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck2 =
                        MultipartBody.Part.createFormData(
                            "image",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )
                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck2,id2
                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                } catch (e: java.lang.Exception) {
                }
            }
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


                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck3, id3
                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_LICENSE_FRONT) {
            if (resultCode == RESULT_OK) {
                try {
                    imageFile = File(imagePath)
                    Glide.with(this).load(imageFile).into(binding.ivTruck3)
//                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
                    var requestGalleryImageFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck3 =
                        MultipartBody.Part.createFormData(
                            "image",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )
                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck3,id3
                    )

//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                } catch (e: java.lang.Exception) {
                }
            }
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
                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck4,id4
                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_LICENSE_BACK) {
            if (resultCode == RESULT_OK) {

                try {
                    imageFile = File(imagePath)
                    Glide.with(this).load(imageFile).into(binding.ivTruck4)
//                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
                    var requestGalleryImageFile: RequestBody =
                        imageFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())

                    imagetruck4 =
                        MultipartBody.Part.createFormData(
                            "image",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )
                    viewModel2.passenger_vehicle_img_update(
                        "Bearer "+userPref.getToken().toString(),
                        vehicle_id,
                        imagetruck4,id4
                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                } catch (e: java.lang.Exception) {
                }
            }
        }else  if (requestCode == pickPdf) {

            val fileUris = data?.data
            var path= writeFileContent(fileUris!!)
            var fileSelected= File(path)

            binding.tvRcbook.text = fileSelected.absolutePath

            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                fileSelected)
            pdfFile = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
            //  viewModel.onUpdateCv(pdfFile)



        }else  if (requestCode == pickPdf1) {

            val fileUris = data?.data
            var path= writeFileContent(fileUris!!)
            var fileSelected= File(path)

            binding.tvInsurancedoc.text = fileSelected.absolutePath

            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                fileSelected)
            pdfFile1 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
            //  viewModel.onUpdateCv(pdfFile)


        }else  if (requestCode == pickPdf2) {

            val fileUris = data?.data
            var path= writeFileContent(fileUris!!)
            var fileSelected= File(path)

            binding.tvPollutiondoc.text = fileSelected.absolutePath

            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                fileSelected)
            pdfFile2 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
            //  viewModel.onUpdateCv(pdfFile)

        }else  if (requestCode == pickPdf3) {

            val fileUris = data?.data
            var path= writeFileContent(fileUris!!)
            var fileSelected= File(path)

            binding.tvFitnessdoc.text = fileSelected.absolutePath

            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                fileSelected)
            pdfFile3 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)


        }else  if (requestCode == pickPdf4) {

            val fileUris = data?.data
            var path= writeFileContent(fileUris!!)
            var fileSelected= File(path)

            binding.tvRtodoc.text = fileSelected.absolutePath

            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                fileSelected)
            pdfFile4 = MultipartBody.Part.createFormData("doc", fileSelected.name, requestFile)
            //  viewModel.onUpdateCv(pdfFile)


        }

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
                var fileName:String?=getFileDisplayName(uri)
                fileName!!.replace("[^a-zA-Z0-9]", " ")
                val filePath = certCacheDir.absolutePath.toString() + "/" +fileName
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
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        return displayName
    }

    fun Selectdate(){
        val myCalendar = Calendar.getInstance()
        var day = myCalendar.get(Calendar.DAY_OF_MONTH)
        var year = myCalendar.get(Calendar.YEAR)
        var month = myCalendar.get(Calendar.MONTH)
        datePicker = DatePickerDialog(this)
        datePicker = DatePickerDialog(
            this, R.style.DatePickerTheme,
            { view, year, month, dayOfMonth -> // adding the selected date in the edittext
                if (datepickerflag.equals("Rc")){
                    binding.tvDate.setText(dayOfMonth.toString() + "-" + (month + 1) + "-" + year)
                }
                else if (datepickerflag.equals("Insurance")){
                    binding.date1.setText(dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

                }
                else if (datepickerflag.equals("pollution")){
                    binding.date2.setText(dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

                }
                else if (datepickerflag.equals("fitness")){
                    binding.date3.setText(dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

                }
                else if (datepickerflag.equals("Rto")){
                    binding.date4.setText(dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

                }
            }, year, month, day
        )
//            date  = DateFormat.proxyDateFormat(binding.Selectdate.text.toString()).toString()
        datePicker!!.getDatePicker().setMinDate(myCalendar.getTimeInMillis())
        // show the dialog
        datePicker!!.show()
    }

}