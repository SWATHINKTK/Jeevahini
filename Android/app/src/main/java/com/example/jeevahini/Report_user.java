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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Report_user extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView l1;
    SharedPreferences sh;
    Spinner s1;
    Button b1;
    RadioButton r1,r2,r3,r4;
    EditText e1;

    String reason,user_id;
    ArrayList<String> uid,name,fname,lname;

    RadioGroup rg;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),driver_home.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);
//        l1=findViewById(R.id.list2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.editTextTextPersonName15);
        b1=findViewById(R.id.button20);
        s1=findViewById(R.id.spinner);
        r1=findViewById(R.id.radioButton7);
        r2=findViewById(R.id.radioButton8);
        r3=findViewById(R.id.radioButton9);
        r4=findViewById(R.id.radioButton);

        e1.setVisibility(View.GONE);

        rg = findViewById(R.id.rg1);

        String url ="http://"+sh.getString("ip", "") + ":5000/report_user_view";
        RequestQueue queue = Volley.newRequestQueue(Report_user.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(Send_Feedback.this, response, Toast.LENGTH_LONG).show();

                    JSONArray ar=new JSONArray(response);
                    uid= new ArrayList<>();
                    name= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        uid.add(jo.getString("L_id"));
                        name.add(jo.getString("F_name")+jo.getString("L_name"));



                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(Report_user.this,android.R.layout.simple_list_item_1,name);
                    s1.setAdapter(ad);

                    s1.setOnItemSelectedListener(Report_user.this);

//                    l1.setAdapter(new Custom(viewuser.this,name,place));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Report_user.this, "err"+error, Toast.LENGTH_SHORT).show();
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

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                Toast.makeText(Report_user.this, ""+checkedId, Toast.LENGTH_SHORT).show();

                if (checkedId == r4.getId())
                {
//                    Toast.makeText(Report_user.this, "r1", Toast.LENGTH_SHORT).show();
                    e1.setVisibility(View.VISIBLE);
                }

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r1.isChecked())
                {
                    reason = r1.getText().toString();
                }
                else if (r2.isChecked())
                {
                    reason = r2.getText().toString();
                }
                else if (r3.isChecked())
                {
                    reason = r3.getText().toString();
                }
                else if (r4.isChecked())
                {
                    reason =e1.getText().toString();

                    if (reason.equalsIgnoreCase(""))
                    {
                        e1.setError("Required");
                        e1.requestFocus();
                    }

                }

//                Toast.makeText(Report_user.this, ""+reason, Toast.LENGTH_SHORT).show();



                RequestQueue queue = Volley.newRequestQueue(Report_user.this);
                String url = "http://" + sh.getString("ip", "") + ":5000/report";

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
                                Toast.makeText(Report_user.this, " SUCCESSFULLY SEND...", Toast.LENGTH_SHORT).show();
                                Intent ru=new Intent(getApplicationContext(), driver_home.class);
                                startActivity(ru);
                            }
                            else
                            {
                                Toast.makeText(Report_user.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

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
                        params.put("user",user_id );
                        params.put("driver", sh.getString("lid",""));
                        params.put("reason", reason);



                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        user_id = uid.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}