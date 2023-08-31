package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class Update_status extends AppCompatActivity {
    RadioButton r1,r2;
    Button b1;
    SharedPreferences sh;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),driver_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        r1=findViewById(R.id.radioButton2);
        r2=findViewById(R.id.radioButton3);
        b1=findViewById(R.id.button7);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status;

                if (r1.isChecked()){
                    status = r1.getText().toString();
                }
                else {
                    status = r2.getText().toString();
                }

                RequestQueue queue = Volley.newRequestQueue(Update_status.this);
                String url = "http://" + sh.getString("ip", "") + ":5000/update_status";

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
                                Toast.makeText(Update_status.this, " STATUS UPDATED...", Toast.LENGTH_SHORT).show();
                                Intent ru=new Intent(getApplicationContext(), driver_home.class);
                                startActivity(ru);
                            }
                            else
                            {
                                Toast.makeText(Update_status.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(Update_status.this, "==="+e, Toast.LENGTH_SHORT).show();

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
                        params.put("driver", sh.getString("lid",""));
                        params.put("Status", status);


                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });
    }
}