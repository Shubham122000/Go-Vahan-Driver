package com.govahanpartner.com.passengerviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanpartner.com.model.*
import com.govahanpartner.com.model.*
import com.govahanpartner.com.network.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AddTripPassengerViewModels @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val progressBarStatus = MutableLiveData<Boolean>()
    val _progressBarVisibility = MutableLiveData<Int>()
    var AddTripResponse = MutableLiveData<CreateBusinesscard>()
    var LoadcarryingResponse = MutableLiveData<LoaodCarryingResponse>()
    var LoadCarryingData = MutableLiveData<ArrayList<LoadCarryingData>>()
    var truckTypeResponse = MutableLiveData<TypeofTruckResponse>()
    var truckTypeData = MutableLiveData<ArrayList<TypeofTruckResponseData>>()
    var BodyTypeResponse = MutableLiveData<BodyType>()
    var SeatResponse = MutableLiveData<SeatResponse>()
    var SeatResponseData = MutableLiveData<ArrayList<SeatResponseData>>()
    var VehicalwheelsResponse = MutableLiveData<vehicalwheelsResponse>()
    val DriverlistResponse = MutableLiveData<DriverListResponse>()
    val VehicleDataResponse = MutableLiveData<VehicledataResponse>()
    val DriverlistResponseData = MutableLiveData<ArrayList<DriverListResponseData>>()
    var VehicleListResponse = MutableLiveData<VehicleList>()
    var VehicleListResponseData = MutableLiveData<ArrayList<VehicleListData>>()
    var BodyTypeData = MutableLiveData<ArrayList<BodyTypeData>>()
    var VehicalwheelsData = MutableLiveData<ArrayList<vehicalwheelsResponseData>>()
    var HightTypeResponse = MutableLiveData<hightResponse>()
    var HeightTypeData = MutableLiveData<ArrayList<hightResponseData>>()
    var ColorResponse = MutableLiveData<ColorResponse>()
    var YearResponse = MutableLiveData<YearResponse>()
    var Colordata = MutableLiveData<ArrayList<ColorResponseData>>()
    var Yearresponsedata = MutableLiveData<ArrayList<YearResponseData>>()
    var AddpassengerResponse = MutableLiveData<AddloaderResponse>()
    var AddloaderResponse = MutableLiveData<AddloaderResponse>()
    var Loaderimage = MutableLiveData<Loaderimage>()
    var AddVehicalfinalResponse = MutableLiveData<AddVehicalfinalResponse>()
    var VehiclenumberResponse = MutableLiveData<VehicleNumberPassengerLIst>()
    var VehiclenumberData = MutableLiveData<ArrayList<VehicleNumberData>>()
    var addpassengerLoaderResponse = MutableLiveData<ArrayList<VehicleNumberData>>()
    var AddTripDriverMOdelClass = MutableLiveData<AddTripDriverMOdelClass>()


    fun Year(token :String,type:String): MutableLiveData<YearResponse> {
        if (YearResponse == null) {
            YearResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.YearApi(token,type)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    YearResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return YearResponse
    }

    fun VehicleNumberLIst(token :String): MutableLiveData<VehicleNumberPassengerLIst> {
        if (VehiclenumberResponse == null) {
            VehiclenumberResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.get_passenger_vehicleno(token)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    VehiclenumberResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return VehiclenumberResponse
    }


    fun TypeofTruckApi(token :String,type: String): MutableLiveData<TypeofTruckResponse> {
        if (truckTypeResponse == null) {
            truckTypeResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.TypeofTruckApi(token,type)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    truckTypeResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return truckTypeResponse
    }

    fun self_driver_passenger_trip(token :String): MutableLiveData<AddTripDriverMOdelClass> {
        if (AddTripDriverMOdelClass == null) {
            AddTripDriverMOdelClass = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.self_driver_passenger_trip(token)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    AddTripDriverMOdelClass.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return AddTripDriverMOdelClass
    }

    fun SeatAPI(token :String): MutableLiveData<SeatResponse> {
        if (SeatResponse == null) {
            SeatResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.vehicleSeat(token)
                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    SeatResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return SeatResponse
    }

    fun Vehicalwheels(token :String,type: String): MutableLiveData<vehicalwheelsResponse> {
        if (VehicalwheelsResponse == null) {
            VehicalwheelsResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.vehicalwheels(token,type)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    VehicalwheelsResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return VehicalwheelsResponse
    }

    fun driverListApi(
        token: String,
        vendor_id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.driverList(token, vendor_id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DriverlistResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
 fun get_passenger_vehicleno_details(
        token: String,
        id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_passenger_vehicleno_details(token, id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                VehicleDataResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }



    fun PassengerimageAPI(
        token :String,
        vehicle_id:String,
        Image: MultipartBody.Part?
    ) {
        val vehicle_id: RequestBody = vehicle_id.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.passengerVehicleimg(
                token,
                vehicle_id,
                Image!!
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                Loaderimage.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }


    fun AddpassengerVehicalfinalApi(
        token: String,
        driver_id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response = mainRepository.FinalpassengervehicalSubmit(token,driver_id)
            if (response.isSuccessful) {

                progressBarStatus.value = false
                Log.d("TAG", "success")
                AddVehicalfinalResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", "Failed")
            }
        }
    }
    fun PassengerdocAPI(
        token :String,
        vehicle_id:String,
        doc_name:String,
        exp_date:String,
        Doc: MultipartBody.Part?
    ) {
        val vehicle_id: RequestBody = vehicle_id.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name: RequestBody = doc_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date: RequestBody = exp_date.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.Passengervehicledoc(
                token,
                vehicle_id,
                doc_name,
                exp_date,
                Doc!!
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                Loaderimage.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun passenger_vehicle_doc_update(
        token :String,
        vehicle_id:String,
        doc_name:String,
        exp_date:String,
        Doc: MultipartBody.Part?
    ) {
        val vehicle_id: RequestBody = vehicle_id.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name: RequestBody = doc_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date: RequestBody = exp_date.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.passenger_vehicle_doc_update(
                token,
                vehicle_id,
                doc_name,
                exp_date,
                Doc!!
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                Loaderimage.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun loader_vehicle_doc_update(
        token :String,
        vehicle_id:String,
        doc_name:String,
        exp_date:String,
        Doc: MultipartBody.Part?
    ) {
        val vehicle_id: RequestBody = vehicle_id.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name: RequestBody = doc_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date: RequestBody = exp_date.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.loader_vehicle_doc_update(
                token,
                vehicle_id,
                doc_name,exp_date,
                Doc!!
            )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                Loaderimage.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }


      fun AddTripApi(
        token: String,
        tip_task: String,
        from_trip: String,
        to_trip: String,
        vehicle_type: String,
        vehicle_numbers: String,
        no_tyers: String,
        body_type: String,
        assign_driver: String,
        total_distance: String,
        freight_amount: String,
        pickup_lat: String,
        pickup_long: String,
        dropup_lat: String,
        dropup_long: String,
        vehicle_id: String,
        booking_date_from: String,
        booking_time: String,
        fuel_charge: String,
        toll_tax: String,
        driver_fee: String
        ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.AddTripPassenger(
                    token,
                    tip_task,
                    from_trip,
                    to_trip,
                    vehicle_type,
                    vehicle_numbers,
                    no_tyers,
                    body_type,
                    assign_driver,
                    total_distance,
                    freight_amount,
                    pickup_lat,
                    pickup_long,
                    dropup_lat,
                    dropup_long,
                    vehicle_id,
                    booking_date_from,
                    booking_time,fuel_charge,
                    toll_tax,
                    driver_fee
                )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddTripResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

    fun Addpassengervehical(
        token: String,
        driver_id: String,
        vehicle_owner_name: String,
        vehicle_name: String,
        year_of_model: String,
        vehicle_number: String,
        vehicle_type: String,
        capacity: String,
        height: String,
        color: String,
        no_of_tyres: String,
        body_type: String,
        seat:String,
        images1: MultipartBody.Part,
        images2: MultipartBody.Part,
        images3: MultipartBody.Part,
        images4: MultipartBody.Part,
        doc1: MultipartBody.Part,
        doc2: MultipartBody.Part,
        doc3: MultipartBody.Part,
        doc4: MultipartBody.Part,
        doc5: MultipartBody.Part,
        doc6: MultipartBody.Part,
        exp_date_1: String,
        exp_date_2: String,
        exp_date_3: String,
        exp_date_4: String,
        exp_date_5: String,
        other_exp_date: String,
        doc_name_1: String,
        doc_name_2: String,
        doc_name_3: String,
        doc_name_4: String,
        doc_name_5: String,
        other_doc_name: String,
    ) {
        val driver_id: RequestBody = driver_id.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_owner_name: RequestBody = vehicle_owner_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_name: RequestBody = vehicle_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val year_of_model: RequestBody = year_of_model.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_number: RequestBody = vehicle_number.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_type: RequestBody = vehicle_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val color: RequestBody = color.toRequestBody("text/plain".toMediaTypeOrNull())
        val capacity: RequestBody = capacity.toRequestBody("text/plain".toMediaTypeOrNull())
        val height: RequestBody = height.toRequestBody("text/plain".toMediaTypeOrNull())
        val no_of_tyres: RequestBody = no_of_tyres.toRequestBody("text/plain".toMediaTypeOrNull())
        val body_type: RequestBody = body_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val seat: RequestBody = seat.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_1: RequestBody = exp_date_1.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_2: RequestBody = exp_date_2.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_3: RequestBody = exp_date_3.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_4: RequestBody = exp_date_4.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_5: RequestBody = exp_date_5.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_exp_date: RequestBody = other_exp_date.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_1: RequestBody = doc_name_1.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_2: RequestBody = doc_name_2.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_3: RequestBody = doc_name_3.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_4: RequestBody = doc_name_4.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_5: RequestBody = doc_name_5.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_doc_name: RequestBody = other_doc_name.toRequestBody("text/plain".toMediaTypeOrNull())

        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.addpassengervehical(
                    token,
                    driver_id,
                    vehicle_owner_name,
                    vehicle_name,
                    year_of_model,
                    vehicle_number,
                    vehicle_type,
                    capacity,
                    height,
                    color,
                    no_of_tyres,
                    body_type,
                    seat,images1, images2, images3, images4, doc1, doc2, doc3, doc4, doc5, doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4, exp_date_5, other_exp_date, doc_name_1, doc_name_2, doc_name_3, doc_name_4, doc_name_5, other_doc_name
                )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddloaderResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun indi_add_passenger_vehicle(
        token: String,
        driver_id: String,
        vehicle_owner_name: String,
        vehicle_name: String,
        year_of_model: String,
        vehicle_number: String,
        vehicle_type: String,
//        vehicle_category: String,
        capacity: String,
        height: String,
        color: String,
        no_of_tyres: String,
        body_type: String,
        seat:String,
        images1: MultipartBody.Part,
        images2: MultipartBody.Part,
        images3: MultipartBody.Part,
        images4: MultipartBody.Part,
        doc1: MultipartBody.Part,
        doc2: MultipartBody.Part,
        doc3: MultipartBody.Part,
        doc4: MultipartBody.Part,
        doc5: MultipartBody.Part,
        doc6: MultipartBody.Part,
        exp_date_1: String,
        exp_date_2: String,
        exp_date_3: String,
        exp_date_4: String,
        exp_date_5: String,
        other_exp_date: String,
        doc_name_1: String,
        doc_name_2: String,
        doc_name_3: String,
        doc_name_4: String,
        doc_name_5: String,
        other_doc_name: String,
    ) {
        val driver_id: RequestBody = driver_id.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_owner_name: RequestBody = vehicle_owner_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_name: RequestBody = vehicle_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val year_of_model: RequestBody = year_of_model.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_number: RequestBody = vehicle_number.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_type: RequestBody = vehicle_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val color: RequestBody = color.toRequestBody("text/plain".toMediaTypeOrNull())
        val capacity: RequestBody = capacity.toRequestBody("text/plain".toMediaTypeOrNull())
        val height: RequestBody = height.toRequestBody("text/plain".toMediaTypeOrNull())
        val no_of_tyres: RequestBody = no_of_tyres.toRequestBody("text/plain".toMediaTypeOrNull())
        val body_type: RequestBody = body_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val seat: RequestBody = seat.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_1: RequestBody = exp_date_1.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_2: RequestBody = exp_date_2.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_3: RequestBody = exp_date_3.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_4: RequestBody = exp_date_4.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_5: RequestBody = exp_date_5.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_exp_date: RequestBody = other_exp_date.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_1: RequestBody = doc_name_1.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_2: RequestBody = doc_name_2.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_3: RequestBody = doc_name_3.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_4: RequestBody = doc_name_4.toRequestBody("text/plain".toMediaTypeOrNull())
        val doc_name_5: RequestBody = doc_name_5.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_doc_name: RequestBody = other_doc_name.toRequestBody("text/plain".toMediaTypeOrNull())


        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.indi_add_passenger_vehicle(
                    token,
                    driver_id,
                    vehicle_owner_name,
                    vehicle_name,
                    year_of_model,
                    vehicle_number,
                    vehicle_type,
//                    vehicle_category,
                    capacity,
                    height,
                    color,
                    no_of_tyres,
                    body_type,
                    seat,images1, images2, images3, images4, doc1, doc2, doc3, doc4, doc5, doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4, exp_date_5, other_exp_date, doc_name_1, doc_name_2, doc_name_3, doc_name_4, doc_name_5, other_doc_name
                )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddloaderResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }
    fun passenger_vehicle_update(
        token: String,
        driver_id: String,
        vehicle_owner_name: String,
        vehicle_name: String,
        year_of_model: String,
        vehicle_number: String,
        vehicle_type: String,
//        vehicle_category: String,
        capacity: String,
        height: String,
        color: String,
        no_of_tyres: String,
        body_type: String,
        seat:String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.passenger_vehicle_update(
                    token,
                    driver_id,
                    vehicle_owner_name,
                    vehicle_name,
                    year_of_model,
                    vehicle_number,
                    vehicle_type,
//                    vehicle_category,
                    capacity,
                    height,
                    color,
                    no_of_tyres,
                    body_type,
                    seat
                )
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddloaderResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }

    }

}