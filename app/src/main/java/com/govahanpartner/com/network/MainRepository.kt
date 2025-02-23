package com.govahanpartner.com.network

import com.govahanpartner.com.model.*
import com.govahanpartner.com.ui.common.ReferNEarnResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


interface MainRepository {

    suspend fun driverLogin(
        email: String,
        password: String,
        device_token: String,
        device_type: String,
        type: String,
    ): Response<RegisterResponseModel>

    suspend fun add_driving_licence(
        id: RequestBody,
        driving_licence: MultipartBody.Part
    ): Response<RegisterResponseModel>

    suspend fun vehicleSeat(
        token: String,
    ): Response<SeatResponse>

    suspend fun payment_status_check(
        token: String,
        transaction_id: String
    ): Response<Razorpay_status_Response>

     suspend fun checksumApi(
         token: String,
        amount: String,
       mobile: String,
       user_id: String,
    ): Response<ChecksumResponse>

     suspend fun paymentcheckapi(
         token: String,
        transaction_id: String,
        ): Response<PaymentSuccessMsgResponse>

  suspend fun withdrawapi(
         token: String,
        amount: String,
        ): Response<BankAccountListResponse>

  suspend fun vendor_driver_transection(
         token: String,
        amount: String,
        ): Response<BankAccountListResponse>

     suspend fun addloadervehical(
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
        isFromPassenger: RequestBody,
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
//        doc_name_1: RequestBody,
//        doc_name_2: RequestBody,
//        doc_name_3: RequestBody,
//        doc_name_4: RequestBody,
//        doc_name_5: RequestBody,
        other_doc_name: RequestBody,
    ): Response<AddloaderResponse>

    suspend fun indi_add_loader_vehicle(
        token: String,
        vehicle_owner_name: RequestBody,
        vehicle_name: RequestBody,
        year_of_model: RequestBody,
        vehicle_number: RequestBody,
        vehicle_type: RequestBody,
//        vehicle_category: String,
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
    ): Response<AddloaderResponse>

    suspend fun loader_vehicle_update(
        token: String,
        id: String,
        vehicle_owner_name: String,
        vehicle_name: String,
        year_of_model: String,
        vehicle_number: String,
        capacity: String,
        height: String,
        no_of_tyres: String,
        body_type: String,
    ): Response<AddloaderResponse>

    suspend fun addpassengervehical(
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
    ): Response<AddloaderResponse>

    suspend fun indi_add_passenger_vehicle(
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
    ): Response<AddloaderResponse>
    suspend fun passenger_vehicle_update(
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
        seat: String,
    ): Response<AddloaderResponse>
    suspend fun driverSendOtpApi(mobile : String) : Response<RegisterResponseModel>
    suspend fun DriverProfile(header: String,id:String): Response<DriverProfile>
    suspend fun getVehicleDetails(header: String,vehicle_id:String): Response<TaxiRepositoryViewDetailsResponse>
//    suspend fun loader_truck_repository_image_list_details(header: String,vehicle_id:String): Response<TruckImagesModelCLass>
    suspend fun passengers_truck_repository_image_list(header: String,vehicle_id:String): Response<TruckImagesModelCLass>
//    suspend fun loader_truck_repository_doc_list_details(header: String,vehicle_id:String): Response<TruckDocumentsDetailsResponse>
    suspend fun passengers_truck_repository_list_details(header: String,vehicle_id:String): Response<TaxiRepositoryViewDetailsResponse>
    suspend fun passengers_truck_repository_doc_list(header: String,vehicle_id:String): Response<TruckDocumentsDetailsResponse>
    suspend fun loader_truck_repository_list_delete(header: String,id:String): Response<DriverProfile>
    suspend fun passengers_truck_repository_list_delete(header: String,id:String): Response<DriverProfile>
    suspend fun EditProfile(
        token: String,
        name: RequestBody,
        email: RequestBody,
        mobile: RequestBody,
        address: RequestBody,
        device_token:RequestBody, device_type:RequestBody, device_id:RequestBody, Image: MultipartBody.Part?
    ): Response<ProfileEditResponse>
    suspend fun FinalvehicalSubmit(
        token: String,
        driver_id: String
    ): Response<AddVehicalfinalResponse>
    suspend fun RideCompleted(header: String,id:String): Response<RideCompletedResponse>
    suspend fun DriverUpdateProfile(header: String,id: RequestBody,name:RequestBody,mobile_number:RequestBody,experience:RequestBody
                                    ,licence_number:RequestBody,device_token:RequestBody,device_type:RequestBody,device_id:RequestBody,password:RequestBody,profile_image:MultipartBody.Part): Response<DriverUpdateProfileResponse>
//    suspend fun AcceptRide(header: String,booking_id: String,start_code:String): Response<Addmoneywallet>

