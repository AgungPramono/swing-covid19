/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api.service;

import com.agung.covid19.model.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *
 * @author agung
 */
public interface CountriesServices {
    
    @GET("api/countries/{countries}")
    Call<Country> getLocalData(@Path("countries") String countries);
}
