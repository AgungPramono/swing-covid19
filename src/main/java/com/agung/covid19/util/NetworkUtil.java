package com.agung.covid19.util;

import java.io.IOException;
import java.net.*;

public class NetworkUtil {

    public static Boolean isNetwokConnected(){
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
