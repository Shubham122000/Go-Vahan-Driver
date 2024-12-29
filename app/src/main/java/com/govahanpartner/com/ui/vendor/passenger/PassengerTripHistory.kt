package com.govahanpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.PassengerViewPagerAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityPassengerTripHistoryBinding

class PassengerTripHistory : BaseActivity() {
    lateinit var binding : ActivityPassengerTripHistoryBinding
    private var pagerAdapter: PassengerViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_passenger_trip_history)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })
        setTab()
    }
    private fun setTab() {


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pending"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Ongoing"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Completed"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Cancelled"))


        pagerAdapter = PassengerViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.tabLayout.getChildAt(0)

        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}