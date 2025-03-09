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
                holder.binding.tvDetail.text = "Received requested amount."

            }

//            // Convert ISO string to Instant
//            val instant = Instant.parse(data.createdAt)
//
//            // Convert Instant to LocalDate and LocalTime
//            val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate() // Extract Date
//            val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime() // Extract Time
//
//            // Format Date and Time separately
//            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

//            holder.binding.tvDate.text = "${localDate.format(dateFormatter)} ${localTime.format(timeFormatter)}"
            holder.binding.tvDate.text = data.createdAt?.let { DateFormat.convertDate(it) }

//            val formattedDate =  // e.g., "2025-01-30"
//            val formattedTime = localTime.format(timeFormatter // e.g., "19:26:26"


//            holder.binding.tvDate.text = data.transaction_date
//            if (data.transaction_id== null){
//                holder.binding.transactionId.text="Subscription."
//            }
//            else{
                holder.binding.transactionId.text=data.transactionId
//            }

//            if (data.referal_type==1){
//                if (data.transaction_type.equals("1")){
//                    holder.binding.tvAmount.setTextColor(Color.parseColor("#3CB878"))
//                    holder.binding.tvAmount.text = "+₹${data.amount}"
//                    holder.binding.tvDetail.text = "Amount added by refferal code."
//                }
//            }
//            else if(data.referal_type==2){
//                    holder.binding.tvAmount.setTextColor(Color.parseColor("#FF0000"))
//                    holder.binding.tvAmount.text = "-₹${data.amount}"
//                    holder.binding.tvDetail.text = "Refund amount deducted."
//            }
//            else{
//                if (data.transaction_type.equals("1")){
//                    holder.binding.tvAmount.setTextColor(Color.parseColor("#3CB878"))
//                    holder.binding.tvAmount.text = "+₹${data.amount}"
//                    if(data.custom_key_one==null){
//                        holder.binding.tvDetail.text = "Amount added to wallet"
//                    }else{
//                        if (data.driver_name==null){
//                            holder.binding.tvDetail.text = "Amount pay by user"
//                        }
//                        else{
//                            holder.binding.tvDetail.text = "Amount pay by ${data.driver_name}driver."
//                        }
//                    }
//                }
//                else{
//                    holder.binding.tvAmount.setTextColor(Color.parseColor("#FF0000"))
//                    holder.binding.tvAmount.text = "-₹${data.amount}"
//                    if (data.custom_key==1){
//                        holder.binding.tvDetail.text = "Amount move to vendor."
//                    }
//                    else{
//                        holder.binding.tvDetail.text = "Subscription purchased."
//                    }
//                }
//            }
//             if (data.transaction_id.equals("withdraw")){
//                holder.binding.tvDetail.text = "Withdraw amount."
//            }
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