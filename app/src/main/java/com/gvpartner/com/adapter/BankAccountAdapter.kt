package com.gvpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gvpartner.com.R
import com.gvpartner.com.databinding.BankAccountsListBinding
import com.gvpartner.com.customclick.bankclick
import com.gvpartner.com.model.BankAccountListData
class BankAccountAdapter  (val context : Context, var data: ArrayList<BankAccountListData>,var bankclick: bankclick) :
    RecyclerView.Adapter<BankAccountAdapter.ViewHolder>() {
    private var selectedItem = false
    private var listener: OnItemClickListener? = null
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: BankAccountsListBinding = DataBindingUtil.bind(itemView)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bank_accounts_list, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val List = data[position]

        holder.binding.bankName.text = List.bank_name.toString()
        holder.binding.holdername.text = List.account_holder_name.toString()
        holder.binding.accountNumber.text = List.account_no.toString()
        holder.binding.radiobutton.isChecked = List.status==1
//        holder.binding.radiobutton.isEnabled=false
        holder.binding.radiobutton.setOnClickListener{
        holder.binding.radiobutton.isChecked=true
            bankclick.bankclick(List.id)
        }


        if (selectedItem==false){
            holder.binding.mainLayout.setBackgroundResource(R.color.white)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

}