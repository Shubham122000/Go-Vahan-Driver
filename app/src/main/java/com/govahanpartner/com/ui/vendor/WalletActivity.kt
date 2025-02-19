package com.govahanpartner.com.ui.vendor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefers.UserPref

import com.govahanpartner.com.R
import com.govahanpartner.com.WebViewActivity
import com.govahanpartner.com.adapter.TRansactionwalletfilterAdapter
import com.govahanpartner.com.adapter.TransactionReportAdapter
import com.govahanpartner.com.adapter.WalletVendorAdapter
import com.govahanpartner.com.databinding.ActivityWalletBinding
import java.text.SimpleDateFormat
import java.util.*
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.customclick.wallet_customclick
import com.govahanpartner.com.databinding.WalletpriceBinding
import com.govahanpartner.com.model.WalletFilterListData
import com.govahanpartner.com.model.WalletFilterListResponse
import com.govahanpartner.com.model.WalletListData
import com.govahanpartner.com.ui.common.BankAccountList
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.SubscriptionPlanViewModel
import com.govahanpartner.com.viewmodel.WalletViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class WalletActivity : BaseActivity(),wallet_customclick, PopupMenu.OnMenuItemClickListener,
    PaymentResultWithDataListener
{
    private lateinit var binding : ActivityWalletBinding
    private lateinit var c: Calendar
    private val viewModel1: SubscriptionPlanViewModel by viewModels()
    var phonepayurl = ""
    private val viewModel: WalletViewModel by viewModels()
    var Listdata:ArrayList<WalletFilterListData> = ArrayList()
//    var Listdata2:ArrayList<VendorWalletData> = ArrayList()
//    var Listdata1:ArrayList<WalletFilterListData> = ArrayList()
    lateinit var bindingDialog: WalletpriceBinding
    var selectedDateFormat2 = ""
    private val B2B_PG_REQUEST_CODE = 777
    var  paymentprice = 0
    lateinit var prefrence: UserPref
    lateinit var adapter: TransactionReportAdapter
    lateinit var adapter2: WalletVendorAdapter
    lateinit var adapter1: TRansactionwalletfilterAdapter
    lateinit var fillAmount: String
    lateinit var date: String
    var transactionid=""
    var transactionType=""
    var transactionid2=""
    var formattedDate=""
    var transactiontype=""
    var amount: Int = 0
    var finalPamountInt = 0
    var flag=""
    var flag1=""
    var walletamount=""
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefrence= UserPref(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        Log.d("TAG", "onCreate: "+userPref.getToken().toString())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet)
        if (intent != null) {
            flag1= intent.getStringExtra("flag1").toString()
        }

        c= Calendar.getInstance()
        System.out.println("Current time => " + c.getTime())
        val df = SimpleDateFormat("dd-MM-yyyy")
        formattedDate = df.format(c.getTime())

        binding.tvDate.setText(formattedDate)

        viewModel.walletListApi(
                "Bearer "+userPref.getToken().toString(),"",transactionType
        )

        if (userPref.getRole().equals("3")){
            binding.btnMoneyTrans.visibility=View.GONE
            binding.btnAddMoney.visibility=View.GONE
            binding.payoutrequest.text="Move to vendor"
//            viewModel.WalletListApi(
//                "Bearer "+userPref.getToken().toString(),"",""
//            )
         } else if (userPref.getRole().equals("2")){
            binding.btnMoneyTrans.visibility=View.VISIBLE
            binding.payoutrequest.text="Payout Request"
//            viewModel.individual_payment_list(
//                "Bearer "+userPref.getToken().toString(),"",""
//            )
        }else if (userPref.getRole().equals("4")){
            binding.btnMoneyTrans.visibility=View.VISIBLE
            binding.btnAddMoney.visibility=View.GONE
            binding.payoutrequest.text="Payout Request"
//            viewModel.vendor_wallet_list(
//                "Bearer "+userPref.getToken().toString(),"",""
//            )
        }
//        viewModel.bank_account_listResponse.observe(this){
//            if (it.status==1){
//                if (userPref.getRole().equals("3")){
//                    binding.btnMoneyTrans.visibility=View.GONE
//                    binding.payoutrequest.text="Move to vendor"
//                    viewModel.walletListApi(
//                        "Bearer "+userPref.getToken().toString(),selectedDateFormat2,transactionType
//                    )
//                    }else if (userPref.getRole().equals("2")){
//                    binding.btnMoneyTrans.visibility=View.VISIBLE
//                    binding.payoutrequest.text="Payout Request"
////                    viewModel.individual_payment_list(
////                        "Bearer "+userPref.getToken().toString(),"",""
////                    )
//
//                     }else if (userPref.getRole().equals("4")){
//                    binding.btnMoneyTrans.visibility=View.VISIBLE
//                    binding.payoutrequest.text="Payout Request"
//
////                    viewModel.vendor_wallet_list(
////                        "Bearer "+userPref.getToken().toString(),"",""
////                    )
//                }
//            }else{
//                toast(it.message)
//            }
//        }
//        binding.btnDownload.setOnClickListener {
//            transactionid
//        }

        binding.ivPrevious.setOnClickListener {
                c.add(Calendar.DATE, -1)
                formattedDate = df.format(c.time)
                Log.v("PREVIOUS DATE : ", formattedDate)
                binding.tvDate.setText(formattedDate)
        }

        binding.btnMoneyTrans.setOnClickListener {
            val intent = Intent(this, BankAccountList::class.java)
            startActivity(intent)
        }

        binding.ivNext.setOnClickListener(View.OnClickListener() {
                c.add(Calendar.DATE, 1)
                formattedDate = df.format(c.time)
                Log.v("NEXT DATE : ", formattedDate)
                binding.tvDate.setText(formattedDate)
        })

        binding.ivCalender.setOnClickListener {
            clickDataPicker()
        }
        viewModel1.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })

