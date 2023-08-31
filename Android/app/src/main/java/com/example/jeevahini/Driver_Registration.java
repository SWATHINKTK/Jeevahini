package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class
Driver_Registration extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;
    SharedPreferences sh;
    String fname,lname,address,vnumber,lnumber,phone,email,uname,passwd;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),Login.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);
        e1=findViewById(R.id.editTextTextPersonName9);
        e2=findViewById(R.id.editTextTextPersonName10);
        e3=findViewById(R.id.editTextTextPersonName11);
        e4=findViewById(R.id.editTextTextPersonName12);
        e5=findViewById(R.id.editTextTextPersonName13);
        e6=findViewById(R.id.editTextPhone2);
        e7=findViewById(R.id.editTextTextEmailAddress2);
        e8=findViewById(R.id.editTextTextPersonName14);
        e9=findViewById(R.id.editTextTextPassword3);
        b1=findViewById(R.id.button19);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                address=e3.getText().toString();
                vnumber=e4.getText().toString();
                lnumber=e5.getText().toString();
                phone=e6.getText().toString();
                email=e7.getText().toString();
                uname=e8.getText().toString();
                passwd=e9.getText().toString();

                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Please enter your first name");
                    e1.requestFocus();
                }
                else if (!fname.matches("^[a-z A-Z]*$")) {
                    e1.setError("Only characters are allowed");
                    e1.requestFocus();
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Please enter your last name");
                    e2.requestFocus();
                } else if (!lname.matches("^[a-z A-Z]*$")) {
                    e2.setError("Only characters are allowed");
                    e2.requestFocus();

                } else if (address.equalsIgnoreCase("")) {
                    e3.setError("Please enter your address");
                    e3.requestFocus();
                } else if (!address.matches("^[a-z A-Z]*$")) {
                    e3.setError("Only characters are allowed");
                    e3.requestFocus();

                }else if (vnumber.equalsIgnoreCase("")) {
                    e4.setError("Please enter your vehicle number");
                    e4.requestFocus();
                } else if (!vnumber.matches("^[a-z A-Z]*$")) {
                    e4.setError("Only characters are allowed");
                    e4.requestFocus();

                }
                else if (lnumber.equalsIgnoreCase("")) {
                    e5.setError("Please enter your license number");
                    e5.requestFocus();
                } else if (!lnumber.matches("^[a-z A-Z]*$")) {
                    e5.setError("Only characters are allowed");
                    e5.requestFocus();

                }
                else if (phone.equalsIgnoreCase("")) {
                    e6.setError("Please enter your phone number");
                    e6.requestFocus();
                } else if (phone.length() != 10) {
                    e6.setError("Phone number must be 10 numbers");
                    e6.requestFocus();
                }

                else if(email.equalsIgnoreCase(""))
                {
                    e7.setError("Enter Your Email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e7.setError("Enter Valid Email");
                    e7.requestFocus();
                }

                else if (uname.equalsIgnoreCase("")) {
                    e8.setError("Please enter your username");
                    e8.requestFocus();
                } else if (passwd.equalsIgnoreCase("")) {
                    e9.setError("Please enter your password");
                    e9.requestFocus();
                } else if (passwd.length() < 6) {
                    e9.setError("Password must be 6 characters long");
                    e9.requestFocus();
                }  else {


                    RequestQueue queue = Volley.newRequestQueue(Driver_Registration.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/signup1";

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
                                    Toast.makeText(Driver_Registration.this, "REGISTER SUCESSFULL...", Toast.LENGTH_SHORT).show();
                                    Intent ru = new Intent(getApplicationContext(), Login.class);
                                    startActivity(ru);
                                } else {
                                    Toast.makeText(Driver_Registration.this, "Invalid Entry", Toast.LENGTH_SHORT).show();

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
                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("address", address);
                            params.put("Vehicle_no", vnumber);
                            params.put("License_no", lnumber);
                            params.put("Phone_No", phone);
                            params.put("Email", email);
                            params.put("uname", uname);
                            params.put("passwd", passwd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }


            }
        });
    }
}