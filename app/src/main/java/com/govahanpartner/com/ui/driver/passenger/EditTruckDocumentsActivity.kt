package com.govahanpartner.com.ui.driver.passenger

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
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
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.TruckDocumentsAdapter
import com.govahanpartner.com.adapter.TruckImagesAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityEditTruckDocumentsBinding
import com.govahanpartner.com.model.*
import com.govahanpartner.com.passengerviewmodel.AddTripPassengerViewModels
import com.govahanpartner.com.permission.RequestPermission
import com.govahanpartner.com.viewmodel.TruckRepositoryViewModel
import com.govahanpartner.com.viewmodel.TypeOfTruckViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class EditTruckDocumentsActivity : BaseActivity() {
    private lateinit var binding : ActivityEditTruckDocumentsBinding
    private val viewModel: TruckRepositoryViewModel by viewModels()
    var Listdata:ArrayList<TruckImagesData> = ArrayList()
    var wheels : ArrayList<vehicalwheelsResponseData> = ArrayList()
    var year :ArrayList<String> = ArrayList()
    lateinit var adapter : TruckImagesAdapter
    lateinit var adapter1 : TruckDocumentsAdapter
    var vehicle :Vehicles? = null
    val CAMERA_PERM_CODE_ID_Front = 101
    var imageFile: File? = null
    private var file: File? = null
    private var mUri: Uri? = null
    var imagePath = ""
    var photoURI: Uri? = null
    lateinit var imageprats: MultipartBody.Part
    private val GALLERY_ID_FRONT = 1
    private var CAMERA_ID_FRONT: Int = 2
    lateinit var image: Uri
    var flag: String = ""
    var flagforpdf: String = ""
    var datePicker: DatePickerDialog? = null
    var datepickerflag=""
    var imagetruck1: MultipartBody.Part? = null
    var imagetruck2: MultipartBody.Part? = null
    var imagetruck3: MultipartBody.Part? = null
    var imagetruck4: MultipartBody.Part? = null

    val CAMERA_PERM_CODE_ID_BACK = 102
    private val GALLERY_ID_BACK = 3
    private var CAMERA_ID_BACK: Int = 4
    val CAMERA_PERM_CODE_LICENSE_BACK = 104
    val CAMERA_PERM_CODE_LICENSE_FRONT = 103
    private val GALLERY_LICENSE_FRONT = 5
    private var CAMERA_LICENSE_FRONT: Int = 6

    private val GALLERY_LICENSE_BACK = 7
    private var CAMERA_LICENSE_BACK: Int = 8

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

    val FILE_BROWSER_CACHE_DIR = "doc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_truck_documents)

        if (intent!= null){
            vehicle = intent.getParcelableExtra<Vehicles>("vehicle")
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
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
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
//        viewModel1.progressBarStatus.observe(this) {
//            if (it) {
//                showProgressDialog()
//            } else {
//                hideProgressDialog()
//            }
//        }
                    Glide.with(this).load(vehicle?.vehicleImages?.image1).placeholder(R.drawable.image_placeholder).into(binding.ivTruck1)
                    Glide.with(this).load(vehicle?.vehicleImages?.image2).placeholder(R.drawable.image_placeholder).into(binding.ivTruck2)
                    Glide.with(this).load(vehicle?.vehicleImages?.image3).placeholder(R.drawable.image_placeholder).into(binding.ivTruck3)
                    Glide.with(this).load(vehicle?.vehicleImages?.image4).placeholder(R.drawable.image_placeholder).into(binding.ivTruck4)

//        viewModel2.progressBarStatus.observe(this) {
//            if (it) {
//                showProgressDialog()
//            } else {
//                hideProgressDialog()
//            }
//        }
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


        binding.btnSubmit.setOnClickListener {
            if (imagetruck1 == null) {
                val requestFile = "".toRequestBody("image/*".toMediaTypeOrNull())
                imagetruck1 = MultipartBody.Part.createFormData("image_1", "", requestFile)
            }else if (imagetruck2 == null) {
                val requestFile = "".toRequestBody("image/*".toMediaTypeOrNull())
                imagetruck2 = MultipartBody.Part.createFormData("image_2", "", requestFile)
            }else if (imagetruck3 == null) {
                val requestFile = "".toRequestBody("image/*".toMediaTypeOrNull())
                imagetruck3 = MultipartBody.Part.createFormData("image_3", "", requestFile)
            }else if (imagetruck4 == null) {
                val requestFile = "".toRequestBody("image/*".toMediaTypeOrNull())
                imagetruck4 = MultipartBody.Part.createFormData("image_4", "", requestFile)
            }
                viewModel.editVehicle(
                    "Bearer " + userPref.getToken().toString(),
//                    userPref.getid().toString(),
//                    binding.etTruckownername.text.toString(),
//                    selectedTruckTypeName,
//                    selectedYearId,
//                    binding.etVehicalNumber.text.toString(),
//                    selectedTruckTypeId,
//                    binding.etCapacity.text.toString(),
//                    selectedHightId,
//                    "white",
//                    selectedWheelsId,
//                    selectedBodyId,
//                    "0",
                    "",
                    imagetruck1!!,
                    imagetruck2!!,
                    imagetruck3!!,
                    imagetruck4!!,
                    pdfFile!!,pdfFile1!!,pdfFile2!!,pdfFile3!!,pdfFile4!!,
                    pdfFile5!!,
                    binding.tvDate.text.toString(),
                    binding.date1.text.toString(),
                    binding.date2.text.toString(),
                    binding.date3.text.toString(),
                    binding.date4.text.toString(),
                    binding.date5.text.toString(),
                    "Others",
                )
        }




        binding.llRcbook.setOnClickListener {
//            viewModel2.loader_vehicle_doc_update(
//                "Bearer "+userPref.getToken().toString(),
//                vehicle?.id.toString(),
//                "Rc-book",binding.tvDate.text.toString(),
//                pdfFile
//            )
        }




        binding.llPollutiondoc.setOnClickListener {
//            viewModel2.loader_vehicle_doc_update(
//                "Bearer "+userPref.getToken().toString(),
//                vehicle?.id.toString(),
//                "Pollution-Document",binding.date2.text.toString(),
//                pdfFile2
//            )

        }
        binding.lyFitnessdoc.setOnClickListener {
//            viewModel2.loader_vehicle_doc_update(
//                "Bearer "+userPref.getToken().toString(),
//                vehicle?.id.toString(),
//                "Fitness-Papers",binding.date3.text.toString(),
//                pdfFile3
//            )
        }
        binding.lyInsurancedoc.setOnClickListener {
//            viewModel2.loader_vehicle_doc_update(
//                "Bearer "+userPref.getToken().toString(),
//                vehicle?.id.toString(),
//                "Insurance-Document",binding.date1.text.toString(),
//                pdfFile1
//            )
        }
        binding.lyRtodoc.setOnClickListener {
//            viewModel2.loader_vehicle_doc_update(
//                "Bearer "+userPref.getToken().toString(),
//                vehicle?.id.toString(),
//                "RTO Documents",binding.date4.text.toString(),
//                pdfFile4
//            )

        }


//        viewModel1.Loaderimage.observe(this){
//            if (it!!.status == 1) {
//                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }
//        viewModel2.Loaderimage.observe(this){
//            if (it!!.status == 1) {
//                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }

//        viewModel1.AddloaderResponse.observe(this) {
//            if (it?.error == false) {
//                Toast.makeText(this, "Vehicle Updated Successfully...", Toast.LENGTH_LONG).show()
//                finish()
//            } else {
//                //toast(it.message)
//                snackbar(it?.message!!)
//            }
//        }
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
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//            if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
//                try {
//                    imageFile = createImageFile()!!
//                } catch (ex: IOException) {
//                }
//                if (imageFile != null) {
//                    photoURI = FileProvider.getUriForFile(this, "com.govahanpartner.com.fileprovider.unique", imageFile!!
//                    )
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                    if (flag == "firstone") {
//                        startActivityForResult(takePictureIntent, CAMERA_ID_FRONT)
//                    } else if (flag == "secondone") {
//                        startActivityForResult(takePictureIntent, CAMERA_ID_BACK)
//                    } else if (flag == "thirdone") {
//                        startActivityForResult(takePictureIntent, CAMERA_LICENSE_FRONT)
//                    } else if (flag == "fourthone") {
//                        startActivityForResult(takePictureIntent, CAMERA_LICENSE_BACK)
//                    }
//
//                    dialog.dismiss()
//                }
//            }
            captureImage()
            dialog.dismiss()
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
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////        if (resultCode == Activity.RESULT_CANCELED) {
////            return
////        }
//        if (requestCode == GALLERY_ID_FRONT) {
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    image = data.data!!
//                    val path = getPathFromURI(image)
//
//                    if (path != null) {
//                        imageFile = File(path)
//                        Glide.with(this).load(imageFile).into(binding.ivTruck1)
//                        //   images.add(imageFile!!.absolutePath.toString())
//                    }
//                    //   images.add(path.toString())
//                    val requestFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck1 =MultipartBody.Part.createFormData(
//                        "image",
//                        imageFile?.name,
//                        requestFile
//                    )
//
//                }
////                viewModel1.loader_vehicle_img_update(
////                    "Bearer "+userPref.getToken().toString(),
////                    vehicle?.id.toString(),
////                    imagetruck1,vehicle?.id.toString()
////                )
//
//
//            }
//        } else if (requestCode == CAMERA_ID_FRONT) {
//            if (resultCode == RESULT_OK) {
//
//                try {
//
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck1)
//                    //   images.add(imageFile!!.absolutePath.toString())
//                    val requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck1 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile?.name,
//                            requestGalleryImageFile
//                        )
////                    viewModel1.loader_vehicle_img_update(
////                        "Bearer "+userPref.getToken().toString(),
////                        vehicle?.id.toString(),
////                        imagetruck1,vehicle?.id.toString()
////                    )
//
//                } catch (e: java.lang.Exception) {
//                }
//
//            }
//        } else if (requestCode == GALLERY_ID_BACK) {
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    image = data.data!!
//                    val path = getPathFromURI(image)
//
//                    if (path != null) {
//                        imageFile = File(path)
//                        Glide.with(this).load(imageFile).into(binding.ivTruck2)
//                        //  images.add(imageFile!!.absolutePath.toString())
//
//                    }
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck2 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile?.name,
//                            requestGalleryImageFile
//                        )
////                    viewModel1.loader_vehicle_img_update(
////                        "Bearer "+userPref.getToken().toString(),
////                        vehicle?.id.toString(),
////                        imagetruck2,vehicle?.id.toString()
////                    )
//
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                }
//            }
//        } else if (requestCode == CAMERA_ID_BACK) {
//            if (resultCode == RESULT_OK) {
//
//                try {
//
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck2)
//                    //  images.add(imageFile!!.absolutePath.toString())
////                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck2 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
////                    viewModel1.loader_vehicle_img_update(
////                        "Bearer "+userPref.getToken().toString(),
////                        vehicle?.id.toString(),
////                        imagetruck2,vehicle?.id.toString()
////                    )
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                } catch (e: java.lang.Exception) {
//                }
//            }
//        } else if (requestCode == GALLERY_LICENSE_FRONT) {
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    image = data.data!!
//                    val path = getPathFromURI(image)
//
//                    if (path != null) {
//                        imageFile = File(path)
//                        Glide.with(this).load(imageFile).into(binding.ivTruck3)
//                    }
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck3 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
//
//
////                    viewModel1.loader_vehicle_img_update(
////                        "Bearer "+userPref.getToken().toString(),
////                        vehicle?.id.toString(),
////                        imagetruck3,vehicle?.id.toString()
////                    )
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                }
//            }
//        } else if (requestCode == CAMERA_LICENSE_FRONT) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck3)
////                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck3 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
////                    viewModel1.loader_vehicle_img_update(
////                        "Bearer "+userPref.getToken().toString(),
////                        vehicle?.id.toString(),
////                        imagetruck3,vehicle?.id.toString()
////                    )
//
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                } catch (e: java.lang.Exception) {
//                }
//            }
//        } else if (requestCode == GALLERY_LICENSE_BACK) {
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    image = data.data!!
//                    val path = getPathFromURI(image)
//
//                    if (path != null) {
//                        imageFile = File(path)
//                        Glide.with(this).load(imageFile).into(binding.ivTruck4)
//
//                    }
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck4 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile?.name,
//                            requestGalleryImageFile
//                        )
////                    viewModel1.loader_vehicle_img_update(
////                        "Bearer "+userPref.getToken().toString(),
////                        vehicle?.id.toString(),
////                        imagetruck4,vehicle?.id.toString()
////                    )
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                }
//            }
//        } else if (requestCode == CAMERA_LICENSE_BACK) {
//            if (resultCode == RESULT_OK) {
//
//                try {
//                    imageFile = File(imagePath)
//                    Glide.with(this).load(imageFile).into(binding.ivTruck4)
////                        ProfileImage.setImageURI(Uri.fromFile(imageFile))
//                    var requestGalleryImageFile: RequestBody =
//                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
//
//                    imagetruck4 =
//                        MultipartBody.Part.createFormData(
//                            "image",
//                            imageFile!!.name,
//                            requestGalleryImageFile
//                        )
////                    viewModel1.loader_vehicle_img_update(
////                        "Bearer "+userPref.getToken().toString(),
////                        vehicle?.id.toString(),
////                        imagetruck4,vehicle?.id.toString()
////                    )
////                    USER_IMAGE_UPLOADED = true
////                    if(USER_IMAGE_UPLOADED) {
////                        uploadUserImageApi()
////                    }
//                } catch (e: java.lang.Exception) {
//                }
//            }
//        }else  if (requestCode == pickPdf) {
//
//            val fileUris = data?.data
//            var path= writeFileContent(fileUris!!)
//            var fileSelected= File(path)
//
//            binding.tvRcbook.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = RequestBody.create(
//                "multipart/form-data".toMediaTypeOrNull(),
//                fileSelected)
//            pdfFile = MultipartBody.Part.createFormData("doc_1", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)
//
//
//        }else  if (requestCode == pickPdf1) {
//
//            val fileUris = data?.data
//            var path= writeFileContent(fileUris!!)
//            var fileSelected= File(path)
//            binding.tvInsurancedoc.text = fileSelected.absolutePath
//            val requestFile: RequestBody = RequestBody.create(
//                "multipart/form-data".toMediaTypeOrNull(),
//                fileSelected)
//            pdfFile1 = MultipartBody.Part.createFormData("doc_2", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)
//
//
//        }else  if (requestCode == pickPdf2) {
//
//            val fileUris = data?.data
//            var path= writeFileContent(fileUris!!)
//            var fileSelected= File(path)
//
//            binding.tvPollutiondoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = RequestBody.create(
//                "multipart/form-data".toMediaTypeOrNull(),
//                fileSelected)
//            pdfFile2 = MultipartBody.Part.createFormData("doc_3", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)
//
//        }else  if (requestCode == pickPdf3) {
//
//            val fileUris = data?.data
//            var path= writeFileContent(fileUris!!)
//            var fileSelected= File(path)
//
//            binding.tvFitnessdoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = RequestBody.create(
//                "multipart/form-data".toMediaTypeOrNull(),
//                fileSelected)
//            pdfFile3 = MultipartBody.Part.createFormData("doc_4", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)
//
//
//        }else  if (requestCode == pickPdf4) {
//
//            val fileUris = data?.data
//            var path= writeFileContent(fileUris!!)
//            var fileSelected= File(path)
//
//            binding.tvRtodoc.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = RequestBody.create(
//                "multipart/form-data".toMediaTypeOrNull(),
//                fileSelected)
//            pdfFile4 = MultipartBody.Part.createFormData("doc_5", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)
//        }else  if (requestCode == pickPdf5) {
//
//            val fileUris = data?.data
//            var path= writeFileContent(fileUris!!)
//            var fileSelected= File(path)
//
//            binding.tvOther.text = fileSelected.absolutePath
//
//            val requestFile: RequestBody = RequestBody.create(
//                "multipart/form-data".toMediaTypeOrNull(),
//                fileSelected)
//            pdfFile4 = MultipartBody.Part.createFormData("doc_6", fileSelected.name, requestFile)
//            //  viewModel.onUpdateCv(pdfFile)
//        }
//
//
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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
                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())
                    imagetruck1 = MultipartBody.Part.createFormData(
                        "image_1",
                        imageFile?.name,
                        requestFile
                    )
                }
