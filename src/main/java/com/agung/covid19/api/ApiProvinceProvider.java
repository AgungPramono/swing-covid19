/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author agung
 */
public class ApiProvinceProvider implements ApiConstan{
    private static ApiProvinceProvider singleton;
    private Retrofit retrofit;

    public ApiProvinceProvider(){
        init();
    }
    public static synchronized ApiProvinceProvider getInstance() {
        if (singleton == null) {
            singleton = new ApiProvinceProvider();
        }

        return singleton;
    }

    private void init() {


        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_KAWAL_CORONA)
                .client(httpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public <T> T createService(Class<T> clazz) {
        if (null != retrofit) {
            return retrofit.create(clazz);
        } else {
            return null;
        }
    }
}
