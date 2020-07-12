/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19;

import com.agung.covid19.ui.Covid19MainFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 *
 * @author agung
 */
public class Main {
    
    public static ApplicationContext context;
    private static Covid19MainFrame mainFrame;
    
    private static void initContext(){
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        mainFrame = context.getBean(Covid19MainFrame.class);
    }
    
    private static void initMainView(){
         mainFrame.initData();
         mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        initContext();
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              initMainView();
            }
        });
    }
    
}
