/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.controller;

import com.agung.covid19.api.Covid19ApiProvider2;
import com.agung.covid19.api.service.SummaryService;
import static com.agung.covid19.controller.BaseController.log;
import com.agung.covid19.model.Global;
import com.agung.covid19.pojo.Country;
import com.agung.covid19.pojo.Summary;
import com.agung.covid19.ui.Covid19MainFrame;
import com.agung.covid19.util.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author agung
 */
public class Covid19Controller {

    private static final Logger log = LoggerFactory.getLogger(Covid19ApiProvider2.class);
    private Covid19MainFrame mainFrame;

    public Covid19Controller(Covid19MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void getAllSummary(String country) {
        SummaryService summaryService = Covid19ApiProvider2.getInstance().createService(SummaryService.class);
        Call<Summary> summaryCall = summaryService.getSummary();
        summaryCall.enqueue(new Callback<Summary>() {
            @Override
            public void onResponse(Call<Summary> call, Response<Summary> response) {
                Summary summary = response.body();
                showGlobalCase(summary.getGlobal());
                log.debug(summary.toString());
                for (Country c : summary.getCountries()) {
                    if (c.getCountry().equals(country)) {
                        showDataByCountries(c);
                        log.debug("Country {}", c.getCountry());
                        log.debug("Confirmed {}", c.getTotalConfirmed());
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<Summary> call, Throwable thrwbl) {
                log.error("error {}", thrwbl.getMessage());
                thrwbl.printStackTrace();
            }
        });
    }

    private void showDataByCountries(Country country) {

        if (country != null) {
            mainFrame.getLblConfirmed().setText(TextUtil.formatDecimal(country.getTotalConfirmed()));
            mainFrame.getLblDeath().setText(TextUtil.formatDecimal(country.getTotalDeaths()));
            mainFrame.getLblRecovered().setText(TextUtil.formatDecimal(country.getTotalRecovered()));
            mainFrame.getLblLastUpdate().setText("Pembaruan Terakhir : " + TextUtil.formatDate(country.getDate()));

        }
    }

    private void showGlobalCase(com.agung.covid19.pojo.Global global) {
        if (global != null) {
            mainFrame.getLblGlobalConfirmed().setText(TextUtil.formatDecimal(global.getTotalConfirmed()));
            mainFrame.getLblGlobalDeath().setText(TextUtil.formatDecimal(global.getTotalDeaths()));
            mainFrame.getLblGlobalRecovered().setText(TextUtil.formatDecimal(global.getTotalRecovered()));

        }
    }

}
