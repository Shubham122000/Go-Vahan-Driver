package com.govahanpartner.com.customclick

import com.govahanpartner.com.model.TripListResponseData

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

    fun click(id:String?)

}
interface loadervehiclelist {

    fun loadervehiclelist(id: String?)

}
interface loadervehicleedit {

    fun loadervehicleedit(id: String?)

}