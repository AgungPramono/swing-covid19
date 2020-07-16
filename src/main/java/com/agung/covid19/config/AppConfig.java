/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.config;

import com.agung.covid19.api.service.BaseService;
import com.agung.covid19.controller.MainController;
import com.agung.covid19.view.MainFrame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author agung
 */

@Configuration
@ComponentScan(basePackages = {"com.agung.covid19"})
public class AppConfig {
    
    
    @Bean
    public MainFrame mainFrame(){
        return new MainFrame();
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
