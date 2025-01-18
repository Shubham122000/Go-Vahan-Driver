package com.govahanpartner.com.network

import com.govahanpartner.com.model.*
import com.govahanpartner.com.ui.common.ReferNEarnResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiService: ApiService) : MainRepository {
    override suspend fun driverLogin(
        email: String,
        password: String,
        device_token: String,
        device_type: String,
        type: String
        ): Response<RegisterResponseModel> =
        apiService.driverLogin(email, password, device_token, device_type,type)
    override suspend fun add_driving_licence(
        id: RequestBody,
        driving_licence: MultipartBody.Part,
    ): Response<RegisterResponseModel> =
        apiService.add_driving_licence(id, driving_licence)

    override suspend fun addloadervehical(
        token: String,
        driver_id: RequestBody,
        vehicle_owner_name: RequestBody,
        vehicle_name: RequestBody,
        year_of_model: RequestBody,
        vehicle_number: RequestBody,
        vehicle_type: RequestBody,
//        vehicle_category: String,
        capacity: RequestBody,
        height: RequestBody,
        color: RequestBody,
        no_of_tyres: RequestBody,
        body_type: RequestBody,
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
        exp_date_1: RequestBody,
        exp_date_2: RequestBody,
        exp_date_3: RequestBody,
        exp_date_4: RequestBody,
        exp_date_5: RequestBody,
        other_exp_date: RequestBody,
//        doc_name_1: RequestBody,
//        doc_name_2: RequestBody,
//        doc_name_3: RequestBody,
//        doc_name_4: RequestBody,
//        doc_name_5: RequestBody,
        other_doc_name: RequestBody,
    ): Response<AddloaderResponse> = apiService.addloadervehical(
        token,
        driver_id,
        vehicle_owner_name,
        vehicle_name,
        year_of_model,
        vehicle_number,
        vehicle_type,
//        vehicle_category,
        capacity,
        height,
        color,
        no_of_tyres,
        body_type,
        images1,
        images2,
        images3,
        images4, doc1, doc2, doc3, doc4, doc5,doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4,exp_date_5, other_exp_date, /*doc_name_1, doc_name_2, doc_name_3, doc_name_4,doc_name_5,*/ other_doc_name
    )
    override suspend fun indi_add_loader_vehicle(
        token: String,
        vehicle_owner_name: RequestBody,
        vehicle_name: RequestBody,
        year_of_model: RequestBody,
        vehicle_number: RequestBody,
        vehicle_type: RequestBody,
        capacity: RequestBody,
        height: RequestBody,
        no_of_tyres: RequestBody,
        body_type: RequestBody,
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
        exp_date_1: RequestBody,
        exp_date_2: RequestBody,
        exp_date_3: RequestBody,
        exp_date_4: RequestBody,
        exp_date_5: RequestBody,
        other_exp_date: RequestBody,
        doc_name_1: RequestBody,
        doc_name_2: RequestBody,
        doc_name_3: RequestBody,
        doc_name_4: RequestBody,
        doc_name_5: RequestBody,
        other_doc_name: RequestBody,
//      yList<MultipartBody.Part>,
//        doc: ArrayList<MultipartBody.Part>,
//        docName: ArrayList<RequestBody>,
//        expDate: ArrayList<RequestBody>
    ): Response<AddloaderResponse> {
        return apiService.indi_add_loader_vehicle(
            token,
            vehicle_owner_name,
            vehicle_name,
            year_of_model,
            vehicle_number,
            vehicle_type,
            //        vehicle_category,
            capacity,
            height,
            no_of_tyres,
            body_type,
            images1,
            images2,
            images3,
            images4, doc1, doc2, doc3, doc4, doc5,doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4,exp_date_5, other_exp_date, doc_name_1, doc_name_2, doc_name_3, doc_name_4,doc_name_5, other_doc_name
        )
    }

  override suspend fun loader_vehicle_update(
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
    ): Response<AddloaderResponse> = apiService.loader_vehicle_update(
        token,
        id,
        vehicle_owner_name,
        vehicle_name,
        year_of_model,
        vehicle_number,
        capacity,
        height,
        no_of_tyres,
        body_type
    )
    override suspend fun checksumApi(
       token: String,
       amount: String,
       mobile: String,
       user_id: String,
    ): Response<ChecksumResponse> = apiService.checksumApi(token,
        amount,mobile,user_id
    )

    override suspend fun payment_status_check(
        token: String,
        transaction_id: String
    ): Response<Razorpay_status_Response> = apiService.payment_status_check(token,
        transaction_id
    )


    override suspend fun paymentcheckapi(
        token: String,
        transaction_id: String,
    ): Response<PaymentSuccessMsgResponse> = apiService.paymentcheckapi(token,
        transaction_id
    )
    override suspend fun withdrawapi(
        token: String,
        amount: String,

    ): Response<BankAccountListResponse> = apiService.withdrawapi(token,
        amount

    )
    override suspend fun vendor_driver_transection(
        token: String,
        amount: String,

    ): Response<BankAccountListResponse> = apiService.vendor_driver_transection(token,
        amount

    )
    override suspend fun addpassengervehical(
        token: String,
        driver_id: RequestBody,
        vehicle_owner_name: RequestBody,
        vehicle_name: RequestBody,
        year_of_model: RequestBody,
        vehicle_number: RequestBody,
        vehicle_type: RequestBody,
        capacity: RequestBody,
        height: RequestBody,
        color: RequestBody,
        no_of_tyres: RequestBody,
        body_type: RequestBody,
        seat: RequestBody,
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
        exp_date_1: RequestBody,
        exp_date_2: RequestBody,
        exp_date_3: RequestBody,
        exp_date_4: RequestBody,
        exp_date_5: RequestBody,
        other_exp_date: RequestBody,
        doc_name_1: RequestBody,
        doc_name_2: RequestBody,
        doc_name_3: RequestBody,
        doc_name_4: RequestBody,
        doc_name_5: RequestBody,
        other_doc_name: RequestBody,
    ): Response<AddloaderResponse> = apiService.addpassengervehical(
        token,
        driver_id,
        vehicle_owner_name,
        vehicle_name,
        year_of_model,
        vehicle_number,
        vehicle_type,
        color,
        no_of_tyres,
        seat,images1, images2, images3, images4, doc1, doc2, doc3, doc4, doc5, doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4, exp_date_5, other_exp_date, doc_name_1, doc_name_2, doc_name_3, doc_name_4, doc_name_5, other_doc_name
    )
    override suspend fun indi_add_passenger_vehicle(
        token: String,
        driver_id: RequestBody,
        vehicle_owner_name: RequestBody,
        vehicle_name: RequestBody,
        year_of_model: RequestBody,
        vehicle_number: RequestBody,
        vehicle_type: RequestBody,
        capacity: RequestBody,
        height: RequestBody,
        color: RequestBody,
        no_of_tyres: RequestBody,
        body_type: RequestBody,
        seat: RequestBody,
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
        exp_date_1: RequestBody,
        exp_date_2: RequestBody,
        exp_date_3: RequestBody,
        exp_date_4: RequestBody,
        exp_date_5: RequestBody,
        other_exp_date: RequestBody,
        doc_name_1: RequestBody,
        doc_name_2: RequestBody,
        doc_name_3: RequestBody,
        doc_name_4: RequestBody,
        doc_name_5: RequestBody,
        other_doc_name: RequestBody,
    ): Response<AddloaderResponse> = apiService.indi_add_passenger_vehicle(
        token,
        driver_id,
        vehicle_owner_name,
        vehicle_name,
        year_of_model,
        vehicle_number,
        vehicle_type,
        color,
        no_of_tyres,
        seat,images1, images2, images3, images4, doc1, doc2, doc3, doc4, doc5, doc6, exp_date_1, exp_date_2, exp_date_3, exp_date_4, exp_date_5, other_exp_date, doc_name_1, doc_name_2, doc_name_3, doc_name_4, doc_name_5, other_doc_name

    )
    override suspend fun passenger_vehicle_update(
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
        seat: String,
    ): Response<AddloaderResponse> = apiService.passenger_vehicle_update(
        token,
        driver_id,
        vehicle_owner_name,
        vehicle_name,
        year_of_model,
        vehicle_number,
        vehicle_type,
//        vehicle_category,
        color,
        no_of_tyres,
        seat
    )

    override suspend fun driverSignupApi(
        name: String,
        countryCode: String,
        mobile: String,
        email: String,
        gst_number: String,
        password: String,
        referal_code: String,
        role: String,
//        type_vehicle: String,
        device_type: String,
        device_name: String,
        device_token: String,
        device_id: String,

        ): Response<RegisterResponseModel> =
        apiService.driverRegister(
            name,
            countryCode,
            mobile,
            email,
            gst_number,
            password,referal_code,
            role,
//            type_vehicle,
            device_type,
            device_name,
            device_token,
            device_id
        )

    override suspend fun BusinesscardApi(
        token: String,
        Company: RequestBody,
        name: RequestBody,
        mobile: RequestBody,
        email: RequestBody,
        address: RequestBody,
        role: RequestBody,
        image: MultipartBody.Part
    ): Response<CreateBusinesscard> =apiService.BusinessCardDriver(
        token,
        Company,name,mobile,email,address,role
        ,image!!
    )
    override suspend fun update_business_cardApi(
        token: String,
        Company: RequestBody,
        name: RequestBody,
        mobile: RequestBody,
        email: RequestBody,
        address: RequestBody,
        role: RequestBody,
        image: MultipartBody.Part
    ): Response<CreateBusinesscard> =apiService.update_business_cardApi(
        token,
        Company,name,mobile,email,address,role
        ,image!!
    )

    override suspend fun DriverUpdateProfile(header: String,id: RequestBody,name:RequestBody,mobile_number:RequestBody,experience:RequestBody
                                             ,licence_number:RequestBody,device_token:RequestBody,device_type:RequestBody,device_id:RequestBody,password : RequestBody,profile_image:MultipartBody.Part): Response<DriverUpdateProfileResponse> {
        return apiService.Updatedriver(header,id,name,mobile_number,experience,licence_number,device_token,device_type,device_id, password ,profile_image)
    }


    override suspend fun LoaderVehicleimage(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part
    ): Response<Loaderimage>  =apiService.loadervehicleimg(token,
        vehicle_id,Image!!
    )

    override suspend fun passengerVehicleimg(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part
    ): Response<Loaderimage>  =apiService.passengervehicleimg(token,
        vehicle_id,Image!!
    )
    override suspend fun loader_vehicle_img_update(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part,
        id: RequestBody
    ): Response<Loaderimage>  =apiService.loader_vehicle_img_update(token,
        vehicle_id,Image!!,id
    )
    override suspend fun passenger_vehicle_img_update(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part,
        id: RequestBody
    ): Response<Loaderimage>  =apiService.passenger_vehicle_img_update(token,
        vehicle_id,Image!!,id
    )
    override suspend fun termsAndConditions(token: String): Response<PrivacyPolicyModel> = apiService.termsAndConditions(token)

    override suspend fun Passengervehicledoc(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,
        exp_date: RequestBody,
        Doc: MultipartBody.Part
    ): Response<Loaderimage>  =apiService.passengervehicledoc(token,
        vehicle_id,doc_name,exp_date,Doc!!
    )
    override suspend fun loader_vehicle_doc_update(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,
        exp_date: RequestBody,
        Doc: MultipartBody.Part
    ): Response<Loaderimage>  =apiService.loader_vehicle_doc_update(token,
        vehicle_id,doc_name,exp_date,Doc!!
    )
   override suspend fun passenger_vehicle_doc_update(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,
        exp_date: RequestBody,
        Doc: MultipartBody.Part
    ): Response<Loaderimage>  =apiService.passenger_vehicle_doc_update(token,
        vehicle_id,doc_name,exp_date,Doc!!
    )

    override suspend fun Loadervehicledoc(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,exp_date: RequestBody,
        Doc: MultipartBody.Part
    ): Response<Loaderimage> = apiService.loadervehicledoc(token,
        vehicle_id,doc_name,exp_date,Doc!!
    )

    override suspend fun AdddriverApi(
        token: String,
        drivername: RequestBody,
        driverexperience: RequestBody,
        Licence: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        email: RequestBody,
        Password: RequestBody,
        Image: MultipartBody.Part,
        pdfFile:MultipartBody.Part,
        vendorid:RequestBody,
//        serviceid:RequestBody,

        ): Response<AddDriverResponse>  = apiService.adddriver(token,drivername,driverexperience,Licence,countryCode,mobile,email,
    Password,Image,pdfFile,vendorid/*,serviceid*/)

    override suspend fun driverSendOtpApi(mobile: String): Response<RegisterResponseModel> =
        apiService.driverSendOTP(mobile)

    override suspend fun FinalvehicalSubmit(
        token: String,
        driver_id: String
    ): Response<AddVehicalfinalResponse> =
        apiService.FinalvehicalSubmit(token,driver_id)

    override suspend fun loderfarecalculator(
        token: String,
        pickup_lat:String,
        pickup_long:String,
        dropup_lat:String,
        dropup_long:String,
        mileage:String,
        vehicle_type:String,oil_price:String
    ): Response<FareCaluculationResponse> =
    apiService.loderfarecalculator(token,pickup_lat,pickup_long,dropup_lat,dropup_long,mileage,vehicle_type,oil_price)

    override suspend fun passenger_fare_calculatorApi(
        token: String,
        pickup_lat:String,
        pickup_long:String,
        dropup_lat:String,
        dropup_long:String,
        mileage:String,
        vehicle_type:String,oil_price:String
    ): Response<FareCaluculationResponse> =
        apiService.passenger_fare_calculatorApi(token,pickup_lat,pickup_long,dropup_lat,dropup_long,mileage,vehicle_type,oil_price)

    override suspend fun loader_vehicle_payment(
        token: String,
        id:String,subscribe:String,payment_mode:String,
        transaction_id:String,payment_crdated:String,status:String

    ): Response<AddDriverResponse> =
        apiService.loader_vehicle_payment(token,
            id,subscribe,payment_mode,
            transaction_id,payment_crdated,status)

    override suspend fun add_passenger_vehicle_payment(
        token: String,
        id:String,subscribe:String,fare:String,payment_mode:String,
        transaction_id:String,validity:String,payment_crdated:String,status:String
    ): Response<AddDriverResponse> =
        apiService.add_passenger_vehicle_payment(token,
        id,subscribe,fare,payment_mode,
        transaction_id,validity,payment_crdated,status)

    override suspend fun FinalpassengervehicalSubmit(
        token: String,
        driver_id: String
    ): Response<AddVehicalfinalResponse> =
        apiService.FinalpassengerSubmit(token,driver_id)

    override suspend fun driverChangePwd(
        token: String,
        password: String
    ): Response<RegisterResponseModel> =
        apiService.driverChangePwd(token, password)

    override suspend fun EditProfile(
        token: String,
        name: RequestBody,
        email: RequestBody,
        mobile: RequestBody,
        address: RequestBody,
        device_token:RequestBody,
        device_type:RequestBody,
        device_id:RequestBody,
        Image: MultipartBody.Part?
    ): Response<ProfileEditResponse> =apiService.EditProfile(
        token,
        name,email,mobile,address
        ,device_token,device_type,device_id,Image
    )

    override suspend fun GetProfile(
        token: String,
    ): Response<ProfileResponse> =
        apiService.GetDriverProfile(token)

    override suspend fun my_driver_wallet_list_donload(
        token: String,
    ): Response<ProfileResponse> =
        apiService.my_driver_wallet_list_donload(token)
    override suspend fun referralsApi(
        token: String,
    ): Response<ReferNEarnResponse> =
        apiService.referralsApi(token)

    override suspend fun vehicleSeat(
        token: String,
    ): Response<SeatResponse> =
        apiService.vehicleSeat(token)



    override suspend fun GetVehicle(
        token: String,
    ): Response<VehicleList> =
        apiService.GetVehicleList(token)
    override suspend fun get_passenger_vehicleno(
        token: String,
    ): Response<VehicleNumberPassengerLIst> =
        apiService.get_passenger_vehicleno(token)

  override suspend fun get_loder_vehicleno(
        token: String,
    ): Response<VehicleNumberListMOdelCLass> =
        apiService.get_loder_vehicleno(token)

    override suspend fun driverList(
        token: String,
//        vendor_id: String
    ): Response<DriverListResponse> = apiService.driverList(token)

    override suspend fun get_passenger_vehicleno_details(
        token: String,
       id: String
    ): Response<VehicledataResponse> = apiService.get_passenger_vehicleno_details(token,id)
   override suspend fun get_loader_vehicleno_details(
        token: String,
       id: String
    ): Response<VehicledataResponse> = apiService.get_loader_vehicleno_details(token,id)

    override suspend fun subscriptionplan(
        token: String,
        forPassenger:Int
    ): Response<SubscriptionPlan> = apiService.subscriptonplan(token,forPassenger)
//    override suspend fun subscription_plan_passengers(
//        token: String
//    ): Response<SubscriptionPlan> = apiService.subscription_plan_passengers(token)

    override suspend fun Loadertruckrepositorylist(
        token: String
    ): Response<LoaderTruckRepositoryListResponse> = apiService.LoaderTruckRepositoryList(token)

    override suspend fun Loadertruckrepositorypassengerlist(
        token: String
    ): Response<LoaderTruckRepositoryListResponse> = apiService.LoaderTruckRepositoryLPassengerist(token)

    override suspend fun getDashboardBannertApi(
        token: String,vehicle_type: String
    ): Response<BannerResponse> = apiService.getDashboardBannertApi(token,vehicle_type)

    override suspend fun DeiselList(
        id: String
    ): Response<DeiselPrice> = apiService.deiselList(id)

    override suspend fun StateList(): Response<StateListResponse> = apiService.StateList()

    override suspend fun driverVerifyOtp(
        otp: String,
        mobile: String,
    ): Response<RegisterResponseModel> = apiService.driverVerifyOtp(otp, mobile)


    override suspend fun AddTrip(
        token: String,
        tip_task: String,
        load_caring: String,
        from_trip: String,
        to_trip: String,
//        vehicle_type: String,
//        vehicle_numbers: String,
//        no_tyers: String,
//        body_type: String,
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
    ): Response<AddTripDriverMOdelClass> = apiService.AddTrip(token,tip_task,load_caring,from_trip,to_trip,
    /*vehicle_type,vehicle_numbers,no_tyers,body_type,*/assign_driver,
        total_distance,freight_amount,pickup_lat,
        pickup_long,dropup_lat,dropup_long,vehicle_id,booking_date_from,
        booking_time,fuel_charge,toll_tax,driver_fee)
   override suspend fun AddTripVendor(
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

    ): Response<CreateBusinesscard> = apiService.add_loader_vendor_trip(token,tip_task,load_caring,from_trip,to_trip,
    vehicle_type,vehicle_numbers,no_tyers,body_type,assign_driver,total_distance,freight_amount,pickup_lat,pickup_long,dropup_lat,dropup_long,vehicle_id,booking_date_from,booking_time,fuel_charge,toll_tax,driver_fee)

    override suspend fun AddTripPassenger(
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
        driver_fee: String,
    ): Response<CreateBusinesscard> = apiService.AddTripPassenger(token,tip_task,from_trip,to_trip,
        vehicle_type,vehicle_numbers,no_tyers,body_type,assign_driver,total_distance,freight_amount,pickup_lat,pickup_long,dropup_lat,dropup_long,vehicle_id,booking_date_from,booking_time,fuel_charge,toll_tax,driver_fee)
  override suspend fun add_passenger_vendor_trip(
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
    ): Response<AddTripDriverMOdelClass> = apiService.add_passenger_vendor_trip(token,tip_task,load_caring,from_trip,to_trip,
        vehicle_type,vehicle_numbers,no_tyers,body_type,assign_driver,total_distance,freight_amount,pickup_lat,pickup_long,dropup_lat,dropup_long,vehicle_id,booking_date_from,booking_time,       fuel_charge,
      toll_tax,
      driver_fee)

    override suspend fun ForgotPassword(
        mobile: String,
        password: String,
    ): Response<RegisterResponseModel> = apiService.changePass(mobile,password)

    override suspend fun termsAPi(header: String): Response<RegisterResponseModel> {
        return apiService.termsCondition(header)
    }

    override suspend fun aboutUs(header: String): Response<RegisterResponseModel> {
        return apiService.aboutUs(header)
    }

    override suspend fun contactus(header: String): Response<RegisterResponseModel> {
        return apiService.contactus(header)
    }

    override suspend fun privacypolicy(header: String): Response<RegisterResponseModel> {
        return apiService.privacyPolicy(header)
    }
    override suspend fun calcelation_refund_policy(header: String): Response<PrivacyPolicyModel> {
        return apiService.calcelation_refund_policy(header)
    }
    override suspend fun get_user_check_statusApi(header: String): Response<PrivacyPolicyModel> {
        return apiService.get_user_check_statusApi(header)
    }
    override suspend fun checkTripPaymentsApi(header: String,amount: String,booking_id: String): Response<CheckTripPaymentResponse> {
        return apiService.checkTripPaymentsApi(header,amount,booking_id)
    }
    override suspend fun passenger_Payments_checkApi(header: String,amount: String,booking_id: String): Response<CheckTripPaymentResponse> {
        return apiService.passenger_Payments_checkApi(header,amount,booking_id)
    }


    override suspend fun truckTypeApi(): Response<TruckTypeResponse> = apiService.truckType()
    override suspend fun TypeofTruckApi(token: String,type:String): Response<TypeofTruckResponse> = apiService.TypeofTruck(token,type)
    override suspend fun BodyTypeApi(token: String): Response<BodyType> = apiService.BodyTpe(token)
    override suspend fun HightApi(token: String): Response<hightResponse> =apiService.Height(token)
    override suspend fun CapacityApi(token: String): Response<LoaodCarryingResponse> =apiService.Capacity(token)
//    override suspend fun ColorApi(token: String): Response<ColorResponse> =apiService.Color(token)
    override suspend fun YearApi(token: String,type: String): Response<YearResponse> =apiService.Year(token,type)
    override suspend fun vehicalwheels(token: String,type:String): Response<vehicalwheelsResponse> =apiService.vehicalwheels(token,type)
    override suspend fun Businessdata(header: String): Response<BusinessListResponse>  = apiService.Businesscarddetails(header)
    override suspend fun getProfile(header: String): Response<ListResponseData> {
       return apiService.getProfile(header)
    }

    override suspend fun OngoinTripHistory(header: String): Response<TripHistoryResponse> {
        return apiService.OngoingTripHistory(header)
    }
    override suspend fun cancel_booking_history_loader(header: String): Response<TripHistoryResponse> {
        return apiService.cancel_booking_history_loader(header)
    }
    override suspend fun cnacel_booking_passengers(header: String): Response<TripHistoryResponse> {
        return apiService.cnacel_booking_passengers(header)
    }

    override suspend fun OngoinTripHistoryPassenger(header: String): Response<TripHistoryResponse> {
        return apiService.OngoingTripHistoryPassenger(header)
    }

    override suspend fun LoaderCancelReason(header: String): Response<Loader_cancel_ReasonList_Response> {
        return apiService.loadercancellationReasonlist(header)
    }

    override suspend fun UpcomingsTripHistory(header: String): Response<TripHistoryResponse> {
        return apiService.UpcomingsTripHistory(header)
    }
    override suspend fun vendor_upcooming_booking_loder(header: String): Response<TripHistoryResponse> {
        return apiService.vendor_upcooming_booking_loder(header)
    }

    override suspend fun UpcomingsPassengerTripHistory(header: String): Response<TripHistoryResponse> {
        return apiService.UpcomingsPassengerTripHistory(header)
    }
   override suspend fun vendor_upcooming_booking_passengers(header: String): Response<TripHistoryResponse> {
        return apiService.vendor_upcooming_booking_passengers(header)
    }
//    override suspend fun loader_trip_list_details(header: String,loader_id:String): Response<TripListDetailsModelClass> {
//        return apiService.loader_trip_list_details(header,loader_id)
//    }
    override suspend fun passenger_trip_list_details(header: String,loader_id:String): Response<TripListDetailsModelClass> {
        return apiService.passenger_trip_list_details(header,loader_id)
    }

    override suspend fun CompletedTripHistory(header: String): Response<TripHistoryResponse> {
        return apiService.CompletedTripHistory(header)
    }

    override suspend fun CompletedTripHistoryPassenger(header: String): Response<TripHistoryResponse> {
        return apiService.CompletedTripHistoryPassenger(header)
    }

    override suspend fun LoaderDriverTripCancel(header: String,booking_id: String,reason_id:String,reason:String): Response<LoaderDriverTripCancelResponse> {
        return apiService.LoaderdrivertripHistory(header,booking_id,reason_id,reason)
    }
    override suspend fun passenger_driver_trip_cancelApi(header: String,booking_id: String,reason_id:String,reason:String): Response<LoaderDriverTripCancelResponse> {
        return apiService.passenger_driver_trip_cancelApi(header,booking_id,reason_id,reason)
    }
    override suspend fun DriverProfile(header: String,id:String): Response<DriverProfile> {
        return apiService.driverProfile(header,id)
    }
    override suspend fun getVehicleDetails(header: String,vehicle_id:String): Response<TaxiRepositoryViewDetailsResponse> {
        return apiService.getVehicleDetails(header,vehicle_id)
    }
//    override suspend fun loader_truck_repository_image_list_details(header: String,vehicle_id:String): Response<TruckImagesModelCLass> {
//        return apiService.loader_truck_repository_image_list_details(header,vehicle_id)
//    }
    override suspend fun passengers_truck_repository_image_list(header: String,vehicle_id:String): Response<TruckImagesModelCLass> {
        return apiService.passengers_truck_repository_image_list(header,vehicle_id)
    }
//    override suspend fun loader_truck_repository_doc_list_details(header: String,vehicle_id:String): Response<TruckDocumentsDetailsResponse> {
//        return apiService.loader_truck_repository_doc_list_details(header,vehicle_id)
//    }
    override suspend fun passengers_truck_repository_list_details(header: String,vehicle_id:String): Response<TaxiRepositoryViewDetailsResponse> {
        return apiService.passengers_truck_repository_list_details(header,vehicle_id)
    }
    override suspend fun passengers_truck_repository_doc_list(header: String,vehicle_id:String): Response<TruckDocumentsDetailsResponse> {
        return apiService.passengers_truck_repository_doc_list(header,vehicle_id)
    }
    override suspend fun loader_trip_delete(header: String,id:String): Response<DriverProfile> {
        return apiService.loader_trip_delete(header,id)
    }
    override suspend fun loader_truck_repository_list_delete(header: String,id:String): Response<DriverProfile> {
        return apiService.loader_truck_repository_list_delete(header,id)
    }
    override suspend fun passengers_truck_repository_list_delete(header: String,id:String): Response<DriverProfile> {
        return apiService.passengers_truck_repository_list_delete(header,id)
    }
    override suspend fun vendor_driver_delete(header: String,id:String): Response<DriverProfile> {
        return apiService.vendor_driver_delete(header,id)
    }
    override suspend fun my_wallet_payment(header: String,type:String,transaction_id:String,amount:String): Response<DriverProfile> {
        return apiService.my_wallet_payment(header,type,transaction_id,amount)
    }
    override suspend fun add_bank_account(header: String,account_no:RequestBody,name:RequestBody,ifsc:RequestBody,bank_name:RequestBody,account_branch:RequestBody,upi_id:RequestBody,image: MultipartBody.Part): Response<DriverProfile> {
        return apiService.add_bank_account(header,account_no,name,ifsc,bank_name,account_branch,upi_id,image)
    }
    override suspend fun loader_builty_img(header: String,booking_id:RequestBody,type:RequestBody,pod:MultipartBody.Part?,signature:MultipartBody.Part?,builty:MultipartBody.Part?): Response<DriverProfile> {
        return apiService.loader_builty_img(header,booking_id,type,pod,signature,builty)
    }
    override suspend fun RideCompleted(header: String,id:String): Response<RideCompletedResponse> {
        return apiService.rideCompleted(header,id)
    }
   override suspend fun passengers_ride_completed(header: String,id:String): Response<RideCompletedResponse> {
        return apiService.passengers_ride_completed(header,id)
    }

    override suspend fun AcceptRide(header: String,booking_id: String,start_code:String): Response<Addmoneywallet> {
        return apiService.AcceptRide(header,booking_id,start_code)
    }

    override suspend fun WalletList(header: String,date : String,transaction_type : String): Response<VendorWalletActivity> {
        return apiService.Mywalletlist(header,date,transaction_type)
    }
    override suspend fun vendor_wallet_list(header: String,date : String,transaction_type : String): Response<VendorWalletActivity> {
        return apiService.vendor_wallet_list(header,date,transaction_type)
    }
    override suspend fun individual_payment_list(header: String,date : String,transaction_type : String): Response<VendorWalletActivity> {
        return apiService.individual_payment_list(header,date,transaction_type)
    }

    override suspend fun TransactionReport(header: String): Response<TransactionReportResponse> {
        return apiService.Transactionreport(header)
    }
    override suspend fun VisitingCard(header: String): Response<VisitingCardUrlResponse> {
        return apiService.Visitingcardurl(header)
    }

    override suspend fun ContactUs(header: String): Response<ContactUSRsponse> {
        return apiService.ContactUS(header)
    }

    override suspend fun Aboutus(header: String): Response<AboutUs> {
        return apiService.AboutUS(header)
    }
    override suspend fun PrivacyPolicy(header: String): Response<PrivacyPolicy> {
        return apiService.PrivacyPolicy(header)
    }
    override suspend fun Notification(header: String): Response<NotificationResponse> {
        return apiService.Notification(header)
    }
    override suspend fun Rating(header: String): Response<RatingResponse> {
        return apiService.Ratings(header)
    }

    override suspend fun VisitingPdf(header: String): Response<DownloadPdfResponse> {
        return apiService.Visitingcardpdf(header)
    }

    override suspend fun InvoiceDownload(header: String,invoiceno:String): Response<DownloadPdfResponse> {
        return apiService.InvoiceDownload(header,invoiceno)
    }

    override suspend fun Addmoneywallet(header: String,amount:String): Response<Addmoneywallet> {
        return apiService.Addmoneywallet(header,amount)
    }
    override suspend fun purchase_plan_from_walletApi(header: String,amount:String,truck_id:String,transaction_type:String): Response<Addmoneywallet> {
        return apiService.purchase_plan_from_walletApi(header,amount,transaction_type)
    }
    override suspend fun buySubscriptionWalletApi(header: String,amount:String,transaction_type:String): Response<Addmoneywallet> {
        return apiService.buySubscriptionWalletApi(header,amount,transaction_type)
    }
    override suspend fun buySubscriptionOnlineApi(header: String,amount:String,transaction_type:String,transection:String): Response<Addmoneywallet> {
        return apiService.buySubscriptionOnlineApi(header,amount,transaction_type,transection)
    }
    override suspend fun purchase_plan_from_walletApi_to_truck(header: String,amount:String,truck_id:String,transaction_type:String,validity:String): Response<Addmoneywallet> {
        return apiService.purchase_plan_from_walletApi_to_truck(header,amount, truck_id ,transaction_type,validity)
    }
    override suspend fun purchase_plan_from_walletApi_to_passenger(header: String,amount:String,truck_id:String,transaction_type:String,validity:String): Response<Addmoneywallet> {
        return apiService.purchase_plan_from_walletApi_to_passenger(header,amount, truck_id ,transaction_type,validity)
    }

    override suspend fun TripList(header: String): Response<TripListResponse> {
        return apiService.LoaderTripList(header)
    }
    override suspend fun vendor_driver_loadertrip_list(header: String,driver_id:String,type:String): Response<TripListResponse> {
        return apiService.vendor_driver_loadertrip_list(header,driver_id,type)
    }
    override suspend fun driver_loadertrip_list(header: String,type:String): Response<TripListResponse> {
        return apiService.driver_loadertrip_list(header,type)
    }
    override suspend fun PassengerTripList(header: String): Response<TripListResponse> {
        return apiService.PassengerTripList(header)
    }
    override suspend fun self_driver_trip(header: String): Response<AddTripDriverMOdelClass> {
        return apiService.self_driver_trip(header)
    }
    override suspend fun self_driver_passenger_trip(header: String): Response<AddTripDriverMOdelClass> {
        return apiService.self_driver_passenger_trip(header)
    }
    override suspend fun InvoiceList(header: String,type:String): Response<InvoiceListResponse> {
        return apiService.InvoiceList(header,type)
    }
    override suspend fun loader_driver_invoice_url(header: String,booking_id:String,type:String): Response<InvoiceurldownloadResponse> {
        return apiService.loader_driver_invoice_url(header,booking_id,type)
    }
    override suspend fun getBankAccountsApi(header: String): Response<GetBankAcountResponse> {
        return apiService.getBankAccountsApi(header)
    }
    override suspend fun bank_account_list(header: String): Response<BankAccountListResponse> {
        return apiService.bank_account_list(header)
    }
    override suspend fun bank_account_list_id(header: String,id: String): Response<BankAccountListResponse> {
        return apiService.bank_account_list_id(header,id)
    }

    override suspend fun Filteredwallet(header: String,date:String,transaction_type:String): Response<WalletFilterLIstREsponse> {
        return apiService.walletfiltered(header,date,transaction_type)
    }
    override suspend fun LoaderinvoiceSummery(header: String,invoice_numbers:String): Response<InvoiceSummeryResponse> {
        return apiService.LoaderinvoiceSummery(header,invoice_numbers)
    }

//    override suspend fun Loaderinvoice(header: String,invoice_numbers:String): Response<InvoiceResponse> {
//        return apiService.Loaderinvoice(header,invoice_numbers)
//    }

    override suspend fun Completedrivertripdetails(header: String,booking_id:String): Response<CompleteDriverDetailsResponse> {
        return apiService.Completedrivertripdetails(header,booking_id)
    }
    override suspend fun ongoing_booking_history_loader_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse> {
        return apiService.ongoing_booking_history_loader_details(header,booking_id)
    }
    override suspend fun ongoing_booking_history_passenger_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse> {
        return apiService.ongoing_booking_history_passenger_details(header,booking_id)
    }
    override suspend fun cancel_booking_history_passenger_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse> {
        return apiService.cancel_booking_history_passenger_details(header,booking_id)
    }
    override suspend fun Completedriverpassengertripdetails(header: String,booking_id:String): Response<CompleteDriverDetailsResponse> {
        return apiService.CompletedriverPassengertripdetails(header,booking_id)
    }
    override suspend fun cancel_booking_history_loader_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse> {
        return apiService.cancel_booking_history_loader_details(header,booking_id)
    }

    override suspend fun Senddriverloaderinvoice(header: String,booking_id:String): Response<SendDriverLoaderInvoiceResponse> {
        return apiService.SendDriverLoaderInvoice(header,booking_id)
    }



    /*
      override suspend fun viewProfile(token: String): Response<ViewProfileResponseModel> = apiService.viewProfileApi(token)

      override suspend fun updateProfileApi(
          token: String,
          name: RequestBody,
          bio: RequestBody,
          email: RequestBody,
          gender: RequestBody,
          image: MultipartBody.Part
      ): Response<LoginResponseModel> = apiService.updateProfileApi(token,name,bio,email,gender,image)

      override suspend fun dashboardApi(token: String): Response<DashboardResponseModel> = apiService.dashboardApi(token)

      override suspend fun postLikeApi(
          token: String,
          post_id: String,
          type:String
      ): Response<CommonDataResponse> = apiService.postLikeApi( token,post_id,type)

      override suspend fun voteApi(
          token: String,
          post_id: String,
          vote:String
      ): Response<CommonDataResponse> = apiService.voteApi( token,post_id,vote)*/

}