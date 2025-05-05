package com.gvpartner.com.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gvpartner.com.R
import com.gvpartner.com.model.Documents

//import com.govahanpartner.com.customclick.click
import com.gvpartner.com.utils.toast


class TruckDocumentsAdapter  (var context: Context, var list: List<Documents>/*, var click: click*/)
    : RecyclerView.Adapter<TruckDocumentsAdapter.ViewHolder>() {
//    var newurl :String = ""
    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
//        var binding: TruckRepositoryDocumentItemsBinding = DataBindingUtil.bind(itemview)!!

        var tv_rcbook: TextView
        var fileDwnd: ImageView
        var date: TextView

        init {
            tv_rcbook = itemView.findViewById(R.id.tv_rcbook)
            fileDwnd = itemView.findViewById(R.id.file_dwnd)
            date = itemView.findViewById(R.id.date)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.truck_repository_document_items, parent, false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = list[position]
        if (data.docType == 1){
            holder.tv_rcbook.text= "Rc"
        }else if (data.docType == 2){
            holder.tv_rcbook.text= "Insurance"
        }else if (data.docType == 3){
            holder.tv_rcbook.text= "Pollution"
        }else if (data.docType == 4){
            holder.tv_rcbook.text= "Fitness"
        }else if (data.docType == 5){
            holder.tv_rcbook.text= "Rto"
        }else if (data.docType == 6){
            holder.tv_rcbook.text= "Other"
        }
        holder.date.text=data.docExpiryDate
        holder.fileDwnd.setOnClickListener {
            if (data.documentUrl?.isNotEmpty() == true) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            data.documentUrl
                        )
                    )
                )
            }else{
                context.toast("No document found.")
            }
        }

    }



    override fun getItemCount(): Int {
        return list.size
    }
}