package com.govahanpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.RowWalletListBinding
import com.govahanpartner.com.customclick.wallet_customclick
import com.govahanpartner.com.model.WalletFilterListData
import com.govahanpartner.com.utils.DateFormat
import com.prefers.UserPref
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//import com.govahanpartner.com.model.VendorWalletData

class WalletVendorAdapter (val context : Context, var wallet_customclick: wallet_customclick, val list: List<WalletFilterListData>) :
    RecyclerView.Adapter<WalletVendorAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null
    lateinit var userPref : UserPref

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowWalletListBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_list, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        userPref = UserPref(context)

        try {
            if (data.type == 1) {
                holder.binding.tvDetail.text = "Amount credited for ${data.bookingId}."
                holder.binding.tvAmount.text = "+₹${data.amount}"
            }else if (data.type == 2){
                holder.binding.tvDetail.text = "Added amount."
                holder.binding.tvAmount.text = "+₹${data.amount}"
            }else if (data.type == 3){
                holder.binding.tvDetail.text = "Payout Requested successfully."
                holder.binding.tvAmount.text = "-₹${data.amount}"
                holder.binding.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.light_red))
            }else if (data.type == 4){
                if(userPref.getRole() == "4"){
                    holder.binding.tvAmount.text = "+₹${data.amount}"
                    holder.binding.tvDetail.text = "${data.fromUser?.name} has sent to you."
                }else {
                    holder.binding.tvDetail.text = "Amount moved to vendor"
                    holder.binding.tvAmount.text = "-₹${data.amount}"
                    holder.binding.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.light_red))
                }
            }else{
                holder.binding.tvDetail.text = "Referral amount credited."

            }

            holder.binding.tvDate.text = data.createdAt?.let { DateFormat.convertDate(it) }
                holder.binding.transactionId.text=data.transactionId
        }catch (e:Exception){
            e.printStackTrace()
        }
        holder.binding.linearItem.setOnClickListener(View.OnClickListener {
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