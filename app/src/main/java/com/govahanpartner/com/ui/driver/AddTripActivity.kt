package com.govahanpartner.com.ui.driver

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityAddTripBinding
import com.govahanpartner.com.ui.common.AddTruckDocumentsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddTripActivity : BaseActivity() {

    private lateinit var binding : ActivityAddTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_add_trip)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip)


        binding.llDate.setOnClickListener {
            chooseDataPicker()
        }

        binding.btnAddthistrip.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddTruckDocumentsActivity::class.java)
            startActivity(intent)

        })


    }

    private fun chooseDataPicker() {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        cal.timeZone = TimeZone.getTimeZone("UTC")

        val datePickerDialog = DatePickerDialog(
            this, { view, year, monthOfYear, dayOfMonth ->
                cal.set(year, monthOfYear, dayOfMonth)
                binding.tvDate.text = simpleDateFormat.format(cal.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }


}