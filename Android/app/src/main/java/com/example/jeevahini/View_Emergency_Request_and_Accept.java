package com.example.jeevahini;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class View_Emergency_Request_and_Accept extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    ListView l1;
    SharedPreferences sh;
    String url = "", ip = "",rrid;
    ArrayList<String> user, phone, rid,address, lati, longi,status;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),driver_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emergency_request_and_accept);
        l1 = findViewById(R.id.list5);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url = "http://" + sh.getString("ip", "") + ":5000/viewemergencyreq";
        RequestQueue queue = Volley.newRequestQueue(View_Emergency_Request_and_Accept.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
//                    Toast.makeText(View_Emergency_Request_and_Accept.this, response, Toast.LENGTH_SHORT).show();
                    JSONArray ar = new JSONArray(response);
                    rid = new ArrayList<>();
                    user = new ArrayList<>();
                    phone = new ArrayList<>();
                    address = new ArrayList<>();
                    lati=new ArrayList<>();
                    longi=new ArrayList<>();
                    status=new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        rid.add(jo.getString("Req_id"));
                        user.add(jo.getString("F_name") + (jo.getString("L_name")));
                        phone.add(jo.getString("Phone_NO"));
                        address.add(jo.getString("Address"));
                        lati.add(jo.getString("latitude"));
                        longi.add(jo.getString("longitude"));
                        status.add(jo.getString("Request_status"));
                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
                    l1.setAdapter(new custom1(View_Emergency_Request_and_Accept.this,  address,user, phone,lati,longi,rid,status));
//                    l1.setOnItemClickListener(View_Emergency_Request_and_Accept.this);
                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(View_Emergency_Request_and_Accept.this, "err" + error, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rrid=rid.get(position);
        AlertDialog.Builder ald = new AlertDialog.Builder(View_Emergency_Request_and_Accept.this);
        ald.setTitle("OPTION")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            RequestQueue queue = Volley.newRequestQueue(View_Emergency_Request_and_Accept.this);
                            String url = "http://" + sh.getString("ip", "") + ":5000/acceptreq";

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
                                            Toast.makeText(View_Emergency_Request_and_Accept.this, " REQUEST ACCEPTED...", Toast.LENGTH_SHORT).show();
                                            Intent ru = new Intent(getApplicationContext(), driver_home.class);
                                            startActivity(ru);
                                        } else {
                                            Toast.makeText(View_Emergency_Request_and_Accept.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

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
                                    params.put("rid", rrid);


                                    return params;
                                }
                            };
                            queue.add(stringRequest);






                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(" Reject ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        RequestQueue queue = Volley.newRequestQueue(View_Emergency_Request_and_Accept.this);
                        String url = "http://" + sh.getString("ip", "") + ":5000/rejectreq";

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
                                        Toast.makeText(View_Emergency_Request_and_Accept.this, " Rejected...", Toast.LENGTH_SHORT).show();
                                        Intent ru = new Intent(getApplicationContext(), driver_home.class);
                                        startActivity(ru);
                                    } else {
                                        Toast.makeText(View_Emergency_Request_and_Accept.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

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
                                params.put("rid", rrid);


                                return params;
                            }
                        };
                        queue.add(stringRequest);

                    }
                });
        AlertDialog al = ald.create();
        al.show();
    }
}
