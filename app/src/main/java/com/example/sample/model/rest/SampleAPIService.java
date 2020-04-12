package com.example.sample.model.rest;

import com.example.sample.BuildConfig;
import com.example.sample.common.constants.DomainConstants;
import com.example.sample.model.rest.exception.RxErrorHandlingCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleAPIService {

    private SampleAPI sampleAPI;

    public SampleAPIService() {
        String url = DomainConstants.SERVER_URL;
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(url)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
        sampleAPI = restAdapter.create(SampleAPI.class);
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(); // Log Requests and Responses
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(logging);
        }

        return client.build();
    }

    public SampleAPI getApi(){
        return sampleAPI;
    }
}
