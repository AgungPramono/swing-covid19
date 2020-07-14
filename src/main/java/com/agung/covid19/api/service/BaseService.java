/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.api.service;

import com.agung.covid19.api.Covid19ApiProvider2;
import com.agung.covid19.controller.MainController;
import com.agung.covid19.pojo.Countries;
import com.agung.covid19.pojo.Country;
import com.agung.covid19.pojo.Summary;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * @author agung
 */
@Slf4j
public class BaseService {
    
    public Response<Summary> getAllSummary() throws IOException {
        SummaryAPI summaryService = Covid19ApiProvider2.getInstance().createService(SummaryAPI.class);
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
    
    
    public Response<Countries[]> getAllCountries() throws IOException {
        SummaryAPI summaryService = Covid19ApiProvider2.getInstance().createService(SummaryAPI.class);
        Call<Countries[]> summaryCall = summaryService.getCountries();
        try {
            Response<Countries[]> response = summaryCall.execute();
            if(response.code() == 200){
                return response;
            }
        } catch (IOException ex) {
            log.error("Gagal mengambil data {}", ex.getMessage());
            throw new IOException(ex);
        }
        return null;
    }
}
