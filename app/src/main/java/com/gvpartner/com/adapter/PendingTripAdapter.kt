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
import com.gvpartner.com.databinding.RowTriphistoryListBinding
import com.gvpartner.com.model.TripHistoryResponseData
import com.gvpartner.com.ui.common.TripDetailsActivity

class PendingTripAdapter (val context : Context, val list: List<TripHistoryResponseData>, var flag:String) : RecyclerView.Adapter<PendingTripAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowTriphistoryListBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_triphistory_list, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvDate.text =  data.tripDetails?.bookingDateFrom
        holder.binding.tvTime.text =  data.bookingTime
        holder.binding.tvUsername.text = data.bookingId
        holder.binding.tvStart.text = data.tripDetails?.fromTrip
        holder.binding.tvStop.text = data.tripDetails?.toTrip
        holder.binding.tvBookingid.text = "Booking  Id: ${data.bookingId}"
        holder.binding.tvWeight.text = data.tripDetails?.vehicle?.capacity
        Glide.with(context).load(data.tripDetails?.vehicle?.vehicleImage).into(holder.binding.ivTruck)
        if (flag == "PendingLoader") {
            holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, TripDetailsActivity::class.java)
//                intent.putExtra("orderType", "4")
                intent.putExtra("booking", data)
                intent.putExtra("flag", "upcomingloader")
                context.startActivity(intent)
            })
        }
        else if (flag == "PendingPassenger"){
            holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                    val intent = Intent(context, TripDetailsActivity::class.java)
//                intent.putExtra("orderType", "4")
                    intent.putExtra("booking", data)
                    intent.putExtra("flag", "upcomingpassenger")
                    context.startActivity(intent)
                })
            })
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