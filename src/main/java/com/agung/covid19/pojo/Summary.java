/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author agung
 */

@Data
public class Summary {

    @JsonProperty("ID")
    private String id;
    @JsonProperty("Global")
    private Global global;
    @JsonProperty("Countries")
    private List<Country> Countries = new ArrayList<>();
    @JsonProperty("Date")
    private String Date;
    @JsonProperty("Message")
    private String message;
    
    
}
