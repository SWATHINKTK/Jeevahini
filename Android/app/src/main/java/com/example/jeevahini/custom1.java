package com.example.jeevahini;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class custom1 extends BaseAdapter {
    private Context context;
    ImageButton b1;
    ArrayList<String> a,b,c,d,e,f,g;





    public custom1(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d, ArrayList<String> e, ArrayList<String> f, ArrayList<String> g) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        this.e=e;
        this.f=f;
        this.g=g;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_custom1, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView7);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView10);
        TextView tv5=(TextView)gridView.findViewById(R.id.tvroom);
        TextView tv6=(TextView)gridView.findViewById(R.id.textView12);
        TextView tv7=(TextView)gridView.findViewById(R.id.textView13);
        TextView tv8=(TextView)gridView.findViewById(R.id.textView14);
        ImageButton b1=(ImageButton)gridView.findViewById(R.id.button16) ;

        LinearLayout l1 = (LinearLayout)gridView.findViewById(R.id.cst1);

        SharedPreferences sh;

        sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());


        tv6.setTextColor(Color.BLUE);

        tv8.setVisibility(View.GONE);


        if (g.get(position).equalsIgnoreCase("Accepted"))
        {
            l1.setBackgroundColor(Color.GREEN);
            tv7.setVisibility(View.GONE);
            tv8.setVisibility(View.VISIBLE);

        }
        else if (g.get(position).equalsIgnoreCase("Rejected"))
        {
            l1.setBackgroundColor(Color.RED);
            tv7.setVisibility(View.GONE);
        }
        else if (g.get(position).equalsIgnoreCase("Completed"))
        {
            l1.setBackgroundColor(Color.BLUE);
            tv7.setVisibility(View.GONE);
        }


        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv5.setText(c.get(position));


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps?q="+d.get(position)+","+e.get(position)));
                context.startActivity(ii);
            }
        });  tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps?q="+d.get(position)+","+e.get(position)));
                context.startActivity(ii);
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String req_id = f.get(position);

                AlertDialog.Builder ald = new AlertDialog.Builder(context);
                ald.setTitle("OPTION")
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {

                                    RequestQueue queue = Volley.newRequestQueue(context);
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
                                                    Toast.makeText(context, " REQUEST ACCEPTED...", Toast.LENGTH_SHORT).show();
                                                    Intent ru = new Intent(context, View_Emergency_Request_and_Accept.class);
                                                    context.startActivity(ru);
                                                } else {
                                                    Toast.makeText(context, "Invalid Entry", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {


                                            Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("rid", req_id);


                                            return params;
                                        }
                                    };
                                    queue.add(stringRequest);




                                } catch (Exception e) {
                                    Toast.makeText(context, e + "", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Reject", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                RequestQueue queue = Volley.newRequestQueue(context);
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
                                                Toast.makeText(context, " Rejected...", Toast.LENGTH_SHORT).show();
                                                Intent ru = new Intent(context, View_Emergency_Request_and_Accept.class);
                                                context.startActivity(ru);
                                            } else {
                                                Toast.makeText(context, "Invalid Entry", Toast.LENGTH_SHORT).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {


                                        Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("rid", req_id);


                                        return params;
                                    }
                                };
                                queue.add(stringRequest);



                            }
                        });
                AlertDialog al = ald.create();
                al.show();
//                if (g.get(position).equalsIgnoreCase("pending")) {
//
//
//                }


            }
        });


        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String req_id = f.get(position);

                AlertDialog.Builder ald = new AlertDialog.Builder(context);
                ald.setTitle("OPTION")
                        .setPositiveButton("Completed", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {

                                    RequestQueue queue = Volley.newRequestQueue(context);
                                    String url = "http://" + sh.getString("ip", "") + ":5000/completedreq";

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
                                                    Toast.makeText(context, " COMPLETED...", Toast.LENGTH_SHORT).show();
                                                    Intent ru = new Intent(context, View_Emergency_Request_and_Accept.class);
                                                    context.startActivity(ru);
                                                } else {
                                                    Toast.makeText(context, "Invalid Entry", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {


                                            Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("rid", req_id);


                                            return params;
                                        }
                                    };
                                    queue.add(stringRequest);




                                } catch (Exception e) {
                                    Toast.makeText(context, e + "", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(" Close. ", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {






                            }
                        });
                AlertDialog al = ald.create();


                    al.show();

            }
        });


        return gridView;

    }


}