package com.govahanpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.RowWalletListBinding
import com.govahanpartner.com.customclick.wallet_customclick
import com.govahanpartner.com.model.WalletListData

class TransactionReportAdapter (val context : Context, var wallet_customclick: wallet_customclick, val list: List<WalletListData>) :
    RecyclerView.Adapter<TransactionReportAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowWalletListBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_list, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        try {


            holder.binding.tvAmount.text = data.amount
            holder.binding.tvDate.text = data.transaction_date

            if (data.transaction_type.equals("1")){
                holder.binding.tvDetail.text = "Amount added to wallet"
            }else{

                holder.binding.tvDetail.text = "Subscription purchased."
            }



            holder.binding.transactionId.text=data.transaction_id

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