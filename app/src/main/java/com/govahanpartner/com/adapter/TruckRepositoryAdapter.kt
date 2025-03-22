package com.govahanpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.govahanpartner.com.R
import com.govahanpartner.com.customclick.loadervehicleedit
import com.govahanpartner.com.customclick.loadervehiclelist
import com.govahanpartner.com.customclick.tripdelete

import com.govahanpartner.com.databinding.UiRowTruckRepositoryBinding
import com.govahanpartner.com.model.Vehicles


class TruckRepositoryAdapter(
    val context: Context,
    var loadervehicleedit: loadervehicleedit,
    val list: List<Vehicles>,
    var deletevehicle: tripdelete,
    var loadervehiclelist: loadervehiclelist
) : RecyclerView.Adapter<TruckRepositoryAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: UiRowTruckRepositoryBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ui_row_truck_repository, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]
        Log.d("TAG", "onBindViewHolder: " + data.status)
//        if (data.payment_status.equals("1")) {
//            holder.binding.status.text = "Payment Pending"
//        } else if (data.payment_status.equals("0")) {
//            holder.binding.status.text = "Under reviewed"
//
//        } else if (data.payment_status.equals("2")) {
//            holder.binding.status.text = "Completed"
//        }

        if (data.documentStatus == 1) {
            if (data.paymentStatus == 0) {
                holder.binding.status.text = "Payment Pending"
            } else if (data.paymentStatus == 1 && data.isSubscriptionValid == "Valid") {
                holder.binding.status.text = "Completed"
                holder.binding.status.setTextColor(Color.GREEN)
            } else if (data.isSubscriptionValid == "Expired"){
                    holder.binding.status.text = "Expired"
                    holder.binding.status.setTextColor(Color.RED)
            }
        }else{
            if (data.paymentStatus == 0) {
                holder.binding.status.text = "Under reviewed"
            }
        }



//        if (data.payment_status.equals("1")) {
//            holder.binding.status.text = "Completed"
//        }

        holder.binding.tvTrucknumber.text = data.vehicleNumber
        holder.binding.tvTruckname.text = data.vehicleName
        holder.binding.tvOwnername.text = data.user?.name
        holder.binding.validity.text = data.subscription_end
        Glide.with(context).load(data.mainImage).into(holder.binding.ivVehicle)
        holder.binding.delete.setOnClickListener {
            deletevehicle.tripdelete(data.id)
        }
        holder.binding.linearItem.setOnClickListener {
            loadervehiclelist.loadervehiclelist(data?.id.toString())
        }
        holder.binding.edit.setOnClickListener {
            loadervehicleedit.loadervehicleedit(data)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }


}