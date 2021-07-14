/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.config;

import com.agung.covid19.api.ApiProvider;
import com.agung.covid19.api.service.BaseService;
import com.agung.covid19.controller.MainController;
import com.agung.covid19.view.MainFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author agung
 */

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.agung.covid19"})
@PropertySource("classpath:appconfig.properties")
@EnableScheduling
public class AppConfig {
    
    @Value("${client.id}")
    private String clientId;
    
    @Bean
    public ApiProvider apiProvider(){
        return new ApiProvider();
    }
    
    @Bean
    public MainFrame mainFrame(){
        log.debug("client id {}",clientId);
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
