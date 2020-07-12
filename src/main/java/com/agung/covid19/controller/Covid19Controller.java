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
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
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
public class Covid19Controller {

    @Autowired
    private Covid19MainFrame mainFrame;

    @Getter @Setter
    private Country country;

    public void getAllSummary(String country) {
        SummaryService summaryService = Covid19ApiProvider2.getInstance().createService(SummaryService.class);
        Call<Summary> summaryCall = summaryService.getSummary();
        try {
            Response<Summary> response = summaryCall.execute();
//        summaryCall.enqueue(new Callback<Summary>() {
//            @Override
//            public void onResponse(Call<Summary> call, Response<Summary> response) {
                Summary summary = response.body();
                loadGlobalCase(summary.getGlobal());
                log.debug(summary.toString());
                for (Country c : summary.getCountries()) {
                    if (c.getCountry().equals(country)) {
                        loadCaseByCountry(c);
                        log.debug("Country {}", c.getCountry());
                        log.debug("Confirmed {}", c.getTotalConfirmed());
                        return;
                    }
                }
//            }
//
//            @Override
//            public void onFailure(Call<Summary> call, Throwable thrwbl) {
//                log.error("error {}", thrwbl.getMessage());
//                thrwbl.printStackTrace();
//            }
//        });
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Covid19Controller.class.getName()).log(Level.SEVERE, null, ex);
            log.error("Gagal mengambil data {}", ex.getMessage());
            JOptionPane.showMessageDialog(mainFrame, "Gagal Mengambil Data, Periksa Koneksi Anda");
        }
    }

    private void loadCaseByCountry(Country country) {

        if (country != null) {
            mainFrame.getLblConfirmed().setText(TextUtil.formatDecimal(country.getTotalConfirmed()));
            mainFrame.getLblNewConfirmed().setText("+"+TextUtil.formatDecimal(country.getNewConfirmed()));
            mainFrame.getLblDeath().setText(TextUtil.formatDecimal(country.getTotalDeaths()));
            mainFrame.getLblNewDeath().setText(TextUtil.formatDecimal(country.getNewDeaths()));
            mainFrame.getLblRecovered().setText(TextUtil.formatDecimal(country.getTotalRecovered()));
//            mainFrame.getLblLastUpdate().setText("Pembaruan Terakhir : " + TextUtil.formatDate(country.getDate()));
            setCountry(country);
//            mainFrame.getBarChart().setConfirm(country.getTotalConfirmed());
//            mainFrame.getBarChart().setDeaths(country.getTotalDeaths());
//            mainFrame.getBarChart().setRecovered(country.getTotalRecovered());
        }
    }

    private void loadGlobalCase(com.agung.covid19.pojo.Global global) {
        if (global != null) {
            mainFrame.getLblGlobalConfirmed().setText(TextUtil.formatDecimal(global.getTotalConfirmed()));
            mainFrame.getLblGlobalDeath().setText(TextUtil.formatDecimal(global.getTotalDeaths()));
            mainFrame.getLblGlobalRecovered().setText(TextUtil.formatDecimal(global.getTotalRecovered()));

        }
    }

}
