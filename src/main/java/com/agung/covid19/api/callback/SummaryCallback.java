package com.agung.covid19.api.callback;

import com.agung.covid19.pojo.Summary;

public interface SummaryCallback {
    void onSuccess(Summary summary);
    void onError(Throwable t) throws Exception;
}
