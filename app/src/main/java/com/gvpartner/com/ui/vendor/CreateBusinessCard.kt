package com.gvpartner.com.ui.vendor

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.CursorLoader
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityCreateBusinessCardBinding
import com.gvpartner.com.permission.RequestPermission
import com.gvpartner.com.utils.CommonUtils
import com.gvpartner.com.viewmodel.BusinessCard
import com.gvpartner.com.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateBusinessCard : BaseActivity() {
    private val PICK_IMAGE_CAMERA = 1
    private val PICK_IMAGE_GALLERY = 2
    private var imageFile: MultipartBody.Part? = null
    private var mUri: Uri? = null
    private var file: File? = null
    var image = ""
    var uri: Uri? = null
    var flag: Boolean = false
    lateinit var currentPhotoPath: String
    private val viewModel: BusinessCard by viewModels()
    private val viewModel1: HomeViewmodel by viewModels()
    private lateinit var binding: ActivityCreateBusinessCardBinding
    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<Intent>
    var flag1=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_create_business_card)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_business_card)

        if (intent!=null){
           flag1=intent.getStringExtra("flag").toString()
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        if (flag1.equals("edit")){
            binding.title.text="Edit Business Card"
            viewModel1.GetBusinessdetails("Bearer " + userPref.getToken())
            viewModel1.BusinessListResponse.observe(this) {
                if (it!!.status == 1) {

                    if (it.data!=null){
                        Glide.with(this).load(it.data?.image)
                            .apply(RequestOptions.placeholderOf(R.drawable.transportlogo))
                            .apply(RequestOptions.errorOf(R.drawable.transportlogo))
                            .into(binding.ivDriver)
                        binding.edtCompanyname.setText(it.data?.companyName.toString())
                        binding.edtPhonenumber.setText(it.data?.mobile.toString())
                        binding.edtEmail.setText(it.data?.email.toString())
                        binding.edtAddress.setText(it.data?.address.toString())
                        if (it.data?.role==null){
                            binding.edtRole.setText("")
                        }else{
                            binding.edtRole.setText(it.data?.role.toString())
                        }

                        binding.edtFullname.setText(it.data?.fullName.toString())
                        flag=true
                    }
                    else{
                        flag=false
                    }
                }
            }
        } else{
            binding.title.text="Create Business Card"
        }

        binding.ivCam.setOnClickListener {
            selectImage()
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.createBusinesscardResponse.observe(this) {
            if (it?.status == 1) {
                Toast.makeText(this, "Business created successfully", Toast.LENGTH_LONG).show()
                finish()
            } else {
                snackbar(it?.message!!)
            }
        }

        binding.btnSubmit.setOnClickListener {
            BusinesscardApi()
        }

        pickSingleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                Log.d("TAG", "@@onActivityResult:.....")
                if (it.resultCode != Activity.RESULT_OK) {
                    Toast.makeText(this, "Failed picking media.", Toast.LENGTH_SHORT).show()
                } else {
                    val uri = it.data?.data
//                    Glide.with(this).load(uri).placeholder(R.drawable.profile).into(binding.ivDriver).toString()
                    val path = getPathFromURI(uri)

                    if (path != null) {
                        file = File(path)
                        Glide.with(this).load(file).into(binding.ivDriver)
                        //   images.add(imageFile!!.absolutePath.toString())
                    }

                    val requestFile: RequestBody =
                        file!!.asRequestBody("image/*".toMediaTypeOrNull())

                    flag = true
                    imageFile = MultipartBody.Part.createFormData(
                        "image",
                        file!!.name,
                        requestFile
                    )
                }
            }
        binding.ivCam.setOnClickListener {
            RequestPermission.requestMultiplePermissions(this)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    PICK_IMAGE_CAMERA
                )
            }else {
                selectImage()
            }
        }
    }

    fun BusinesscardApi() {
        if (binding.edtCompanyname.text.toString().isNullOrEmpty()) {
            snackbar("Please enter company name.")
        } else if (binding.edtFullname.text.toString().isNullOrEmpty()) {
            snackbar("Please enter name")
        } else if (binding.edtPhonenumber.text.toString().isNullOrEmpty()) {
            snackbar("Please enter mobile number.")
        } else if (binding.edtPhonenumber.text.toString().length < 10) {
            snackbar("Please enter valid mobile number")
        } else if (binding.edtEmail.text.toString().isNullOrEmpty()) {
            snackbar("Please enter email")
        } else if (!CommonUtils.isValidMail(binding.edtEmail.text.toString().trim())) {
            snackbar("Please enter valid email")
        } else if (binding.edtAddress.text.toString().isNullOrEmpty()) {
            snackbar("Please enter address")
        } else {
            if (flag1.equals("add")) {
                if (imageFile == null) {
                    val requestFile =
                        RequestBody.create("image/*".toMediaTypeOrNull(), "")
                    imageFile =
                        MultipartBody.Part.createFormData("image", "", requestFile)
                }
                viewModel.BusinessCardAPI(
                    "Bearer " + userPref.getToken().toString(),
                    binding.edtCompanyname.text.toString(),
                    binding.edtFullname.text.toString(),
                    binding.edtPhonenumber.text.toString(),
                    binding.edtEmail.text.toString(),
                    binding.edtAddress.text.toString(),binding.edtRole.text.toString(),
                    imageFile
                )
            }else{
                if (imageFile == null) {
                    val requestFile =
                        RequestBody.create("image/*".toMediaTypeOrNull(), "")
                    imageFile =
                        MultipartBody.Part.createFormData("image", "", requestFile)
                }
                viewModel.update_business_cardApi(
                    "Bearer " + userPref.getToken().toString(),
                    binding.edtCompanyname.text.toString(),
                    binding.edtFullname.text.toString(),
                    binding.edtPhonenumber.text.toString(),
                    binding.edtEmail.text.toString(),
                    binding.edtAddress.text.toString(),binding.edtRole.text.toString(),
                    imageFile
                )

            }
        }
    }
