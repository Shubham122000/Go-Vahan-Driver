package com.govahanpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahanpartner.com.R
import com.govahanpartner.com.customclick.Click
import com.govahanpartner.com.databinding.InvoicelistDataLayoutBinding
import com.govahanpartner.com.model.InvoiceListResponseData


class InvoiceListAdapter (val context : Context, var list: ArrayList<InvoiceListResponseData>,var click:Click) :
    RecyclerView.Adapter<InvoiceListAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null

    inner  class ViewHolder(items: View) : RecyclerView.ViewHolder(items){
        var binding: InvoicelistDataLayoutBinding = DataBindingUtil.bind(items)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val items = LayoutInflater.from(parent.context).inflate(R.layout.invoicelist_data_layout, parent, false)
        return ViewHolder(items)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = list[position]
        holder.binding.date.text = data.tripDetails?.bookingDateFrom
        holder.binding.locationfrom.text = data.tripDetails?.fromTrip
        holder.binding.locationto.text = data.tripDetails?.toTrip
        holder.binding.tvBookingid.text = data.bookingId
        holder.binding.tvInvoicenumber.text = data.paymentDetails.get(0).invoice

        holder.binding.click.setOnClickListener {
            click.click(data)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener

    }

    interface OnItemClickListener  {
        fun onItemClick(position: Int, view: View)
    }
}