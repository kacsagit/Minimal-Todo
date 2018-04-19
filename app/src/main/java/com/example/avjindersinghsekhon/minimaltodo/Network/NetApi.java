package com.example.avjindersinghsekhon.minimaltodo.Network;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NetApi {


    @GET("/v0.1/receipts/{transactionCode}")
    Call<Receipt> getReceipt(@Path(value = "transactionCode")String transactionCode , @Query("mid") String mercantCode);


}
