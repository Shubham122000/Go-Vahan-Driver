package com.gvpartner.com.ui.common

import android.Manifest
import android.app.Activity
import android.content.CursorLoader
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityUploadDocumentsRegardingTripBinding
import com.gvpartner.com.permission.RequestPermission
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.AddDriverViewModel
import com.gvpartner.com.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UploadDocumentsRegardingTrip : BaseActivity() {
    private val PICK_IMAGE_CAMERA: Int = 1
    private val PICK_IMAGE_GALLERY: Int = 2
    private val PICK_IMAGE_CAMERA2: Int = 5
    private val PICK_IMAGE_GALLERY2: Int = 6
    private val PICK_IMAGE_GALLERY1: Int = 3
    private val PICK_IMAGE_CAMERA1: Int = 4
    var imageFile: File? = null
    var image1: MultipartBody.Part? = null
    var image2: MultipartBody.Part? = null
    var image3: MultipartBody.Part? = null
    var customersignatureimage: MultipartBody.Part? = null
    var photoURI: Uri? = null
    private var file: File? = null
    var flag: Boolean = false
    var flag1 = ""
    var imagePath = ""
    var currentPhotoPath = ""
    var id=""
    var flag2=""
    val CAMERA_PERM_CODE_LICENSE_BACK = 104
    val CAMERA_PERM_CODE_LICENSE_FRONT = 103
    var type=""
    private var requestCodeForLegacy: Int? = null
    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<Intent>
    private val viewModel: AddDriverViewModel by viewModels()
    private lateinit var binding: ActivityUploadDocumentsRegardingTripBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_documents_regarding_trip)
        if (intent!=null){
            id= intent.getStringExtra("id").toString()
            flag2= intent.getStringExtra("flag").toString()
        }
        if (flag2.equals("OngoingLoader")){
            type="1"
        }else{
            type="2"
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

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
                    if (requestCodeForLegacy == PICK_IMAGE_GALLERY){
                        Glide.with(this).load(file).into(binding.biltiImage)
                        image1 = MultipartBody.Part.createFormData(
                            "builty",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    }else if (requestCodeForLegacy == PICK_IMAGE_GALLERY1){
                        Glide.with(this).load(file).into(binding.ivPod)
                        image2 = MultipartBody.Part.createFormData(
                            "pod",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    }else if (requestCodeForLegacy == PICK_IMAGE_GALLERY2){
                        Glide.with(this).load(file).into(binding.signatureImage)
                        image3 = MultipartBody.Part.createFormData(
                            "signature",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    }

                }
            }

        viewModel.DriverProfileResponse.observe(this) {
            if (it?.error == false) {
                toast(it.message)
                finish()
            } else {
                snackbar(it?.message!!)
            }
        }
        binding.camera.setOnClickListener {
            flag1 = "firstone"
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
                requestCodeForLegacy = PICK_IMAGE_GALLERY

            }
        }
        binding.camera2.setOnClickListener {
            flag1 = "second"
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
                requestCodeForLegacy = PICK_IMAGE_GALLERY1
            }
        }
        binding.signCam.setOnClickListener {
            flag1 = "sign"
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
                requestCodeForLegacy = PICK_IMAGE_GALLERY2
            }
        }


