package com.govahanpartner.com.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.govahanpartner.com.model.*
import com.govahanpartner.com.network.MainRepository
import com.govahanpartner.com.model.TypeofTruckResponse
import com.govahanpartner.com.model.hightResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class TypeOfTruckViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    var truckTypeResponse = MutableLiveData<TypeofTruckResponse>()
    var BodyTypeResponse = MutableLiveData<BodyType>()
    var HightTypeResponse = MutableLiveData<hightResponse>()
    var ColorResponse = MutableLiveData<ColorResponse>()
    var YearResponse = MutableLiveData<YearResponse>()
    var VehicleListResponse = MutableLiveData<VehicleList>()
    var VehiclenumberResponse = MutableLiveData<VehicleNumberListMOdelCLass>()
    var VehicleListResponseData = MutableLiveData<ArrayList<VehicleListData>>()
    var VehiclenumberData = MutableLiveData<ArrayList<VehicleNumberListData>>()
    var VehicalwheelsResponse = MutableLiveData<vehicalwheelsResponse>()
    var AddloaderResponse = MutableLiveData<AddloaderResponse>()
    var AddTripResponse = MutableLiveData<AddTripDriverMOdelClass>()
    var AddVehicalfinalResponse = MutableLiveData<AddVehicalfinalResponse>()
    var LoadcarryingResponse = MutableLiveData<LoaodCarryingResponse>()
    var AddTripDriverMOdelClass = MutableLiveData<AddTripDriverMOdelClass>()
