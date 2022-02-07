package com.example.puranenn.traintracker;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    protected EditText startEdit;
    protected EditText destinationEdit;
    private String start;
    private String destination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startEdit = startEdit = (EditText) findViewById(R.id.startEdit);
        this.destinationEdit = destinationEdit = (EditText) findViewById(R.id.destinationEdit);

    }

    public void startLocation() {
        start = startEdit.getText().toString();
    }

    public void destinationLocation() {
        destination = destinationEdit.getText().toString();
    }


    public void toList(View v) {
        startLocation();
        destinationLocation();

        Intent intent = new Intent(this, DateActivity.class);
        intent.putExtra("start",start);
        intent.putExtra("destination",destination);
        startActivity(intent);
        System.out.println(start);
    }
    /*

    public void nextActivity(View v) {

        startActivity(new Intent(MainActivity.this,DateActivity.class));

    }


    public void onGet(View v){
        String JSONresponse = new String();
        try   {
                JSONresponse = new HTTPGet().execute("https://rata.digitraffic.fi/api/v1/live-trains/station/"+start+"/"+destination+"?departure_date=2018-05-07").get();

        }
        catch (InterruptedException e) {

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(JSONresponse!=null){
            showResults(JSONresponse);

        }
    }


    public void showResults(String JSONData) {
        try {
            JSONObject jsobj = new JSONObject(JSONData);
            String order = jsobj.getJSONArray("").getJSONObject(0).getJSONObject("trainNumber").toString();
            Toast.makeText(getApplicationContext(), order, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    */

}