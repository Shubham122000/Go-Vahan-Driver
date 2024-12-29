package com.govahanpartner.com.ui.vendor

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.govahanpartner.com.R
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityDieselPriceBinding
import com.govahanpartner.com.model.StateListResponseData
import com.govahanpartner.com.viewmodel.DeiselViewModel

class DieselPriceActivity : BaseActivity() {
    private lateinit var binding : ActivityDieselPriceBinding
    private val viewModel :DeiselViewModel by viewModels()
    var statedata :ArrayList<StateListResponseData> = arrayListOf()
    var statenamedata :ArrayList<String> = arrayListOf()
    var idstatedata :ArrayList<String> = arrayListOf()
    lateinit var selectedStateId :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diesel_price)
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
        viewModel.StateListAPI().observe(this) {
            if (it!!.status == 1) {
                statedata.clear()
                statenamedata.clear()
                statedata.addAll(it!!.data)
                viewModel.stateListData.value = it.data
                for (i in 0 until it.data.size) {
                    statenamedata.add(it.data[i].stateName.toString())
                    idstatedata.add(it.data[i].id.toString())
                }
                val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    statenamedata
                )
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.selectstatespinner.adapter = spinnerArrayAdapter
            }
        }

        binding.selectstatespinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedStateId=idstatedata[p2]
                viewModel.DeiselPriceAPI(selectedStateId)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        viewModel.deiselPriceResponse.observe(this){
            if (it.status == 1){
                binding.tvPrice.text = it.data.dieselPrice.toString()
                binding.tvPricePetrol.text = it.data.petrol_price.toString()
            }
        }
    }
}