//    var AddTripDriverData = MutableLiveData<AddTripDriverData>()
    var LoadCarryingData = MutableLiveData<ArrayList<LoadCarryingData>>()
    var Loaderimage = MutableLiveData<Loaderimage>()
    var truckTypeData = MutableLiveData<ArrayList<TypeofTruckResponseData>>()
    var BodyTypeData = MutableLiveData<ArrayList<BodyTypeData>>()
    var Colordata = MutableLiveData<ArrayList<ColorResponseData>>()
    var Yearresponsedata = MutableLiveData<ArrayList<YearResponseData>>()
    var HeightTypeData = MutableLiveData<ArrayList<hightResponseData>>()
    var VehicalwheelsData = MutableLiveData<ArrayList<vehicalwheelsResponseData>>()
    val progressBarStatus = MutableLiveData<Boolean>()
    val _progressBarVisibility = MutableLiveData<Int>()
    val DriverlistResponse = MutableLiveData<DriverListResponse>()
    val DriverlistResponseData = MutableLiveData<ArrayList<DriverListResponseData>>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility
    val VehicleDataResponse = MutableLiveData<VehicledataResponse>()




    fun CapacityAPI(token :String): MutableLiveData<LoaodCarryingResponse> {
        if (LoadcarryingResponse == null) {
            LoadcarryingResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.CapacityApi(token)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    LoadcarryingResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return LoadcarryingResponse
    }
    fun self_driver_trip(token :String): MutableLiveData<AddTripDriverMOdelClass> {
        if (AddTripDriverMOdelClass == null) {
            AddTripDriverMOdelClass = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.self_driver_trip(token)

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
    fun get_loader_vehicleno_details(
        token: String,
        id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.get_loader_vehicleno_details(token, id)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                VehicleDataResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false

                Log.d("TAG", response.body().toString())
            }
        }
    }
    fun TypeofTruckApi(token :String,type: String): MutableLiveData<TypeofTruckResponse> {
        if (truckTypeResponse == null) {
            truckTypeResponse = MutableLiveData()
        }
        progressBarStatus.value = true

        viewModelScope.launch {
            try {
                val response = mainRepository.TypeofTruckApi(token,type)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    truckTypeResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                progressBarStatus.value = false

                e.printStackTrace()
            }
        }
        return truckTypeResponse
    }

    fun BodyType(token :String): MutableLiveData<BodyType> {
        if (BodyTypeResponse == null) {
            BodyTypeResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.BodyTypeApi(token)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    BodyTypeResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                progressBarStatus.value = false

                e.printStackTrace()
            }
        }
        return BodyTypeResponse
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
                progressBarStatus.value = false

                e.printStackTrace()
            }
        }
        return VehicalwheelsResponse
    }

    fun driverListApi(
        token: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.driverList(token)
            if (response.isSuccessful) {
                progressBarStatus.value = false
                DriverlistResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.body().toString())
            }
        }
    }

    fun HightType(token :String): MutableLiveData<hightResponse> {
        if (HightTypeResponse == null) {
            HightTypeResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.HightApi(token)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    HightTypeResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return HightTypeResponse
    }

//    fun Color(token :String): MutableLiveData<ColorResponse> {
//        if (ColorResponse == null) {
//            ColorResponse = MutableLiveData()
//        }
//        viewModelScope.launch {
//            try {
//                val response = mainRepository.ColorApi(token)
//
//                if (response.isSuccessful) {
//                    progressBarStatus.value = false
//                    ColorResponse.postValue(response.body())
//                }
//            }catch (e:Exception) {
//                _progressBarVisibility.postValue(0)
//                e.printStackTrace()
//            }
//        }
//        return ColorResponse
//    }


    fun VehicleList(token :String): MutableLiveData<VehicleList> {
        if (VehicleListResponse == null) {
            VehicleListResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.GetVehicle(token)

                if (response.isSuccessful) {
                    progressBarStatus.value = false
                    VehicleListResponse.postValue(response.body())
                }
            }catch (e:Exception) {
                _progressBarVisibility.postValue(0)
                e.printStackTrace()
            }
        }
        return VehicleListResponse
    }

    fun VehicleNumberLIst(token :String): MutableLiveData<VehicleNumberListMOdelCLass> {
        if (VehiclenumberResponse == null) {
            VehiclenumberResponse = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.get_loder_vehicleno(token)

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

      fun TripLoaderDriver(token :String): MutableLiveData<AddTripDriverMOdelClass> {
        if (AddTripDriverMOdelClass == null) {
            AddTripDriverMOdelClass = MutableLiveData()
        }
        viewModelScope.launch {
            try {
                val response = mainRepository.self_driver_trip(token)

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


    fun Addloadervehical(
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
        isFromPassenger: String,
        seat: String,
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
        /*doc_name_1: String,
        doc_name_2: String,
        doc_name_3: String,
        doc_name_4: String,
        doc_name_5: String,*/
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
        val isFromPassenger: RequestBody = isFromPassenger.toRequestBody("text/plain".toMediaTypeOrNull())
        val seat: RequestBody = seat.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_1: RequestBody = exp_date_1.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_2: RequestBody = exp_date_2.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_3: RequestBody = exp_date_3.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_4: RequestBody = exp_date_4.toRequestBody("text/plain".toMediaTypeOrNull())
        val exp_date_5: RequestBody = exp_date_5.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_exp_date: RequestBody = other_exp_date.toRequestBody("text/plain".toMediaTypeOrNull())
//        val doc_name_1: RequestBody = doc_name_1.toRequestBody("text/plain".toMediaTypeOrNull())
//        val doc_name_2: RequestBody = doc_name_2.toRequestBody("text/plain".toMediaTypeOrNull())
//        val doc_name_3: RequestBody = doc_name_3.toRequestBody("text/plain".toMediaTypeOrNull())
//        val doc_name_4: RequestBody = doc_name_4.toRequestBody("text/plain".toMediaTypeOrNull())
//        val doc_name_5: RequestBody = doc_name_5.toRequestBody("text/plain".toMediaTypeOrNull())
        val other_doc_name: RequestBody = other_doc_name.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.addloadervehical(
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
                    isFromPassenger,seat,
                    images1, images2, images3, images4, doc1, doc2, doc3, doc4, doc5,doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4,exp_date_5, other_exp_date, /*doc_name_1, doc_name_2, doc_name_3, doc_name_4, doc_name_5,*/  other_doc_name
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

    fun indi_add_loader_vehicle(
        token: String,
        vehicle_owner_name: String,
        vehicle_name: String,
        year_of_model: String,
        vehicle_number: String,
        vehicle_type: String,
        capacity: String,
        height: String,
        no_of_tyres: String,
        body_type: String,
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
        // Convert string parameters to RequestBody
        val vehicle_owner_name_body: RequestBody = vehicle_owner_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_name_body: RequestBody = vehicle_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val year_of_model_body: RequestBody = year_of_model.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_number_body: RequestBody = vehicle_number.toRequestBody("text/plain".toMediaTypeOrNull())
        val vehicle_type_body: RequestBody = vehicle_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val capacity_body: RequestBody = capacity.toRequestBody("text/plain".toMediaTypeOrNull())
        val height_body: RequestBody = height.toRequestBody("text/plain".toMediaTypeOrNull())
        val no_of_tyres_body: RequestBody = no_of_tyres.toRequestBody("text/plain".toMediaTypeOrNull())
        val body_type_body: RequestBody = body_type.toRequestBody("text/plain".toMediaTypeOrNull())
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



        // Show progress bar
        progressBarStatus.value = true

        // Launch coroutine in ViewModel scope
        viewModelScope.launch {
            // Call repository method and pass the converted parameters
            val response = mainRepository.indi_add_loader_vehicle(
                token,
                vehicle_owner_name_body,
                vehicle_name_body,
                year_of_model_body,
                vehicle_number_body,
                vehicle_type_body,
                capacity_body,
                height_body,
                no_of_tyres_body,
                body_type_body,
                images1, images2, images3, images4, doc1, doc2, doc3, doc4, doc5,doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4,exp_date_5, other_exp_date, doc_name_1, doc_name_2, doc_name_3, doc_name_4, doc_name_5,  other_doc_name
            )

            // Handle the response
            if (response.isSuccessful) {
                progressBarStatus.value = false
                AddloaderResponse.postValue(response.body())
            } else {
                progressBarStatus.value = false
                Log.d("TAG", response.errorBody()?.string() ?: "Unknown error")
            }
        }
    }

    // Utility function to convert ArrayList<String> to ArrayList<RequestBody>
    fun convertStringsToRequestBodies(strings: ArrayList<String>): ArrayList<RequestBody> {
        val mediaType = "text/plain".toMediaTypeOrNull()
        return strings.map { it.toRequestBody(mediaType) }.toCollection(ArrayList())
    }

    fun loader_vehicle_update(
        token: String,
        id: String,
        vehicle_owner_name: String,
        vehicle_name: String,
        year_of_model: String,
        vehicle_number: String,
        capacity: String,
        height: String,
        no_of_tyres: String,
        body_type: String
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response =
                mainRepository.loader_vehicle_update(
                    token,
                    id,
                    vehicle_owner_name,
                    vehicle_name,
                    year_of_model,
                    vehicle_number,
//                    vehicle_category,
                    capacity,
                    height,
                    no_of_tyres,
                    body_type
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

    fun LoaderimageAPI(
        token :String,
        vehicle_id:String,
        Image: MultipartBody.Part?
    ) {
        val vehicle_id: RequestBody = vehicle_id.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.LoaderVehicleimage(
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
  fun loader_vehicle_img_update(
        token :String,
        vehicle_id:String,
        Image: MultipartBody.Part?,
        id: String
    ) {
        val vehicle_id: RequestBody = vehicle_id.toRequestBody("text/plain".toMediaTypeOrNull())
        val id: RequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.loader_vehicle_img_update(
                token,
                vehicle_id,
                Image!!,id
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
  fun passenger_vehicle_img_update(
        token :String,
        vehicle_id:String,
        Image: MultipartBody.Part?,
  id:String
    ) {
        val vehicle_id: RequestBody = vehicle_id.toRequestBody("text/plain".toMediaTypeOrNull())
        val id: RequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())
        progressBarStatus.value = true
        viewModelScope.launch {

            val response = mainRepository.passenger_vehicle_img_update(
                token,
                vehicle_id,
                Image!!,id
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


    fun LoaderdocAPI(
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

            val response = mainRepository.Loadervehicledoc(
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


    fun AddVehicalfinalApi(
        token: String,
        driver_id: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {
            val response = mainRepository.FinalvehicalSubmit(token,driver_id)
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

    fun AddTripApi(
        token: String,
        from_trip: String,
        to_trip: String,
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
        driver_fee: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            val response =
                mainRepository.AddTrip(
                    token,
                    from_trip,
                    to_trip,
                    assign_driver,
                    total_distance,
                    freight_amount,
                    pickup_lat,
                    pickup_long,
                    dropup_lat,
                    dropup_long,
                    vehicle_id,
                    booking_date_from,
                    booking_time,
                    fuel_charge,
                    toll_tax,
                    driver_fee,
                    "1"
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
//    fun AddTripVendor(
//        token: String,
//        tip_task: String,
//        load_caring: String,
//        from_trip: String,
//        to_trip: String,
//        vehicle_type: String,
//        vehicle_numbers: String,
//        no_tyers: String,
//        body_type: String,
//        assign_driver: String,
//        total_distance: String,
//        freight_amount: String,
//        pickup_lat: String,
//        pickup_long: String,
//        dropup_lat: String,
//        dropup_long: String,
//        vehicle_id: String,
//        booking_date_from: String,
//        booking_time: String,
//        fuel_charge: String,
//        toll_tax: String,
//        driver_fee: String,
//
//    ) {
//        progressBarStatus.value = true
//        viewModelScope.launch {
//
//            val response =
//                mainRepository.AddTripVendor(
//                    token,
//                    tip_task,
//                    load_caring,
//                    from_trip,
//                    to_trip,
//                    vehicle_type,
//                    vehicle_numbers,
//                    no_tyers,
//                    body_type,
//                    assign_driver,
//                    total_distance,
//                    freight_amount,
//                    pickup_lat,
//                    pickup_long,
//                    dropup_lat,
//                    dropup_long,
//                    vehicle_id,
//                    booking_date_from,
//                    booking_time,  fuel_charge,
//                    toll_tax,
//                    driver_fee,
//
//                )
//            if (response.isSuccessful) {
//                progressBarStatus.value = false
//                AddTripResponse.postValue(response.body())
//            } else {
//                progressBarStatus.value = false
//                Log.d("TAG", response.body().toString())
//            }
//        }
//
//    }
  fun add_passenger_vendor_trip(
        token: String,
        tip_task: String,
        load_caring: String,
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
        driver_fee: String,
    ) {
        progressBarStatus.value = true
        viewModelScope.launch {

            try {
                val response =
                    mainRepository.add_passenger_vendor_trip(
                        token,
                        tip_task,
                        load_caring,
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
                        booking_time,
                        fuel_charge,
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
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }


}