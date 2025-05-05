package com.gvpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gvpartner.com.R
import com.gvpartner.com.adapter.DriverAssignedListAdapter
import com.gvpartner.com.databinding.ActivityTripAssignToDriverBinding
import java.util.*
import com.gvpartner.com.base.BaseActivity

class TripAssignToDriverActivity : BaseActivity() {
    private lateinit var binding : ActivityTripAssignToDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_assign_to_driver)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })


        val layoutManagerTopSongs: RecyclerView.LayoutManager = LinearLayoutManager(this,
            GridLayoutManager.VERTICAL, false)
        binding.rvList.setLayoutManager(layoutManagerTopSongs)
        val itemsTopSongs: List<String> = Arrays.asList("item", "item", "item", "item", "item", "item", "item", "item", "item", "item")
        binding.rvList.setAdapter(DriverAssignedListAdapter(this, itemsTopSongs))


    }
}