//        viewModel.addmoneytowalletresponse.observe(this) {
//            if (it.status == 1) {
//                toast("Successfully added.")
//                viewModel.WalletListApi(
//                    "Bearer "+userPref.getToken().toString(),
//                )
//            } else {
//                toast(it.message!!)
//            }
//        }

        binding.btnDownload.setOnClickListener {
            viewModel.my_driver_wallet_list_donload(
                "Bearer "+userPref.getToken().toString(),
            )
        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

//        viewModel.walletListfilterResponse.observe(this){
//            if (it?.error == false) {
//                Listdata.clear()
//                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
//                binding.rvMywallet.layoutManager = LinearLayoutManager(this)
//                adapter1 = TRansactionwalletfilterAdapter(this, this,Listdata)
//                binding.rvMywallet.adapter =adapter1
//            } else {
//                toast(it.message)
//            }
//        }
        viewModel.walletListResponse.observe(this) {
            if (it?.error == false) {
                binding.rvMywallet.visibility=View.VISIBLE
                binding.tvBalance.text = "₹"+it.result?.totalAmount
                walletamount = it.result?.totalAmount.toString()
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                binding.rvMywallet.layoutManager = LinearLayoutManager(this)
//                adapter1 = TRansactionwalletfilterAdapter(this, this,Listdata)
//                binding.rvMywallet.adapter =adapter1
//                binding.rvMywallet.layoutManager = LinearLayoutManager(this)
                adapter2 = WalletVendorAdapter(this, this,Listdata)
                binding.rvMywallet.adapter =adapter2
                } else {
                binding.rvMywallet.visibility=View.GONE
                toast(it.message)
                }
        }
//        viewModel.walletData.observe(this) {
//            if (it?.status == 1) {
//                binding.rvMywallet.visibility=View.VISIBLE
//                binding.tvBalance.text = "₹"+it.TotalAmount
//                walletamount = it.TotalAmount
//                Listdata2.clear()
//                Listdata2.addAll(it.data)
//                binding.rvMywallet.layoutManager = LinearLayoutManager(this)
//                adapter2 = WalletVendorAdapter(this, this,Listdata2)
//                binding.rvMywallet.adapter =adapter2
//            } else {
//                binding.rvMywallet.visibility=View.GONE
//                toast(it.message)
//            }
//        }
        viewModel.addmoneytowalletresponse.observe(this) {
            if (it.error == false) {
                toast(it.message)
                viewModel.walletListApi(
                    "Bearer "+userPref.getToken().toString(),"",transactionType
                )
            } else {
                toast(it.message!!)
            }
        }
        viewModel.walletDownload.observe(this){
            if (it?.error == false) {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                toast("Invoice Downloaded successfully!")
            } else {
                toast(it.message)
            }
        }
        viewModel1.ChecksumResponse.observe(this) {
            if (it.success == true) {
                phonepayurl = it.data.instrumentResponse.redirectInfo.url
                transactionid2=it.data.merchantTransactionId
                flag1=""
//                handleUPIIntent(phonepayurl)
                startActivity(
                    Intent(applicationContext, WebViewActivity::class.java)
                    .putExtra("phonepay", phonepayurl.toString())
                )
            } else {
                toast(it.message!!)
            }
        }
        binding.btnAddMoney.setOnClickListener {
            flag="addmoney"
            AddMoney()
        }
        binding.payoutrequest.setOnClickListener {
            flag="payout"
            if (userPref.getRole() == "4"){
                viewModel.addMoney(
                    "Bearer " + userPref.user.apiToken,
                    "2",
                    "",
                    binding.tvBalance.text.toString()
                )
            }else{
                viewModel.addMoney(
                    "Bearer " + userPref.user.apiToken,
                    "3",
                    "",
                    binding.tvBalance.text.toString()
                )
            }

//            AddMoney()
        }

    }
