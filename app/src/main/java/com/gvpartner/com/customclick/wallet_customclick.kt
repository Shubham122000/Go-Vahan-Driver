package com.gvpartner.com.customclick

import com.gvpartner.com.model.InvoiceListResponseData
import com.gvpartner.com.model.TripListResponseData
import com.gvpartner.com.model.Vehicles

interface wallet_customclick {

    fun onItemClick(id:String)

}
interface tripdelete {

    fun tripdelete(id:Int?)

}
interface tripclick {

    fun tripclick(tripData: TripListResponseData?)

}
interface deleteVehicle{

    fun deleteVehicle(id:Int?)

}
interface Click{

    fun click(data: InvoiceListResponseData?)

}
interface loadervehiclelist {

    fun loadervehiclelist(id: String?)

}
interface loadervehicleedit {

    fun loadervehicleedit(vehicle: Vehicles?)

}