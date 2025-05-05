package com.gvpartner.com.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.prefers.UserPref
import com.gvpartner.com.R
import com.gvpartner.com.network.ApiService
import com.gvpartner.com.utils.Utils
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
open class BaseFragment : Fragment() {
    @Inject
    lateinit var userPref: UserPref

    @Inject
    lateinit var utils: Utils

    @Inject
    lateinit var apiService: ApiService

    var progressDialog: ProgressDialog? = null
    var dialog: Dialog? = null


    protected fun showProgressDialog() {
        if (dialog == null)
            dialog = Dialog(requireActivity())
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

    fun snackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}