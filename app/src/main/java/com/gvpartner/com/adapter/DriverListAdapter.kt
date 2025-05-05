package com.gvpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.gvpartner.com.R
import com.gvpartner.com.databinding.RowDriverListBinding
import com.gvpartner.com.customclick.tripdelete
import com.gvpartner.com.model.DriverListResponseData
import com.gvpartner.com.ui.vendor.DriverDetailActivity

class DriverListAdapter (val context : Context, var data: ArrayList<DriverListResponseData>, var deletedriver: tripdelete,var type:String) :
    RecyclerView.Adapter<DriverListAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowDriverListBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_driver_list, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val List = data[position]
        holder.binding.tvUsername.text = List.name.toString()
        holder.binding.tvPhone.text = List.mobileNumber.toString()
        holder.binding.tvLicno.text = List.licenceNumber.toString()
        holder.binding.tvVehicleNumber.text = List.vehicleNumber.toString()
        Glide.with(context).load(List.profileImage).into(holder.binding.imgUser)
        holder.binding.emailId.text=List.email
         if (List.status==0){
             holder.binding.status.text="Under Review"
         }else{
             holder.binding.status.text="Approved"
         }

        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
           val intent = Intent(context, DriverDetailActivity::class.java)
          intent.putExtra("orderType", "4")
          intent.putExtra("id",List.id)
          intent.putExtra("type",type)
            context.startActivity(intent)
        })

        holder.binding.delete.setOnClickListener {
            deletedriver.tripdelete(List.id)
        }
  }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

}