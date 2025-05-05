package com.gvpartner.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.gvpartner.com.R
import com.gvpartner.com.databinding.RowRatingreviewBinding
import com.gvpartner.com.model.RatingResponseData


class ReviewAdapter (val context : Context, val list: List<RatingResponseData>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    private var listener: OnItemClickListener? = null


    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding: RowRatingreviewBinding = DataBindingUtil.bind(itemView)!!

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_ratingreview, parent, false)
        return ViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = list[position]

        Glide.with(context).load(data.profileImage).into(holder.binding.imgUser)
        holder.binding.tvUsername.text = data.name.toString()
        holder.binding.tvPhone.text = data.address.toString()
        holder.binding.tvDate.text = data.createdAt.toString()
        holder.binding.rating.rating = data.rating!!.toFloat()
        holder.binding.tvLicno.text = data.description.toString()

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