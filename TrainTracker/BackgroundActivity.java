package com.example.puranenn.traintracker;

import android.os.AsyncTask;
import android.view.View;

import java.util.Map;

//används int
public class BackgroundActivity extends AsyncTask<Void, Void, Void> {

    MapsActivity mp;

    public BackgroundActivity(MapsActivity mp) {
        this.mp =  mp;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mp.onGet();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
