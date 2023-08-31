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

public class Login extends AppCompatActivity {
    EditText e1,e2;
    Button  b1,b2;
    SharedPreferences sh;
    String uname,passwd,url;

    @Override
    public void onBackPressed(){
        Intent i=new Intent(getApplicationContext(),MainActivity.class);

        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.editTextTextPersonName2);
        e2=findViewById(R.id.editTextTextPersonName4);
        b1=findViewById(R.id.button12);
        b2=findViewById(R.id.button11);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = e1.getText().toString();
                passwd = e2.getText().toString();

                if (uname.equalsIgnoreCase("")) {
                    e1.setError("Please enter your username");
                    e1.requestFocus();
                } else if (passwd.equalsIgnoreCase("")) {
                    e2.setError("Please enter your password");
                    e2.requestFocus();
                } else if (passwd.length() < 6) {
                    e2.setError("Password must be 6 characters long");
                    e2.requestFocus();
                }else {
                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/login";

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

                                if (res.equalsIgnoreCase("success")) {
                                    String lid = json.getString("lid");
                                    String name = json.getString("name");
                                    String type = json.getString("type");
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();

                                    if (type.equalsIgnoreCase("user")) {
                                        Intent i = new Intent(getApplicationContext(), LocationService.class);
                                        startService(i);
                                        Intent iii = new Intent(getApplicationContext(), LocationServiceno.class);
                                        startService(iii);

                                        Toast.makeText(Login.this, "welcome user", Toast.LENGTH_SHORT).show();

                                        SharedPreferences.Editor edp1 = sh.edit();
                                        edp1.putString("name", name);
                                        edp1.commit();
                                        Intent ik = new Intent(getApplicationContext(), user_home.class);
                                        ik.putExtra("name", name);
                                        startActivity(ik);
                                    } else {
                                        Intent i = new Intent(getApplicationContext(), LocationServiceno.class);
                                        startService(i);
//                                    Intent ikkk= new Intent(getApplicationContext(),LocationServiceno.class);
//                                    startService(ikkk);
                                        SharedPreferences.Editor edp1 = sh.edit();
                                        edp1.putString("name", name);
                                        edp1.commit();
                                        Intent ii = new Intent(getApplicationContext(), driver_home.class);
                                        ii.putExtra("name", name);
                                        startActivity(ii);
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

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
                            params.put("uname", uname);
                            params.put("password", passwd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }



        }
    });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder ald=new AlertDialog.Builder(Login.this);
                ald.setTitle("Register")
                        .setPositiveButton("User register", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try
                                {
                                    Intent ui=new Intent(getApplicationContext(), Register_user.class);
                                    startActivity(ui);

                                }
                                catch(Exception e)
                                {
                                    Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton(" Driver register", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent di=new Intent(getApplicationContext(), Driver_Registration.class);
                                startActivity(di);


                            }
                        });

                AlertDialog al=ald.create();
                al.show();




            }
        });
    }
}