package com.govahanpartner.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.govahanpartner.com.R
import com.govahanpartner.com.databinding.TruckImagesItemsBinding
import com.govahanpartner.com.model.TruckImagesData

class TruckImagesAdapter (var context: Context, var list: List<TruckImagesData>)
    : RecyclerView.Adapter<TruckImagesAdapter.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var binding: TruckImagesItemsBinding = DataBindingUtil.bind(itemview)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.truck_images_items, parent, false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = list[position]
//        Glide.with(context).load(data.image).into(holder.binding.ivTruck2)



    }

    override fun getItemCount(): Int {
        return list.size
    }
}