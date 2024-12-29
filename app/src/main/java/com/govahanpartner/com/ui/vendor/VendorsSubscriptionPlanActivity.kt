package com.govahanpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.SubscriptionPlanPAdapter
import com.govahanpartner.com.databinding.ActivityVendorsSubscriptionPlanBinding
import java.util.*
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.customclick.SubscriptionPLanClick
import com.govahanpartner.com.model.SubscriptionPlanData
import com.govahanpartner.com.ui.common.PaymentThroughActivity
import com.govahanpartner.com.viewmodel.SubscriptionPlanViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class VendorsSubscriptionPlanActivity : BaseActivity(),SubscriptionPLanClick{

    private lateinit var binding : ActivityVendorsSubscriptionPlanBinding
    private val viewModel: SubscriptionPlanViewModel by viewModels()
    var Listdata:ArrayList<SubscriptionPlanData> = ArrayList()
    lateinit var adapter : SubscriptionPlanPAdapter
    var amount:Int=0
    var finalPamountInt=0
    var vehicle_owner_name=""
    var vehicle_name=""
    var model_year=""
    var vehicle_number=""
    var vehicle_type=""
    var capacity=""
    var height=""
    var number_of_tyres=""
    var bodytype=""
    var paymentprice=""
    var vehicle_owner_name1=""
    var vehicle_name1=""
    var model_year1=""
    var vehicle_number1=""
    var vehicle_type1=""
    var capacity1=""
    var height1=""
    var number_of_tyres1=""
    var bodytype1=""
    var paymentprice1=""
    var flag=""
    var seat=""
    var color=""
    var vehicle_id=""
    var plantype=""
    var currentdate=""
    var buysubscription=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_vendors_subscription_plan)
        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })
        if (intent != null){
            vehicle_owner_name = intent.getStringExtra("vehicle_owner_name").toString()
            vehicle_id = intent.getStringExtra("vehicle_id").toString()
            vehicle_name = intent.getStringExtra("vehicle_name").toString()
            model_year = intent.getStringExtra("model_year").toString()
            buysubscription = intent.getStringExtra("buysubscription").toString()
            vehicle_number = intent.getStringExtra("vehicle_number").toString()
            vehicle_type = intent.getStringExtra("vehicle_type").toString()
            capacity = intent.getStringExtra("capacity").toString()
            height = intent.getStringExtra("height").toString()
            number_of_tyres = intent.getStringExtra("number_of_tyres").toString()
            bodytype = intent.getStringExtra("bodytype").toString()
            vehicle_owner_name1 = intent.getStringExtra("vehicle_owner_name1").toString()
            vehicle_name1 = intent.getStringExtra("vehicle_name1").toString()
            model_year1 = intent.getStringExtra("model_year1").toString()
            vehicle_number1 = intent.getStringExtra("vehicle_number1").toString()
            vehicle_type1 = intent.getStringExtra("vehicle_type1").toString()
            capacity1 = intent.getStringExtra("capacity1").toString()
            height1 = intent.getStringExtra("height1").toString()
            number_of_tyres1 = intent.getStringExtra("number_of_tyres1").toString()
            seat = intent.getStringExtra("seat").toString()
            color = intent.getStringExtra("color").toString()
            bodytype1 = intent.getStringExtra("bodytype1").toString()
            flag = intent.getStringExtra("flag1").toString()
        }
            viewModel.subscriptionPlan.observe(this){
            Listdata.clear()
            Listdata.addAll(it.data)
            binding.rvPlans.layoutManager = LinearLayoutManager(this)
            adapter = SubscriptionPlanPAdapter(this, Listdata,this)
            binding.rvPlans.adapter = adapter
        }
        if (flag.equals("passenger")){
            viewModel.subscription_plan_passengers(
                "Bearer "+userPref.getToken().toString())
        }
        else{
            viewModel.SubscriptionApi(
           "Bearer "+userPref.getToken().toString())

        }

        currentdate=getCurrentDate()

        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }



    }

    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    override fun PLanClick(payment: String, plan_type: String,id:String) {
        paymentprice=payment
        plantype=plan_type
        if (flag=="passenger"){
            val intent = Intent(this, PaymentThroughActivity::class.java)
            intent.putExtra("flag1","1")
            intent.putExtra("fromScreen","passenger")
            intent.putExtra("buysubscription",buysubscription)
            intent.putExtra("plantype",plantype)
            intent.putExtra("paymentprice",paymentprice.toInt())
            intent.putExtra("currentdate",currentdate)
            intent.putExtra("id",id)
            intent.putExtra("vehicle_id",vehicle_id)
            intent.putExtra("flag",flag)
            startActivity(intent)
        } else if (flag=="loader"){
            val intent = Intent(this, PaymentThroughActivity::class.java)
            intent.putExtra("flag1","1")
            intent.putExtra("fromScreen","loader")
            intent.putExtra("buysubscription",buysubscription)
            intent.putExtra("plantype",plantype)
            intent.putExtra("paymentprice",paymentprice.toInt())
            intent.putExtra("currentdate",currentdate)
            intent.putExtra("id",id)
            intent.putExtra("vehicle_id",vehicle_id)
            intent.putExtra("flag",flag)
            startActivity(intent)
        } else {
            val intent = Intent(this, PaymentThroughActivity::class.java)
            intent.putExtra("flag1","1")
            intent.putExtra("plantype",plantype)
            intent.putExtra("paymentprice",paymentprice.toInt())
            intent.putExtra("currentdate",currentdate)
            intent.putExtra("id",id)
            intent.putExtra("vehicle_id",vehicle_id)
            intent.putExtra("flag",flag)
            startActivity(intent)
        }
    }

}