//                viewModel.LoaderimageAPI(
//                    "Bearer " + userPref.getToken().toString(),
//                    vehicleid,
//                    imagetruck1
//                )


            }
        } else if (requestCode == CAMERA_ID_FRONT) {
            val myBitmap = BitmapFactory.decodeFile(file!!.absolutePath)
            binding.ivTruck1.setImageBitmap(myBitmap)
//                 mPhotoFile = File(getPath(photoURICamera!!))
            val requestFile: RequestBody =
                file!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck1 =
                MultipartBody.Part.createFormData(
                    "image_1",
                    file!!.name,
                    requestFile
                )
//            viewModel.LoaderimageAPI(
//                "Bearer " + userPref.getToken().toString(),
//                vehicleid,
//                imagetruck1
//            )
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
                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())

                    imagetruck2 =
                        MultipartBody.Part.createFormData(
                            "image_2",
                            imageFile?.name,
                            requestGalleryImageFile
                        )
//                    viewModel.LoaderimageAPI(
//                        "Bearer " + userPref.getToken().toString(),
//                        vehicleid,
//                        imagetruck2
//                    )

//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_ID_BACK) {
            val myBitmap = BitmapFactory.decodeFile(file!!.absolutePath)
            binding.ivTruck2.setImageBitmap(myBitmap)
