/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author agung
 */
@Data
public class Global {

    @JsonProperty("confirmed")
    private Confirmed confirmed;
    @JsonProperty("recovered")
    private Recovered recovered;
    @JsonProperty("deaths")
    private Deaths deaths;
    @JsonProperty("lastUpdate")
    private String lastUpdate;
    private String dailySummary;
    private DailyTimeSeries dailyTimeSeries;
    private CountryDetail countryDetail;

    private String source;

    private String countries;
    private String image;

}
