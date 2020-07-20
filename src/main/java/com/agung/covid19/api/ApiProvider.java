/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 *
 * @author agung
 */
public class ApiProvider implements ApiConstan{
    private static ApiProvider singleton;
    private Retrofit retrofit;

    public ApiProvider(){
        init();
    }
    public static synchronized ApiProvider getInstance() {
        if (singleton == null) {
            singleton = new ApiProvider();
        }

        return singleton;
    }

    private void init() {


        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_COVID19_API)
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
