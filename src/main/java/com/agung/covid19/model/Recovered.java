/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author agung
 */
public class Recovered {

    @JsonProperty("value")
    private Long value;
    @JsonProperty("detail")
    private String detail;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    
}
