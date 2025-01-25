package com.govahanpartner.com.ui.common


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil

import com.govahanpartner.com.R
import com.govahanpartner.com.WebViewActivity
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityPaymentThroughBinding
import com.govahanpartner.com.ui.DashboardActivity
import com.govahanpartner.com.ui.vendor.TruckRepositoryActivity
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.SubscriptionPlanViewModel
import com.govahanpartner.com.viewmodel.WalletViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class PaymentThroughActivity : BaseActivity(), PaymentResultWithDataListener {
    lateinit var binding: ActivityPaymentThroughBinding
    private val viewModel: SubscriptionPlanViewModel by viewModels()
    private val viewModel1: WalletViewModel by viewModels()
    val handlerUpComing = Handler()
    var runnableUpComing: Runnable? = null
    var flag1 = ""
    var fromScreen = ""
    //    var amount1:BigInteger="271456456456546588888"
    var amount: Int = 0
    var finalPamountInt = 0
    var dropLocation = ""
    private val B2B_PG_REQUEST_CODE = 777
    //     var checksum=""
    var checksumpo = ""
    var dropLat = ""
    var dropLong = ""
    var driverId = ""
    var bodytype = ""
    var capacity = ""
    var distance = ""
    var vehicleNumber = ""
    var flag = ""
    var vehicle_id1 = ""
    var plantype = ""
    var planid = ""
    var paymentprice = 0
    var currentdate = ""
    var totalamount = 0
    var transactionId = ""
    var phonepayurl = ""
    var buysubscription = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_through)
//        PhonePe.init(this)
        binding.tvHeaderText.text = "Select Payment Option"
        if (intent != null) {
            planid = intent.getStringExtra("id").toString()
            vehicle_id1 = intent.getStringExtra("vehicle_id").toString()
            buysubscription = intent.getStringExtra("buysubscription").toString()
            flag = intent.getStringExtra("flag").toString()
            flag1 = intent.getStringExtra("flag1").toString()
            fromScreen = intent.getStringExtra("fromScreen").toString()
            currentdate = intent.getStringExtra("currentdate").toString()
            paymentprice = intent.getIntExtra("paymentprice", 0)
            plantype = intent.getStringExtra("plantype").toString()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
        viewModel.subscriptionPlanPayment.observe(this){
                if (it.error == false) {
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    this.startActivity(intent)
//                if (flag.equals("passenger")) {
//                    val intent = Intent(this, DashboardActivity::class.java)
//                    intent.putExtra("FromLoader","FromPassenger")
//                    startActivity(intent)
//                    finish()
//                } else{
//                        val intent = Intent(this, TruckRepositoryActivity::class.java)
//                        intent.putExtra("FromLoader","FromLoader")
//                        startActivity(intent)
//                        finish()
//                }
            } else {

            }
        }
        finalPamountInt = paymentprice * 100
//        viewModel.checkupApi(
//            totalamount.toString(),"12345","INR",userPref.getEmail().toString()
//        )
//        viewModel.ChecksumResponse.observe(this) {
//            if (it.status == 1) {
//               checksum=it.data.checksum
//                checksumpo= it .data.checksum_po
//            } else {
//                toast(it.message!!)
//            }
//        }

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel1.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }
        viewModel.ChecksumResponse.observe(this) {
            if (it.success == true) {
                phonepayurl = it.data.instrumentResponse.redirectInfo.url
//                handleUPIIntent(phonepayurl)
                transactionId = it.data.merchantTransactionId

                startActivity(
                    Intent(applicationContext, WebViewActivity::class.java)
                        .putExtra("phonepay", phonepayurl.toString())
                )

// To Initiate Payment.
//                startActivityForResult(intent, B2B_PG_REQUEST_CODE);
            } else {
                toast(it.message)
            }
        }
        viewModel1.AddwalletResponse.observe(this) {
            if (it.error == false) {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)
//                if (flag.equals("passenger")) {
//                    if (buysubscription.equals("buysubscription")) {
//                        val intent = Intent(this, DashboardActivity::class.java)
//                        startActivity(intent)
//                        finishAffinity()
//                    }
//                    else{
//                        val intent = Intent(this, TruckRepositoryActivity::class.java)
//                        intent.putExtra("FromLoader", "FromPassenger")
//                        startActivity(intent)
//                        finish()
//                    }
//                }else {
//                    if (buysubscription.equals("buysubscription")) {
//                        val intent = Intent(this, DashboardActivity::class.java)
//                        startActivity(intent)
//                        finishAffinity()
//                    } else {
//                        val intent = Intent(this, TruckRepositoryActivity::class.java)
//                        intent.putExtra("FromLoader", "FromLoader")
//                        startActivity(intent)
//                        finish()
//                    }
//                }
            } else {
                toast(it.message!!)
            }
        }
        binding.walletButton.setOnClickListener {
            if (fromScreen=="loader") {
                if (buysubscription.equals("buysubscription")){
                    viewModel1.buySubscriptionWalletApi("Bearer " + userPref.user.apiToken.toString(),plantype,"2")
                }
                else{
                viewModel1.purchase_plan_from_walletApi_to_truck(
                    "Bearer " + userPref.user.apiToken,
                    paymentprice.toString(),
                    vehicle_id1,
                    "2",plantype
                )
                }
            }
            else if (fromScreen=="passenger") {
                if (buysubscription.equals("buysubscription")){
                    viewModel1.buySubscriptionWalletApi("Bearer " + userPref.user.apiToken.toString(),plantype,"1")
                }
                else {
                    viewModel1.purchase_plan_from_walletApi_to_passenger(
                        "Bearer " + userPref.user.apiToken,
                        paymentprice.toString(),
                        vehicle_id1,
                        "2", plantype
                    )
                }
            }else{
                viewModel1.purchase_plan_from_walletApi(
                    "Bearer " + userPref.user.apiToken,
                    paymentprice.toString(),
                    vehicle_id1,
                    "2"
                )
            }
        }

        binding.onlineButton.setOnClickListener {
            flag1 = ""
            startPayment(finalPamountInt.toString())
//            viewModel.checkupApi(
//                "Bearer " + userPref.user.apiToken,
//                finalPamountInt.toString(),
//                userPref.getMobile().toString(),
//                userPref.getuserid().toString()
//            )
        }
    }
