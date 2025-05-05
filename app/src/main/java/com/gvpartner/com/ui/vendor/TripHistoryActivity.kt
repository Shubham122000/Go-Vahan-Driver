package com.gvpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.gvpartner.com.R
import com.gvpartner.com.adapter.ViewPagerAdapter
import com.gvpartner.com.databinding.ActivityTripHistoryBinding
import com.gvpartner.com.base.BaseActivity

class TripHistoryActivity : BaseActivity() {
     lateinit var binding : ActivityTripHistoryBinding
    private var pagerAdapter: ViewPagerAdapter? = null
    var usertype=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)


        if (intent!=null){
            usertype=intent.getStringExtra("usertype").toString()
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_history)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })

        setTab()
    }


    private fun setTab() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Ongoing"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Completed"))


        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.tabLayout.getChildAt(0)

        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }


}