    suspend fun updateBookingStatus(header: String,bookingId: String,startCode:String,status:String,cancelReason:String): Response<Addmoneywallet>
    suspend fun OngoinTripHistory(header: String): Response<TripHistoryResponse>
    suspend fun LoaderCancelReason(header: String): Response<Loader_cancel_ReasonList_Response>
    suspend fun CompletedTripHistory(header: String): Response<TripHistoryResponse>
    suspend fun UpcomingsTripHistory(header: String, hitFromDriver :String, forPassenger :String, bookingStatus :String): Response<TripHistoryResponse>
    suspend fun vendor_upcooming_booking_loder(header: String): Response<TripHistoryResponse>
    suspend fun vendor_driver_loadertrip_list(header: String,driver_id:String,type: String): Response<TripListResponse>
    suspend fun vendor_upcooming_booking_passengers(header: String): Response<TripHistoryResponse>
     suspend fun walletList(header: String,date : String,transactionType : String): Response<WalletFilterListResponse>
//    suspend fun vendor_wallet_list(header: String,date : String,transaction_type : String): Response<VendorWalletActivity>
//    suspend fun individual_payment_list(header: String,date : String,transaction_type : String): Response<VendorWalletActivity>
    suspend fun loader_trip_delete(header: String,id:String): Response<DriverProfile>
    suspend fun vendor_driver_delete(header: String,id:String): Response<DriverProfile>
    suspend fun addMoney(header: String,type:String,transaction_id:String,amount: String): Response<DriverProfile>
    suspend fun add_bank_account(header: String,account_no:RequestBody,name:RequestBody,ifsc:RequestBody,bank_name:RequestBody,account_branch:RequestBody,upi_id:RequestBody,image: MultipartBody.Part): Response<DriverProfile>
    suspend fun createBookingDocument(header: String,booking_id:RequestBody,pod:MultipartBody.Part?,signature:MultipartBody.Part?,builty:MultipartBody.Part?): Response<DriverProfile>
    suspend fun getBankAccountsApi(header: String): Response<GetBankAcountResponse>
//    suspend fun loader_driver_invoice_url(header: String,booking_id:String,type:String): Response<InvoiceurldownloadResponse>
    suspend fun my_driver_wallet_list_donload(header: String): Response<ProfileResponse>
    suspend fun LoaderDriverTripCancel(header: String,booking_id: String,reason_id:String,reason:String): Response<LoaderDriverTripCancelResponse>
    suspend fun passenger_driver_trip_cancelApi(header: String,booking_id: String,reason_id:String,reason:String): Response<LoaderDriverTripCancelResponse>
    suspend fun Addmoneywallet(header: String,amount:String): Response<Addmoneywallet>
    suspend fun purchase_plan_from_walletApi(header: String,amount:String,truck_id:String,transaction_type:String): Response<Addmoneywallet>
    suspend fun buySubscriptionWalletApi(header: String,amount:String,transaction_type:String): Response<Addmoneywallet>
    suspend fun buySubscriptionOnlineApi(header: String,amount:String,transaction_type:String,transection:String): Response<Addmoneywallet>
    suspend fun referralsApi(
        token: String,
    ): Response<ReferNEarnResponse>
    suspend fun purchase_plan_from_walletApi_to_truck(header: String,amount:String,truck_id:String,transaction_type:String,validity: String): Response<Addmoneywallet>
    suspend fun purchase_plan_from_walletApi_to_passenger(header: String,amount:String,truck_id:String,transaction_type:String,validity: String): Response<Addmoneywallet>

