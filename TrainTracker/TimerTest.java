package com.example.puranenn.traintracker;

import android.os.Handler;

import java.util.Timer;

public class TimerTest {

    MapsActivity mp;

    public TimerTest(MapsActivity mp) {
        this.mp = mp;
    }

    // Init
    //appen crashar fö myky på tråden? eller så enligt nettin så autocrashar de pga fö taxing
    public void test () {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mp.onGet();
                handler.postDelayed(this,5000);
            }
        };
        handler.postDelayed(runnable,5000);
}

    //runnar på UIthread
    public void test1(){
        mp.runOnUiThread(new Runnable() {
            Handler handler = new Handler();
            @Override
            public void run() {
                mp.onGet();
                handler.postDelayed(this,5000);
            }
        });
    }

}

