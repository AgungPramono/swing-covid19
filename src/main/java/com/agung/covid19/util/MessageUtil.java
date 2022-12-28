/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.util;

import javax.swing.*;

/**
 *
 * @author agung
 */
public class MessageUtil {

    public static final String WARNING_MESSAGE = "warning_message";
    public static final String INFO_MESSAGE = "info_message";
    public static final String ERROR_MESSAGE = "error_message";

    public static void showMessage(JComponent parent, String message, String typeMessage) {
        switch (typeMessage) {
            case WARNING_MESSAGE:
                JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.WARNING_MESSAGE);
                break;
            case INFO_MESSAGE:
                JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.INFORMATION_MESSAGE);
                break;
            case ERROR_MESSAGE:
                JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.ERROR_MESSAGE);
                break;

        }
    }
}
