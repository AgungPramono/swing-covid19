package com.agung.covid19.controller;

import com.agung.covid19.pojo.Country;
import com.agung.covid19.util.TextUtil;
import com.agung.covid19.view.CaseBarChart;
import com.agung.covid19.view.MainFrame;
import com.agung.covid19.view.PieChart;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:appconfig.properties")
public class ViewHelper {

    @Value("$time.update")
    private String timeUpdate;

    private static final Logger log = LoggerFactory.getLogger(ViewHelper.class);

    @Getter
    @Setter
    private Country country;

    @Autowired
    private CaseBarChart caseBarChart;

    @Autowired
    private PieChart pieChart;

    @Autowired
    private MainFrame mainFrame;

    public void fillCountryView(Country country) {
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

    public void fillGlobalView(com.agung.covid19.pojo.Global global) {
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

    @Scheduled(fixedRateString = "${time.update}")
    public void realtimeUpdaterScheduler() {
        log.debug("Running Realtime Update");
//        mainFrame.initCountryCombo();
        mainFrame.runWorker(false);
    }
}
