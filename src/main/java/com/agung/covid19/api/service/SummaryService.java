/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api.service;

import com.agung.covid19.pojo.Countries;
import com.agung.covid19.pojo.Summary;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 *
 * @author agung
 */
public interface SummaryService {
    
    @GET("summary")
    Call<Summary> getSummary();
    @GET("countries")
    Call<List<Countries>> getCountries();
    
}
