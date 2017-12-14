package com.example.bg50xx.assignment;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bg50xx on 14/12/2017.
 */

public class CurrencyHttpClient {
    private static String BASE_URL = "https://api.fixer.io/latest?base=GBP";

    public String getCurrencyData() {
        HttpURLConnection con = null ;
        InputStream is = null;
        String urlString = "";

        try {
            // create URL for specified city and metric units (Celsius)
            urlString = BASE_URL;
            Log.d("urlString",urlString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        try {
            con = (HttpURLConnection) (new URL(urlString)).openConnection();
            con.setRequestMethod("GET");
            // con.setDoInput(true);
            // con.setDoOutput(true);
            con.connect();

            int response = con.getResponseCode();
            Log.d("test",Integer.toString(response));
            if (response == HttpURLConnection.HTTP_OK) {
                // Let's read the response
                StringBuilder buffer = new StringBuilder();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    Log.d("JSON-line",line);
                    buffer.append(line + "\r\n");
                }
                is.close();
                con.disconnect();
                Log.d("JSON",buffer.toString());
                return buffer.toString();
            }
            else {
                Log.d("HttpURLConnection","Unable to connect");
                return null;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Exception e) {}
            try { con.disconnect(); } catch(Exception e) {}
        }

        return null;
    }
}