//                 mPhotoFile = File(getPath(photoURICamera!!))
            val requestFile: RequestBody =
                file!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck2 =
                MultipartBody.Part.createFormData(
                    "image_2",
                    file!!.name,
                    requestFile
                )
//            viewModel.LoaderimageAPI(
//                "Bearer " + userPref.getToken().toString(),
//                vehicleid,
//                imagetruck2
//            )

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
                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())

                    imagetruck3 =
                        MultipartBody.Part.createFormData(
                            "image_3",
                            imageFile!!.name,
                            requestGalleryImageFile
                        )
//                    if (vehicleid.isNullOrEmpty()) {
//                        toast("Please ")
//                    }
//                    viewModel.LoaderimageAPI(
//                        "Bearer " + userPref.getToken().toString(),
//                        vehicleid,
//                        imagetruck3
//                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_LICENSE_FRONT) {
            val myBitmap = BitmapFactory.decodeFile(file!!.absolutePath)
            binding.ivTruck3.setImageBitmap(myBitmap)
//                 mPhotoFile = File(getPath(photoURICamera!!))
            val requestFile: RequestBody =
                file!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck3 =
                MultipartBody.Part.createFormData(
                    "image_3",
                    file!!.name,
                    requestFile
                )
//            viewModel.LoaderimageAPI(
//                "Bearer " + userPref.getToken().toString(),
//                vehicleid,
//                imagetruck3
//            )


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
                        imageFile!!.asRequestBody("image/*".toMediaTypeOrNull())

                    imagetruck4 =
                        MultipartBody.Part.createFormData(
                            "image_4",
                            imageFile?.name,
                            requestGalleryImageFile
                        )
