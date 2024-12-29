package com.govahanpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prefers.UserPref
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.RowUpcomingBookingsBinding
import com.govahanpartner.com.model.TripHistoryResponseData
import com.govahanpartner.com.ui.common.TripDetailsActivity


class UpcomingBookingsAdapter (val context : Context,var userPref: UserPref, val list: ArrayList<TripHistoryResponseData>,var flag: String) : RecyclerView.Adapter<UpcomingBookingsAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null


    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowUpcomingBookingsBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_upcoming_bookings, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        try {


            holder.binding.tvDate.text = data.bookingDate
            holder.binding.tvTime.text = data.bookingTime
            holder.binding.tvUsername.text = data.partyName
            holder.binding.tvStart.text = data.picupLocation
            holder.binding.tvStop.text = data.dropLocation
            holder.binding.tvBookingid.text = "Booking  Id: ${data.bookingId}"
            holder.binding.tvWeight.text = data.capacity
            Glide.with(context).load(data.vehicleImage).into(holder.binding.ivTruck)
        }catch (e:Exception){
            e.printStackTrace()
        }


        if (userPref.getRole().equals("3")){
            if (flag == "upcomingpassenger"){
                holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                    val intent = Intent(context, TripDetailsActivity::class.java)
//                intent.putExtra("orderType", "4")
                    intent.putExtra("bookingid", data.bookingId.toString())
                    intent.putExtra("flag", "upcomingpassenger")
                    context.startActivity(intent)
                })
            }else if (flag == "upcomingloader"){
                holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                    val intent = Intent(context, TripDetailsActivity::class.java)
//                intent.putExtra("orderType", "4")
                    intent.putExtra("bookingid", data.bookingId.toString())
                    intent.putExtra("flag", "upcomingloader")
                    context.startActivity(intent)
                })
            }
        }
        else if (userPref.getRole().equals("2")){
            if (flag == "upcomingpassenger"){
                holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                    val intent = Intent(context, TripDetailsActivity::class.java)
//                intent.putExtra("orderType", "4")
                    intent.putExtra("bookingid", data.bookingId.toString())
                    intent.putExtra("flag", "upcomingpassenger")
                    context.startActivity(intent)
                })
            }else if (flag == "upcomingloader"){
                holder.binding.linearItem.setOnClickListener(View.OnClickListener {
                    val intent = Intent(context, TripDetailsActivity::class.java)
//                intent.putExtra("orderType", "4")
                    intent.putExtra("bookingid", data.bookingId.toString())
                    intent.putExtra("flag", "upcomingloader")
                    context.startActivity(intent)
                })
            }
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