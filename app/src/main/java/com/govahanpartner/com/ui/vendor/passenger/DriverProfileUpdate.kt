package com.govahanpartner.com.ui.vendor.passenger

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.CursorLoader
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityDriverProfileUpdateBinding
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.AddDriverViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DriverProfileUpdate : BaseActivity() {
    private lateinit var binding : ActivityDriverProfileUpdateBinding
    private val viewModel: AddDriverViewModel by viewModels()
    var driverid: String = ""
    private var imageFile: MultipartBody.Part? = null
    private val pickImageCamera = 1
    private val pickImageGallery = 2
     var currentPhotoPath: String =""
    var mPhotoFile: File? = null
    var photoURICamera: Uri?=null
    var flag:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_profile_update)

        if (intent!=null){
            driverid = intent.getStringExtra("driverid").toString()
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.btnAddthisdriver.setOnClickListener {

            if (binding.etFullName2.text.equals("")){
                toast("Enter driver name.")
            }
            else if (binding.edtMobile.text.equals("")){
                toast("Enter mobile number.")
            }
            else if (binding.edtDrivingexp.text.equals("")){
                toast("Enter Driving experience.")
            }
            else if (binding.licenceno.text.equals("")){
                toast("Enter licence number.")
            }
            else if (binding.etPassword.text.equals("")){
                toast("Enter password.")
            }
            viewModel.DriverProfileUpdateAPI(
                "Bearer "+userPref.getToken().toString(),
                driverid,
                binding.etFullName2.text.toString(),
                binding.edtMobile.text.toString(),
                binding.edtDrivingexp.text.toString(),
                binding.licenceno.text.toString(),
                "Android",
                "Android",
                "Android",
                binding.etPassword.text.toString(),
                imageFile
            )
        }
        viewModel.DriverProfileAPI(
            "Bearer "+userPref.getToken().toString(),
            driverid
        )
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.DriverProfileResponse.observe(this){
            if (it.status == 1){
                binding.etFullName2.setText(it.data?.name)
                binding.edtDrivingexp.setText(it.data?.experience)
                binding.edtMobile.setText( it.data?.mobileNumber.toString())
                binding.licenceno.setText(it.data?.licenceNumber.toString())
                binding.edtEmail.setText(it.data?.email.toString())
                binding.etPassword.setText(it.data?.password)
                Glide.with(this).load(it.data?.profileImage).placeholder(R.drawable.profile).into(binding.ivDriver).toString()
            }else{
                toast(it.message)
            }
        }
        viewModel.DriverProfileUpdateResponse.observe(this){
            if (it.status == 1){
                toast(it.message)
                finish()
            }else{
                toast(it.message)
            }
        }
        if (imageFile == null) {
            val requestFile =
                "".toRequestBody("image/jpg".toMediaTypeOrNull())
            imageFile =
                MultipartBody.Part.createFormData("profile_image", "", requestFile)
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        binding.ivDriver.setOnClickListener{
            selectImage()
        }
    }
    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose From Gallery", "Cancel")
        val pm: PackageManager = packageManager
        val builder =
            android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle("Select Option")
        builder.setItems(
            options
        ) { dialog: DialogInterface, item: Int ->
            if (options[item] == "Take Photo") {
                dialog.dismiss()
                requestStoragePermission(true)
            } else if (options[item] == "Choose From Gallery") {
                dialog.dismiss()
                requestStoragePermission(false)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG", "@@onActivityResult:")
        if (requestCode == pickImageCamera) {
            val bitmap = BitmapFactory.decodeStream(
                contentResolver.openInputStream(photoURICamera!!)
            )

            Glide.with(this).load(bitmap!!)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder))
                .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
                .into(binding.ivDriver)
            flag = true

            mPhotoFile = File(getPath(photoURICamera!!))
            val requestFile = mPhotoFile?.asRequestBody("image/*".toMediaTypeOrNull())
            imageFile = MultipartBody.Part.createFormData("profile_image", mPhotoFile?.name, requestFile!!)

//            placeOrderApi("2")
        } else if (requestCode == pickImageGallery && data != null) {
            val selectedImage = data.data
            try {
                binding.ivDriver.setImageURI(selectedImage)
                val file = File(getPath(selectedImage!!))
                val requestFile = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                imageFile = MultipartBody.Part.createFormData("profile_image", file.name, requestFile)
                flag = true
//                placeOrderApi("2")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        photoURICamera = this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURICamera)
        startActivityForResult(cameraIntent, pickImageCamera)
    }

    private fun getPath(uri: Uri): String {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader =
            CursorLoader(this, uri, data, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        Log.d("image path", cursor.getString(column_index))
        return cursor.getString(column_index)
    }

    private fun requestStoragePermission(isCamera: Boolean) {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        if (isCamera) {
                            openCamera()
                        } else {
                            openGallery()
                        }
                    }
                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            })
            .withErrorListener { error ->

            }
            .onSameThread()
            .check()
    }

    private fun openGallery() {
        val pickPhoto =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, pickImageGallery)
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage(
            "This app needs permission to use this feature. You can grant them in app settings."
        )
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog: DialogInterface, which: Int ->
            openSettings()
            dialog.cancel()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", "com.govahanpartner.com", null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

}