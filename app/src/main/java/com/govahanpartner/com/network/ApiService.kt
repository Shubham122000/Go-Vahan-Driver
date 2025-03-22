package com.govahanpartner.com.network

import com.govahanpartner.com.model.*
import com.govahanpartner.com.ui.common.ReferNEarnResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
interface ApiService {

       @FormUrlEncoded
       @POST("driver_register")
       suspend fun driverRegister(
        @Field("name") name: String,
        @Field("country_code") countryCode: String,
        @Field("mobile") mobile: String,
        @Field("email") email: String,
        @Field("gst_number") gst_number: String,
        @Field("password") password: String,
        @Field("referal_code") referal_code : String,
        @Field("user_type") user_type: String,
        @Field("device_type") device_type:String,
        @Field("device_name") device_name:String,
        @Field("device_token") device_token: String,
        @Field("device_id") device_id: String,
        ): Response<RegisterResponseModel>

        @FormUrlEncoded
        @POST("driver_login")
        suspend fun driverLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_token") device_token: String,
        @Field("device_type") device_type: String,
        @Field("role") type: String,
        ): Response<RegisterResponseModel>

      @Multipart
      @POST("add_driving_licence")
      suspend fun add_driving_licence(
        @Part("id") id: RequestBody,
        @Part driving_licence: MultipartBody.Part
      ): Response<RegisterResponseModel>

       @Multipart
       @POST("add_vehicle")
       suspend fun addloadervehical(
        @Header("Authorization") authorization: String,
        @Part("driver_id") driver_id: RequestBody,
        @Part("vehicle_owner_name") vehicle_owner_name: RequestBody,
        @Part("vehicle_name") vehicle_name: RequestBody,
        @Part("year_of_model") year_of_model: RequestBody,
        @Part("vehicle_number") vehicle_number: RequestBody,
        @Part("vehicle_type") vehicle_type: RequestBody,
//        @Field("vehicle_category") vehicle_category: String,
        @Part("capacity") capacity: RequestBody,
        @Part("height") height: RequestBody,
        @Part("color") color: RequestBody,
        @Part("no_of_tyres") no_of_tyres: RequestBody,
        @Part("body_type") body_type: RequestBody,
        @Part("is_from_passenger") isFromPassenger: RequestBody,
        @Part("seat") seat: RequestBody,
        @Part images1: MultipartBody.Part,
        @Part images2: MultipartBody.Part,
        @Part images3: MultipartBody.Part,
        @Part images4: MultipartBody.Part,
        @Part doc1: MultipartBody.Part,
        @Part doc2: MultipartBody.Part,
        @Part doc3: MultipartBody.Part,
        @Part doc4: MultipartBody.Part,
        @Part doc5: MultipartBody.Part,
        @Part doc6: MultipartBody.Part,
        @Part("exp_date_1") exp_date_1: RequestBody,
        @Part("exp_date_2") exp_date_2: RequestBody,
        @Part("exp_date_3") exp_date_3: RequestBody,
        @Part("exp_date_4") exp_date_4: RequestBody,
        @Part("exp_date_5") exp_date_5: RequestBody,
        @Part("other_exp_date") other_exp_date: RequestBody,
        @Part("other_doc_name") other_doc_name: RequestBody,
    ): Response<AddloaderResponse>

    @Multipart
    @POST("edit_vehicle")
    suspend fun editVehicle(
        @Header("Authorization") authorization: String,
//        @Part("driver_id") driver_id: RequestBody,
//        @Part("vehicle_owner_name") vehicle_owner_name: RequestBody,
//        @Part("vehicle_name") vehicle_name: RequestBody,
//        @Part("year_of_model") year_of_model: RequestBody,
//        @Part("vehicle_number") vehicle_number: RequestBody,
//        @Part("vehicle_type") vehicle_type: RequestBody,
////        @Field("vehicle_category") vehicle_category: String,
//        @Part("capacity") capacity: RequestBody,
//        @Part("height") height: RequestBody,
//        @Part("color") color: RequestBody,
//        @Part("no_of_tyres") no_of_tyres: RequestBody,
//        @Part("body_type") body_type: RequestBody,
//        @Part("is_from_passenger") isFromPassenger: RequestBody,
//        @Part("seat") seat: RequestBody,
        @Part images1: MultipartBody.Part?,
        @Part images2: MultipartBody.Part?,
        @Part images3: MultipartBody.Part?,
        @Part images4: MultipartBody.Part?,
        @Part doc1: MultipartBody.Part?,
        @Part doc2: MultipartBody.Part?,
        @Part doc3: MultipartBody.Part?,
        @Part doc4: MultipartBody.Part?,
        @Part doc5: MultipartBody.Part?,
        @Part doc6: MultipartBody.Part?,
        @Part("exp_date_1") exp_date_1: RequestBody,
        @Part("exp_date_2") exp_date_2: RequestBody,
        @Part("exp_date_3") exp_date_3: RequestBody,
        @Part("exp_date_4") exp_date_4: RequestBody,
        @Part("exp_date_5") exp_date_5: RequestBody,
        @Part("other_exp_date") other_exp_date: RequestBody,
        @Part("other_doc_name") other_doc_name: RequestBody,
    ): Response<AddloaderResponse>

    @Multipart
    @POST("indi_add_loader_vehicle")
    suspend fun indi_add_loader_vehicle(
        @Header("Authorization") authorization: String,
        @Part ("vehicle_owner_name") vehicle_owner_name: RequestBody,
        @Part ("vehicle_name") vehicle_name: RequestBody,
        @Part ("year_of_model") year_of_model: RequestBody,
        @Part ("vehicle_number") vehicle_number: RequestBody,
        @Part ("vehicle_type") vehicle_type: RequestBody,
//        @Field("vehicle_category") vehicle_category: String,
        @Part ("capacity") capacity: RequestBody,
        @Part ("height") height: RequestBody,
        @Part ("no_of_tyres") no_of_tyres: RequestBody,
        @Part ("body_type") body_type: RequestBody,
        @Part images1: MultipartBody.Part,
        @Part images2: MultipartBody.Part,
        @Part images3: MultipartBody.Part,
        @Part images4: MultipartBody.Part,
        @Part doc1: MultipartBody.Part,
        @Part doc2: MultipartBody.Part,
        @Part doc3: MultipartBody.Part,
        @Part doc4: MultipartBody.Part,
        @Part doc5: MultipartBody.Part,
        @Part doc6: MultipartBody.Part,
        @Part("exp_date_1") exp_date_1: RequestBody,
        @Part("exp_date_2") exp_date_2: RequestBody,
        @Part("exp_date_3") exp_date_3: RequestBody,
        @Part("exp_date_4") exp_date_4: RequestBody,
        @Part("exp_date_5") exp_date_5: RequestBody,
        @Part("other_exp_date") other_exp_date: RequestBody,
        @Part("doc_name_1") doc_name_1: RequestBody,
        @Part("doc_name_2") doc_name_2: RequestBody,
        @Part("doc_name_3") doc_name_3: RequestBody,
        @Part("doc_name_4") doc_name_4: RequestBody,
        @Part("doc_name_5") doc_name_5: RequestBody,
        @Part("other_doc_name") other_doc_name: RequestBody,
    ): Response<AddloaderResponse>

    @FormUrlEncoded
    @POST("loader_vehicle_update")
    suspend fun loader_vehicle_update(
        @Header("Authorization") authorization: String,
        @Field("id") driver_id: String,
        @Field("vehicle_owner_name") vehicle_owner_name: String,
        @Field("vehicle_name") vehicle_name: String,
        @Field("year_of_model") year_of_model: String,
        @Field("vehicle_number") vehicle_number: String,
//        @Field("vehicle_category") vehicle_category: String,
        @Field("capacity") capacity: String,
        @Field("height") height: String,
        @Field("no_of_tyres") no_of_tyres: String,
        @Field("body_type") body_type: String,
    ): Response<AddloaderResponse>

    @Multipart
    @POST("add_passenger_vehicle")
    suspend fun addpassengervehical(
        @Header("Authorization") authorization: String,
        @Part("driver_id") driver_id: RequestBody,
        @Part("owner_name") vehicle_owner_name: RequestBody,
        @Part("vehicle_name") vehicle_name: RequestBody,
        @Part("year_model") year_of_model: RequestBody,
        @Part("vehicle_no") vehicle_number: RequestBody,
        @Part("vehicle_type") vehicle_type: RequestBody,
        @Part("color") color: RequestBody,
        @Part("no_tyres") no_of_tyres: RequestBody,
        @Part("seat") seat: RequestBody,
        @Part images1: MultipartBody.Part,
        @Part images2: MultipartBody.Part,
        @Part images3: MultipartBody.Part,
        @Part images4: MultipartBody.Part,
        @Part doc1: MultipartBody.Part,
        @Part doc2: MultipartBody.Part,
        @Part doc3: MultipartBody.Part,
        @Part doc4: MultipartBody.Part,
        @Part doc5: MultipartBody.Part,
        @Part doc6: MultipartBody.Part,
        @Part("exp_date_1") exp_date_1: RequestBody,
        @Part("exp_date_2") exp_date_2: RequestBody,
        @Part("exp_date_3") exp_date_3: RequestBody,
        @Part("exp_date_4") exp_date_4: RequestBody,
        @Part("exp_date_5") exp_date_5: RequestBody,
        @Part("other_exp_date") other_exp_date: RequestBody,
        @Part("doc_name_1") doc_name_1: RequestBody,
        @Part("doc_name_2") doc_name_2: RequestBody,
        @Part("doc_name_3") doc_name_3: RequestBody,
        @Part("doc_name_4") doc_name_4: RequestBody,
        @Part("doc_name_5") doc_name_5: RequestBody,
        @Part("other_doc_name") other_doc_name: RequestBody,
        ): Response<AddloaderResponse>

    @FormUrlEncoded
    @POST("phonepay")
    suspend fun checksumApi(
        @Header("Authorization") authorization: String,
        @Field("amount") amount: String,
        @Field("mobile") mobile: String,
        @Field("user_id") user_id: String,
    ): Response<ChecksumResponse>

    @FormUrlEncoded
    @POST("phonepay-check-status")
    suspend fun paymentcheckapi(
        @Header("Authorization") authorization: String,
        @Field("transaction_id") amount: String,
    ): Response<PaymentSuccessMsgResponse>

    @FormUrlEncoded
    @POST("withdraw")
    suspend fun withdrawapi(
        @Header("Authorization") authorization: String,
        @Field("amount") amount: String,
    ): Response<BankAccountListResponse>

    @GET("calcelation_refund_policy")
    suspend fun calcelation_refund_policy(
        @Header("Authorization") authorization: String
    ): Response<PrivacyPolicyModel>
    @GET("get_user_check_status")
    suspend fun get_user_check_statusApi(@Header("Authorization") token : String):
            Response<PrivacyPolicyModel>

    @FormUrlEncoded
    @POST("vendor_driver_transection")
    suspend fun vendor_driver_transection(
        @Header("Authorization") authorization: String,
        @Field("amount") amount: String,
    ): Response<BankAccountListResponse>

    @FormUrlEncoded
    @POST("indi_add_passenger_vehicle")
    suspend fun indi_add_passenger_vehicle(
        @Header("Authorization") authorization: String,
        @Part("driver_id") driver_id: RequestBody,
        @Part("owner_name") vehicle_owner_name: RequestBody,
        @Part("vehicle_name") vehicle_name: RequestBody,
        @Part("year_model") year_of_model: RequestBody,
        @Part("vehicle_no") vehicle_number: RequestBody,
        @Part("vehicle_type") vehicle_type: RequestBody,
        @Part("color") color: RequestBody,
        @Part("no_tyres") no_of_tyres: RequestBody,
        @Part("seat") seat: RequestBody,
        @Part images1: MultipartBody.Part,
        @Part images2: MultipartBody.Part,
        @Part images3: MultipartBody.Part,
        @Part images4: MultipartBody.Part,
        @Part doc1: MultipartBody.Part,
        @Part doc2: MultipartBody.Part,
        @Part doc3: MultipartBody.Part,
        @Part doc4: MultipartBody.Part,
        @Part doc5: MultipartBody.Part,
        @Part doc6: MultipartBody.Part,
        @Part("exp_date_1") exp_date_1: RequestBody,
        @Part("exp_date_2") exp_date_2: RequestBody,
        @Part("exp_date_3") exp_date_3: RequestBody,
        @Part("exp_date_4") exp_date_4: RequestBody,
        @Part("exp_date_5") exp_date_5: RequestBody,
        @Part("other_exp_date") other_exp_date: RequestBody,
        @Part("doc_name_1") doc_name_1: RequestBody,
        @Part("doc_name_2") doc_name_2: RequestBody,
        @Part("doc_name_3") doc_name_3: RequestBody,
        @Part("doc_name_4") doc_name_4: RequestBody,
        @Part("doc_name_5") doc_name_5: RequestBody,
        @Part("other_doc_name") other_doc_name: RequestBody,
    ): Response<AddloaderResponse>

    @FormUrlEncoded
    @POST("passenger_vehicle_update")
    suspend fun passenger_vehicle_update(
        @Header("Authorization") authorization: String,
        @Field("id") driver_id: String,
        @Field("owner_name") vehicle_owner_name: String,
        @Field("vehicle_name") vehicle_name: String,
        @Field("year_model") year_of_model: String,
        @Field("vehicle_no") vehicle_number: String,
        @Field("vehicle_type") vehicle_type: String,
        @Field("color") color: String,
        @Field("no_tyres") no_of_tyres: String,
        @Field("seat") seat: String,
    ): Response<AddloaderResponse>

    @FormUrlEncoded
    @POST("driver_send_otp")
    suspend fun driverSendOTP(
        @Field("mobile") mobile: String,
    ): Response<RegisterResponseModel>

    @FormUrlEncoded
    @POST("loder_fare_calculator")
    suspend fun loderfarecalculator(
        @Header("Authorization") authorization: String,
        @Field("pickup_lat") pickup_lat: String,
        @Field("pickup_long") pickup_long: String,
        @Field("dropup_lat") dropup_lat: String,
        @Field("dropup_long") dropup_long: String,
        @Field("mileage") mileage: String,
        @Field("vehicle_type") vehicle_type: String,
        @Field("oil_price") oil_price: String,
    ): Response<FareCaluculationResponse>

    @FormUrlEncoded
    @POST("passenger_fare_calculator")
    suspend fun passenger_fare_calculatorApi(
        @Header("Authorization") authorization: String,
        @Field("pickup_lat") pickup_lat: String,
        @Field("pickup_long") pickup_long: String,
        @Field("dropup_lat") dropup_lat: String,
        @Field("dropup_long") dropup_long: String,
        @Field("mileage") mileage: String,
        @Field("vehicle_type") vehicle_type: String,
        @Field("oil_price") oil_price: String,
    ): Response<FareCaluculationResponse>

    @GET("user_termcondition")
    suspend fun termsAndConditions(@Header("Authorization") token : String): Response<PrivacyPolicyModel>

    @FormUrlEncoded
    @POST("vehicle_payment")
    suspend fun loader_vehicle_payment(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
        @Field("subscribe") subscribe: String,
        @Field("payment_mode") payment_mode: String,
        @Field("transaction_id") transaction_id: String,
        @Field("payment_crdated") payment_crdated: String,
        @Field("doc_status") status: String,
        ): Response<AddDriverResponse>

    @FormUrlEncoded
    @POST("add_passenger_vehicle_payment")
    suspend fun add_passenger_vehicle_payment(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
        @Field("subscribe") subscribe: String,
        @Field("fare") fare: String,
        @Field("payment_mode") payment_mode: String,
        @Field("transaction_id") transaction_id: String,
        @Field("validity") validity: String,
        @Field("payment_crdated") payment_crdated: String,
        @Field("doc_status") status: String,
    ): Response<AddDriverResponse>

    @FormUrlEncoded
    @POST("final_vehicle_submition")
    suspend fun FinalvehicalSubmit(
        @Header("Authorization") authorization: String,
        @Field("id") vehicle_id: String,
    ): Response<AddVehicalfinalResponse>

    @FormUrlEncoded
    @POST("final_passenger_vehicle_submition")
    suspend fun FinalpassengerSubmit(
        @Header("Authorization") authorization: String,
        @Field("id") vehicle_id: String,
    ): Response<AddVehicalfinalResponse>

    @Multipart
    @POST("passenger_vehicle_img")
    suspend fun passengervehicleimg(
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part image: MultipartBody.Part,
    ): Response<Loaderimage>

    @Multipart
    @POST("loader_vehicle_img_update")
    suspend fun loader_vehicle_img_update (
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part image: MultipartBody.Part,
        @Part ("id")id: RequestBody
    ): Response<Loaderimage>

    @Multipart
    @POST("passenger_vehicle_img_update")
    suspend fun passenger_vehicle_img_update (
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("id") id: RequestBody,
    ): Response<Loaderimage>

    @Multipart
    @POST("passenger_vehicle_doc")
    suspend fun passengervehicledoc(
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part("doc_name") doc_name: RequestBody,
        @Part("exp_date") exp_date: RequestBody,
        @Part pdf: MultipartBody.Part,
    ): Response<Loaderimage>

    @Multipart
    @POST("loader_vehicle_doc_update")
    suspend fun loader_vehicle_doc_update(
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part("doc_name") doc_name: RequestBody,
        @Part("exp_date") exp_date: RequestBody,
        @Part pdf: MultipartBody.Part,
    ): Response<Loaderimage>

    @Multipart
    @POST("passenger_vehicle_doc_update")
    suspend fun passenger_vehicle_doc_update(
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part("doc_name") doc_name: RequestBody,
        @Part("exp_date") exp_date: RequestBody,
        @Part pdf: MultipartBody.Part,
    ): Response<Loaderimage>

    @Multipart
    @POST("loader_vehicle_img")
    suspend fun loadervehicleimg(
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part image: MultipartBody.Part,
    ): Response<Loaderimage>

    @Multipart
    @POST("loader_vehicle_doc")
    suspend fun loadervehicledoc(
        @Header("Authorization") authorization: String,
        @Part("vehicle_id") vehicle_id: RequestBody,
        @Part("doc_name") doc_name: RequestBody,
        @Part("exp_date") exp_date: RequestBody,
        @Part pdf: MultipartBody.Part,
    ): Response<Loaderimage>

    @FormUrlEncoded
    @POST("driver_verfiy_otp")
    suspend fun driverVerifyOtp(
        @Field("otp") otp: String,
        @Field("mobile") mobile: String,
    ): Response<RegisterResponseModel>

    @Multipart
    @POST("create_business_card")
    suspend fun BusinessCardDriver(
        @Header("Authorization") authorization: String,
        @Part("company_name") company_name: RequestBody,
        @Part("full_name") full_name: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part("role") role: RequestBody,
        @Part image: MultipartBody.Part,
    ): Response<CreateBusinesscard>
    @Multipart
    @POST("update_business_card")
    suspend fun update_business_cardApi(
        @Header("Authorization") authorization: String,
        @Part("company_name") company_name: RequestBody,
        @Part("full_name") full_name: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part("role") role: RequestBody,
        @Part image: MultipartBody.Part,
    ): Response<CreateBusinesscard>

    @Multipart
    @POST("add_vendor_driver")
    suspend fun adddriver(
        @Header("Authorization") authorization: String,
        @Part("name") name: RequestBody,
        @Part("driving_exp") driving_exp: RequestBody,
        @Part("driving_lic") driving_lic: RequestBody,
        @Part("country_code") countryCode: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("vehicle_id") vehicleId: RequestBody,
        @Part("password") password: RequestBody,
        @Part profile_image: MultipartBody.Part,
        @Part id_proof: MultipartBody.Part,
        @Part("vendor_id") vendor_id: RequestBody,
//        @Part("service_id") service_id: RequestBody,
        ): Response<AddDriverResponse>

    @Multipart
    @POST("user_update_profiile")
    suspend fun EditProfile(
        @Header("Authorization") authorization: String,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("mobile_number") mobile_number: RequestBody,
        @Part("address") address: RequestBody,
        @Part("device_token") device_token: RequestBody,
        @Part("device_type") device_type: RequestBody,
        @Part("device_id") device_id: RequestBody,
        @Part profile_image: MultipartBody.Part?
    ): Response<ProfileEditResponse>


    @FormUrlEncoded
    @POST("driver_change_pwd")
    suspend fun driverChangePwd(
        @Header("Authorization") authorization: String,
        @Field("password") password: String,
    ): Response<RegisterResponseModel>

    @GET("loader_driver_vehicle_list")
    suspend fun GetVehicleList(
        @Header("Authorization") authorization: String,
    ): Response<VehicleList>

    @GET("get_passenger_vehicleno")
    suspend fun get_passenger_vehicleno(
        @Header("Authorization") authorization: String,
    ): Response<VehicleNumberPassengerLIst>

    @FormUrlEncoded
    @POST("get_connected_vehicle_number")
    suspend fun get_loder_vehicleno(
        @Header("Authorization") authorization: String,
        @Field("is_from_passenger") isFromPassenger: String
    ): Response<VehicleNumberListMOdelCLass>

    @GET("get_driver_profile")
    suspend fun GetDriverProfile(
        @Header("Authorization") authorization: String,
    ): Response<ProfileResponse>

    @GET("my_driver_wallet_list_donload")
    suspend fun my_driver_wallet_list_donload(
        @Header("Authorization") authorization: String,
    ): Response<ProfileResponse>

    @POST("referrals")
    suspend fun referralsApi(
        @Header("Authorization") authorization: String,
    ): Response<ReferNEarnResponse>

    @GET("vehicle_seat")
    suspend fun vehicleSeat(
        @Header("Authorization") authorization: String,
    ): Response<SeatResponse>

