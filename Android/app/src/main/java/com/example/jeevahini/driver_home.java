package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class driver_home extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6;
    SharedPreferences sh;

    TextView t1;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),Login.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        b1=findViewById(R.id.button2);
        b2=findViewById(R.id.button3);
        b3=findViewById(R.id.button21);
        b4=findViewById(R.id.button5);
        b5=findViewById(R.id.button6);
        b6=findViewById(R.id.button13);

        t1 = findViewById(R.id.textView48);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            t1.setText(sh.getString("name",""));
        }
        catch (Exception e)
        {
            t1.setText("");
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Update_status.class);
                startActivity(i);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),View_Emergency_Request_and_Accept.class);
                startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Payment_status.class);
                startActivity(i);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Report_user.class);
                startActivity(i);

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Notify_Nearest_Hospital.class);
                startActivity(i);

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);

            }
        });
    }

}