//                    viewModel.LoaderimageAPI(
//                        "Bearer " + userPref.getToken().toString(),
//                        vehicleid,
//                        imagetruck4
//                    )
//                    USER_IMAGE_UPLOADED = true
//                    if(USER_IMAGE_UPLOADED) {
//                        uploadUserImageApi()
//                    }
                }
            }
        } else if (requestCode == CAMERA_LICENSE_BACK) {
            val myBitmap = BitmapFactory.decodeFile(file!!.absolutePath)
            binding.ivTruck4.setImageBitmap(myBitmap)
//                 mPhotoFile = File(getPath(photoURICamera!!))
            val requestFile: RequestBody =
                file!!.asRequestBody("image/*".toMediaTypeOrNull())

            imagetruck4 =
                MultipartBody.Part.createFormData(
                    "image_4",
                    file!!.name,
                    requestFile
                )
//            viewModel.LoaderimageAPI(
//                "Bearer " + userPref.getToken().toString(),
//                vehicleid,
//                imagetruck4
//            )

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
            }
        }else if (requestCode == pickPdf5) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    image = data.data!!
                    var path1 = writeFileContent(image!!)
                    var fileSelected = File(path1)
                    binding.tvOtherdoc.text = fileSelected.name

                    if (fileSelected!!.endsWith(".jpg") || fileSelected!!.endsWith(".jpeg") || fileSelected!!.endsWith(
                            ".png"
                        ) ||
                        fileSelected!!.endsWith(".webp") || fileSelected!!.endsWith(".svg")) {
//                        binding.tvPollutiondoc.text = imageFile?.absolutePath
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

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "*/*"
        if (flagforpdf == "firstone"){
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf)
        }else if (flagforpdf == "secondone"){
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf1)
        }else if (flagforpdf == "thirdone"){
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf2)
        }else if (flagforpdf == "fourthone"){
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf3)
        }else if (flagforpdf == "fifthone"){
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf4)
        } else if (flagforpdf == "sixthone") {
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, pickPdf5)
        }
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