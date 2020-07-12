/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19;

import com.agung.covid19.controller.Covid19Controller;
import com.agung.covid19.ui.CaseBarChart;
import com.agung.covid19.ui.Covid19MainFrame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author agung
 */

@Configuration
public class AppConfig {
    
    @Bean
    public Covid19MainFrame mainFrame(){
        return new Covid19MainFrame();
    }
    
    @Bean
    public Covid19Controller controller(){
        return new Covid19Controller();
    }    
}
