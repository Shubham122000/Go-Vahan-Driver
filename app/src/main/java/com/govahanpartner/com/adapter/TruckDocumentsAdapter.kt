package com.govahanpartner.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.govahanpartner.com.R

//import com.govahanpartner.com.customclick.click
import com.govahanpartner.com.model.TruckDocumentsData


class TruckDocumentsAdapter  (var context: Context, var list: List<TruckDocumentsData>/*, var click: click*/)
    : RecyclerView.Adapter<TruckDocumentsAdapter.ViewHolder>() {
    var newurl :String = ""
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

        holder.tv_rcbook.text=data.doc_name
        holder.date.text=data.exp_date
        holder.fileDwnd.setOnClickListener {
//            click.Click(position,data.doc)

        }

    }



    override fun getItemCount(): Int {
        return list.size
    }
}