//    private fun requestStoragePermission(isCamera: Boolean) {
//        Dexter.withActivity(this)
//            .withPermissions(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA
//            )
//            .withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                        if (isCamera) {
//                            openCamera()
//                        } else {
//                            openGallery()
//                        }
////                    }
//                    // check for permanent denial of any permission
////                    if (report.isAnyPermissionPermanentlyDenied) {
////                        // show alert dialog navigating to Settings
////                        showSettingsDialog()
////                    }
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
//                    token: PermissionToken?
//                ) {
//                    token!!.continuePermissionRequest()
//                }
//            })
//            .withErrorListener { error ->
//
//            }
//            .onSameThread()
//            .check()
//    }
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", "com.govahanpartner.com", null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

//
//    private fun showSettingsDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Need Permissions")
//        builder.setMessage(
//            "This app needs permission to use this feature. You can grant them in app settings."
//        )
//        builder.setPositiveButton(
//            "GOTO SETTINGS"
//        ) { dialog: DialogInterface, which: Int ->
//            openSettings()
//            dialog.cancel()
//        }
//        builder.setNegativeButton(
//            "Cancel"
//        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
//        builder.show()
//    }
//
//    private fun openGallery() {
//        val pickPhoto =
//            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
//    }
    private fun selectImage() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_drawer, null)
        dialog.setCancelable(true)

        val CameraButton = view.findViewById<TextView>(R.id.camera_open)
        CameraButton.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                captureImage()

                // Setup max pick medias

            } else {
                captureImage()
            }




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
                startActivityForResult(intent, PICK_IMAGE_GALLERY)

            }
            dialog.dismiss()
        }
        val cancel = view.findViewById<TextView>(R.id.cancel)
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(view)
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
                    if (file != null) {
                        mUri = FileProvider.getUriForFile(
                            this,
                            "com.govahanpartner.com.fileprovider.unique",
                            file!!
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)

                        startActivityForResult(takePictureIntent, PICK_IMAGE_CAMERA)
                    }
                } catch (ex: Exception) {

                }

            } else {
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CAMERA && resultCode == Activity.RESULT_OK) {

            val myBitmap = BitmapFactory.decodeFile(file!!.absolutePath)
            binding.ivDriver.setImageBitmap(myBitmap)
            val requestFile: RequestBody =
                file!!.asRequestBody("image/*".toMediaTypeOrNull())

            flag = true
            imageFile = MultipartBody.Part.createFormData(
                "image",
                file!!.name,
                requestFile
            )
        }
        else if (requestCode == PICK_IMAGE_GALLERY && data != null) {
            if (resultCode == RESULT_OK) {
                try {
                    if (data != null) {
                        uri = data.data!!
                        val path = getPathFromURI(uri)

                        if (path != null) {
                            file = File(path)
                            Glide.with(this).load(file).into(binding.ivDriver)
                            //   images.add(imageFile!!.absolutePath.toString())
                        }
                        //   images.add(path.toString())
                        val requestFile: RequestBody =
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())

                        flag = true
                        imageFile = MultipartBody.Part.createFormData(
                            "image",
                            file!!.name,
                            requestFile
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        else {
            Log.d("TAG", "@@onActivityResult:.....$requestCode")

        }
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
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {

                var photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                    photoFile?.also {
                    val photoURI: Uri =
                        FileProvider.getUriForFile(
                            this,
                            "com.govahanpartner.com.fileprovider.unique",
                            it
                        )
                        file = photoFile
                    mUri = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, PICK_IMAGE_CAMERA)
                }
            }
        }

    }


    private fun getPath(uri: Uri): String {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, uri, data, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 ->
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera()
                } else {
                    val cameraPermission = permissions[0]
                    val showRationaleCamera = shouldShowRequestPermissionRationale(cameraPermission)
                    if (!showRationaleCamera) {
                        permissionAlert("Required CAMERA permission to open your camera")
                    } else {
                        Toast.makeText(
                            this,
                            "Permission denied to open your camera",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            2 -> {
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
            }
        }
    }

    private fun permissionAlert(message: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle("Need Permission")
        builder.setMessage(message)
        builder.setNegativeButton("NO", null)
        builder.setPositiveButton("YES") { dialogInterface, i ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", this.packageName, null)
            intent.data = uri
            startActivityForResult(intent, 1)
        }
        builder.create()
        builder.show()
    }
}