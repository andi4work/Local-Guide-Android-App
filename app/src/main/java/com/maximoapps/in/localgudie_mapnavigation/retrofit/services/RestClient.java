package com.maximoapps.in.localgudie_mapnavigation.retrofit.services;

import android.util.Base64;

import com.maximoapps.in.localgudie_mapnavigation.helpers.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${ChandraMohanReddy} on 23/12/15.
 */
public class RestClient {

    /*https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=12.9716,77.5946&radius=500&type=restaurant&keyword=food&key=AIzaSyABpsBAhnFtxKZzC4W-PjH2o7pABWywcC0*/
    //Live URL
    private static final String BASE_URL_GOOGLE_PLACES = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    String Image_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CoQBdwAAAL44ls3qKZ_woAnmyoZKHwSeWNL6lz4nqM_cFWlRaIs2WwdhKjIE_JaTj7H-L6X2oPJWASBNLR1_Q5KbmNc6-P-d9c2X2gl-EHkLVcspS0-70WsfYXyDQRbYcANdcBJa4YXyhtovfPSaG0ZLkAE3ZUNgEcOTuUsPp8iO1guB1edvEhDCzrxbvE3nK9T1YdZtJXl2GhR9d1c1FEx8Etg6Xryoj9UQkF6KLw&key=" + Constants.API_KEY;

    private ApiServices apiService;

    public RestClient() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "")
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(request);
                    }


                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_GOOGLE_PLACES)
                .client(client)

                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiService = retrofit.create(ApiServices.class);


    }


    public ApiServices getApiService() {
        return apiService;
    }


    private String getCustomAuth() {

        final String basicAuth = "Basic " + Base64.encodeToString("a:a".getBytes(), Base64.NO_WRAP);
        return basicAuth;

    }


}
