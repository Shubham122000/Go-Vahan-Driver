package com.govahanpartner.com.ui.vendor.passenger

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.ActivityTripDetailsPactivityBinding
import com.govahanpartner.com.databinding.DialogCancelTripBinding
import com.govahanpartner.com.base.BaseActivity

class TripDetailsPActivity : BaseActivity() {
    private lateinit var binding : ActivityTripDetailsPactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details_pactivity)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })
        binding.btnRejecttrip.setOnClickListener(View.OnClickListener {

            cancelDialog()

        })


        binding.btnStarttrip.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TripDetailsAssignPActivity::class.java)
            startActivity(intent)


        })


    }


    private fun cancelDialog() {
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        val bindingDialog: DialogCancelTripBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_cancel_trip,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }

        bindingDialog.ivClose.setOnClickListener(View.OnClickListener {
            cDialog.dismiss()
        })
        /*bindingDialog.btnLogout.setOnClickListener {
            userPref.clearPref()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //  finishActivity()
            requireContext().finish()
            cDialog.dismiss()
            startActivity(intent)
            cDialog.dismiss()
        }*/
    }
}