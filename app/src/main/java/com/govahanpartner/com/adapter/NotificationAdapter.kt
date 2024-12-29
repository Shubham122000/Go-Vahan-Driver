package com.govahanpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahanpartner.com.databinding.RowNotificationLayoutBinding

import com.govahanpartner.com.R
import com.govahanpartner.com.model.NotificationResponseData

class NotificationAdapter(val mcontext :Context,val list:ArrayList<NotificationResponseData>):
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var binding: RowNotificationLayoutBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_notification_layout, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.tvHeadnotification.text = data.title.toString()
        holder.binding.tvDetail.text = data.message.toString()
//        holder.binding.tvCount.text = data.
        holder.binding.tvTime.text = data.createAt

    }

    override fun getItemCount(): Int {
        return list.size
    }


}