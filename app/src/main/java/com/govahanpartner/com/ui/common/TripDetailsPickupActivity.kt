package com.govahanpartner.com.ui.common

import android.os.Bundle
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripDetailsPickupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details_pickup)
    }
}