    suspend fun CapacityApi(token: String): Response<LoaodCarryingResponse>
    suspend fun Filteredwallet(header: String,date:String,transaction_type:String): Response<WalletFilterListResponse>
    suspend fun CompletedTripHistoryPassenger(header: String): Response<TripHistoryResponse>
    suspend fun VisitingCard(header: String): Response<VisitingCardUrlResponse>
    suspend fun OngoinTripHistoryPassenger(header: String): Response<TripHistoryResponse>
    suspend fun cancel_booking_history_loader(header: String): Response<TripHistoryResponse>
    suspend fun cnacel_booking_passengers(header: String): Response<TripHistoryResponse>
    suspend fun AddTrip(
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
        isFromPassenger: String,
    ): Response<AddTripDriverMOdelClass>

   suspend fun AddTripVendor(
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
   ): Response<CreateBusinesscard>
     suspend fun add_passenger_vendor_trip(
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
    ): Response<AddTripDriverMOdelClass>
    suspend fun AddTripPassenger(
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
    ): Response<CreateBusinesscard>
    suspend fun driverChangePwd(
        token:String,
        password: String
    ): Response<RegisterResponseModel>
     suspend fun passengers_ride_completed(header: String,id:String): Response<RideCompletedResponse>

    suspend fun DeiselList(
        id: String
    ): Response<DeiselPrice>
    suspend fun driverList(
        token:String,
//        vendor_id: String
    ): Response<DriverListResponse>
   suspend fun bank_account_list(
        token:String): Response<BankAccountListResponse>
  suspend fun bank_account_list_id(
        token:String,id: String): Response<BankAccountListResponse>

    suspend fun get_passenger_vehicleno_details(
        token:String,
        id: String
    ): Response<VehicledataResponse>
    suspend fun termsAndConditions(token : String) : Response<PrivacyPolicyModel>

    suspend fun get_loader_vehicleno_details(
        token:String,
        id: String
    ): Response<VehicledataResponse>
    suspend fun StateList(): Response<StateListResponse>

    suspend fun subscriptionplan(
        token: String,
        forPassenger:Int
    ): Response<SubscriptionPlan>

//    suspend fun subscription_plan_passengers(
//        token: String
//    ): Response<SubscriptionPlan>
    suspend fun Loadertruckrepositorypassengerlist(
        token: String
    ): Response<LoaderTruckRepositoryListResponse>
     suspend fun getDashboardBannertApi(
        token: String,vehicle_type: String
    ): Response<BannerResponse>

    suspend fun Loadertruckrepositorylist(
        token: String,
        isFromPassenger: String
    ): Response<LoaderTruckRepositoryListResponse>
    suspend fun PrivacyPolicy(header: String): Response<PrivacyPolicy>

    suspend fun TransactionReport(header: String): Response<TransactionReportResponse>
    suspend fun VisitingPdf(header: String): Response<DownloadPdfResponse>
    suspend fun InvoiceDownload(header: String,invoiceno:String): Response<DownloadPdfResponse>
    suspend fun Notification(header: String): Response<NotificationResponse>

    suspend fun driverVerifyOtp(otp: String, mobile: String, ): Response<RegisterResponseModel>
//    suspend fun ContactUs(header: String): Response<ContactUSRsponse>
    suspend fun Rating(header: String): Response<RatingResponse>
    suspend fun Aboutus(header: String): Response<AboutUs>

    suspend fun TripList(header: String,isFromPassenger: String): Response<TripListResponse>
    suspend fun PassengerTripList(header: String): Response<TripListResponse>
    suspend fun self_driver_trip(header: String): Response<AddTripDriverMOdelClass>
    suspend fun self_driver_passenger_trip(header: String): Response<AddTripDriverMOdelClass>
    suspend fun InvoiceList(header: String,type:String): Response<InvoiceListResponse>

