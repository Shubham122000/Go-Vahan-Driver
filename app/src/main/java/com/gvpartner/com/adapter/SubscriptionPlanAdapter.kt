package com.gvpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gvpartner.com.R
import com.gvpartner.com.databinding.UiRowIndividualSubscriptionplanBinding
import com.gvpartner.com.ui.vendor.PaymentMethodsVActivity


class SubscriptionPlanAdapter (val context : Context, val list: List<String>) : RecyclerView.Adapter<SubscriptionPlanAdapter.ViewHolder>() {
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

        /*holder.binding.tvCountryname.text = data.country
        holder.binding.tvComment.text = data.comment

        holder.binding.tvDate.text = data.start_date
        holder.binding.tvDatetype.text = data.date_type
        Glide.with(context).load(data.profile_image).into(holder.binding.imgUser)


       holder.binding.btnView.setOnClickListener(View.OnClickListener {
           val intent = Intent(context, EventCreatorDetailActivity::class.java)
               .putExtra("getuserid",data.id.toString())
           context.startActivity(intent)
       })*/



        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
           val intent = Intent(context, PaymentMethodsVActivity::class.java)
          intent.putExtra("orderType", "4")
            context.startActivity(intent)


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