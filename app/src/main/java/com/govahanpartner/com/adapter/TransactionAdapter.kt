package com.govahanpartner.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.prefers.UserPref
import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.RowWalletListBinding
import com.govahanpartner.com.model.TransactionReportResponseData
import com.govahanpartner.com.utils.DateFormat

class TransactionAdapter (val context: Context, val list : ArrayList<TransactionReportResponseData>, var userPref:UserPref):
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    private var listener: TransactionAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvAmount.text = "₹${data.amount.toString()}"
        holder.binding.tvDetail.text = "You have sent payout request."
//        if (userPref.getName().equals(data.name)){
//            holder.binding.tvDetail.text= "Subscription purchased."
//        }else{
//            holder.binding.tvDetail.text = "You have received Rs.${data.fare} form ${data.name}"
//        }
        holder.binding.tvDate.text=DateFormat.TimeFormat(data.createdAt)
        if (data.status == 1){
            holder.binding.transactionId.text= "Pending"
        }else if (data.status == 2){
            holder.binding.transactionId.text= "Approved"
        }else{
            holder.binding.transactionId.text= "Rejected"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowWalletListBinding = DataBindingUtil.bind(itemView)!!

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

}