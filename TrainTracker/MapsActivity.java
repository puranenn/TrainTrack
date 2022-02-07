package com.example.puranenn.traintracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String menuValues;
    private TextView routeName;
    private double lon;
    private double lat;
    private Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button button3 = findViewById(R.id.button3);
        /*
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BackgroundActivity ba = new BackgroundActivity();
                ba.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });*/
        routeName = findViewById(R.id.textView2);
        Intent flagIntent = getIntent();
        menuValues = flagIntent.getStringExtra("menuValues");

        routeName.setText(menuValues);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
    }

    public void onGett(View v) {
        new TimerTest(this).test1();
        mMap.clear();
        LatLng train = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(train).title("Marker at train"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(train));
        //new BackgroundActivity(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void onGet() {

        mMap.clear();
        LatLng train = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(train).title("Marker at train"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(train));

        String JSONresponse = new String();
        System.out.println("in i onget");
        try {
            System.out.println("före try");
            JSONresponse = new HTTPGet().execute("https://rata.digitraffic.fi/api/v1/train-locations/latest/" + routeName.getText()).get();
            System.out.println("efter try");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("execution");
        }

        if (JSONresponse != null) {
            System.out.println("toimar");
            showResults(JSONresponse);
            /*
            LatLng train = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(train).title("Marker at train"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(train));

        */}
    }


    private void showResults(String JSONData) {
        try {
            String pattern = "MM/dd/yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date test;

            TreeMap<String, String> trainTree = new TreeMap<>();
            String routeInfo;
            //JSONObject jsobj = new JSONObject(JSONData);

            JSONArray jsarr = new JSONArray(JSONData);

            for (int i = 0; i < jsarr.length(); i++) {
                JSONObject json = jsarr.getJSONObject(i);
                JSONObject location = json.getJSONObject("location");
                JSONArray coordinates = location.getJSONArray("coordinates");
                System.out.println("coordinates " + coordinates);
                lon = coordinates.getDouble(0);
                lat = coordinates.getDouble(1);
            }
            System.out.println(lon);
            System.out.println(lat);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
/*
    public class AsyncTaskTest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String JSONresponse = new String();
            System.out.println("in i onget");
            try {
                System.out.println("före try");
                JSONresponse = new HTTPGet().execute("https://rata.digitraffic.fi/api/v1/train-locations/latest/" + routeName.getText()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("interrupted");
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println("execution");
            }
            if (JSONresponse==null){
                System.out.println(JSONresponse);
            }

            if (JSONresponse != null) {
                System.out.println("toimar");
                showResults(JSONresponse);
            }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




    return null;
        }
    }
    */
}

