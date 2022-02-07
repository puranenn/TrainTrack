package com.example.puranenn.traintracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class ListActivity extends AppCompatActivity {
    private String start;
    private String destination;
    private EditText eText;
    private ListView listRoutes;
    private int day;
    private int month;
    private int year;
    private String a;
    private String b;
    private String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //eText = (EditText) findViewById(R.id.editText);

        listRoutes = (ListView)findViewById(R.id.listRoutes);

        Intent flagintent = getIntent();

        start = flagintent.getStringExtra("start");
        destination = flagintent.getStringExtra("destination");

        this.day = flagintent.getIntExtra("day", 0);
        this.month = flagintent.getIntExtra("month", 0);
        this.year = flagintent.getIntExtra("year", 0);
        this.month += 1;
        this.a = String.format("%02d", day);
        this.b = String.format("%02d", month);
    }

    public void onGet(View v) {
        String JSONresponse = new String();
        System.out.println(a);
        System.out.println(b);
        System.out.println(year);
        try {
            JSONresponse = new HTTPGet().execute("https://rata.digitraffic.fi/api/v1/live-trains/station/" + start + "/" + destination + "?departure_date=" + year + "-" + b + "-" + a).get();
            System.out.println(JSONresponse);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("interrupted");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("execution");
        }
        if (JSONresponse != null) {
            showResults(JSONresponse);
        }
    }

    public void showResults(String JSONData) {
        try {
            String pattern = "MM/dd/yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date test;

            TreeMap<String,String> trainTree=new TreeMap<>();
            String routeInfo;
            //JSONObject jsobj = new JSONObject(JSONData);

            JSONArray jsarr = new JSONArray(JSONData);
            LinkedHashMap<String, String> trainList = new LinkedHashMap<>();
            for (int k = 0; k < jsarr.length(); k++) {
                String trainNumber = jsarr.getJSONObject(k).getString("trainNumber");
                String trainType = jsarr.getJSONObject(k).getString("trainType");
                String departureTime = jsarr.getJSONObject(k).getJSONArray("timeTableRows").getJSONObject(0).getString("scheduledTime");
                String dest = "";
                int i = -1;
                while (!destination.equals(dest)) {
                    i++;
                    dest = jsarr.getJSONObject(k).getJSONArray("timeTableRows").getJSONObject(i).getString("stationShortCode");
                    //System.out.println(destination + dest);
                }
                String arrivalTime = jsarr.getJSONObject(k).getJSONArray("timeTableRows").getJSONObject(i).getString("scheduledTime");
                //System.out.println(departureTime + "test");
                //System.out.println(start + "moi");
                //String pelle = fromISO8601UTC(departureTime);
                String pelle = fromISO8601UTC(departureTime);
                String pelle2 = fromISO8601UTC(arrivalTime);
                routeInfo = trainType + " " + trainNumber + " " + start + " " + pelle + ", " + destination + " " + pelle2;
                trainTree.put(trainNumber,routeInfo);
                trainList.put(trainNumber, routeInfo);
                System.out.println(trainNumber);

            }

            final String[] menuValues = new String[trainList.size()];
            int j = 0;
            Map<String, String> map = new LinkedHashMap<String, String>();

            for (Map.Entry<String, String> entry : trainList.entrySet()) {
                System.out.println(trainList.entrySet());
                //System.out.println(entry.getKey());

                menuValues[j] = entry.getValue();
                j++;
            }
            ArrayList<HashMap<String,String>> items = new ArrayList<>();
            items.add(trainList);
            //ArrayAdapter<HashMap<String ,String >>ad=   new ArrayAdapter<HashMap<String, String>>(this,android.R.layout.simple_list_item_1,items);
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuValues);
            final ListView lv = findViewById(R.id.listRoutes);
            lv.setAdapter(itemsAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String menuValue = (String) lv.getItemAtPosition(i);
                    String [] splitted = menuValue.split("\\s+");
                    String routeName = String.valueOf(splitted[1]);

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("menuValues", routeName);

                    startActivity(intent);
                    finish();
                }

            });
            // String kalle  = df.format(pelle);
            // Toast.makeText(getApplicationContext(), routeInfo ,Toast.LENGTH_LONG).show();
            //eText.setText(pelle);
            //String formattedDate = sdf.format(new Date());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /*
        public static Date fromISO8601UTC(String dateStr) {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("HH:mm");
            try {
                df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }
        */
    public static String fromISO8601UTC(String dateStr) {

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat df2 = new SimpleDateFormat("HH:mm");
        try {
            Date test = df1.parse(dateStr);
            return df2.format(test);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
       /*
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("HH:mm");

        Date test = df.parse(dateStr);


        test = df.format()
        */


    }
}
