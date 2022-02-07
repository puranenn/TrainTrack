package com.example.puranenn.traintracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;

public class DateActivity extends AppCompatActivity {
    protected String start;
    protected String destination;
    protected DatePicker datePicker;
    protected int day;
    protected int month;
    protected int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        datePicker= findViewById(R.id.datePicker);

        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        String destination = intent.getStringExtra("destination");
        this.start=start;
        this.destination=destination;
        System.out.println(start);
    }

    public void nextActivity(View v) {
        startActivity(new Intent(DateActivity.this,ListActivity.class));
    }
    public void getDate(){
        this.day = datePicker.getDayOfMonth();
        this.month =datePicker.getMonth();
        this.year=datePicker.getYear();
    }

    public void toList(View v) {
        getDate();
        System.out.println(start);
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        intent.putExtra("start",start);
        intent.putExtra("destination",destination);

        intent.putExtra("day",day);
        intent.putExtra("month",month);
        intent.putExtra("year",year);

        startActivity(intent);
        finish();
    }

}
