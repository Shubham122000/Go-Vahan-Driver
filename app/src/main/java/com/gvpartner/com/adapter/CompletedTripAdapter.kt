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
import com.gvpartner.com.ui.vendor.BookingDetailsActivity
import com.gvpartner.com.utils.Bookingid


class CompletedTripAdapter (val context : Context, val list: List<TripHistoryResponseData>, var getbookindid : Bookingid, var flag:String) : RecyclerView.Adapter<CompletedTripAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null
    lateinit var bookingid :String

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
        holder.binding.tvDate.text = data.tripDetails?.bookingDateFrom
        holder.binding.tvTime.text =  data.bookingTime
        holder.binding.tvUsername.text = data.bookingId
        holder.binding.tvStart.text = data.tripDetails?.fromTrip
        holder.binding.tvStop.text = data.tripDetails?.toTrip
        holder.binding.tvBookingid.text = "Booking  Id: ${data.bookingId}"
        holder.binding.tvWeight.text = data.tripDetails?.vehicle?.capacity
        bookingid = data.bookingId.toString()
        getbookindid.getbooking(bookingid)
        Glide.with(context).load(data.tripDetails?.vehicle?.vehicleImage).into(holder.binding.ivTruck)

        if (flag == "AdapterforLoader"){
            holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, BookingDetailsActivity::class.java)
                intent.putExtra("orderType", "4")
                intent.putExtra("booking", data)
                intent.putExtra("flag","AdapterforLoader")
                context.startActivity(intent)
            })

        }else if(flag == "AdapterforPassenger"){
            holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, BookingDetailsActivity::class.java)
                intent.putExtra("orderType", "4")
                intent.putExtra("booking", data)
                intent.putExtra("flag","AdapterforPassenger")
                context.startActivity(intent)
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
        fun onItemClick(position: Int, view: View,)
    }
}