//    @FormUrlEncoded
    @POST("vendor_driver_list")
    suspend fun driverList(
        @Header("Authorization") authorization: String,
//        @Field("vendor_id") vendor_id: String,
    ): Response<DriverListResponse>

    @FormUrlEncoded
    @POST("get_passenger_vehicleno_details")
    suspend fun get_passenger_vehicleno_details(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
    ): Response<VehicledataResponse>

    @FormUrlEncoded
    @POST("get_loader_vehicleno_details")
    suspend fun get_loader_vehicleno_details(
        @Header("Authorization") authorization: String,
        @Field("id") id: String,
    ): Response<VehicledataResponse>

    @FormUrlEncoded
    @POST("subscription_plan")
    suspend fun subscriptonplan(
        @Header("Authorization") authorization: String,
        @Field ("for_passenger") forPassenger:Int
    ): Response<SubscriptionPlan>

//    @GET("subscription_plan_passengers")
//    suspend fun subscription_plan_passengers(
//        @Header("Authorization") authorization: String,
//    ): Response<SubscriptionPlan>

    @FormUrlEncoded
    @POST("payment_status_check")
    suspend fun payment_status_check(
        @Header("Authorization") authorization: String,
        @Field("transaction_id") transaction_id: String
    ): Response<Razorpay_status_Response>

    @FormUrlEncoded
    @POST("vehicle_repository_list")
    suspend fun LoaderTruckRepositoryList(
        @Header("Authorization") authorization: String,
        @Field("is_from_passenger") isFromPassenger: String
    ): Response<LoaderTruckRepositoryListResponse>

    @GET("passengers_truck_repository_list")
    suspend fun LoaderTruckRepositoryLPassengerist(
        @Header("Authorization") authorization: String,
    ): Response<LoaderTruckRepositoryListResponse>

    @FormUrlEncoded
    @POST("vehicle_banner")
    suspend fun getDashboardBannertApi(
        @Header("Authorization") token : String,
        @Field("vehicle_type") vehicle_type: String): Response<BannerResponse>

    @FormUrlEncoded
    @POST("diesel_list")
    suspend fun deiselList(
        @Field("id") id: String,
    ): Response<DeiselPrice>

    @GET("state_list")
    suspend fun StateList(): Response<StateListResponse>
    @GET("business_card_details")
    suspend fun Businesscarddetails(
        @Header("Authorization") authorization: String
    ): Response<BusinessListResponse>

    @GET("driver_service_type")
    suspend fun truckType(
    ): Response<TruckTypeResponse>

    @FormUrlEncoded
    @POST("vehicle_type")
    suspend fun TypeofTruck(
        @Header("Authorization") authorization: String,
        @Field("type") type: String
    ): Response<TypeofTruckResponse>

    @FormUrlEncoded
    @POST("checkTripPayments")
    suspend fun checkTripPaymentsApi(
        @Header("Authorization") authorization: String,
        @Field("amount") amount: String,
        @Field("booking_id") booking_id: String,
        ): Response<CheckTripPaymentResponse>

    @FormUrlEncoded
    @POST("passenger_Payments_check")
    suspend fun passenger_Payments_checkApi(
        @Header("Authorization") authorization: String,
        @Field("amount") amount: String,
        @Field("booking_id") booking_id: String,
    ): Response<CheckTripPaymentResponse>

    @POST("body_type")
    suspend fun BodyTpe(
        @Header("Authorization") authorization: String
    ): Response<BodyType>

