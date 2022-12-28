package com.agung.covid19.api.service;

import com.agung.covid19.api.ApiProvinceProvider;
import com.agung.covid19.api.callback.ProvinceCallback;
import com.agung.covid19.model.dto.ProvinceDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ProvinceService {

    @Autowired
    private ApiProvinceProvider apiProvinceProvider;

    public List<ProvinceDto> getAllProvince() {
        ProvinceApi provinceAPi = apiProvinceProvider.createService(ProvinceApi.class);
        Call<List<ProvinceDto>> province = provinceAPi.getAllProvince();
        try {
            Response<List<ProvinceDto>> response = province.execute();
            if (response.code() == 200) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAllDataProvince(ProvinceCallback provinceCallBack) {
        ProvinceApi provinceAPi = apiProvinceProvider.createService(ProvinceApi.class);
        Call<List<ProvinceDto>> province = provinceAPi.getAllProvince();
        province.enqueue(new Callback<List<ProvinceDto>>() {
            @Override
            public void onResponse(Call<List<ProvinceDto>> call, Response<List<ProvinceDto>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                       provinceCallBack.onSuccess(response.body());
                    }
                }
            }

            @SneakyThrows
            @Override
            public void onFailure(Call<List<ProvinceDto>> call, Throwable t) {
                provinceCallBack.onError(t);
            }
        });
    }
}
