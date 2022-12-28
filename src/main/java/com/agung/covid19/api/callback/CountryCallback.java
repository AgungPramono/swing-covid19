package com.agung.covid19.api.callback;

import com.agung.covid19.pojo.Countries;
import com.agung.covid19.pojo.Country;

public interface CountryCallback {
    void onSuccess(Countries[] countries);
    void onError(Throwable t);
}
