/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api.service;

import com.agung.covid19.model.Global;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 *
 * @author agung
 */
public interface GlobalApiService {
    @GET("api")
    Call<Global> getGlobalData();
    
}