package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class Payment_status extends AppCompatActivity {

    SharedPreferences sh;
    ListView l1;

    ArrayList<String> name,amount,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = findViewById(R.id.lst_pstatus);

        String url ="http://"+sh.getString("ip", "") + ":5000/payment_details";
        RequestQueue queue = Volley.newRequestQueue(Payment_status.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);
                    name= new ArrayList<>();
                    amount= new ArrayList<>();
                    date= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("F_name")+(jo.getString("L_name")));
                        amount.add(jo.getString("Amount"));
                        date.add(jo.getString("Date"));

                        //Toast.makeText(view_request_status.this, "values"+name+status+phone, Toast.LENGTH_SHORT).show();
                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
                    l1.setAdapter(new custom_payment(Payment_status.this,name,amount,date));
//                    l1.setOnItemClickListener(view_request_status.this);
                } catch (Exception e) {
                    Toast.makeText(Payment_status.this, ""+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Payment_status.this, "err"+error, Toast.LENGTH_SHORT).show();
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
}