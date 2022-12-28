/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api.service;

import com.agung.covid19.api.ApiProvider;
import com.agung.covid19.api.callback.CountryCallback;
import com.agung.covid19.api.callback.SummaryCallback;
import com.agung.covid19.pojo.Countries;
import com.agung.covid19.pojo.Summary;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author agung
 */
@Slf4j
public class BaseService {

    @Autowired
    private ApiProvider apiProvider;

    public void getAllSummary(SummaryCallback summaryCallback) {
        SummaryService summaryService = apiProvider.createService(SummaryService.class);
        Call<Summary> summaryCall = summaryService.getSummary();
        summaryCall.enqueue(new Callback<Summary>() {
            @Override
            public void onResponse(Call<Summary> call, Response<Summary> response) {
                if (response.code() == 200) {
                    summaryCallback.onSuccess(response.body());
                }
            }

            @SneakyThrows
            @Override
            public void onFailure(Call<Summary> call, Throwable t) {
                summaryCallback.onError(t);
            }
        });
//        try {
//            Response<Summary> response = summaryCall.execute();
//            if(response.code() == 200){
//                return response;
//            }
//        } catch (IOException ex) {
//            log.error("Gagal mengambil data {}", ex.getMessage());
//            throw new IOException(ex);
//        }
//        return null;
    }


    public List<Countries> getAllCountries(CountryCallback countryCallback) throws IOException {
        SummaryService summaryService = apiProvider.createService(SummaryService.class);
        Call<List<Countries>> countryCall = summaryService.getCountries();
        try {
            Response<List<Countries>> response = countryCall.execute();
            if (response.code() == 200) {
                return response.body();
            }
        } catch (IOException ex) {
            log.error("Gagal mengambil data {}", ex.getMessage());
            throw new IOException(ex);
        }
        return new ArrayList<>();
    }
}
