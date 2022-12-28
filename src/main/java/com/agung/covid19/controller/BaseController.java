/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.controller;

import com.agung.covid19.api.ApiProvider;
import com.agung.covid19.api.service.CountriesServices;
import com.agung.covid19.api.Covid19ApiProvider;
import com.agung.covid19.api.service.GlobalApiService;
import com.agung.covid19.api.service.SummaryService;
import com.agung.covid19.model.Country;
import com.agung.covid19.model.Global;
import com.agung.covid19.pojo.Summary;
import com.agung.covid19.view.MainFrame;
import com.agung.covid19.util.TextUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author agung
 */

@Slf4j
public class BaseController {

    @Autowired
    private ViewHelper viewHelper;

    public void loadGlobalCase() {
        SummaryService globalApiService = ApiProvider.getInstance().createService(SummaryService.class);
        Call<Summary> globalData = globalApiService.getSummary();
        globalData.enqueue(new Callback<Summary>() {
            @Override
            public void onResponse(Call<Summary> global, Response<Summary> response) {
                viewHelper.fillGlobalView(response.body().getGlobal());
            }

            @Override
            public void onFailure(Call<Summary> call, Throwable thrwbl) {
                log.error("gagal mengambil data {}", thrwbl.getMessage());
                thrwbl.printStackTrace();
            }
        }
        );
    }


    public void loadByCountry(String countryCode) {

        SummaryService summaryService = ApiProvider.getInstance().createService(SummaryService.class);
        Call<Summary> countriesCall = summaryService.getSummary();
        countriesCall.enqueue(new Callback<Summary>() {
            @Override
            public void onResponse(Call<Summary> call, Response<Summary> response) {
                for (com.agung.covid19.pojo.Country country : response.body().getCountries()) {
                    if (country.getCountryCode().equals(countryCode)) {
                        viewHelper.fillCountryView(country);
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<Summary> call, Throwable thrwbl) {
                log.error(thrwbl.getMessage());
                thrwbl.printStackTrace();
            }
        });
    }
}
