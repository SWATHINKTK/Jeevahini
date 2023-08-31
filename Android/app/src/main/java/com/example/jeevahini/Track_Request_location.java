package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

public class Track_Request_location extends AppCompatActivity {
    ListView l1;
    SharedPreferences sh;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),driver_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_request_location);
        l1=findViewById(R.id.list4);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



    }
}