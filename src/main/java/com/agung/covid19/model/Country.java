/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.model;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author agung
 */
@Data
public class Country {
    
    private Confirmed confirmed;
    private Recovered recovered;
    private Deaths deaths;
    private String lastUpdate;
    
}