    suspend fun LoaderinvoiceSummery(header: String,invoice_numbers:String): Response<InvoiceSummeryResponse>
    suspend fun ForgotPassword(
        mobile: String,
        password: String,
    ): Response<RegisterResponseModel>
//    suspend fun Loaderinvoice(header: String,invoice_numbers:String): Response<InvoiceResponse>

    suspend fun GetProfile(token: String, ): Response<ProfileResponse>

    suspend fun Completedrivertripdetails(header: String,booking_id:String): Response<CompleteDriverDetailsResponse>
    suspend fun checkTripPaymentsApi(header: String,amount: String,booking_id: String): Response<CheckTripPaymentResponse>
     suspend fun driver_loadertrip_list(header: String,type:String): Response<TripListResponse>
    suspend fun passenger_Payments_checkApi(header: String,amount: String,booking_id: String): Response<CheckTripPaymentResponse>
    suspend fun ongoing_booking_history_loader_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse>
    suspend fun ongoing_booking_history_passenger_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse>
    suspend fun cancel_booking_history_passenger_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse>
    suspend fun Completedriverpassengertripdetails(header: String,booking_id:String): Response<CompleteDriverDetailsResponse>
    suspend fun cancel_booking_history_loader_details(header: String,booking_id:String): Response<CompleteDriverDetailsResponse>
     suspend fun get_user_check_statusApi(header: String): Response<PrivacyPolicyModel>

    suspend fun Senddriverloaderinvoice(header: String,booking_id:String): Response<SendDriverLoaderInvoiceResponse>
    suspend fun termsAPi(
        header: String
    ): Response<RegisterResponseModel>

    suspend fun aboutUs(
        header: String
    ): Response<RegisterResponseModel>

    suspend fun privacypolicy(
        header: String
    ): Response<RegisterResponseModel>
    suspend fun calcelation_refund_policy(header: String): Response<PrivacyPolicyModel>


    suspend fun contactUs(
        header: String
    ): Response<ContactUsRsponse>

    suspend fun truckTypeApi(
    ): Response<TruckTypeResponse>

    suspend fun TypeofTruckApi(
        header: String,
        type:String
    ): Response<TypeofTruckResponse>

    suspend fun BodyTypeApi(
        header: String
    ): Response<BodyType>

    suspend fun HightApi(
        header: String
    ): Response<hightResponse>

//    suspend fun ColorApi(
//        header: String
//    ): Response<ColorResponse>

     suspend fun YearApi(token: String,type: String): Response<YearResponse>

    suspend fun vehicalwheels(
        header: String,type:String
    ): Response<vehicalwheelsResponse>

    suspend fun Businessdata(
        header: String
    ): Response<BusinessListResponse>

    suspend fun getProfile(   header: String
    ): Response<ListResponseData>
    /*
      suspend fun viewProfile(
          token: String,
      ): Response<ViewProfileResponseModel>

      suspend fun updateProfileApi(
          token: String,
          name: RequestBody,
          bio:RequestBody,
          email: RequestBody,
          gender: RequestBody,
          image: MultipartBody.Part,
      ): Response<LoginResponseModel>

      suspend fun dashboardApi(
          token: String,
      ): Response<DashboardResponseModel>

      suspend fun postLikeApi(
          token: String,
          post_id:String,
          type:String
      ): Response<CommonDataResponse>

      suspend fun voteApi(
          token: String,
          post_id:String,
         vote:String
      ): Response<CommonDataResponse>
  */
    suspend fun driverSignupApi(
        name: String,
        countryCode: String,
        mobile: String,
        email: String,
        gst_number: String,
        password: String,referal_code:String,
        role: String,
//        type_vehicle: String,
        device_type: String,
        device_name: String,
        device_token: String,
        device_id: String,
    ): Response<RegisterResponseModel>