//        binding.submit.setOnClickListener { binding.signaturepad!!.clear() }
        binding.submit.setOnClickListener {

            if (binding.biltiImage.equals("")) {
                snackbar("Please select image")
                snackbar("@@@Please select image")
//                val file = File((binding.tvSelectImage.text.toString()))

                val requestFile = "file".toRequestBody("image/*".toMediaTypeOrNull())
                image1 =
                    MultipartBody.Part.createFormData("builty", file?.name, requestFile)

            } else if (binding.ivPod.equals("")) {

                val requestFile = "file".toRequestBody("image/*".toMediaTypeOrNull())
                image2 = MultipartBody.Part.createFormData("pod", file?.name, requestFile)

            } else if (binding.signatureImage.equals("")) {
                val requestFile = "file".toRequestBody("image/*".toMediaTypeOrNull())
                image3 = MultipartBody.Part.createFormData("signature", file?.name, requestFile)

            } else {
                /*   val requestFile =
                    RequestBody.create("image/jpg".toMediaTypeOrNull(), "")
                image1 = MultipartBody.Part.createFormData("builty", "", requestFile)
            }
*/

                Log.d("typeNEW",type)
                viewModel.createBookingDocument(
                    "Bearer " + userPref.getToken().toString(),
                    id,
                    image2,
                    image3,
                    image1

                )
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
                    if (flag1 == "firstone") {
                        startActivityForResult(takePictureIntent, PICK_IMAGE_CAMERA)
                    } else if (flag1 == "second") {
                        startActivityForResult(takePictureIntent, PICK_IMAGE_CAMERA1)
                    } else if (flag1 == "sign") {
                        startActivityForResult(takePictureIntent, PICK_IMAGE_CAMERA2)
                    }
                    dialog.dismiss()
                }
            }
        }

        val GalleryButton = view.findViewById<TextView>(R.id.gallery_open)
        GalleryButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pickSingleMediaLauncher.launch(Intent(MediaStore.ACTION_PICK_IMAGES))
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                if (flag1 == "firstone") {
                    startActivityForResult(intent, PICK_IMAGE_GALLERY)
                } else if (flag1 == "second") {
                    startActivityForResult(intent, PICK_IMAGE_GALLERY1)
                } else if (flag1 == "sign") {
                    startActivityForResult(intent, PICK_IMAGE_GALLERY2)
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            1 ->
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty()
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    openCamera()
//                } else {
//                    val cameraPermission = permissions[0]
//                    val showRationaleCamera = shouldShowRequestPermissionRationale(cameraPermission)
//                    if (!showRationaleCamera) {
//                        permissionAlert("Required CAMERA permission to open your camera")
//                    } else {
//                        Toast.makeText(
//                            this,
//                            "Permission denied to open your camera",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//            2 -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    val pickPhoto =
//                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
//                } else {
//                    val permission = permissions[0]
//                    val showRationale = shouldShowRequestPermissionRationale(permission)
//                    if (!showRationale) {
//                        permissionAlert("Required STORAGE permission to access gallery")
//                    } else {
//                        Toast.makeText(
//                            this,
//                            "Permission denied to read your External storage",
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                    }
//                }
//            } 3 -> {
//            if (grantResults.isNotEmpty()
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
//            ) {
//                openCamera()
//            } else {
//                val cameraPermission = permissions[0]
//                val showRationaleCamera = shouldShowRequestPermissionRationale(cameraPermission)
//                if (!showRationaleCamera) {
//                    permissionAlert("Required CAMERA permission to open your camera")
//                } else {
//                    Toast.makeText(
//                        this,
//                        "Permission denied to open your camera",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//            } 4 -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    val pickPhoto =
//                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY1)
//                } else {
//                    val permission = permissions[0]
//                    val showRationale = shouldShowRequestPermissionRationale(permission)
//                    if (!showRationale) {
//                        permissionAlert("Required STORAGE permission to access gallery")
//                    } else {
//                        Toast.makeText(
//                            this,
//                            "Permission denied to read your External storage",
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                    }
//                }
//            }
//            5 ->
//                when (requestCode) {
//                    REQUEST_EXTERNAL_STORAGE -> {
//// If request is cancelled, the result arrays are empty.
//                        if (grantResults.size <= 0
//                            || grantResults[0] != PackageManager.PERMISSION_GRANTED
//                        ) {
//                            Toast.makeText(
//                                this@UploadDocumentsRegardingTrip,
//                                "Cannot write images to external storage",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
    }


    //    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//    if (requestCode == PICK_IMAGE_GALLERY) {
