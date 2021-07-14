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
import com.agung.covid19.util.NetworkUtil;
import com.agung.covid19.util.TextUtil;
import com.agung.covid19.view.CaseBarChart;
import com.agung.covid19.view.MainFrame;
import com.agung.covid19.view.PieChart;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author agung
 */
@Slf4j
public class MainController {

    @Autowired
    private MainFrame mainFrame;

    @Autowired
    private BaseService baseService;

    @Getter
    @Setter
    private Country country;

    @Autowired
    private CaseBarChart caseBarChart;

    @Autowired
    private PieChart pieChart;

    public void fetchCaseByCountryCode(String countryName) throws IOException {
        Summary summary = baseService.getAllSummary().body();
        fillGlobalView(summary.getGlobal());
        for (Country country : summary.getCountries()) {
            if (country.getCountryCode().equals(countryName)) {
                fillCountryView(country);
                return;
            }
        }
    }

    private void fillCountryView(Country country) {
        if (country != null) {
            mainFrame.getLblConfirmed().setText(TextUtil.formatDecimal(country.getTotalConfirmed()));
            mainFrame.getLblNewConfirmed().setText("+" + TextUtil.formatDecimal(country.getNewConfirmed()));
            mainFrame.getLblDeath().setText(TextUtil.formatDecimal(country.getTotalDeaths()));
            mainFrame.getLblNewDeath().setText(TextUtil.formatDecimal(country.getNewDeaths()));
            mainFrame.getLblRecovered().setText(TextUtil.formatDecimal(country.getTotalRecovered()));
            mainFrame.getLblNewRecovered().setText(TextUtil.formatDecimal(country.getNewRecovered()));
            mainFrame.getLblLastUpdate().setText(TextUtil.formatDate(country.getDate()));
            setCountry(country);
            fillChart(country);
        }
    }

    private void fillGlobalView(com.agung.covid19.pojo.Global global) {
        if (global != null) {
            mainFrame.getLblGlobalConfirmed().setText(TextUtil.formatDecimal(global.getTotalConfirmed()));
            mainFrame.getLblGlobalDeath().setText(TextUtil.formatDecimal(global.getTotalDeaths()));
            mainFrame.getLblGlobalRecovered().setText(TextUtil.formatDecimal(global.getTotalRecovered()));

        }
    }

    private void fillChart(Country country) {
        if (country != null) {
            caseBarChart.setConfirm(country.getTotalConfirmed());
            caseBarChart.setDeaths(country.getTotalDeaths());
            caseBarChart.setRecovered(country.getTotalRecovered());
            mainFrame.getBarchartPanel().removeAll();
            mainFrame.getBarchartPanel().revalidate();
            mainFrame.getBarchartPanel().add(caseBarChart.generateChart());
            mainFrame.getBarchartPanel().updateUI();
            mainFrame.getBarchartPanel().repaint();

            pieChart.setConfirm(country.getTotalConfirmed());
            pieChart.setDeaths(country.getTotalDeaths());
            pieChart.setRecovered(country.getTotalRecovered());
            mainFrame.getPieChartPanel().removeAll();
            mainFrame.getPieChartPanel().revalidate();
            mainFrame.getPieChartPanel().add(pieChart.generChartPanel());
            mainFrame.getPieChartPanel().updateUI();
            mainFrame.getPieChartPanel().repaint();
        }

    }

    @Getter
    private Map<String, String> mapCountries = new HashMap<>();

    public void loadToCombo() throws IOException {
        mapCountries.clear();
        Countries[] countries = baseService.getAllCountries();
        for (Countries c : countries) {
            mapCountries.put(c.getCountry(), c.getISO2());
        }

        for (String countryName : mapCountries.keySet()) {
            mainFrame.getCountryComboBox().addItem(countryName);
        }
    }

    @Scheduled(fixedDelay = 1000*60)
    public void checkConnectionRealtime() {
        log.debug("Running Realtime Update");
//        if (NetworkUtil.isNetwokConnected()){
        //                loadToCombo();
        mainFrame.runWorker(false);
        //        }
    }
}
