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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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


public class custom2 extends BaseAdapter {
    private Context context;
    ImageButton b1;
    ArrayList<String> a, b, c, d, e,id;


    public custom2(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d, ArrayList<String> e, ArrayList<String> id) {
        // TODO Auto-generated constructor stub
        this.context = applicationContext;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.id = id;
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
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertview == null) {
            gridView = new View(context);
            gridView = inflator.inflate(R.layout.activity_custom2, null);

        } else {
            gridView = (View) convertview;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView7);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView10);
        TextView tv5 = (TextView) gridView.findViewById(R.id.tvroom);
        TextView tv6 = (TextView) gridView.findViewById(R.id.textView12);
        TextView tv8 = (TextView) gridView.findViewById(R.id.textView40);

        ImageButton b1 = (ImageButton) gridView.findViewById(R.id.button16);


        tv6.setTextColor(Color.BLUE);


        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv5.setText(c.get(position));

        if(c.get(position).equalsIgnoreCase("Completed")){
            tv8.setVisibility(View.VISIBLE);

        }
        else{
            tv8.setVisibility(View.GONE);

        }

        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(context.getApplicationContext(),payment_amount.class);

//                Toast.makeText(context, ""+id.get(position), Toast.LENGTH_SHORT).show();

                ii.putExtra("req_id",id.get(position));

                context.startActivity(ii);

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps?q=" + d.get(position) + "," + e.get(position)));
                context.startActivity(ii);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps?q=" + d.get(position) + "," + e.get(position)));
                context.startActivity(ii);
            }
        });


        return gridView;








    }
}