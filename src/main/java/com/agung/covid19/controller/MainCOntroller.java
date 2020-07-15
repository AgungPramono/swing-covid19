/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.controller;

import com.agung.covid19.api.service.BaseService;
import com.agung.covid19.pojo.Countries;
import com.agung.covid19.pojo.Country;
import com.agung.covid19.pojo.Summary;
import com.agung.covid19.ui.Covid19MainFrame;
import com.agung.covid19.util.TextUtil;
import java.io.IOException;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

/**
 *
 * @author agung
 */
@Slf4j
public class MainController {

    @Autowired
    private Covid19MainFrame mainFrame;

    @Autowired
    private BaseService baseService;

    @Getter
    @Setter
    private Country country;

    public void loadCaseByCountry(String countryName) throws IOException {
        Summary summary = baseService.getAllSummary().body();
        loadGlobalCase(summary.getGlobal());
        for (Country country : summary.getCountries()) {
            if (country.getCountryCode().equals(countryName)) {
                if (country != null) {
                    mainFrame.getLblConfirmed().setText(TextUtil.formatDecimal(country.getTotalConfirmed()));
                    mainFrame.getLblNewConfirmed().setText("+" + TextUtil.formatDecimal(country.getNewConfirmed()));
                    mainFrame.getLblDeath().setText(TextUtil.formatDecimal(country.getTotalDeaths()));
                    mainFrame.getLblNewDeath().setText(TextUtil.formatDecimal(country.getNewDeaths()));
                    mainFrame.getLblRecovered().setText(TextUtil.formatDecimal(country.getTotalRecovered()));
                    mainFrame.getLblNewRecovered().setText(TextUtil.formatDecimal(country.getNewRecovered()));
                    mainFrame.getLblLastUpdate().setText(TextUtil.formatDate(country.getDate()));
                    setCountry(country);
                }
                return;
            }
        }
    }

    private void loadGlobalCase(com.agung.covid19.pojo.Global global) {
        if (global != null) {
            mainFrame.getLblGlobalConfirmed().setText(TextUtil.formatDecimal(global.getTotalConfirmed()));
            mainFrame.getLblGlobalDeath().setText(TextUtil.formatDecimal(global.getTotalDeaths()));
            mainFrame.getLblGlobalRecovered().setText(TextUtil.formatDecimal(global.getTotalRecovered()));

        }
    }

    public Map<String,String> getCountries() throws IOException {
        Map<String,String> mapCountries = new HashMap<>();
        Countries[] countries = baseService.getAllCountries().body();
        for(Countries c:countries){
            mapCountries.put(c.getCountry(), c.getISO2());
        }
        
        return mapCountries;
    }

}
