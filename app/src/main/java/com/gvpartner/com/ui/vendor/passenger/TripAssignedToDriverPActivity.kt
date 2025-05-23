package com.gvpartner.com.ui.vendor.passenger

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gvpartner.com.R
import com.gvpartner.com.adapter.DriverAssignedListAdapter
import com.gvpartner.com.databinding.ActivityTripAssignedToDriverPactivityBinding
import java.util.*
import com.gvpartner.com.base.BaseActivity

class TripAssignedToDriverPActivity : BaseActivity() {
    private lateinit var binding : ActivityTripAssignedToDriverPactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_assigned_to_driver_pactivity)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()

        })


        val layoutManagerTopSongs: RecyclerView.LayoutManager = LinearLayoutManager(this,
            GridLayoutManager.VERTICAL, false)
        binding.rvDriverlist.setLayoutManager(layoutManagerTopSongs)
        val itemsTopSongs: List<String> = Arrays.asList("item", "item", "item", "item", "item", "item", "item", "item", "item", "item")
        binding.rvDriverlist.setAdapter(DriverAssignedListAdapter(this, itemsTopSongs))


    }
}