/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.controller;

import com.agung.covid19.api.service.CountriesServices;
import com.agung.covid19.api.Covid19ApiProvider;
import com.agung.covid19.api.service.GlobalApiService;
import com.agung.covid19.model.Country;
import com.agung.covid19.model.Global;
import com.agung.covid19.view.MainFrame;
import com.agung.covid19.util.TextUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author agung
 */
public class BaseController {

    public static final org.slf4j.Logger log = LoggerFactory.getLogger(BaseController.class);
    private MainFrame mainFrame; 
    
    
    public BaseController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void loadGlobalCase() {
        GlobalApiService globalApiService = Covid19ApiProvider.getInstance().createService(GlobalApiService.class);
        Call<Global> globalData = globalApiService.getGlobalData();
        globalData.enqueue(new Callback<Global>() {
            @Override
            public void onResponse(Call<Global> global, Response<Global> response) {
                showGlobalCase(response.body());
            }

            @Override
            public void onFailure(Call<Global> call, Throwable thrwbl) {
                log.error("gagal mengambil data {}", thrwbl.getMessage());
                thrwbl.printStackTrace();
            }
        }
        );
    }

    public void loadByCountry(String country) {

        CountriesServices countriesServices = Covid19ApiProvider.getInstance().createService(CountriesServices.class);
        Call<Country> countriesCall = countriesServices.getLocalData(country);
        countriesCall.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                showDataByCountries(response.body());
            }

            @Override
            public void onFailure(Call<Country> call, Throwable thrwbl) {
                log.error(thrwbl.getMessage());
                thrwbl.printStackTrace();
            }
        });
    }

    private void showDataByCountries(Country countries) {
        
        if (countries != null) {
            mainFrame.getLblConfirmed().setText(TextUtil.formatDecimal(countries.getConfirmed().getValue()));
            mainFrame.getLblDeath().setText(TextUtil.formatDecimal(countries.getDeaths().getValue()));
            mainFrame.getLblRecovered().setText(TextUtil.formatDecimal(countries.getRecovered().getValue()));
//            mainFrame.getLblLastUpdate().setText("Pembaruan Terakhir : "+TextUtil.formatDate(countries.getLastUpdate()));

        }
        log.debug("By Countrie");
        log.debug("confirmed {}", TextUtil.formatDecimal(countries.getConfirmed().getValue()));
        log.debug("Recovered {}", countries.getRecovered().getValue());
        log.debug("Deaths {}", countries.getDeaths().getValue());
    }
    
    private void showGlobalCase(Global global){
        if (global != null) {
            mainFrame.getLblGlobalConfirmed().setText(TextUtil.formatDecimal(global.getConfirmed().getValue()));
            mainFrame.getLblGlobalDeath().setText(TextUtil.formatDecimal(global.getDeaths().getValue()));
            mainFrame.getLblGlobalRecovered().setText(TextUtil.formatDecimal(global.getRecovered().getValue()));

        }
    }

}
