package com.govahanpartner.com.ui.driver

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityAddDrivingLicenseBinding
import com.govahanpartner.com.ui.DashboardActivity
import com.govahanpartner.com.ui.common.LoginActivity
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddDrivingLicenseActivity : BaseActivity() {
    lateinit var binding:ActivityAddDrivingLicenseBinding
    val FILE_BROWSER_CACHE_DIR = "driving_licence"
    private val viewModel: LoginViewModel by viewModels()
    var id=""
    var flag=""
    var flag1=""
    var id1=""
    private var userimage : MultipartBody.Part? = null
    private val pickImageCamera = 1
    private val pickImageGallery = 2
    private lateinit var currentPhotoPath: String
    private var mPhotoFile: File? = null
    private var photoURICamera: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_driving_license)
        if (intent!=null){
            id=intent.getStringExtra("id").toString()
            flag=intent.getStringExtra("flag").toString()
            flag1=intent.getStringExtra("flag1").toString()
        }

        if (flag1.equals("signup")){
            id1=id
        }
        else{
            id1= userPref.getid().toString()
        }
        binding.one.setOnClickListener {
            selectImage()
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        binding.upload.setOnClickListener{
            if (userimage == null){
                userimage = MultipartBody.Part.createFormData("driving_licence", "")
            }
            viewModel.add_driving_licence(id1,userimage!!)
        }
        viewModel.loginResponse.observe(this){
            if (it?.error == false) {
                userPref.setdriver_license("dsghj" )

                if (flag1.equals("signup")){
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("flag",flag)
                    startActivity(intent)
                    toast(it.message)
                }
                else{
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("flag",flag)
                    startActivity(intent)
                    finishAffinity()
                    toast(it.message)

                }

            }
                else{
                toast(it.message)
            }

        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==12 && data != null ) {


            val fileUris = data?.data
            Glide.with(this).load(R.drawable.ic_baseline_picture_as_pdf_24).into(binding.image1)

            var path = writeFileContent(fileUris!!)
            var fileSelected = File(path)

            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                fileSelected!!
            )
            userimage = MultipartBody.Part.createFormData("driving_licence", fileSelected!!.name, requestFile)
        }
        else if (requestCode == pickImageCamera) {
            Glide.with(this).load(photoURICamera).into(binding.image1)

//            binding.ivPict.setImageURI(photoURICamera)
            val file = File(currentPhotoPath)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            userimage = MultipartBody.Part.createFormData("driving_licence", file.name, requestFile)
        } else if (requestCode == pickImageGallery && data != null) {
            val selectedImage = data.data
            try {
                Glide.with(this).load(selectedImage).into(binding.image1)
//                binding.ivPict.setImageURI(selectedImage)
                val file = File(getPath(selectedImage!!))
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                userimage = MultipartBody.Part.createFormData("driving_licence", file.name, requestFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose From Gallery","Files", "Cancel")
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
            } else if (options[item] == "Files") {
                dialog.dismiss()
                selectPdf()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }



    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->

            takePictureIntent.resolveActivity(this.packageManager)?.also {

                val photoFile: File? = try {
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
                    mPhotoFile = photoFile
                    photoURICamera = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, pickImageCamera)
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
        Log.d("image path", cursor.getString(column_index))
        return cursor.getString(column_index)
    }

    private fun requestStoragePermission(isCamera: Boolean) {
        Dexter.withActivity(this)
            .withPermissions(
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
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {
                    token!!.continuePermissionRequest()
                }
            })
            .withErrorListener {
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
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
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
        val uri = Uri.fromParts("package", applicationContext.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

}