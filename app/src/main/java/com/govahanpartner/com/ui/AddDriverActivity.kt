package com.govahanpartner.com.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
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
import android.provider.OpenableColumns
import android.provider.Settings
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityAddDriverBinding

import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.permission.RequestPermission
import com.govahanpartner.com.utils.CommonUtils
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.AddDriverViewModel
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


@AndroidEntryPoint
class AddDriverActivity : BaseActivity() {
    private val pickImageCamera = 1
    private val pickImageGallery = 2
    private var mUri: Uri? = null
    private var file: File? = null
    val FILE_BROWSER_CACHE_DIR = "id_proof"
    var pdfFile: MultipartBody.Part? = null
    var flag:Boolean = false
    private lateinit var binding : ActivityAddDriverBinding
    private val viewModel: AddDriverViewModel by viewModels()
    private var isVisible2 = false
    private var isVisible1 = false
    val PICKFILE_RESULT_CODE = 1
    private var requestCodeForLegacy: Int? = null
    val CAMERA_PERM_CODE_ID_Front = 101
    var imageFile: File? = null
    var imagePath = ""
    var photoURI: Uri? = null
    var imagePrats: MultipartBody.Part? = null
    private val GALLERY_ID_FRONT = 1
    private var CAMERA_ID_FRONT: Int = 2
    lateinit var image: Uri
    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_add_driver)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_driver)

        /*binding.btnAddthisdriver.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, DriverListActivity::class.java)
            startActivity(intent)
            finish()

        })*/
        pickSingleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                Log.d("TAG", "@@onActivityResult:.....")
                if (it.resultCode != Activity.RESULT_OK) {
                    Toast.makeText(this, "Failed picking media.", Toast.LENGTH_SHORT).show()
                } else {
                    val uri = it.data?.data
                    val path = getPathFromURI(uri)

                    if (path != null) {
                        file = File(path)
                        //   images.add(imageFile!!.absolutePath.toString())
                    }
                    if (requestCodeForLegacy == GALLERY_ID_FRONT){
                        Glide.with(this).load(file).into(binding.ivDriver)
                        imagePrats = MultipartBody.Part.createFormData(
                            "profile_image",
                            file!!.name,
                            file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    }

                }
            }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.btnAddthisdriver.setOnClickListener(View.OnClickListener {
            BusinesscardApi()
        })
        binding.edtUploadidproof.setOnClickListener {
            selectPdf()
        }
        binding.ivDriver.setOnClickListener(View.OnClickListener {
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
            }        })
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.addDriverResponse.observe(this) {
            if (it?.status == 1) {
                Toast.makeText(this, "Driver added successfully", Toast.LENGTH_LONG).show()
//
             onBackPressed()

            } else {
                toast(it.message)

            }
        }

        binding.lytVisiblePassadddriver.setOnClickListener {
            if (isVisible1) {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisiblePass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_hide
                        )
                    )
                    binding.edtPassword.setSelection(binding.edtPassword.text.length)
                }
                isVisible1 = false
            } else { //Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisiblePass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_view
                        )
                    )
                    binding.edtPassword.setSelection(binding.edtPassword.text.length)
                }
                isVisible1 = true
            }
        }
        binding.lytVisibleConfirmPassadddriver.setOnClickListener {
            if (isVisible2) {
                binding.edtConfirmpassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisibleConfirmPass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_hide
                        )
                    )
                    binding.edtPassword.setSelection(binding.edtPassword.text.length)
                }
                isVisible2 = false
            } else { //Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                binding.edtConfirmpassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.ivVisibleConfirmPass.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.password_view
                        )
                    )
                    binding.edtConfirmpassword.setSelection(binding.edtConfirmpassword.text.length)
                }
                isVisible2 = true
            }
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
            captureImage()
            dialog.dismiss()
        }

        val GalleryButton = view.findViewById<TextView>(R.id.gallery_open)
        GalleryButton.setOnClickListener {
//            val intent =
//                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.type = "image/*"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pickSingleMediaLauncher.launch(Intent(MediaStore.ACTION_PICK_IMAGES))
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                    startActivityForResult(intent, GALLERY_ID_FRONT)
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
                        startActivityForResult(takePictureIntent, CAMERA_ID_FRONT)
                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
//                    displayMessage(baseContext, ex.message.toString())
                }

            } else {
//                displayMessage(baseContext, "Null")
            }
        }

    }

    fun BusinesscardApi(){
        if (binding.etFullName2.text.toString().isNullOrEmpty()) {
            toast("Please enter driver name.")
        } else if (binding.edtDrivingexp.text.toString().isNullOrEmpty()) {
            toast("Please enter driver's experience.")
        } else if (binding.edtlicense.text.toString().isNullOrEmpty()) {
            toast("Please enter license.")
        }  else if (binding.edtMobile.text.toString().isNullOrEmpty()) {
            toast("Please enter mobile number.")
        } else if (binding.edtMobile.text.toString().length < 10) {
            toast("Please enter valid mobile number.")
        } else if (binding.edtUsername.text.toString().isNullOrEmpty()) {
            toast("Please enter username.")
        }else if (binding.edtPassword.text.toString().isNullOrEmpty()) {
            toast("Please enter password.")
        }else if (binding.edtConfirmpassword.text.toString().isNullOrEmpty()) {
            toast("Please enter confirm password.")
        } else if (!CommonUtils.isPasswordConfirmPasswordSame(binding.edtPassword.text.toString().trim(),binding.edtConfirmpassword.text.toString().trim())) {
            toast("Confirm password doesn't match.")
        }

        else {


            if (binding.edtUploadidproof.text.isNullOrEmpty()) {
                val requestFile =
                    RequestBody.create("id_proof".toMediaTypeOrNull(), "")
                pdfFile = MultipartBody.Part.createFormData("file", "", requestFile)
                toast("Please Select File")
            } else {

                val file = File((binding.edtUploadidproof.text.toString()))

                val requestFile = RequestBody.create("id_proof".toMediaTypeOrNull(), file)
                pdfFile =
                    MultipartBody.Part.createFormData("id_proof", file.name, requestFile)
            }
            if (imagePrats == null) {
                val requestFile =
                    "".toRequestBody("image/*".toMediaTypeOrNull())
                imagePrats =
                    MultipartBody.Part.createFormData("profile_image", "", requestFile)
            }

            viewModel.AddDriverApi(
                "Bearer "+userPref.getToken().toString(),
                binding.etFullName2.text.toString(),
                binding.edtDrivingexp.text.toString(),
                binding.edtlicense.text.toString(),
                binding.edtMobile.text.toString(),
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString(),
                imagePrats,
                pdfFile,
                userPref.getid().toString(),
                "1"
            )
        }

    }

