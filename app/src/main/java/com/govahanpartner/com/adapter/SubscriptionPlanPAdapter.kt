package com.govahanpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.UiRowIndividualSubscriptionplanBinding
import com.govahanpartner.com.customclick.SubscriptionPLanClick
import com.govahanpartner.com.model.SubscriptionPlanData


class SubscriptionPlanPAdapter (val context : Context, val list: List<SubscriptionPlanData>, var SubscriptionPLanClick: SubscriptionPLanClick) : RecyclerView.Adapter<SubscriptionPlanPAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null


    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: UiRowIndividualSubscriptionplanBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ui_row_individual_subscriptionplan, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        holder.binding.headingtext.text = data.planName
        holder.binding.tvDetails.text = data.description
        holder.binding.tvPrice.text = data.amount






        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
            SubscriptionPLanClick.PLanClick(data.amount.toString(),data.validity.toString(),data.id.toString())

        })

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