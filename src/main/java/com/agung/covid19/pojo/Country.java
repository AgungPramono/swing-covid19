/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author agung
 */
@Data
public class Country {

    @JsonProperty("Country")
    private String Country;
    @JsonProperty("CountryCode")
    private String CountryCode;
    @JsonProperty("Slug")
    private String Slug;
    @JsonProperty("NewConfirmed")
    private Long NewConfirmed;
    @JsonProperty("TotalConfirmed")
    private Long TotalConfirmed;
    @JsonProperty("NewDeaths")
    private Long NewDeaths;
    @JsonProperty("TotalDeaths")
    private Long TotalDeaths;
    @JsonProperty("NewRecovered")
    private Long NewRecovered;
    @JsonProperty("TotalRecovered")
    private Long TotalRecovered;
    @JsonProperty("Date")
    private String Date;
    @JsonIgnore
    @JsonProperty("Premium")
    private Premium premium;
}
