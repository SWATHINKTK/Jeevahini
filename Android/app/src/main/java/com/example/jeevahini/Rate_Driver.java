package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;

public class Rate_Driver extends AppCompatActivity {
    Spinner s1;
    RatingBar r1;
    Button b1;
    SharedPreferences sh;
    String rate;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),driver_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_driver);
        s1=findViewById(R.id.spinner2);
        r1=findViewById(R.id.ratingBar);
        b1=findViewById(R.id.button8);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate=String.valueOf(r1.getRating());


            }
        });
    }
}