//        if (resultCode == RESULT_OK) {


        if (requestCode == PICK_IMAGE_GALLERY && data != null) {
            val selectedImage = data.data
            try {
                imagePath = selectedImage.toString()
//                    binding.imgUser.visibility = View.VISIBLE
                binding.biltiImage.setImageURI(selectedImage)
                val file = File(getPath(selectedImage!!))
                val requestFile =
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                image1 =
                    MultipartBody.Part.createFormData("builty", file.name, requestFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == PICK_IMAGE_CAMERA) {
            if (resultCode == RESULT_OK) {

                try {
                    val file = File(currentPhotoPath)
                    binding.biltiImage.setImageURI(photoURI)
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    image1 = MultipartBody.Part.createFormData("builty", file.name, requestFile)
                    Log.d("TAG", "onActivityResult: "+requestFile)


                } catch (e: java.lang.Exception) {
                }

            }
        }

        if (requestCode == PICK_IMAGE_GALLERY1 && data != null) {
            val selectedImage = data.data
            try {
                imagePath = selectedImage.toString()
//                    binding.imgUser.visibility = View.VISIBLE
                binding.ivPod.setImageURI(selectedImage)
                val file = File(getPath(selectedImage!!))
                val requestFile =
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                image2 =
                    MultipartBody.Part.createFormData("pod", file.name, requestFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == PICK_IMAGE_CAMERA1) {
            if (resultCode == RESULT_OK) {

                try {
                    val file = File(currentPhotoPath)
                    binding.ivPod.setImageURI(photoURI)
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    image2 = MultipartBody.Part.createFormData("pod", file.name, requestFile)
                    Log.d("TAG", "onActivityResult: "+requestFile)


                } catch (e: java.lang.Exception) {
                }
            }
        }
        if (requestCode == PICK_IMAGE_GALLERY2 && data != null) {
            val selectedImage = data.data
            try {
                imagePath = selectedImage.toString()
//                    binding.imgUser.visibility = View.VISIBLE
                binding.signatureImage.setImageURI(selectedImage)
                val file = File(getPath(selectedImage!!))
                val requestFile =
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                image3 =
                    MultipartBody.Part.createFormData("signature", file.name, requestFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == PICK_IMAGE_CAMERA2) {
            if (resultCode == RESULT_OK) {

                try {
                    val file = File(currentPhotoPath)
                    binding.signatureImage.setImageURI(photoURI)
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    image3 = MultipartBody.Part.createFormData("signature", file.name, requestFile)
                    Log.d("TAG", "onActivityResult: "+requestFile)

                } catch (e: java.lang.Exception) {
                }
            }
        }


    }

    private fun getPathFromURI(uri: Uri?): String? {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, uri, data, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)

    }

//        fun getAlbumStorageDir(albumName: String?): File {
//            // Get the directory for the user's public pictures directory.
//            val file = File(
//                Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_PICTURES
//                ), albumName
//            )
//            if (!file.mkdirs()) {
//                Log.e("SignaturePad", "Directory not created")
//            }
//            return file
//        }
//        @Throws(IOException::class)
//        fun saveBitmapToJPG(bitmap: Bitmap, photo: File?) {
//            val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(newBitmap)
//            canvas.drawColor(Color.WHITE)
//            canvas.drawBitmap(bitmap, 0f, 0f, null)
//            val stream: OutputStream = FileOutputStream(photo)
//            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
//            stream.close()
//        }
//        fun addJpgSignatureToGallery(signature: Bitmap): Boolean {
//            var result = false
//            try {
//                val photo = File(
//                    getAlbumStorageDir("SignaturePad"),
//                    String.format("Signature_%d.jpg", System.currentTimeMillis())
//                )
//                saveBitmapToJPG(signature, photo)
//                scanMediaFile(photo)
//                val requestFile = RequestBody.create("image/jpg".toMediaTypeOrNull(), photo)
//                customersignatureimage = MultipartBody.Part.createFormData("signature", photo.name, requestFile)
//                result = true
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            return result
//        }

    //        private fun scanMediaFile(photo: File) {
//            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//            val contentUri = Uri.fromFile(photo)
//            mediaScanIntent.data = contentUri
//            this@UploadDocumentsRegardingTrip.sendBroadcast(mediaScanIntent)
//        }
//        fun addSvgSignatureToGallery(signatureSvg: String?): Boolean {
//            var result = false
//            try {
//                val svgFile = File(
//                    getAlbumStorageDir("SignaturePad"),
//                    String.format("Signature_%d.svg", System.currentTimeMillis())
//                )
//                val stream: OutputStream = FileOutputStream(svgFile)
//                val writer = OutputStreamWriter(stream)
//                writer.write(signatureSvg)
//                writer.close()
//                stream.flush()
//                stream.close()
//                scanMediaFile(svgFile)
//                result = true
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            return result
//        }
//        companion object {
//            private const val REQUEST_EXTERNAL_STORAGE = 1
//            private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            fun verifyStoragePermissions(activity: Activity?) {
//                // Check if we have write permission
//                val permission = ActivityCompat.checkSelfPermission(
//                    activity!!,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//                if (permission != PackageManager.PERMISSION_GRANTED) {
//                    // We don't have permission so prompt the user
//                    ActivityCompat.requestPermissions(
//                        activity,
//                        PERMISSIONS_STORAGE,
//                        REQUEST_EXTERNAL_STORAGE
//                    )
//                }
//            }
//        }
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