package com.govahanpartner.com.ui.vendor

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.CursorLoader
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityEditProfileVendorBinding
import com.govahanpartner.com.permission.RequestPermission
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.ProfileViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditProfileVendorActivity : BaseActivity() {
    private val viewModel: ProfileViewModel by viewModels()
    lateinit var binding: ActivityEditProfileVendorBinding
    var imageFile: MultipartBody.Part? = null
    private val pickImageCamera = 2
    private val pickImageGallery = 1
    lateinit var currentPhotoPath: String
    var mPhotoFile: File? = null
    var photoURICamera: Uri? = null
    var uri: Uri? = null
    var flag = false
    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickMultipleMediaLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile_vendor)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        pickSingleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                if (it.resultCode != Activity.RESULT_OK) {
                    Toast.makeText(this, "Failed picking media.", Toast.LENGTH_SHORT).show()
                }
                else {
                    val uri = it.data?.data
                    Glide.with(this).load(uri).placeholder(R.drawable.profile).into(binding.ivDriver).toString()
                    val path = getPathFromURI(uri)

                    if (path != null) {
                        mPhotoFile = File(path)
                        Glide.with(this).load(mPhotoFile).into(binding.ivDriver)
                        //   images.add(imageFile!!.absolutePath.toString())
                    }

                    val requestFile: RequestBody =
                        mPhotoFile!!.asRequestBody("image/*".toMediaTypeOrNull())

                    flag = true
                    imageFile = MultipartBody.Part.createFormData(
                        "profile_image",
                        mPhotoFile!!.name,
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
                    pickImageCamera
                )
            }else {
                selectImage()
            }
        }



//        binding.ivCam.setOnClickListener {
//            RequestPermission.requestMultiplePermissions(this)
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.CAMERA
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.CAMERA),
//                    pickImageCamera
//                )
//            } else {
//                selectImage()
//            }
//        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.profileeditResponse.observe(this) {
            if (it.status == 1) {
                toast(it.message)
                finish()
            } else {
                toast(it.message)
            }
        }
        viewModel.GetProfileAPI(
            "Bearer " + userPref.getToken().toString(),
        )
        viewModel.getProfileResponse.observe(this) {
            if (it?.status == 1) {
                binding.etName.setText(it.data?.name.toString())
                binding.edtPhonenumber.setText(it.data?.mobileNumber.toString())
                binding.etEmail.setText(it.data?.email.toString())
                if (it.data?.address == null){
                    binding.etAddress.setText(" ")
                    toast("abc")
                }else{
                    binding.etAddress.setText(it.data?.address.toString())
                    toast("abcd")
                }

                if (it.data?.profileImage == null) {
                    flag = false
                } else {
                    Glide.with(this).load(it.data?.profileImage).into(binding.ivDriver)
                    flag = true
                }
            } else {
//                toast(.message)
            }
        }
        binding.btnSubmit.setOnClickListener {
            if (binding.etName.text.toString().isNullOrEmpty()) {
                snackbar("Please enter name")
            } else if (binding.edtPhonenumber.text.toString().isNullOrEmpty()) {
                snackbar("Please enter Mobile")
            } else if (binding.etEmail.text.toString().isNullOrEmpty()) {
                snackbar("Please enter email")
            } else if (binding.etAddress.text.toString().isNullOrEmpty()) {
                snackbar("Please enter Address")
            } else if (flag == false) {
                snackbar("Please select image")
            } else {
                if (imageFile == null) {
                    val requestFile = "".toRequestBody("image/*".toMediaTypeOrNull())
                    imageFile = MultipartBody.Part.createFormData("profile_image", "", requestFile)
                }

                viewModel.UpdateProfile(
                    "Bearer " + userPref.getToken().toString(),
                    binding.etName.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.edtPhonenumber.text.toString(),
                    binding.etAddress.text.toString(),
                    "Android",
                    "Android",
                    "Android",
                    imageFile!!

                )
            }
        }
    }

//    private fun selectImage() {
//        val options = arrayOf<CharSequence>("Take Photo", "Choose From Gallery", "Cancel")
//        val pm: PackageManager = packageManager
//        val builder =
//            android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
//        builder.setTitle("Select Option")
//        builder.setItems(
//            options
//        ) { dialog: DialogInterface, item: Int ->
//            if (options[item] == "Take Photo") {
//                dialog.dismiss()
//                RequestPermission.requestMultiplePermissions(this)
//                if (ContextCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.CAMERA
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(Manifest.permission.CAMERA),
//                        pickImageCamera
//                    )
//                } else {
//                    selectImage()
//                }
//            } else if (options[item] == "Choose From Gallery") {
//                dialog.dismiss()
//                requestStoragePermission(false)
//            } else if (options[item] == "Cancel") {
//                dialog.dismiss()
//            }
//        }
//        builder.show()
//    }

    private fun dispatchTakePictureIntent() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        photoURICamera =
            this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, pickImageCamera)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
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
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

