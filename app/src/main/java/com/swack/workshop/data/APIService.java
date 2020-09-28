package com.swack.workshop.data;


import com.swack.workshop.model.CityListModel;
import com.swack.workshop.model.DistrictModel;
import com.swack.workshop.model.ItemListModel;
import com.swack.workshop.model.OrderListModel2;
import com.swack.workshop.model.ResponseResult;
import com.swack.workshop.model.TalukaListModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService
{

    @FormUrlEncoded
    @POST("otp_verification.php")
    Call<ResponseResult> otpVerificationPassword(
            @Field("key") String key,
            @Field("gar_id") String id,
            @Field("gar_mobi") String mobile,
            @Field("new_password") String password,
            @Field("OTP") String otp);


    //The forgot password call
    @FormUrlEncoded
    @POST("ForgotPass.php")
    Call<ResponseResult> forgotPassword(
            @Field("key") String key,
            @Field("gar_mobi") String mobile_no);

    //The login call
    @FormUrlEncoded
    @POST("Login.php")
    Call<ResponseResult> callLoginApi(
            @Field("key") String key,
            @Field("fcmcode") String fcmcode,
            @Field("gar_mobi") String gar_mobi,
            @Field("gar_password") String gar_password);

    @FormUrlEncoded
    @POST("mobileVerifyG.php")
    Call<ResponseResult> mobileVerification(
            @Field("key") String key,
            @Field("gar_mobi") String gar_mobi);

    //check version code
    @FormUrlEncoded
    @POST("SelectItem.php")
    Call<ItemListModel> getItem(
            @Field("key") String key);


    @FormUrlEncoded
    @POST("confirmorder.php")
    Call<ResponseResult> confirmOrder(
            @Field("key") String key,
            @Field("cus_id") String cus_id,
            @Field("order_id") String ordrequniq_id,
            @Field("gar_id") String gar_id
 );

    //check version code
    @FormUrlEncoded
    @POST("orderlistcom.php")
    Call<OrderListModel2> finaList(
            @Field("key") String key,
            @Field("garage_id") String job_ord_id,
            @Field("from_lat") String from_lat,
            @Field("from_long") String from_long
    );
    //check version code
    @FormUrlEncoded
    @POST("showjobIemDetails.php")
    Call<ResponseResult> showJobItemDetails(
            @Field("key") String key,
            @Field("job_ord_id") String job_ord_id
    );


 //check version code
    @FormUrlEncoded
    @POST("dashoOrd.php")
    Call<ResponseResult> dashboard(
            @Field("key") String key   );

    //check version code
    @FormUrlEncoded
    @POST("jobIemDetails.php")
    Call<OrderListModel2> jobitemsubmit(
            @Field("key") String key,
            @Field("servicepart_id") String servicepart_id,
            @Field("job_estimate") String job_estimate,
            @Field("job_finalaount") String job_finalaount,
            @Field("job_ord_id") String job_ord_id
    );

    //check version code
    @FormUrlEncoded
    @POST("dashoOrd.php")
    Call<ItemListModel> itemList(
            @Field("key") String key   );


    //check version code
    @FormUrlEncoded
    @POST("orderlist.php")
    Call<OrderListModel2> orderList(
            @Field("key") String key,
            @Field("garage_id") String garage_id,
             @Field("from_lat") String from_lat,
            @Field("from_long") String from_long
    );

    @FormUrlEncoded
    @POST("newRequestOrder.php")
    Call<OrderListModel2> newRequestOrder(
            @Field("key") String key,
            @Field("garage_id") String garage_id,
            @Field("from_lat") String from_lat,
            @Field("from_long") String from_long
    );

    @FormUrlEncoded
    @POST("support.php")
    Call<ResponseResult> getSupport(
            @Field("key") String key);


    @FormUrlEncoded
    @POST("TransportListDetails.php")
    Call<ResponseResult> transportList(
            @Field("key") String key,
            @Field("from_lat") String from_lat,
            @Field("from_long") String from_long
    );

    @FormUrlEncoded
    @POST("show_slider.php")
    Call<ResponseResult> callSlider(
            @Field("key") String key);

    @FormUrlEncoded
    @POST("get_work_city_list")
    Call<ResponseResult> callCityList(
            @Field("key") String key,
            @Field("language_id") String language_id
    );

    @FormUrlEncoded
    @POST("get_category_wise_product")
    Call<ResponseResult> callProductWList(
            @Field("key") String key,
            @Field("category_id") String category_id,
            @Field("language_id") String language_id
    );

    @FormUrlEncoded
    @POST("get_language_list")
    Call<ResponseResult> getLanguageList(
            @Field("key") String key
    );

 @FormUrlEncoded
    @POST("order_list")
    Call<ResponseResult> getOrderList(
         @Field("key") String key,
         @Field("user_id") String user_id
 );

    //category list
 @FormUrlEncoded
    @POST("category_list")
    Call<ResponseResult> callCategories(
         @Field("key") String key,
         @Field("language_id") String language_id);



 //sub category list
 @FormUrlEncoded
    @POST("get_product_categorywise")
    Call<ResponseResult> callProductList(
         @Field("key") String key,
         @Field("catergory_id") String catergory_id
 );

 //sub category list
 @FormUrlEncoded
    @POST("version_update.php")
    Call<ResponseResult> card_Versioncode(
         @Field("key") String key,
         @Field("version_code") String version_code
 );

   @FormUrlEncoded
   @POST("RegGarage.php")
   Call<ResponseResult> callRegister(
           @Field("key") String key,
           @Field("gar_name") String gar_name,
           @Field("gar_email") String gar_email,
           @Field("gar_mobi") String gar_mobi,
           @Field("gar_password") String gar_password,
           @Field("gar_address") String cus_address,
           @Field("gar_lat") String gar_lat,
           @Field("gar_long") String gar_long,
           @Field("gar_district") String gar_district,
           @Field("gar_taluka") String gar_taluka,
           @Field("gar_state") String gar_state
   );

   //The filters_fragment call


    //The OTP verification for new filters_fragment call
    @FormUrlEncoded
    @POST("otp_varification")
    Call<ResponseResult> callOtpVerificationRegister(
            @Field("key") String key,
            @Field("mobile_no") String mobile_no,
            @Field("password") String password,
            @Field("otp") String otp);

    //The OTP verification for new password call
    @FormUrlEncoded
    @POST("forgot_otp_varification")
    Call<ResponseResult> callOtpVerification(
            @Field("key") String key,
            @Field("mobile_no") String mobile_no,
            @Field("password") String password,
            @Field("otp") String otp);

    //The OTP resend call
    @FormUrlEncoded
    @POST("resend_otp")
    Call<ResponseResult> callResendOTP(
            @Field("key") String key,
            @Field("mobile_no") String mobile_no);

    //The forgot password call
    @FormUrlEncoded
    @POST("forgot_change_password")
    Call<ResponseResult> callForgotPassword(
            @Field("key") String key,
            @Field("reg_id") String reg_id,
            @Field("new_password") String new_password
    );

    //The change password call
    @FormUrlEncoded
    @POST("ChangePass.php")
    Call<ResponseResult> callChangePassword(
            @Field("key") String key,
            @Field("gar_id") String cus_id,
            @Field("gar_password") String cus_password);

    //
    //working city list
    @FormUrlEncoded
    @POST("get_work_city_list")
    Call<ResponseResult> callWorkingCityList(
            @Field("key") String key,
            @Field("language_id") String language_id);



    //The city locality call
    @FormUrlEncoded
    @POST("get_locality")
    Call<ResponseResult> callLocalityList(
            @Field("key") String key,
            @Field("work_city_id") String workspaceCityId,
            @Field("language_id") String language_id
    );

    //The category call
    @FormUrlEncoded
    @POST("get_category")
    Call<ResponseResult> callCategoryTab(
            @Field("key") String key);

    //The feedback call
    @FormUrlEncoded
    @POST("customer_feedback")
    Call<ResponseResult> callSendFeedback(
            @Field("key") String key,
            @Field("rating") String strRating,
            @Field("suggesstion") String strFeedBack,
            @Field("login_id") String id);


    //The workspace call
    @FormUrlEncoded
    @POST("get_workspace_list")
    Call<ResponseResult> callWorkspaceList(
            @Field("key") String key,
            @Field("work_city_id") String intCityId,
            @Field("locality_id") String intLocalityId,
            @Field("category_id") String intCategoryId,
            @Field("page") String pages);

    //The user booking history call
    @FormUrlEncoded
    @POST("get_booking_history_by_id")
    Call<ResponseResult> callBookingHistoryList(
            @Field("key") String key,
            @Field("login_id") String userId);

    //The user book now  call
    @FormUrlEncoded
    @POST("add_booking")
    Call<ResponseResult> callSubmitBooking(
            @Field("key") String key,
            @Field("work_space_id") String work_space_id,
            @Field("work_space_name") String work_space_name,
            @Field("customer_id") String customer_id,
            @Field("customer_name") String customer_name,
            @Field("customer_email_id") String customer_email_id,
            @Field("customer_mobile_no") String customer_mobile_no,
            @Field("desk_id") String desk_id,
            @Field("desk_qty") String desk_qty,
            @Field("book_start_time") String book_start_time,
            @Field("book_end_time") String book_end_time,
            @Field("book_hours") String book_hours,
            @Field("book_day") String book_day,
            @Field("book_month") String book_month,
            @Field("book_three_month") String book_three_month,
            @Field("book_six_month") String book_six_month,
            @Field("book_year") String book_year,
            @Field("booking_type") String booking_type,
            @Field("booking_date") String booking_date,
            @Field("total_amount") String total_amount,
            @Field("gst_percentage") String gst_percentage,
            @Field("gst_amount") String gst_amount,
            @Field("owner_email") String owner_email,
            @Field("owner_mobile_no") String owner_mobile_no,
            @Field("owner_name") String owner_name,
            @Field("final_total_amount") String final_total_amount,
            @Field("booking_status") String booking_status);

    //The all State call
    @FormUrlEncoded
    @POST("state.php")
    Call<DistrictModel> getState(@Field("key") String key);

    //The city call
    @FormUrlEncoded
    @POST("city.php")
    Call<CityListModel> getCity(@Field("key") String key,
                                @Field("state_id") String state_id);

    //The taluka call
    @FormUrlEncoded
    @POST("taluka.php")
    Call<TalukaListModel> getTaluka(@Field("key") String key,
                                    @Field("city_id") String city_id);
    //The get booking by id call
    @FormUrlEncoded
    @POST("get_booking_by_id")
    Call<ResponseResult> callLastBookingHistory(
            @Field("key") String key,
            @Field("work_space_id") String work_space_id,
            @Field("current_date") String current_date);

    @FormUrlEncoded
    @POST("add_order")
    Call<ResponseResult>callOrderPlace(
            @Field("key") String key,
            @Field("user_id") String user_id,
            @Field("total_qty") String total_qty,
            @Field("final_total_amount") String final_total_amount,
            @Field("delivery_address") String delivery_address,
            @Field("product_list") String product_list
    );

    //The Submit Order call
    @Multipart
    @POST("submitOrder.php")
    Call<ResponseResult> callSubmitBill(
            @Part("key") RequestBody key,
            @Part("garage_id") RequestBody garage_id,
            @Part("cus_id") RequestBody customer_id,
            @Part("ordreq_id") RequestBody order_id,
            @Part MultipartBody.Part file);

    //The update profile call
    @Multipart
    @POST("Profile_upload.php")
    Call<ResponseResult> updateProfile(
            @Part("key") RequestBody key,
            @Part("gar_id") RequestBody gar_id,
            @Part("gar_name") RequestBody gar_name,
            @Part("gar_email") RequestBody gar_email,
            @Part("gar_mobi") RequestBody gar_mobi,
            @Part("gar_address") RequestBody gar_address,
            @Part("ProfilePhoto") RequestBody profilePhoto,
            @Part("AadharPhoto") RequestBody aadharPhoto,
            @Part("PanPhoto") RequestBody panPhoto,
            @Part("ShopactPhoto") RequestBody shopactPhoto,
            @Part MultipartBody.Part profile_file,
            @Part MultipartBody.Part aadhar_file,
            @Part MultipartBody.Part pan_file,
            @Part MultipartBody.Part logo_file);
}
