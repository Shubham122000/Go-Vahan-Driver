package com.gvpartner.com.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

interface Bookingid{
    fun getbooking(bookingid:String)
}
interface Canceldata{
    fun idreason(reasonid:String)
}

fun convertTimestampToTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault()) // Format: 12:30 PM
    val date = Date(timestamp)
    return sdf.format(date)
}