//            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
//                try {
//                    imageFile = createImageFile()!!
//                } catch (ex: IOException) {
//                }
//                if (imageFile != null) {

//                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
//                        intent.resolveActivity(packageManager)?.also {
//                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//                        }
//                    }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                captureImage()

                // Setup max pick medias

            } else {
                captureImage()
//                captureImage()
//                val values = ContentValues()
//                values.put(MediaStore.Images.Media.TITLE, "New Picture")
//                values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//                photoURICamera =
//                    this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURICamera)
//                startActivityForResult(takePictureIntent, pickImageCamera)

            }




            dialog.dismiss()
        }
//            }
//        }

        val GalleryButton = view.findViewById<TextView>(R.id.gallery_open)
        GalleryButton.setOnClickListener {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Setup pick 1 image/video
                    pickSingleMediaLauncher.launch(
                        Intent(MediaStore.ACTION_PICK_IMAGES)
                    )

                // Setup max pick medias

            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, pickImageGallery)

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
                    mPhotoFile = createImageFile()
                    // Continue only if the File was successfully created
                    if (mPhotoFile != null) {
                         photoURICamera = FileProvider.getUriForFile(
                            this,
                            "com.govahanpartner.com.fileprovider.unique",
                            mPhotoFile!!
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURICamera)

                        startActivityForResult(takePictureIntent, pickImageCamera)
                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    displayMessage(baseContext, ex.message.toString())
                }

            } else {
                displayMessage(baseContext, "Null")
            }
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    private fun displayMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
    private fun getPath(uri: Uri): String {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader = androidx.loader.content.CursorLoader(this, uri, data, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        Log.d("image path", cursor.getString(column_index))
        return cursor.getString(column_index)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG", "@@onActivityResult:")



            if (requestCode == pickImageCamera && resultCode == Activity.RESULT_OK) {

                val myBitmap = BitmapFactory.decodeFile(mPhotoFile!!.absolutePath)
                binding.ivDriver.setImageBitmap(myBitmap)
//                 mPhotoFile = File(getPath(photoURICamera!!))
                val requestFile: RequestBody =
                    mPhotoFile!!.asRequestBody("image/*".toMediaTypeOrNull())

                flag = true
                imageFile = MultipartBody.Part.createFormData(
                    "profile_image",
                    mPhotoFile!!.name,
                    requestFile
                )
            }

        else if (requestCode == pickImageGallery && data != null) {
            if (resultCode == RESULT_OK) {
                try {
                    if (data != null) {
                        uri = data.data!!
                        val path = getPathFromURI(uri)
                        val file = File(getPath(uri!!))
                        flag = true
                        if (path != null) {
                            mPhotoFile = File(path)
                            Glide.with(this).load(mPhotoFile).into(binding.ivDriver)
                            //   images.add(imageFile!!.absolutePath.toString())
                        }
                        //   images.add(path.toString())
                        val requestFile: RequestBody =
                            mPhotoFile!!.asRequestBody("image/*".toMediaTypeOrNull())

                        imageFile = MultipartBody.Part.createFormData("profile_image", file.name, requestFile)

                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }


            }

        }
    }

//    private fun openCamera() {
////        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
////            takePictureIntent.resolveActivity(packageManager)?.also {
////
////                val photoFile: File? = try {
////                    createImageFile()
////                } catch (ex: IOException) {
////
////
////                    null
////                }
////
////                photoFile?.also {
////                    val photoURI: Uri =
////                        FileProvider.getUriForFile(
////                            this,
////                            "com.transportationapp.fileprovider",
////                            it
////                        )
////                    mPhotoFile = photoFile
////                    photoURICamera = photoURI
////                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "New Picture")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//        photoURICamera =
//            this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURICamera)
//        startActivityForResult(takePictureIntent, pickImageCamera)
//    }

//    private fun getPath(uri: Uri): String {
//        val data = arrayOf(MediaStore.Images.Media.DATA)
//        val loader =
//            CursorLoader(this, uri, data, null, null, null)
//        val cursor = loader.loadInBackground()
//        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        cursor.moveToFirst()
//        Log.d("image path", cursor.getString(column_index))
//        return cursor.getString(column_index)
//    }

//    private fun requestStoragePermission(isCamera: Boolean) {
//        Dexter.withActivity(this)
//            .withPermissions(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA
//            )
//            .withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                    // check if all permissions are granted
////                    if (report.areAllPermissionsGranted()) {
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

//    private fun openGallery() {
//        val pickPhoto =
//            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(pickPhoto, pickImageGallery)
//    }

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

//    private fun openSettings() {
//        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//        val uri = Uri.fromParts("package", "com.govahanpartner.com", null)
//        intent.data = uri
//        startActivityForResult(intent, 101)
//    }

}