//    override fun onStart() {
//        super.onStart()
//        if (flag1.equals("1")) {
//        } else {
//            viewModel.paymentcheckApi("Bearer " + userPref.user.apiToken, transactionId)
//            viewModel.Paymentsuccessmsgresponse.observe(this) {
//                if (it.code == "PAYMENT_SUCCESS") {
//                    toast(it.message)
//                    if (flag.equals("passenger")) {
//                        if (buysubscription.equals("buysubscription")) {
//                            viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"1",transactionId)
//                        }
//                        else {
//                            viewModel.paymentsSubscriptionPassenger(
//                                "Bearer " + userPref.user.apiToken,
//                                vehicle_id1, "2", paymentprice.toString(), "2", transactionId, plantype,
//                                currentdate, "2"
//                            )
//                        }
//                    } else {
//                        if (buysubscription.equals("buysubscription")) {
//                            viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"2",transactionId)
//                        }
//                        else {
//                            viewModel.PaymentSubscriptionPlan(
//                                "Bearer " + userPref.user.apiToken,
//                                vehicle_id1, "2", paymentprice.toString(), "2", transactionId.toString(),
//                                plantype, currentdate, "2"
//                            )
//                        }
//                    }
//
//                } else {
//                    toast(it.message)
//                    if (flag.equals("passenger")) {
//                        if (buysubscription.equals("buysubscription")) {
//                            viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"1",transactionId)
//                        }
//                        else {
//                            viewModel.paymentsSubscriptionPassenger(
//                                "Bearer " + userPref.user.apiToken,
//                                vehicle_id1, "2", paymentprice.toString(), "2", transactionId, plantype,
//                                currentdate, "3"
//                            )
//                        }
//                    } else {
//                        if (buysubscription.equals("buysubscription")) {
//                            viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"2",transactionId)
//                        }
//                        else {
//                            viewModel.PaymentSubscriptionPlan(
//                                "Bearer " + userPref.user.apiToken,
//                                vehicle_id1, "2", paymentprice.toString(), "2", transactionId.toString(),
//                                plantype, currentdate, "3"
//                            )
//                        }
//                    }
//
//                }
//            }
//        }
//
//    }

    private fun startPayment(amountofuser:String) {
        /*
      *  You need to pass current activity in order to let Razorpay create CheckoutActivity
      * */
//        amount = (binding.payableAmount.text.toString().replace("₹", "").toFloat() * 100).toInt()
        amount = (amountofuser.toFloat()).toInt()
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

            co.open(this@PaymentThroughActivity, options)
        } catch (e: Exception) {
            Toast.makeText(this@PaymentThroughActivity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        viewModel.my_wallet_payment(
//            "Bearer " + userPref.user.apiToken,"1",p1!!.paymentId,bindingDialog.edtText.text.toString()
//        )
//        viewModel.payment_status_check("Bearer " + userPref.getToken().toString(),p1!!.paymentId)
        Toast.makeText(this@PaymentThroughActivity, "Payment Successful", Toast.LENGTH_LONG).show()
        if (flag.equals("passenger")) {
            if (buysubscription.equals("buysubscription")) {
                viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"1",transactionId)
            }
            else {
                viewModel.paymentsSubscriptionPassenger(
                    "Bearer " + userPref.user.apiToken,
                    vehicle_id1, "2", paymentprice.toString(), "2", p1?.paymentId.toString(), plantype,
                    currentdate, "2"
                )
            }
        } else {
            if (buysubscription.equals("buysubscription")) {
                viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"2",transactionId)
            }
            else {
                viewModel.PaymentSubscriptionPlan(
                    "Bearer " + userPref.user.apiToken,
                    vehicle_id1, "2",  "2", p1?.paymentId.toString(), currentdate, "2"
                )
            }
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this@PaymentThroughActivity, "Error in payment: ", Toast.LENGTH_LONG).show()
        if (flag.equals("passenger")) {
            if (buysubscription.equals("buysubscription")) {
                viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"1",transactionId)
            }
            else {
                viewModel.paymentsSubscriptionPassenger(
                    "Bearer " + userPref.user.apiToken,
                    vehicle_id1, "2", paymentprice.toString(), "2", transactionId, plantype,
                    currentdate, "3"
                )
            }
        } else {
            if (buysubscription.equals("buysubscription")) {
                viewModel1.buySubscriptionOnlineApi("Bearer " + userPref.user.apiToken,plantype,"2",transactionId)
            }
            else {
                viewModel.PaymentSubscriptionPlan(
                    "Bearer " + userPref.user.apiToken,
                    vehicle_id1, "2" , "2", transactionId.toString(), currentdate, "3"
                )
            }
        }

    }


}


