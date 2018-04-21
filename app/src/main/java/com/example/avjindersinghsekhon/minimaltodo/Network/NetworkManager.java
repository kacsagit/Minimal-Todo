package com.example.avjindersinghsekhon.minimaltodo.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import org.greenrobot.eventbus.EventBus;

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




    public void getReceipt(Context context,String transactionCode, String merchantCode) {
        if (isOnline(context)) {
            netApi.getReceipt(transactionCode,merchantCode).enqueue(new Callback<Receipt>() {
                @Override
                public void onResponse(Call<Receipt> call, Response<Receipt> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, response.body().toString());
                        GetReceiptEvent getReceiptEvent = new GetReceiptEvent();
                        getReceiptEvent.setData(response.body());
                        EventBus.getDefault().post(getReceiptEvent);
                    }else{
                        ErrorEvent errorEvent=new ErrorEvent();
                        errorEvent.setData(response.message());
                        EventBus.getDefault().post(errorEvent);
                    }
                }

                @Override
                public void onFailure(Call<Receipt> call, Throwable t) {
                    ErrorEvent errorEvent=new ErrorEvent();
                    errorEvent.setData(t.getMessage());
                    EventBus.getDefault().post(errorEvent);
                }
            });
        } else {
            NoInternetEvent noInternetEvent = new NoInternetEvent();
            EventBus.getDefault().post(noInternetEvent);

        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return conMgr != null && (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
    }





}
