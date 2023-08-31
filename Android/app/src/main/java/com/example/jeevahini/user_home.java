package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class user_home extends AppCompatActivity {
    Button b1,b2,b3,b4,b5;
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
        setContentView(R.layout.activity_user_home);
        

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        t1 = findViewById(R.id.textView50);
        try {
            t1.setText(sh.getString("name",""));
        }
        catch (Exception e)
        {
            t1.setText("");
        }



        b1=findViewById(R.id.button14);
        b2=findViewById(R.id.button15);
        //b3=findViewById(R.id.button16);
        b4=findViewById(R.id.button17);
        b5=findViewById(R.id.button18);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Track_Ambulance_Location.class);
                startActivity(i);

            }
        });
       b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),view_request_status.class);
                startActivity(i);

            }
        });
/*         b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Rate_Driver.class);
                startActivity(i);

            }
        });*/
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Send_Feedback.class);
                startActivity(i);

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);

            }
        });
    }
}