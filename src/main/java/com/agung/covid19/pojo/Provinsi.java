/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.pojo;

import lombok.Data;

/**
 *
 * @author agung
 */
@Data
public class Provinsi {
    private Attributes attributes;
}

@Data
class Attributes {

    private Long FID;
    private Long Kode_Provi;
    private String Provinsi;
    private Long Kasus_Posi;
    private Long Kasus_Semb;
    private Long Kasus_Meni;
}
