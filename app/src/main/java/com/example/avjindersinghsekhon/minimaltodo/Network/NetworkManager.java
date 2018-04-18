package com.example.avjindersinghsekhon.minimaltodo.Network;

import android.util.Log;


import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class NetworkManager {

    public static final String ENDPOINT_ADDRESS = "https://receipts-ng.sumup.com/";

    private static final String TAG = "NetworkManager";
    private static NetworkManager instance;


    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();

        }
        return instance;
    }

    private Retrofit retrofit;
    private NetApi netApi;


    private NetworkManager() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_ADDRESS).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        netApi = retrofit.create(NetApi.class);


    }




    public void getData(String transactionCode,String merchantCode) {
            netApi.getData(transactionCode,merchantCode).enqueue(new Callback<Receipt>() {
                @Override
                public void onResponse(Call<Receipt> call, Response<Receipt> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<Receipt> call, Throwable t) {

                }
            });

    }




    public interface ResponseListener<T> {
        void onResponse(T t);

        void onError(Exception e);
    }

}
