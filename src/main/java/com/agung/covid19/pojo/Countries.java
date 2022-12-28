/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author agung
 */
@Data
public class Countries {

    @JsonProperty("Country")
    private String country;
    @JsonProperty("Slug")
    private String slug;
    @JsonProperty("ISO2")
    private String ISO2;

//    @Data
//    public static class Detail {
//
//    }
}
