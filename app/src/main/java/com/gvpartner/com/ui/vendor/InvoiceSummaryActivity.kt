package com.gvpartner.com.ui.vendor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.databinding.ActivityInvoiceSummaryBinding
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.utils.toast
import com.gvpartner.com.viewmodel.InvoiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoiceSummaryActivity : BaseActivity() {
    private lateinit var binding : ActivityInvoiceSummaryBinding
    private val viewModel: InvoiceViewModel by viewModels()
    lateinit var invoice:String
    var newurl :String = ""
    var downlloadpdf :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice_summary)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        if (intent != null) {
            invoice = intent.getStringExtra("invoicenumber").toString()
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.InvoicesummeryAPI(
            "Bearer " + userPref.getToken().toString(),
            invoice
        )
            viewModel.InvoiceSummeryResponse.observe(this) {
                if (it?.status == 1) {
                    try {
                        binding.tvName.text = it.userDetails?.name.toString()
                        binding.tvPhone.text = it.userDetails?.mobileNumber.toString()
                        binding.tvEmail.text = it.userDetails?.email.toString()
                        binding.tvVehicleowner.text = it.ownerDetails?.name.toString()
                        binding.tvDriver.text = it.userDetails?.name.toString()
                        binding.tvBookingid.text = it.data.bookingId.toString()
                        binding.tvInvoicenumber.text = it.data?.invoiceNumber.toString()
                        binding.tvDepartureplace.text = it.data?.picupLocation.toString()
                        binding.tvDate.text = it.data.bookingDate.toString()
                        binding.tvArrivalplace.text = it.data.dropLocation.toString()
                        viewModel.InvoiceDownloadAPI("Bearer " + userPref.getToken(),
                            it.data?.invoiceNumber.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
                } else {
//                toast(.message)
                }
            }
        viewModel.VisitingPdfResponse.observe(this) {
            if (it!!.status == 1){
                downlloadpdf = it.url.toString()
                var urlsplit = downlloadpdf.split("/public")!!.toTypedArray()

                var url1 = urlsplit[0]
                var url2 = urlsplit[1]
                newurl = url1+url2
            }
        }
        binding.btnEmail.setOnClickListener {
            viewModel.SendDriverLoaderInvoiceAPI(
                "Bearer " + userPref.getToken().toString()
//                    userPref.getToken().toString()
                ,
//            invoice
                invoice
            )
        }
        viewModel.SendDriverLoaderInvoiceResponse.observe(this) {
            if (it?.status == 1) {
                try {
                    toast("Email send successfully...")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.btnDownload.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                newurl
            ))
            )
        }
        binding.btnShare.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            var shareBody: String = newurl
            var shareSubject: String = "Share link:-"
            i.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
            i.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(i, "Sharing using"))
        }
    }
}