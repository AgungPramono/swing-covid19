/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author agung
 */
@Data
public class Confirmed{
    @JsonProperty("value")
    private Long value;
    @JsonProperty("detail")
    private String detail;
}
