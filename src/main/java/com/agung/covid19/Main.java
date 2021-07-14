/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19;

import com.agung.covid19.config.AppConfig;
import com.agung.covid19.view.MainFrame;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author agung
 */
public class Main {

    public static ApplicationContext context;
    private static MainFrame mainFrame;

    private static void initContext() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        mainFrame = context.getBean(MainFrame.class);
    }

    private static void initMainView() throws IOException {
        initContext();
        mainFrame.setVisible(true);
        mainFrame.initCountryCombo();
        mainFrame.initDataIndonesia();
    }

    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    initMainView();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
