/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19;

import com.agung.covid19.api.service.BaseService;
import com.agung.covid19.controller.MainController;
import com.agung.covid19.ui.CaseBarChart;
import com.agung.covid19.ui.Covid19MainFrame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author agung
 */

@Configuration
@ComponentScan(basePackages = {"com.agung.covid19"})
public class AppConfig {
    
    @Bean
    public Covid19MainFrame mainFrame(){
        return new Covid19MainFrame();
    }
    
    @Bean
    public MainController controller(){
        return new MainController();
    }
    
    @Bean
    public BaseService baseService(){
        return new BaseService();
    }
    
}