//    @GET("vehicle_color")
//    suspend fun Color(
//        @Header("Authorization") authorization: String
//    ): Response<ColorResponse>

    @FormUrlEncoded
    @POST("year_model")
    suspend fun Year(
        @Header("Authorization") authorization: String,
        @Field("type") type: String
    ): Response<YearResponse>

    @FormUrlEncoded
    @POST("vehicle_wheels")
    suspend fun vehicalwheels(
        @Header("Authorization") authorization: String,
        @Field("type") type: String
    ): Response<vehicalwheelsResponse>

    @POST("height")
    suspend fun Height(
        @Header("Authorization") authorization: String
    ): Response<hightResponse>

    @GET("loader_loader_carming")
    suspend fun Capacity(
        @Header("Authorization") authorization: String
    ): Response<LoaodCarryingResponse>

    @GET("term_condition")
    suspend fun termsCondition(
        @Header("Authorization") authorization: String
    ): Response<RegisterResponseModel>

    @GET("user_privacypolicy")
    suspend fun privacyPolicy(
        @Header("Authorization") authorization: String
    ): Response<RegisterResponseModel>

    @GET("about_us")
    suspend fun aboutUs(
        @Header("Authorization") authorization: String
    ): Response<RegisterResponseModel>

    @GET("contact_us")
    suspend fun contactUs(
        @Header("Authorization") authorization: String
    ): Response<ContactUsRsponse>

    @GET("driver_get_profile")
    suspend fun getProfile(
        @Header("Authorization") authorization: String
    ): Response<ListResponseData>

    @GET("ongoing_driver_trip_history_loader")
    suspend fun OngoingTripHistory(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

     @GET("cancel_booking_history_loader")
    suspend fun cancel_booking_history_loader(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

    @GET("cnacel_booking_passengers")
    suspend fun cnacel_booking_passengers(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

    @GET("ongoing_booking_passengers")
    suspend fun OngoingTripHistoryPassenger(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

    @GET("loder_driver_cancel_reasons_list")
    suspend fun loadercancellationReasonlist(
        @Header("Authorization") authorization: String
    ): Response<Loader_cancel_ReasonList_Response>

    @FormUrlEncoded
    @POST("get_upcoming_bookings")
    suspend fun UpcomingsTripHistory(
        @Header("Authorization") authorization: String,
        @Field("hit_from_driver") hitFromDriver :String,
        @Field("for_passenger") forPassenger :String,
        @Field("booking_status") bookingStatus :String,
    ): Response<TripHistoryResponse>

    @GET("vendor_upcooming_booking_loder")
    suspend fun vendor_upcooming_booking_loder(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

   @GET("vendor_upcooming_booking_passengers")
    suspend fun vendor_upcooming_booking_passengers(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

    @GET("upcooming_booking_passengers")
    suspend fun UpcomingsPassengerTripHistory(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

    @GET("completed_driver_trip_history_loader")
    suspend fun CompletedTripHistory(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

    @GET("completed_booking_passengers")
    suspend fun CompletedTripHistoryPassenger(
        @Header("Authorization") authorization: String
    ): Response<TripHistoryResponse>

    @GET("payment_list")
    suspend fun payment_list(
        @Header("Authorization") authorization: String
    ): Response<PaymentListResponse>

    @FormUrlEncoded
    @POST("loader_driver_trip_cancel")
    suspend fun LoaderdrivertripHistory(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String,
        @Field("reasons_id") reasons_id:String,
        @Field("message") message:String
    ): Response<LoaderDriverTripCancelResponse>
    @FormUrlEncoded
    @POST("passenger_driver_trip_cancel")
    suspend fun passenger_driver_trip_cancelApi(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String,
        @Field("reasons_id") reasons_id:String,
        @Field("message") message:String
    ): Response<LoaderDriverTripCancelResponse>

    @FormUrlEncoded
    @POST("driver_profile")
    suspend fun driverProfile(
        @Header("Authorization") authorization: String,
        @Field("id") booking_id:String,
    ): Response<DriverProfile>

//    @FormUrlEncoded
//    @POST("loader_trip_list_details")
//    suspend fun loader_trip_list_details(
//        @Header("Authorization") authorization: String,
//        @Field("id") loader_id:String,
//    ): Response<TripListDetailsModelClass>

    @FormUrlEncoded
    @POST("passenger_trip_list_details")
    suspend fun passenger_trip_list_details(
        @Header("Authorization") authorization: String,
        @Field("id") loader_id:String,
    ): Response<TripListDetailsModelClass>

    @FormUrlEncoded
    @POST("get_vehicle_details")
    suspend fun getVehicleDetails(
        @Header("Authorization") authorization: String,
        @Field("vehicle_id") vehicle_id:String,
    ): Response<TaxiRepositoryViewDetailsResponse>

//    @FormUrlEncoded
//    @POST("loader_truck_repository_image_list_details")
//    suspend fun loader_truck_repository_image_list_details(
//        @Header("Authorization") authorization: String,
//        @Field("vehicle_id") vehicle_id:String,
//    ): Response<TruckImagesModelCLass>

    @FormUrlEncoded
    @POST("passengers_truck_repository_image_list")
    suspend fun passengers_truck_repository_image_list(
        @Header("Authorization") authorization: String,
        @Field("vehicle_id") vehicle_id:String,
    ): Response<TruckImagesModelCLass>

//    @FormUrlEncoded
//    @POST("loader_truck_repository_doc_list_details")
//    suspend fun loader_truck_repository_doc_list_details(
//        @Header("Authorization") authorization: String,
//        @Field("vehicle_id") vehicle_id:String,
//    ): Response<TruckDocumentsDetailsResponse>

    @FormUrlEncoded
    @POST("passengers_truck_repository_list_details")
    suspend fun passengers_truck_repository_list_details(
        @Header("Authorization") authorization: String,
        @Field("vehicle_id") vehicle_id:String,
    ): Response<TaxiRepositoryViewDetailsResponse>

    @FormUrlEncoded
    @POST("passengers_truck_repository_doc_list")
    suspend fun passengers_truck_repository_doc_list(
        @Header("Authorization") authorization: String,
        @Field("vehicle_id") vehicle_id:String,
    ): Response<TruckDocumentsDetailsResponse>

    @FormUrlEncoded
    @POST("loader_trip_delete")
    suspend fun loader_trip_delete(
        @Header("Authorization") authorization: String,
        @Field("id") id:String,
    ): Response<DriverProfile>

    @FormUrlEncoded
    @POST("loader_truck_repository_list_delete")
    suspend fun loader_truck_repository_list_delete(
        @Header("Authorization") authorization: String,
        @Field("vehicle_id") vehicle_id:String,
    ): Response<DriverProfile>

    @FormUrlEncoded
    @POST("passengers_truck_repository_list_delete")
    suspend fun passengers_truck_repository_list_delete(
        @Header("Authorization") authorization: String,
        @Field("vehicle_id") vehicle_id:String,
    ): Response<DriverProfile>

    @FormUrlEncoded
    @POST("vendor_driver_delete")
    suspend fun vendor_driver_delete(
        @Header("Authorization") authorization: String,
        @Field("id") id:String,
    ): Response<DriverProfile>

    @FormUrlEncoded
    @POST("add_money")
    suspend fun addMoney(
        @Header("Authorization") authorization: String,
        @Field("type") type:String,
        @Field("transaction_id") transactionId:String,
        @Field("amount") amount:String,
    ): Response<DriverProfile>

    @Multipart
    @POST("add_bank_account")
    suspend fun add_bank_account(
        @Header("Authorization") authorization: String,
        @Part("account_no") account_no:RequestBody,
        @Part("name") name:RequestBody,
        @Part("ifsc") ifsc:RequestBody,
        @Part("bank_name") bank_name:RequestBody,
        @Part("account_branch") account_branch:RequestBody,
        @Part("upi_id") upi_id:RequestBody,
        @Part image: MultipartBody.Part
    ): Response<DriverProfile>

    @Multipart
    @POST("create_booking_document")
    suspend fun createBookingDocument(
        @Header("Authorization") authorization: String,
        @Part("booking_id") booking_id:RequestBody,
        @Part pod: MultipartBody.Part?,
        @Part signature: MultipartBody.Part?,
        @Part builty: MultipartBody.Part?,
        ): Response<DriverProfile>

    @FormUrlEncoded
    @POST("lode_ride_completed")
    suspend fun rideCompleted(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String,
    ): Response<RideCompletedResponse>
@FormUrlEncoded
    @POST("passengers_ride_completed")
    suspend fun passengers_ride_completed(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String,
    ): Response<RideCompletedResponse>


//    @FormUrlEncoded
//    @POST("loader_driver_start_trip")
//    suspend fun AcceptRide(
//        @Header("Authorization") authorization: String,
//        @Field("booking_id") booking_id:String,
//        @Field("start_code") start_code:String,
//    ): Response<Addmoneywallet>

    @FormUrlEncoded
    @POST("update_booking_status")
    suspend fun updateBookingStatus(
        @Header("Authorization") authorization: String,
        @Field("booking_id") bookingId:String,
        @Field("ride_code") rideCode:String,
        @Field("status") status:String,
        @Field("cancel_reason") cancelReason:String,
    ): Response<Addmoneywallet>

    @Multipart
    @POST("driver_update_profiile")
    suspend fun Updatedriver(
        @Header("Authorization") authorization: String,
        @Part("id") id:RequestBody,
        @Part("name") name:RequestBody,
        @Part("mobile_number") mobile_number:RequestBody,
        @Part("experience") experience:RequestBody,
        @Part("licence_number") licence_number:RequestBody,
        @Part("device_token") device_token:RequestBody,
        @Part("device_type") device_type:RequestBody,
        @Part("device_id") device_id:RequestBody,
        @Part("password") password:RequestBody,
        @Part image: MultipartBody.Part
    ): Response<DriverUpdateProfileResponse>

    @FormUrlEncoded
    @POST("wallet_list")
    suspend fun walletList(
        @Header("Authorization") authorization: String,
        @Field("date") date : String,
        @Field("transaction_type") transactionType : String
    ): Response<WalletFilterListResponse>

//    @FormUrlEncoded
//    @POST("vendor_wallet_list")
//    suspend fun vendor_wallet_list(
//        @Header("Authorization") authorization: String,
//        @Field("date") date : String,
//        @Field("transaction_type") transaction_type : String
//    ): Response<WalletFilterListResponse>

//    @FormUrlEncoded
//    @POST("individual_payment_list")
//    suspend fun individual_payment_list(
//        @Header("Authorization") authorization: String,
//        @Field("date") date : String,
//        @Field("transaction_type") transaction_type : String
//    ): Response<VendorWalletActivity>

    @GET("loder_transaction_report")
    suspend fun Transactionreport(
        @Header("Authorization") authorization: String
    ): Response<TransactionReportResponse>

    @GET("visiting_card_url")
    suspend fun Visitingcardurl(
        @Header("Authorization") authorization: String
    ): Response<VisitingCardUrlResponse>

//    @GET("loader_contact_us")
//    suspend fun ContactUS(
//        @Header("Authorization") authorization: String
//    ): Response<ContactUSRsponse>

    @GET("loder_about_us")
    suspend fun AboutUS(
        @Header("Authorization") authorization: String
    ): Response<AboutUs>

    @GET("user_privacypolicy")
    suspend fun PrivacyPolicy(
        @Header("Authorization") authorization: String
    ): Response<PrivacyPolicy>

    @GET("loader_notification_list")
    suspend fun Notification(
        @Header("Authorization") authorization: String
    ): Response<NotificationResponse>

    @GET("driver_rating")
    suspend fun Ratings(
        @Header("Authorization") authorization: String
    ): Response<RatingResponse>

    @GET("visiting_cardPDF")
    suspend fun Visitingcardpdf(
        @Header("Authorization") authorization: String
    ): Response<DownloadPdfResponse>

    @FormUrlEncoded
    @POST("invoice_cardPDF")
    suspend fun InvoiceDownload(
        @Header("Authorization") authorization: String,
        @Field("invoice_numbers") invoice_numbers:String
    ): Response<DownloadPdfResponse>

    @FormUrlEncoded
    @POST("add_driver_my_wallet")
    suspend fun Addmoneywallet(
        @Header("Authorization") authorization: String,
        @Field("amount") amount:String
    ): Response<Addmoneywallet>

    @FormUrlEncoded
    @POST("purchase_plan_from_wallet")
    suspend fun purchase_plan_from_walletApi(
        @Header("Authorization") authorization: String,
        @Field("plan_amount") plan_amount:String,
        @Field("transaction_type") transaction_type:String,
    ): Response<Addmoneywallet>

    @FormUrlEncoded
    @POST("buySubscriptionWallet")
    suspend fun buySubscriptionWalletApi(
        @Header("Authorization") authorization: String,
        @Field("plan_id") plan_id:String,
        @Field("user_type") user_type:String,
    ): Response<Addmoneywallet>

   @FormUrlEncoded
    @POST("buySubscriptionOnline")
    suspend fun buySubscriptionOnlineApi(
        @Header("Authorization") authorization: String,
        @Field("plan_id") plan_id:String,
        @Field("user_type") user_type:String,
        @Field("transection") transection:String,
    ): Response<Addmoneywallet>

    @FormUrlEncoded
    @POST("purchase_plan_from_wallet_to_truck")
    suspend fun purchase_plan_from_walletApi_to_truck(
        @Header("Authorization") authorization: String,
        @Field("plan_amount") plan_amount:String,
        @Field("truck_id") truck_id:String,
        @Field("transaction_type") transaction_type:String,
        @Field("validity") validity:String,
    ): Response<Addmoneywallet>

    @FormUrlEncoded
    @POST("purchase_plan_from_wallet_to_passenger")
    suspend fun purchase_plan_from_walletApi_to_passenger(
        @Header("Authorization") authorization: String,
        @Field("plan_amount") plan_amount:String,
        @Field("passenger_id") truck_id:String,
        @Field("transaction_type") transaction_type:String,
        @Field("validity") validity:String,
    ): Response<Addmoneywallet>

    @FormUrlEncoded
    @POST("loader_trip_list")
    suspend fun LoaderTripList(
        @Header("Authorization") authorization: String,
        @Field("is_from_passenger") isFromPassenger: String,
    ): Response<TripListResponse>
    @FormUrlEncoded
    @POST("vendor_driver_loadertrip_list")
    suspend fun vendor_driver_loadertrip_list(
        @Header("Authorization") authorization: String,
        @Field("driver_id") driver_id: String,
        @Field("type") type: String,
        ): Response<TripListResponse>
  @FormUrlEncoded
    @POST("driver_loadertrip_list")
    suspend fun driver_loadertrip_list(
        @Header("Authorization") authorization: String,
        @Field("type") type: String,
        ): Response<TripListResponse>

    @GET("passenger_trip_list")
    suspend fun PassengerTripList(
        @Header("Authorization") authorization: String,
    ): Response<TripListResponse>

    @POST("self_driver_trip")
    suspend fun self_driver_trip(
        @Header("Authorization") authorization: String,
    ): Response<AddTripDriverMOdelClass>

    @GET("self_driver_passenger_trip")
    suspend fun self_driver_passenger_trip(
        @Header("Authorization") authorization: String,
    ): Response<AddTripDriverMOdelClass>

    @FormUrlEncoded
    @POST("invoice_list")
    suspend fun InvoiceList(
        @Header("Authorization") authorization: String,
        @Field("type") type: String,
    ): Response<InvoiceListResponse>

//    @FormUrlEncoded
//    @POST("loader_driver_invoice_url")
//    suspend fun loader_driver_invoiceinvoice_url(
//        @Header("Authorization") authorization: String,
//        @Field("booking_id") booking_id: String,
//        @Field("type") type: String,
//    ): Response<InvoiceurldownloadResponse>

    @GET("getBankAccounts")
    suspend fun getBankAccountsApi(
        @Header("Authorization") authorization: String,
    ): Response<GetBankAcountResponse>

    @GET("bank_account_list")
    suspend fun bank_account_list(
        @Header("Authorization") authorization: String,
    ): Response<BankAccountListResponse>

        @FormUrlEncoded
        @POST("bank_account_list_id")
        suspend fun bank_account_list_id(
        @Header("Authorization") authorization: String,
        @Field("id") id:String,
        ): Response<BankAccountListResponse>

    @FormUrlEncoded
    @POST("my_driver_wallet_filter_list")
    suspend fun walletfiltered(
        @Header("Authorization") authorization: String,
        @Field("date") date:String,
        @Field("transaction_type") transaction_type:String,
    ): Response<WalletFilterListResponse>

    @FormUrlEncoded
    @POST("completed_driver_trip_history_loader_details")
    suspend fun Completedrivertripdetails(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String
    ): Response<CompleteDriverDetailsResponse>

    @FormUrlEncoded
    @POST("ongoing_booking_history_loader_details")
    suspend fun ongoing_booking_history_loader_details(
        @Header("Authorization") authorization: String,
            @Field("booking_id") booking_id:String
    ): Response<CompleteDriverDetailsResponse>

    @FormUrlEncoded
    @POST("ongoing_booking_history_passenger_details")
    suspend fun ongoing_booking_history_passenger_details(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String
    ): Response<CompleteDriverDetailsResponse>

    @FormUrlEncoded
    @POST("cancel_booking_history_passenger_details")
    suspend fun cancel_booking_history_passenger_details(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String
    ): Response<CompleteDriverDetailsResponse>

    @FormUrlEncoded
    @POST("completed_booking_history_passengers_details")
    suspend fun CompletedriverPassengertripdetails(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String
    ): Response<CompleteDriverDetailsResponse>

   @FormUrlEncoded
    @POST("cancel_booking_history_loader_details")
    suspend fun cancel_booking_history_loader_details(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String
    ): Response<CompleteDriverDetailsResponse>

    @FormUrlEncoded
    @POST("send_driver_loader_invoice")
    suspend fun SendDriverLoaderInvoice(
        @Header("Authorization") authorization: String,
        @Field("booking_id") booking_id:String
    ): Response<SendDriverLoaderInvoiceResponse>

    @FormUrlEncoded
    @POST("loader_driver_invoice_details")
    suspend fun LoaderinvoiceSummery(
        @Header("Authorization") authorization: String,
        @Field("invoice_numbers") invoice_numbers:String
    ): Response<InvoiceSummeryResponse>

//    @FormUrlEncoded
//    @POST("loader_driver_invoice_details")
//    suspend fun Loaderinvoice(
//        @Header("Authorization") authorization: String,
//        @Field("invoice_numbers") invoice_numbers:String
//    ): Response<InvoiceResponse>


    @FormUrlEncoded
    @POST("add_loader_trip")
    suspend fun AddTrip(
        @Header("Authorization") authorization: String,
        @Field("from_trip") from_trip: String,
        @Field("to_trip") to_trip: String,
        @Field("assign_driver") assign_driver: String,
        @Field("total_distance") total_distance: String,
        @Field("freight_amount") freight_amount: String,
        @Field("pickup_lat") pickup_lat: String,
        @Field("pickup_long") pickup_long: String,
        @Field("dropup_lat") dropup_lat: String,
        @Field("dropup_long") dropup_long: String,
        @Field("vehicle_id") vehicle_id: String,
        @Field("booking_date_from") booking_date_from: String,
        @Field("booking_time") booking_time: String,
        @Field("fuel_charge") fuel_charge: String,
        @Field("toll_tax") toll_tax: String,
        @Field("tax") tax: String,
        @Field("driver_fee")  driver_fee: String,
        @Field("is_from_passenger")  isFromPassenger: String,
    ): Response<AddTripDriverMOdelClass>

    @FormUrlEncoded
    @POST("add_loader_vendor_trip")
    suspend fun add_loader_vendor_trip(
        @Header("Authorization") authorization: String,
        @Field("tip_task") tip_task: String,
        @Field("load_caring") load_caring: String,
        @Field("from_trip") from_trip: String,
        @Field("to_trip") to_trip: String,
        @Field("vehicle_type") vehicle_type: String,
        @Field("vehicle_numbers") vehicle_numbers: String,
        @Field("no_tyers") no_tyers: String,
        @Field("body_type") body_type: String,
        @Field("assign_driver") assign_driver: String,
        @Field("total_distance") total_distance: String,
        @Field("freight_amount") freight_amount: String,
        @Field("pickup_lat") pickup_lat: String,
        @Field("pickup_long") pickup_long: String,
        @Field("dropup_lat") dropup_lat: String,
        @Field("dropup_long") dropup_long: String,
        @Field("vehicle_id") vehicle_id: String,
        @Field("booking_date_from") booking_date_from: String,
        @Field("booking_time") booking_time: String,
        @Field("fuel_charge") fuel_charge: String,
        @Field("toll_tax") toll_tax: String,
        @Field("driver_fee")  driver_fee: String,
    ): Response<CreateBusinesscard>

    @FormUrlEncoded
    @POST("add_passenger_trip")
    suspend fun AddTripPassenger(
        @Header("Authorization") authorization: String,
        @Field("tip_task") tip_task: String,
        @Field("from_trip") from_trip: String,
        @Field("to_trip") to_trip: String,
        @Field("vehicle_type") vehicle_type: String,
        @Field("vehicle_numbers") vehicle_numbers: String,
        @Field("no_tyers") no_tyers: String,
        @Field("body_type") body_type: String,
        @Field("assign_driver") assign_driver: String,
        @Field("total_distance") total_distance: String,
        @Field("freight_amount") freight_amount: String,
        @Field("pickup_lat") pickup_lat: String,
        @Field("pickup_long") pickup_long: String,
        @Field("dropup_lat") dropup_lat: String,
        @Field("dropup_long") dropup_long: String,
        @Field("vehicle_id") vehicle_id: String,
        @Field("booking_date_from") booking_date_from: String,
        @Field("booking_time") booking_time: String,
        @Field("fuel_charge") fuel_charge: String,
        @Field("toll_tax") toll_tax: String,
        @Field("driver_fee")  driver_fee: String,
    ): Response<CreateBusinesscard>

    @FormUrlEncoded
    @POST("add_passenger_vendor_trip")
    suspend fun add_passenger_vendor_trip(
        @Header("Authorization") authorization: String,
        @Field("tip_task") tip_task: String,
        @Field("load_caring") load_caring: String,
        @Field("from_trip") from_trip: String,
        @Field("to_trip") to_trip: String,
        @Field("vehicle_type") vehicle_type: String,
        @Field("vehicle_numbers") vehicle_numbers: String,
        @Field("no_tyers") no_tyers: String,
        @Field("body_type") body_type: String,
        @Field("assign_driver") assign_driver: String,
        @Field("total_distance") total_distance: String,
        @Field("freight_amount") freight_amount: String,
        @Field("pickup_lat") pickup_lat: String,
        @Field("pickup_long") pickup_long: String,
        @Field("dropup_lat") dropup_lat: String,
        @Field("dropup_long") dropup_long: String,
        @Field("vehicle_id") vehicle_id: String,
        @Field("booking_date_from") booking_date_from: String,
        @Field("booking_time") booking_time: String,
        @Field("fuel_charge") fuel_charge: String,
        @Field("toll_tax") toll_tax: String,
        @Field("driver_fee") driver_fee: String,

    ): Response<AddTripDriverMOdelClass>


    @FormUrlEncoded
    @POST("driver_forgot_pwd")
    suspend fun changePass(
//        @Header("Authorization") token: String,
//        @Field("oldPassword") password: String,
        @Field("mobile") mobile: String,
        @Field("password") new_password: String,


        ): Response<RegisterResponseModel>

    /*
    @FormUrlEncoded
    @POST("userCategory")
    suspend fun categoryClick(
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
    ): Response<CategoryRequestModel>

    @GET("about_us")
    suspend fun aboutUs(
        @Header("Authorization") authorization: String
    ): Response<AuthResponse>


    @FormUrlEncoded
    @POST("viewUserDetail")
    suspend fun getProfile(
        @Field("user_id") userId: String
    ): Response<ProfileModel>

    @FormUrlEncoded
    @POST("likePost")
    suspend fun callLikeApi(
        @Field("user_id") userId: String,
        @Field("post_id") post_id: String,
        @Field("status") status: String,
    ): Response<LikeModel>

    @FormUrlEncoded
    @POST("notificationList")
    suspend fun getNotificationList(
        @Field("user_id") userId: String
    ): Response<NotificationResponseModel>

    @FormUrlEncoded
    @POST("userDetail")
    suspend fun getProfileData(
        @Field("user_id") userId: String
    ): Response<EditProfileModel>

    @GET("termscondition")
    suspend fun termsCondition(
        @Header("Authorization") authorization: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("send_otp")
    suspend fun sendOtp(
        @Field("username") username: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("change_password")
    suspend fun changePass(
        @Header("Authorization") token: String,
        @Field("oldPassword") password: String,
        @Field("password") new_password: String,
        @Field("user_id") user_id: String,

    ): Response<ChangePasswordResponseModel>

    @FormUrlEncoded
    @POST("forgot_password")
    suspend fun forgotPass(
        @Header("Authorization") authorization: String,
        @Field("username") username: String,
        @Field("password") new_password: String
    ): Response<ChangePassModel>

    @FormUrlEncoded
    @POST("contactus")
    suspend fun contactUs(

        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("message") message: String
    ): Response<AuthResponse>

    @Multipart
    @POST("edit_profile")
    suspend fun updateProfile(
        @Part image: MultipartBody.Part,
        @Part("username") name : RequestBody,
        @Part("email") email : RequestBody,
        @Part("phoneNumber") mobile : RequestBody,
        @Part("_id") userId : RequestBody
    ):Response<EditProfileModel>






    @FormUrlEncoded
    @POST("postList")
    suspend fun homeApi(
        @Field("user_id") user_id: String
    ): Response<HomeApiResponse>


    @GET("registrationList")
    suspend fun registrationDataList(
    ): Response<RegisterationDataList>



    @FormUrlEncoded
    @POST("postDetail")
    suspend fun postDetailApi(
        @Field("post_id") post_id: String
    ): Response<PostDetailModel>


    @POST(AppConstant.CATEGORY_LIST_URL)
    suspend fun categoryListApi(
    ): Response<CategoryListModel>*/


}