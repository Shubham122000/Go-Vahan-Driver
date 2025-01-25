package com.govahanpartner.com.ui.vendor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.DriverListAdapter
import com.govahanpartner.com.databinding.ActivityDriverListBinding
import com.govahanpartner.com.ui.AddDriverActivity
import java.util.*
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.customclick.deleteVehicle
import com.govahanpartner.com.customclick.tripdelete
import com.govahanpartner.com.dialogs.DeleteDriverPopup
import com.govahanpartner.com.model.DriverListResponseData
import com.govahanpartner.com.viewmodel.AddDriverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverListActivity : BaseActivity() ,tripdelete,deleteVehicle{

    private lateinit var binding : ActivityDriverListBinding
    private val viewModel: AddDriverViewModel by viewModels()
    var Listdata:ArrayList<DriverListResponseData> = ArrayList()
    lateinit var adapter : DriverListAdapter
    var id : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_list)

        binding.ivBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.btnAddriver.setOnClickListener(View.OnClickListener {
           val intent = Intent(this, AddDriverActivity::class.java)
            startActivity(intent)
        })
        viewModel.driverListApi(
            "Bearer "+userPref.getToken().toString(),
//            userPref.getid().toString()
        )
        viewModel.DriverProfileResponse.observe(this) {
            if (it?.error == false) {
                viewModel.driverListApi(
                    "Bearer "+userPref.getToken().toString(),
//                    userPref.getid().toString()
                )

            } else {
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
        viewModel.DriverlistResponse.observe(this) {
            if (it?.error == false) {
                Listdata.clear()
                it.result?.data?.let { it1 -> Listdata.addAll(it1) }
                binding.rvDriverlist.layoutManager = LinearLayoutManager(this)
                adapter = DriverListAdapter(this, Listdata,this,"1")
                binding.rvDriverlist.adapter =adapter
            } else {
                //toast(it.message)
                snackbar(it?.message!!)
            }
        }



//        val layoutManagerTopSongs: RecyclerView.LayoutManager = LinearLayoutManager(this,
//            GridLayoutManager.VERTICAL, false)
//        binding.rvDriverlist.setLayoutManager(layoutManagerTopSongs)
//        val itemsTopSongs: List<String> = Arrays.asList("item", "item", "item", "item", "item", "item", "item", "item", "item", "item")
//        binding.rvDriverlist.setAdapter(DriverListAdapter(this, itemsTopSongs))

    }

    override fun tripdelete(id: Int?) {
        supportFragmentManager.let { DeleteDriverPopup(this, id).show(it, "MyCustomFragment") }
    }

    override fun onResume() {
        super.onResume()
        viewModel.driverListApi(
            "Bearer "+userPref.getToken().toString(),
//            userPref.getid().toString()
        )
    }

    override fun deleteVehicle(id: Int?) {
        viewModel.vendor_driver_delete(
            "Bearer "+userPref.getToken().toString(),
            id.toString()
        )    }
}