    suspend fun BusinesscardApi(
        token: String,
        Company: RequestBody,
        name: RequestBody,
        mobile: RequestBody,
        email: RequestBody,
        address: RequestBody,
        role: RequestBody,
        image: MultipartBody.Part,
    ): Response<CreateBusinesscard>
 suspend fun update_business_cardApi(
        token: String,
        Company: RequestBody,
        name: RequestBody,
        mobile: RequestBody,
        email: RequestBody,
        address: RequestBody,
        role: RequestBody,
        image: MultipartBody.Part,
    ): Response<CreateBusinesscard>

    suspend fun LoaderVehicleimage(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part,
    ): Response<Loaderimage>

    suspend fun passengerVehicleimg(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part
    ): Response<Loaderimage>
    suspend fun loader_vehicle_img_update(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part,
        id: RequestBody
    ): Response<Loaderimage>
    suspend fun passenger_vehicle_img_update(
        token: String,
        vehicle_id: RequestBody,
        Image: MultipartBody.Part,id: RequestBody
    ): Response<Loaderimage>
    suspend fun UpcomingsPassengerTripHistory(header: String): Response<TripHistoryResponse>
    suspend fun Passengervehicledoc(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,
        exp_date: RequestBody,
        Doc: MultipartBody.Part
    ): Response<Loaderimage>
 suspend fun passenger_vehicle_doc_update(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,
        exp_date: RequestBody,
        Doc: MultipartBody.Part
    ): Response<Loaderimage>

    suspend fun loader_vehicle_doc_update(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,
        exp_date: RequestBody,
        Doc: MultipartBody.Part
    ): Response<Loaderimage>

    suspend fun GetVehicle(
        token: String,
    ): Response<VehicleList>
   suspend fun get_passenger_vehicleno(
        token: String,
    ): Response<VehicleNumberPassengerLIst>

    suspend fun get_loder_vehicleno(
        token: String,
        isFromPassenger: String
    ): Response<VehicleNumberListMOdelCLass>
    suspend fun loderfarecalculator(
        token: String,
        pickup_lat:String,
        pickup_long:String,
        dropup_lat:String,
        dropup_long:String,
        mileage:String,
        vehicle_type:String,
        oil_price:String
    ): Response<FareCaluculationResponse>

    suspend fun passenger_fare_calculatorApi(
        token: String,
        pickup_lat:String,
        pickup_long:String,
        dropup_lat:String,
        dropup_long:String,
        mileage:String,
        vehicle_type:String,
        oil_price:String
    ): Response<FareCaluculationResponse>

    suspend fun loader_vehicle_payment(
        token: String,
        id:String,subscribe:String,payment_mode:String,
        transaction_id:String,payment_crdated:String,status:String
    ): Response<AddDriverResponse>

  suspend fun add_passenger_vehicle_payment(
      token: String,
      id:String,subscribe:String,fare:String,payment_mode:String,
      transaction_id:String,validity: String,payment_crdated:String,status: String
    ): Response<AddDriverResponse>

    suspend fun FinalpassengervehicalSubmit(
        token: String,
        driver_id: String
    ): Response<AddVehicalfinalResponse>

//    suspend fun loader_trip_list_details(
//        token: String,
//        loader_id: String
//    ): Response<TripListDetailsModelClass>

    suspend fun passenger_trip_list_details(
        token: String,
        loader_id: String
    ): Response<TripListDetailsModelClass>

    suspend fun Loadervehicledoc(
        token: String,
        vehicle_id: RequestBody,
        doc_name: RequestBody,
        exp_date: RequestBody,
        Doc: MultipartBody.Part,
    ): Response<Loaderimage>

    suspend fun AdddriverApi(
        token: String,
        drivername: RequestBody,
        driverexperience: RequestBody,
        Licence: RequestBody,
        countryCode: RequestBody,
        mobile: RequestBody,
        email: RequestBody,
        vehicleId: RequestBody,
        Password: RequestBody,
        Image: MultipartBody.Part,
        pdfFile:MultipartBody.Part,
        vendorid:RequestBody,
//        serviceid:RequestBody,
    ): Response<AddDriverResponse>
}


