package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;

public class Accident_level extends AppCompatActivity {
    SharedPreferences sh;
    TextView t1,t2;
    EditText e1;
    Button b1;
    RadioButton r1,r2,r3;
    RadioGroup rg;
    String level,h_id,d_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_level);
        r1=findViewById(R.id.radioButton100);
        r2=findViewById(R.id.radioButton102);
        r3=findViewById(R.id.radioButton104);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=findViewById(R.id.button200);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r1.isChecked())
                {
                    level = r1.getText().toString();
                }
                else if (r2.isChecked())
                {
                    level = r2.getText().toString();
                }
                else if (r3.isChecked())
                {
                    level = r3.getText().toString();
                }
                RequestQueue queue = Volley.newRequestQueue(Accident_level.this);
                String url = "http://" + sh.getString("ip", "") + ":5000/accident_level";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
//                            Toast.makeText(Login.this, response, Toast.LENGTH_LONG).show();
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("valid")) {
                                Toast.makeText(Accident_level.this, " SUCCESSFULLY SEND...", Toast.LENGTH_SHORT).show();
                                Intent ru=new Intent(getApplicationContext(), driver_home.class);
                                startActivity(ru);
                            }
                            else
                            {
                                Toast.makeText(Accident_level.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("hospital",getIntent().getStringExtra("hid") );
                        params.put("driver", sh.getString("lid",""));
                        params.put("level", level);



                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });


    }
}