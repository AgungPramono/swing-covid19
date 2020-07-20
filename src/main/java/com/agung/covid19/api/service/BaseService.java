/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api.service;

import com.agung.covid19.api.ApiProvider;
import com.agung.covid19.pojo.Countries;
import com.agung.covid19.pojo.Summary;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * @author agung
 */
@Slf4j
public class BaseService {
    
    @Autowired
    private ApiProvider apiProvider;
    
    public Response<Summary> getAllSummary() throws IOException {
        SummaryAPI summaryService = apiProvider.createService(SummaryAPI.class);
        Call<Summary> summaryCall = summaryService.getSummary();
        try {
            Response<Summary> response = summaryCall.execute();
            if(response.code() == 200){
                return response;
            }
        } catch (IOException ex) {
            log.error("Gagal mengambil data {}", ex.getMessage());
            throw new IOException(ex);
        }
        return null;
    }
    
    
    public Countries[] getAllCountries() throws IOException {
        SummaryAPI summaryService = apiProvider.createService(SummaryAPI.class);
        Call<Countries[]> summaryCall = summaryService.getCountries();
        try {
            Response<Countries[]> response = summaryCall.execute();
            if(response.code() == 200){
                return response.body();
            }
        } catch (IOException ex) {
            log.error("Gagal mengambil data {}", ex.getMessage());
            throw new IOException(ex);
        }
        return null;
    }
}
