package com.gvpartner.com.ui.common

import android.os.Bundle
import com.gvpartner.com.R
import com.gvpartner.com.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripDetailsPickupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details_pickup)
    }
}