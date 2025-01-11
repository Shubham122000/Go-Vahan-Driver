package com.govahanpartner.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.govahanpartner.com.R
import com.govahanpartner.com.customclick.tripclick
import com.govahanpartner.com.customclick.tripdelete
import com.govahanpartner.com.databinding.TriplistlayoutBinding
import com.govahanpartner.com.model.TripListResponseData

class TripAdapter(var context: Context,var list: List<TripListResponseData>,var tripdelete: tripdelete,var tripclick:tripclick)
    : RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    inner class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        var binding: TriplistlayoutBinding = DataBindingUtil.bind(itemView)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.triplistlayout,parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = list[position]

        holder.binding.fair.text = "Rs."+data.freightAmount
        holder.binding.tvName.text = data.tipTask
        holder.binding.tvStart.text = data.fromTrip
        holder.binding.tvStop.text = data.toTrip
        holder.binding.tvPartyname.text = data.vehicle?.vehicleName
//        var Datetime = data.createdAt
//        var Datsplit = Datetime?.split(" ")!!.toTypedArray()
//        var date = Datsplit[0]
//        var time = Datsplit[1]
        holder.binding.tvDate.text = data.bookingDateFrom
        holder.binding.tvTime.text = data.time
        Glide.with(context).load(data.vehicleImage).into(holder.binding.ivTruck)
        holder.binding.click.setOnClickListener {
            tripclick.tripclick(data)
        }
        holder.binding.delete.setOnClickListener {
            tripdelete.tripdelete(data.id)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}