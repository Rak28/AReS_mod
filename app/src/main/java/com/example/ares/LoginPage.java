package com.example.ares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    EditText username, password;
    Button doctor, patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        doctor = findViewById(R.id.doctorButton);
        patient = findViewById(R.id.patientButton);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("doctor") && password.getText().toString().equals("doctor")) {
                    Intent myIntent = new Intent(LoginPage.this, MainActivity.class);
                    myIntent.putExtra("key", 2342);
                    LoginPage.this.startActivity(myIntent);
                }
            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("patient") && password.getText().toString().equals("patient")) {
                    Intent myIntent = new Intent(LoginPage.this, PatientLayout.class);
                    myIntent.putExtra("key", 2342);
                    LoginPage.this.startActivity(myIntent);
                }
            }
        });
    }
}