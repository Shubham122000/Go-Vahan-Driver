package com.gvpartner.com.base

import android.app.Dialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.prefers.UserPref
import com.gvpartner.com.R
import com.gvpartner.com.utils.Utils
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
@AndroidEntryPoint
@Module
@InstallIn(SingletonComponent::class)
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var userPref: UserPref

    @Inject
    lateinit var utils: Utils

    var dialog: Dialog? = null
    var progressDialog: ProgressDialog? = null
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

    fun isPasswordConfirmPasswordSame(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun snackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    protected fun showProgressDialog() {
        if (dialog == null)
            dialog = Dialog(this)
        dialog!!.setContentView(R.layout.progress_dialog)
        dialog!!.setCancelable(false)

        if (dialog != null && !dialog!!.isShowing)
            dialog!!.show()
    }

    protected fun hideProgressDialog() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    fun snackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    /*   protected fun replaceFragment(fragment: Fragment) {
           val fragmentTransaction = supportFragmentManager.beginTransaction()
           fragmentTransaction.replace(R.id.frameContainer, fragment, fragment.javaClass.name).commit()
       }*/
}
