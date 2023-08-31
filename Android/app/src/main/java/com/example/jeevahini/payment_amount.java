package com.example.jeevahini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class payment_amount extends AppCompatActivity {

    EditText e1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_amount);

        e1 = findViewById(R.id.editTextNumber);
        b1 = findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = e1.getText().toString();

                Intent i = new Intent(getApplicationContext(),PaymentActivity.class);
                i.putExtra("p",amount);
                i.putExtra("req_id", getIntent().getStringExtra("req_id"));
                Toast.makeText(payment_amount.this, "=="+amount, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });


    }
}