package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
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

public class Send_Feedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText e1;
    Spinner s1;
    RatingBar r1;
    Button b1;
    SharedPreferences sh;
    String feedback,d_id;

    ArrayList<String> did,name,fname,lname;
    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),user_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        s1=findViewById(R.id.spinner3);
        e1=findViewById(R.id.editTextTextPersonName3);
        r1=findViewById(R.id.ratingBar2);
        b1=findViewById(R.id.button9);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        String url ="http://"+sh.getString("ip", "") + ":5000/view_driver";
        RequestQueue queue = Volley.newRequestQueue(Send_Feedback.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(Send_Feedback.this, response, Toast.LENGTH_LONG).show();

                    JSONArray ar=new JSONArray(response);
                    did= new ArrayList<>();
                    name= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        did.add(jo.getString("L_id"));
                        name.add(jo.getString("F_name")+jo.getString("L_name"));



                    }

                     ArrayAdapter<String> ad=new ArrayAdapter<>(Send_Feedback.this,android.R.layout.simple_list_item_1,name);
                    s1.setAdapter(ad);

                    s1.setOnItemSelectedListener(Send_Feedback.this);

//                    l1.setAdapter(new Custom(viewuser.this,name,place));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Send_Feedback.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sh.getString("lid",""));
                return params;
            }
        };
        queue.add(stringRequest);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback=e1.getText().toString();


                String rating = String.valueOf(r1.getRating());



                RequestQueue queue = Volley.newRequestQueue(Send_Feedback.this);
                String url = "http://" + sh.getString("ip", "") + ":5000/send_feedback";

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
                                Toast.makeText(Send_Feedback.this, " SUCCESSFULLY SEND...", Toast.LENGTH_SHORT).show();
                                Intent ru=new Intent(getApplicationContext(), user_home.class);
                                startActivity(ru);
                            }
                            else
                            {
                                Toast.makeText(Send_Feedback.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

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
                        params.put("driver",d_id );
                        params.put("user", sh.getString("lid",""));
                        params.put("feedback", feedback);
                        params.put("Rating",rating);



                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

         d_id = did.get(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}