package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notify_Nearest_Hospital extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String url="",ip="";
    ArrayList<String> name,phone,Address,lati,longi,hid;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),driver_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_nearest_hospital);
        l1=findViewById(R.id.list1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sh.getString("ip", "") + ":5000/notify_hospital";
        RequestQueue queue = Volley.newRequestQueue(Notify_Nearest_Hospital.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);
//                    Toast.makeText(Notify_Nearest_Hospital.this,response, Toast.LENGTH_SHORT).show();
                    name= new ArrayList<>();
                    Address = new ArrayList<>();
                    phone= new ArrayList<>();
                    lati= new ArrayList<>();
                    longi= new ArrayList<>();
                    hid= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        Address.add(jo.getString("Place")+"\n"+jo.getString("Post")+" , "+jo.getString("Pin")+"\n"+jo.getString("District"));
                        name.add(jo.getString("Hospital_name"));
                        phone.add(jo.getString("Phone_NO"));
                        lati.add(jo.getString("Latitude"));
                        longi.add(jo.getString("Longitude"));
                        hid.add(jo.getString("L_id"));


                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
                    l1.setAdapter(new custom3(Notify_Nearest_Hospital.this,Address,name,phone));
                    l1.setOnItemClickListener(Notify_Nearest_Hospital.this);
                } catch (Exception e) {
                    Toast.makeText(Notify_Nearest_Hospital.this, ""+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Notify_Nearest_Hospital.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //params.put("lid", sh.getString("lid", ""));
                params.put("lati",LocationService.lati);
                params.put("longi",LocationService.logi);

                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor edp = sh.edit();
        edp.putString("hid", hid.get(position));
        edp.commit();
        Intent ii=new Intent(getApplicationContext(),Accident_level.class);
        ii.putExtra("hid",hid.get(position));

        startActivity(ii);


    }
}