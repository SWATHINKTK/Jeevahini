package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

public class view_request_status extends AppCompatActivity {
    ListView l1;
    String url="",ip="";
    SharedPreferences sh;
    ArrayList<String> name,phone,status,lati,longi,req_id;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),user_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request_status);
        l1=findViewById(R.id.lv);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_req_status";
        RequestQueue queue = Volley.newRequestQueue(view_request_status.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);
                    name= new ArrayList<>();
                    status= new ArrayList<>();
                    phone= new ArrayList<>();
                    lati= new ArrayList<>();
                    longi= new ArrayList<>();
                    req_id= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        status.add(jo.getString("Request_status"));
                        name.add(jo.getString("F_name")+(jo.getString("L_name")));
                        phone.add(jo.getString("Phone_no"));
                        lati.add(jo.getString("latitude"));
                        longi.add(jo.getString("Longitude"));
                        req_id.add(jo.getString("Req_id"));
                        //Toast.makeText(view_request_status.this, "values"+name+status+phone, Toast.LENGTH_SHORT).show();
                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
                    l1.setAdapter(new custom2(view_request_status.this,phone,name,status,lati,longi,req_id));
//                    l1.setOnItemClickListener(view_request_status.this);
                } catch (Exception e) {
                    Toast.makeText(view_request_status.this, ""+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view_request_status.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));

                return params;
            }
        };
        queue.add(stringRequest);
    }
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Intent ii= new Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://www.google.com/maps?q="+lati.get(i)+","+longi.get(i)));
//        startActivity(ii);
//
//
//    }
}