package com.example.puranenn.traintracker;

/**
 * Created by puranenn on 24.4.2019.
 */

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPGet extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String response = new String();
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String nextLine = new String();
            while ((nextLine = reader.readLine()) != null) {
                response += nextLine;
            }
            return response;
        }
        catch(Exception e) {
            return null;
        }
    }
}