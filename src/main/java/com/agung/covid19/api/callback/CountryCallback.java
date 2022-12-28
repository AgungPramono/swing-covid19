package com.agung.covid19.api.callback;

import com.agung.covid19.pojo.Countries;

public interface CountryCallback {
    void onSuccess(Countries[] countries);
    void onError(Throwable t);
}
