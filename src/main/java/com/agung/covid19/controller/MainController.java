/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.controller;

import com.agung.covid19.api.FailedConnectionException;
import com.agung.covid19.api.callback.CountryCallback;
import com.agung.covid19.api.callback.ProvinceCallback;
import com.agung.covid19.api.callback.SummaryCallback;
import com.agung.covid19.api.service.BaseService;
import com.agung.covid19.api.service.ProvinceService;
import com.agung.covid19.model.dto.ProvinceDto;
import com.agung.covid19.pojo.Countries;
import com.agung.covid19.pojo.Country;
import com.agung.covid19.pojo.Summary;
import com.agung.covid19.util.MessageConstan;
import com.agung.covid19.util.MessageUtil;
import com.agung.covid19.view.MainFrame;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author agung
 */
@Slf4j
public class MainController {

    @Autowired
    private ViewHelper viewHelper;

    @Autowired
    private MainFrame mainFrame;

    @Autowired
    private BaseService baseService;

    @Autowired
    private ProvinceService provinceService;

    @Getter
    @Setter
    private Country country;

    public void fetchCaseByCountryCode(String countryName) {
        baseService.getAllSummary(new SummaryCallback() {
            @Override
            public void onSuccess(Summary summary) {
                viewHelper.fillGlobalView(summary.getGlobal());
                for (Country country : summary.getCountries()) {
                    if (country.getCountryCode().equals(countryName) && countryName != null) {
                        viewHelper.fillCountryView(country);
                    }
                }
            }

            @Override
            public void onError(Throwable t) throws Exception {
                t.printStackTrace();
                MessageUtil.showMessage(null, MessageConstan.CONNECTION_FAILED, MessageUtil.ERROR_MESSAGE);
            }
        });
    }

    public void fetchAllProvinceData() {
        provinceService.getAllDataProvince(new ProvinceCallback() {
            @Override
            public void onSuccess(List<ProvinceDto> provinceDtoList) {
                for (ProvinceDto province : provinceDtoList) {
                    log.debug("FID : {}", province.getProvince().getFID());
                    log.debug("Provinsi : {}", province.getProvince().getProvinceName());
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Getter
    private Map<String, String> mapCountries = new HashMap<>();

    public void loadToCombo() throws IOException {
        mapCountries.clear();
        List<Countries> countries = baseService.getAllCountries(null);
        for (Countries c : countries) {
            mapCountries.put(c.getCountry(), c.getISO2());
        }

        for (String countryName : mapCountries.keySet()) {
            mainFrame.getCountryComboBox().addItem(countryName);
            log.debug("country name {}", countryName);
        }

    }
}
