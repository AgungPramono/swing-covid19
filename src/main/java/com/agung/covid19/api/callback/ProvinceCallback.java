package com.agung.covid19.api.callback;

import com.agung.covid19.model.dto.ProvinceDto;

import java.util.List;

public interface ProvinceCallback {
    void onSuccess(List<ProvinceDto> provinceDtoList);
    void onError(Throwable t);
}
