package com.example.avjindersinghsekhon.minimaltodo.Network;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NetApi {


    @GET("/v0.1/receipts/{transactionCode}")
    Call<Receipt> getData(@Path(value = "transactionCode")String transactionCode , @Query("mid") String mercantCode);

    //https://receipts-ng.sumup.com/v0.1/receipts/TCNQ67KPMR?mid=MCMDCPRC
  //  {"transaction_data":{"transaction_code":"TCNQ67KPMR","amount":"33.0","vat_amount":"0.0","tip_amount":"0.0","fee_amount":"1.22","currency":"EUR","timestamp":"2018-04-18T20:55:32.819Z","status":"SUCCESSFUL","payment_type":"CC_CUSTOMER_ENTERED","entry_mode":"customer entry","verification_method":"none","card":{"last_4_digits":"0251","type":"VISA","cardholder_name":"Ggg hh","expiry_month":"02","expiry_year":"22"},"installments_count":1,"products":[{"name":"Payment","price":"33.0","quantity":1,"total_price":"33.0"}],"vat_rates":[],"location":{"lat":"47.4968431","lon":"19.059685","horizontal_accuracy":"20.9"},"events":[],"customer_mobile_phone":"0036203639701","receipt_no":"S20180000008"},"merchant_data":{"merchant_profile":{"merchant_code":"MCMDCPRC","business_name":"Android Candidate","email":"dev_mcmdcprc@sumup.com","address":{"address_line1":"Erlanger Strasse","city":"Berlin","country":"DE","country_native_name":"Deutschland"}},"locale":"de-DE"},"acquirer_data":{"tid":"00000001","return_code":"00"}}


}