//         override fun onStart() {
//             super.onStart()
//             if(flag1.equals("1")){
//             }
//             else{
//             viewModel1.paymentcheckApi("Bearer " + userPref.user.apiToken,transactionid2)
//             viewModel1.Paymentsuccessmsgresponse.observe(this) {
//                 if (it.code == "PAYMENT_SUCCESS") {
//                     toast(it.message)
//                     viewModel.addMoney(
//                         "Bearer " + userPref.user.apiToken,"1",transactionid2,bindingDialog.edtText.text.toString()
//                     )
//
//                 } else {
//                        toast("Payment Failed")
//                    }
//                 }}
//             }


        fun AddMoney(){
        val cDialog = Dialog(this, R.style.Theme_Tasker_Dialog)
        bindingDialog = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.walletprice,
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
        bindingDialog.btnLogout.setOnClickListener {
             moneyApi(bindingDialog.edtText.text.toString())
//            startPayment()
             cDialog.dismiss()
        }
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }
        binding.tvDate.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                date = p0.toString()
                viewModel.Filteredwalletapi(
                    "Bearer " + userPref.getToken().toString(),
                    formattedDate,""
                )
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        bindingDialog.edtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fillAmount=p0.toString()
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    fun moneyApi(amountofuser: String){

        if (bindingDialog.edtText.text.toString().isEmpty()){
            toast("Please enter amount")
        }else {
            if (flag.equals("addmoney")){
                startPayment(amountofuser)
            }
//            else{
////               if(userPref.getRole().equals("3")){
//                   viewModel.addMoney(
//                       "Bearer " + userPref.user.apiToken,
//                       "2",
//                       "",
//                       bindingDialog.edtText.text.toString()
//                   )
////               }else{
////                   viewModel.withdrawapi("Bearer " + userPref.getToken().toString(),bindingDialog.edtText.text.toString())
////
////               }
//            }
        }
    }


    override fun onItemClick(id: String) {
        transactionid=id
    }
    fun showPopup(v: View?) {
        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.wallet_filter_item)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_all -> {
//                if (userPref.getRole().equals("3")) {
                    viewModel.walletListApi(
                        "Bearer " + userPref.getToken().toString(),selectedDateFormat2,transactionType
                    )
//                } else if (userPref.getRole().equals("2")) {
//                    viewModel.individual_payment_list(
//                        "Bearer " + userPref.getToken().toString(),"",""
//                    )
//                } else if (userPref.getRole().equals("4")) {
//                    viewModel.vendor_wallet_list(
//                        "Bearer " + userPref.getToken().toString(),"",""
//                    )
//                }
                true
            }
            R.id.menu_cedit -> {
                transactionType = "1"
//                if (userPref.getRole().equals("3")) {
                    viewModel.walletListApi(
                        "Bearer " + userPref.getToken().toString(),selectedDateFormat2,transactionType
                    )
//                } else if (userPref.getRole().equals("2")) {
//                    viewModel.individual_payment_list(
//                        "Bearer " + userPref.getToken().toString(),"","1"
//                    )
//                    transactiontype="1"
//                } else if (userPref.getRole().equals("4")) {
//                    viewModel.vendor_wallet_list(
//                        "Bearer " + userPref.getToken().toString(),"","1"
//                    )
//                }
                true
            }
            R.id.menu_debit -> {
                transactionType = "2"
//                if (userPref.getRole().equals("3")) {
                    viewModel.walletListApi(
                        "Bearer " + userPref.getToken().toString(),selectedDateFormat2,transactionType
                    )
//                } else if (userPref.getRole().equals("2")) {
//                    viewModel.individual_payment_list(
//                        "Bearer " + userPref.getToken().toString(),"","2"
//                    )
//                    transactiontype="2"
//                } else if (userPref.getRole().equals("4")) {
//                    viewModel.vendor_wallet_list(
//                        "Bearer " + userPref.getToken().toString(),"","2"
//                    )
//                }


                true
            }
            else -> false
        }
    }
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = android.icu.text.SimpleDateFormat("dd-MM-yyyy")
        val simpleDateFormat2 = android.icu.text.SimpleDateFormat("yyyy-MM-dd")
        cal.timeZone = TimeZone.getTimeZone("UTC")

        val datePickerDialog = DatePickerDialog(
            this, { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
                selectedDateFormat2 = simpleDateFormat2.format(cal.time)
                binding.tvDate.text=selectedDateFormat2
//                if (userPref.getRole().equals("3")) {
                    viewModel.walletListApi(
                        "Bearer " + userPref.getToken().toString(),selectedDateFormat2,transactionType
                    )
//                } else if (userPref.getRole().equals("2")) {
//                    viewModel.individual_payment_list(
//                        "Bearer " + userPref.getToken().toString(),selectedDateFormat2,""
//                    )
//                    transactiontype="2"
//                } else if (userPref.getRole().equals("4")) {
//                    viewModel.vendor_wallet_list(
//                        "Bearer " + userPref.getToken().toString(),selectedDateFormat2,""
//                    )
//                }
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }


    private fun startPayment(amountofuser:String) {
        /*
      *  You need to pass current activity in order to let Razorpay create CheckoutActivity
      * */
//        amount = (binding.payableAmount.text.toString().replace("₹", "").toFloat() * 100).toInt()
        amount = (amountofuser.toFloat() * 100).toInt()
        finalPamountInt = amount
        val co = Checkout()
        co.setKeyID("rzp_test_fWTippdyDGtkYn")
        try {
            var options = JSONObject()
            options.put("name", userPref.user.name)
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "")
            options.put("currency", "INR")
            options.put("amount", finalPamountInt)
            options.put("send_sms_hash", true)

            val prefill = JSONObject()
            prefill.put("email", userPref.user.email)
            prefill.put("contact", userPref.user.mobileNumber)

            options.put("prefill", prefill)

            co.open(this@WalletActivity, options)
        } catch (e: Exception) {
            Toast.makeText(this@WalletActivity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        if (flag == "addmoney") {
            viewModel.addMoney(
                "Bearer " + userPref.user.apiToken,
                "1",
                p1!!.paymentId,
                bindingDialog.edtText.text.toString()
            )
//        }else{
//            viewModel.addMoney(
//                "Bearer " + userPref.user.apiToken,
//                "2",
//                p1!!.paymentId,
//                bindingDialog.edtText.text.toString()
//            )
//        }
//        viewModel.payment_status_check("Bearer " + userPref.getToken().toString(),p1!!.paymentId)
//        Toast.makeText(this@WalletActivity, "Payment Successfully", Toast.LENGTH_LONG).show()

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this@WalletActivity, "Error in payment: ", Toast.LENGTH_LONG).show()

    }


//    private fun startPayment()  {
//        paymentprice= (bindingDialog.edtText.text.toString()).toInt()
//        finalPamountInt=paymentprice*100
//
//        viewModel1.checkupApi("Bearer " + userPref.user.apiToken,finalPamountInt.toString(),userPref.getMobile().toString(),userPref.getuserid().toString())
//
//
//    }



//    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        viewModel.my_wallet_payment(
//            "Bearer " + userPref.user.apiToken,"1",p1?.paymentId!!,bindingDialog.edtText.text.toString()
//
//        )
//
//    }
//
//    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
//        try {
//            Toast.makeText(
//                this,
//                "Payment Unsuccessful", Toast.LENGTH_LONG
//            ).show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

//    viewModel.addmoneywalletapi(
//    "Bearer " + userPref.getToken().toString(),
//    fillAmount
//    )
}
