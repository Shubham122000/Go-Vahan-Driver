package com.gvpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvpartner.com.R
import com.gvpartner.com.adapter.TruckRepositoryAdapter
import com.gvpartner.com.base.BaseActivity
import com.gvpartner.com.customclick.deleteVehicle
import com.gvpartner.com.customclick.loadervehicleedit
import com.gvpartner.com.customclick.loadervehiclelist
import com.gvpartner.com.customclick.tripdelete
import com.gvpartner.com.databinding.ActivityTruckRepositoryBinding
import com.gvpartner.com.dialogs.DeleteVehicleDialog
import com.gvpartner.com.model.Vehicles
import com.gvpartner.com.ui.common.EditTaxiDocumentsActivity
import com.gvpartner.com.ui.common.TaxiRepositoryViewActivity
import com.gvpartner.com.ui.common.TruckRepositoryViewActivity
import com.gvpartner.com.ui.driver.passenger.EditTruckDocumentsActivity
import com.gvpartner.com.viewmodel.TruckRepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TruckRepositoryActivity : BaseActivity(), tripdelete, loadervehiclelist, deleteVehicle,
    loadervehicleedit {
    private lateinit var binding: ActivityTruckRepositoryBinding
    private val viewModel: TruckRepositoryViewModel by viewModels()
    var Listdata: ArrayList<Vehicles> = ArrayList()
    lateinit var adapter: TruckRepositoryAdapter
    lateinit var flag: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_truck_repository)
        if (intent != null) {
            flag = intent.getStringExtra("FromLoader").toString()
        }
        binding.titleName.text = "Vehicle Repository"
        if (flag == "FromLoader") {
            viewModel.TruckrepositoryListApi(
                "Bearer " + userPref.getToken().toString(),"0"
            )
        } else {
            viewModel.TruckrepositoryListApi(
                "Bearer " + userPref.getToken().toString(),"1"
            )
        }
        binding.ivBack.setOnClickListener{
            onBackPressed()
        }
        viewModel.TruckrepositoryDelete.observe(this) {
            if (it?.error == false) {
                if (flag == "FromLoader") {
                    viewModel.TruckrepositoryListApi(
                        "Bearer " + userPref.getToken().toString(),"0"
                    )
                } else {
                    viewModel.TruckrepositoryListApi(
                        "Bearer " + userPref.getToken().toString(),"1"
                    )
                }
                binding.titleName.text = "Truck Repository"
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }
        binding.pulltorefresh.setOnRefreshListener {
            if (flag == "FromLoader") {
                viewModel.TruckrepositoryListApi(
                    "Bearer " + userPref.getToken().toString(),"0"
                )
            } else {
                viewModel.TruckrepositoryListApi(
                    "Bearer " + userPref.getToken().toString(),"1"
                )
            }
            binding.pulltorefresh.isRefreshing = false
            }

           viewModel.TruckrepositorymodelClass.observe(this) {
            if (it?.error == false) {
                if (flag == "FromLoader") {
                    viewModel.TruckrepositoryListApi(
                        "Bearer " + userPref.getToken().toString(),"0"
                    )
                } else {
                    viewModel.TruckrepositoryListApi(
                        "Bearer " + userPref.getToken().toString(),"1"
                    )
                }
                    binding.titleName.text = "Taxi Repository"
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
              }
           }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        }

        viewModel.TruckrepositoryList.observe(this) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.vehicles?.let { it1 -> Listdata.addAll(it1) }
                binding.rvTruckrepo.visibility=View.VISIBLE
                binding.rvTruckrepo.layoutManager = LinearLayoutManager(this)
                adapter = TruckRepositoryAdapter(this, this, Listdata, this, this)
                binding.rvTruckrepo.adapter = adapter
                } else {
                //toast(it.message)
                binding.rvTruckrepo.visibility=View.GONE
                snackbar(it?.message!!)
            }
        }
    }
    override fun tripdelete(id: Int?) {
        supportFragmentManager.let {
            DeleteVehicleDialog(this, id).show(it, "MyCustomFragment")
        }
    }

    override fun loadervehiclelist(id: String?) {
        if (flag == "FromLoader") {
            val intent =
                Intent(this, TruckRepositoryViewActivity::class.java).putExtra("vehicle_id", id)
            startActivity(intent)
        } else {
            val intent =
                Intent(this, TaxiRepositoryViewActivity::class.java).putExtra("vehicle_id", id)
            startActivity(intent)
        }
    }

    override fun deleteVehicle(id: Int?) {
        if (flag == "FromLoader") {
            viewModel.loader_truck_repository_list_deleteListApi(
                "Bearer " + userPref.getToken().toString(), id.toString()
            )
        } else {
            viewModel.passengers_truck_repository_list_delete(
                "Bearer " + userPref.getToken().toString(), id.toString()
            )
        }
    }

    override fun loadervehicleedit(vehicle: Vehicles?) {
        if (flag == "FromLoader") {
            val intent = Intent(this, EditTruckDocumentsActivity::class.java).putExtra("vehicle", vehicle)
            startActivity(intent)

        } else {
            val intent = Intent(this, EditTaxiDocumentsActivity::class.java).putExtra("vehicle", vehicle)
            startActivity(intent)

        }
    }
}