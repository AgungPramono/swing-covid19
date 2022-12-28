package com.agung.covid19.api.service;

import com.agung.covid19.model.Province;
import com.agung.covid19.model.dto.ProvinceDto;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ProvinceApi {

    @GET("indonesia/provinsi/")
    Call<List<ProvinceDto>> getAllProvince();

}
