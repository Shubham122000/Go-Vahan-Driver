package com.gvpartner.com.ui.common

import android.Manifest
import android.content.CursorLoader
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.databinding.ActivityAddAccountBinding
import com.gvpartner.com.permission.RequestPermission
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.WalletViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddAccountActivity : BaseActivity() {
    private lateinit var binding : ActivityAddAccountBinding
    private val viewModel: WalletViewModel by viewModels()
    val CAMERA_PERM_CODE_LICENSE_FRONT = 103
    var currentPhotoPath = ""
    var photoURI: Uri? = null
    private var file: File? = null
    private val PICK_IMAGE_CAMERA: Int = 1
    private val PICK_IMAGE_GALLERY: Int = 2
    var imageFile: File? = null
    var imagePath = ""
    var image1: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_account)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.signCam.setOnClickListener {

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
        binding.PaymentButton.setOnClickListener {
            if (image1 == null) {
                val requestFile = "".toRequestBody("image/*".toMediaTypeOrNull())
                image1 = MultipartBody.Part.createFormData("image",file?.name, requestFile)
            }
            if (binding.bankName.text.toString().equals("")) {
                toast("Please enter bank name.")
            } else if (binding.holdername.text.toString().equals("")) {
                toast("Please enter Account Holder's name.")
            } else if (binding.accountNumber.text.toString().equals("")) {
                toast("Please enter Account number.")
            } else if (binding.accountBranch.text.toString().equals("")) {
                toast("Please enter Account branch.")
            } else if (binding.ifscCode.text.toString().equals("")) {
                toast("Please enter IFSC.")
            }else if (binding.upi.text.toString().equals("")) {
                toast("Please enter UPI Id.")
            } else {
                viewModel.add_bank_account(
                    "Bearer " + userPref.getToken().toString(),
                    binding.accountNumber.text.toString(),
                    binding.holdername.text.toString(),
                    binding.ifscCode.text.toString(),
                    binding.bankName.text.toString(),
                    binding.accountBranch.text.toString(),binding.upi.text.toString(),image1!!
                )
            }
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.addmoneytowalletresponse.observe(this)
        {
            if (it.error == false) {
                toast(it.message)
         finish()
            } else {
                toast(it.message!!)
            }
        }
    }

        @Throws(IOException::class)
        private fun createImageFile(): File {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                currentPhotoPath = absolutePath

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

                if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
                    try {
                        imageFile = createImageFile()
                    } catch (ex: IOException) {
                    }
                    if (imageFile != null) {
                        photoURI = FileProvider.getUriForFile(
                            this, "com.govahanpartner.com.fileprovider.unique", imageFile!!
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                            startActivityForResult(takePictureIntent, PICK_IMAGE_CAMERA)

                        dialog.dismiss()
                    }
                }
            }

            val GalleryButton = view.findViewById<TextView>(R.id.gallery_open)
            GalleryButton.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"

                    startActivityForResult(intent, PICK_IMAGE_GALLERY)

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

        //    @SuppressLint("MissingSuperCall")
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    if (requestCode == PICK_IMAGE_GALLERY) {
//        if (resultCode == RESULT_OK) {


            if (requestCode == PICK_IMAGE_GALLERY && data != null) {
                val selectedImage = data.data
                try {
                    imagePath = selectedImage.toString()
//                    binding.imgUser.visibility = View.VISIBLE
                    binding.signatureImage.setImageURI(selectedImage)
                    val file = File(getPath(selectedImage!!))
                    val requestFile =
                        file.asRequestBody("image/*".toMediaTypeOrNull())
                    image1 =
                        MultipartBody.Part.createFormData("image", file.name, requestFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (requestCode == PICK_IMAGE_CAMERA) {
                if (resultCode == RESULT_OK) {

                    try {
                        val file = File(currentPhotoPath)
                        binding.signatureImage.setImageURI(photoURI)
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        image1 = MultipartBody.Part.createFormData("image", file.name, requestFile)


                    } catch (e: java.lang.Exception) {
                    }

                }
            }

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

}