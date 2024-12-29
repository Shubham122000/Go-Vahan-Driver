package com.govahanpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.NotificationAdapter
import com.govahanpartner.com.databinding.ActivityNotificationBinding
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.model.NotificationResponseData
import com.govahanpartner.com.viewmodel.StaticDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity() {
    private lateinit var binding : ActivityNotificationBinding
    private val viewModel: StaticDataViewModel by viewModels()
    var Listdata : ArrayList<NotificationResponseData> = ArrayList()
    lateinit var adapter : NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
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
        viewModel.NotificationAPI(
            "Bearer "+ userPref.getToken().toString(),
        )
        viewModel.NotificationResponse.observe(this) {
            if (it?.status == 1) {
                Listdata.clear()
                Listdata.addAll(it.data)
                binding.tvCount.text = it.count.toString()
                binding.rvNotification.layoutManager = LinearLayoutManager(this)
                adapter = NotificationAdapter(this, Listdata)
                binding.rvNotification.adapter =adapter

////                userPref.setUserId(it!!.data!!.Id.toString())
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
//                toast(.message)
            }
        }
    }
}