//    @SuppressLint("Range")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }
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


//    private fun selectImage() {
//        val options = arrayOf<CharSequence>("Take Photo", "Choose From Gallery", "Cancel")
//        val pm = this.packageManager
//        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
//        builder.setTitle("Select Option")
//        builder.setItems(options) { dialog, item ->
//            if (options[item] == "Take Photo") {
//                dialog.dismiss()
//                val cameraPermission =
//                    pm?.checkPermission(Manifest.permission.CAMERA, this.packageName)
//                val storagePermission =
//                    pm?.checkPermission(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        this.packageName
//                    )
//                if (cameraPermission == PackageManager.PERMISSION_GRANTED && storagePermission == PackageManager.PERMISSION_GRANTED) {
//
//                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
//
//                        openCamera()
//
//                    }
//
//
//                } else {
//                    requestPermissions(
//                        arrayOf(
//                            Manifest.permission.CAMERA,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        ),
//                        1
//                    )
//                }
//            } else if (options[item] == "Choose From Gallery") {
//                dialog.dismiss()
//                val hasPerm =
//                    pm?.checkPermission(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        this.packageName
//                    )
//                if (hasPerm == PackageManager.PERMISSION_GRANTED) {
//                    val pickPhoto =
//                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                    this.startActivityForResult(pickPhoto, pickImageGallery)
//                } else {
//                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2)
//                }
//            } else if (options[item] == "Cancel") {
//                dialog.dismiss()
//            }
//        }
//        builder.show()
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG", "@@onActivityResult:")

       if (requestCode==PICKFILE_RESULT_CODE && data != null ) {

           val fileUris = data?.data
           var path = writeFileContent(fileUris!!)
           var fileSelected = File(path)

           binding.edtUploadidproof.text = fileSelected!!.absolutePath

           val requestFile: RequestBody = RequestBody.create(
               "multipart/form-data".toMediaTypeOrNull(),
               fileSelected!!
           )
           pdfFile = MultipartBody.Part.createFormData("id_proof", fileSelected!!.name, requestFile)
       } else if (requestCode == CAMERA_ID_FRONT) {
           val myBitmap = BitmapFactory.decodeFile(file!!.absolutePath)
           binding.ivDriver.setImageBitmap(myBitmap)
//           Glide.with(this).load(file).into(binding.ivDriver)
//                 mPhotoFile = File(getPath(photoURICamera!!))
           val requestFile: RequestBody =
               file!!.asRequestBody("image/*".toMediaTypeOrNull())

           imagePrats =
               MultipartBody.Part.createFormData(
                   "profile_image",
                   file!!.name,
                   requestFile
               )
//            imageParts.add(imagetruck3!!)
//            viewModel.LoaderimageAPI(
//                "Bearer " + userPref.getToken().toString(),
//                vehicleid,
//                imagetruck3
//            )


       }
//       else if (requestCode == pickImageGallery && data != null) {
//           val selectedImage = data.data
//           try {
//               val file = File(getPath(selectedImage!!))
//               //  val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//               val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//
//
//               Glide.with(this).load(selectedImage)
//                   .apply(RequestOptions.fitCenterTransform())
//                   .apply(RequestOptions.placeholderOf(R.drawable.image_placeholder))
//                   .apply(RequestOptions.errorOf(R.drawable.image_placeholder))
//                   .into(binding.ivDriver)
//               flag = true
//               imagePrats =
//                   MultipartBody.Part.createFormData("profile_image", file.name, requestFile)
//
//
//           } catch (e: Exception) {
//               e.printStackTrace()
//           }
//       }


        
    }


//    @Throws(IOException::class)
//    private fun createImageFile(): File? {
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val imageFileName = "JPEG_" + timeStamp + "_"
//        val storageDir =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//        val image = File.createTempFile(
//            imageFileName,
//            ".jpg",
//            storageDir
//        )
//
//        currentPhotoPath = image.absolutePath
//        return image
//    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        mUri = this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(cameraIntent, pickImageCamera)
    }
    private fun getPath(uri: Uri): String {
        val data = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, uri, data, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 ->
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
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
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, pickImageGallery)
                } else {
                    val permission = permissions[0]
                    val showRationale = shouldShowRequestPermissionRationale(permission)
                    if (!showRationale) {
                        permissionAlert("Required STORAGE permission to access gallery")
                    } else {
                        Toast.makeText(
                            this,
                            "Permission denied to read your External storage",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }
    }
    private fun selectPdf() {
        var pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "*/*"
//        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, PICKFILE_RESULT_CODE)

    }


}