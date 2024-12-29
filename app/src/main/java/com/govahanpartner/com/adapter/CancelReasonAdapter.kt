package com.govahanpartner.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.RowCancellationReasonBinding
import com.govahanpartner.com.model.Loader_cancel_ReasonList_ResponseData
import com.govahanpartner.com.utils.Canceldata

data class CancelReasonAdapter(val context: Context, val list :ArrayList<Loader_cancel_ReasonList_ResponseData>,
                               var cancel : Canceldata
)
    :RecyclerView.Adapter<CancelReasonAdapter.ViewHolder>(){

    companion object{
        var reason_id :String = ""
        var value :String = ""
    }

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowCancellationReasonBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_cancellation_reason,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.cbReason.text = data.reason.toString()

        var flag1 = true

        holder.binding.cbReason.setOnClickListener {
            if (flag1) {
                holder.binding.cbReason.isChecked = true
                reason_id = data.id.toString()
//                value = data.reason.toString()
                cancel.idreason(reason_id)
                flag1 = false

            } else {
                holder.binding.cbReason.isChecked = false
                value = ""
                flag1 = true
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}
