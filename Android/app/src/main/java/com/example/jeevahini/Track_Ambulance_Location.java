package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Track_Ambulance_Location extends  AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    String url="",ip="";
    SharedPreferences sh;
    ArrayList<String> vhnum,driver,phone,dlid,lati,longi;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),user_home.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_ambulance_location);
        l1=findViewById(R.id.lv);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/user_view_ambulance";
        RequestQueue queue = Volley.newRequestQueue(Track_Ambulance_Location.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);
                    dlid= new ArrayList<>();
                    driver= new ArrayList<>();
                    phone= new ArrayList<>();
                    vhnum= new ArrayList<>();
//                    lati=new ArrayList<>();
//                    longi=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        dlid.add(jo.getString("L_id"));
                        driver.add(jo.getString("F_name")+(jo.getString("L_name")));
                        phone.add(jo.getString("Phone_no"));
                        vhnum.add(jo.getString("Vehicle_no"));
//                        lati.add(jo.getString(""))

                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
                    l1.setAdapter(new custom(Track_Ambulance_Location.this,driver,vhnum,phone));
                    l1.setOnItemClickListener(Track_Ambulance_Location.this);
                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Track_Ambulance_Location.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lati", LocationService.lati);
                params.put("longi", LocationService.logi);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RequestQueue queue = Volley.newRequestQueue(Track_Ambulance_Location.this);
        String url = "http://" + sh.getString("ip", "") + ":5000/send_request";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONObject json = new JSONObject(response);
                    String res = json.getString("task");
                    if (res.equalsIgnoreCase("failed")) {
                        Toast.makeText(getApplicationContext(), "Already requested", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(getApplicationContext(),user_home.class));
                        Toast.makeText(Track_Ambulance_Location.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error"+error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driver_id", dlid.get(position));
                params.put("lati", LocationService.lati);
                params.put("longi", LocationService.logi);
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}