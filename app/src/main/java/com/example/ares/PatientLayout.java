package com.example.ares;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PatientLayout extends AppCompatActivity {

    RecyclerView patientRecyclerView;
    private PrescriptionAdapter prescriptionAdapter;
    private ArrayList<Prescription> prescriptionArrayList;
    private ArrayList<DrugClass> drugClassArrayList;

    private FloatingActionButton mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_layout);

        mapButton = findViewById(R.id.mapFab);

        patientRecyclerView = findViewById(R.id.patientRecyclerView);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        prescriptionArrayList = new ArrayList<>();
        drugClassArrayList = new ArrayList<>();
        drugClassArrayList.add(new DrugClass(1, "Dolo", "Tablet", "3", "231564123", true, true, true, "11-11-22"));
        drugClassArrayList.add(new DrugClass(2, "Alex", "Syrup", "2", "231564123", true, true, true, "11-11-22"));
//        int index, String drugName, String route, String days, String rxcode, boolean morning, boolean afternoon, boolean night, String date
        prescriptionArrayList.add(new Prescription("Dr. Ramesh", "11-11-22", "20", "120/80", "80", "Fever", drugClassArrayList));
        prescriptionArrayList.add(new Prescription("Dr. Suresh", "10-11-22", "20", "120/80", "80", "Cold", drugClassArrayList));

        prescriptionAdapter = new PrescriptionAdapter(this, prescriptionArrayList);
        Log.e("Prescriptions", prescriptionArrayList.toString());
        patientRecyclerView.setAdapter(prescriptionAdapter);
        patientRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(PatientLayout.this, MapsActivity.class);
                myIntent.putExtra("key", 2342); //Optional parameters
                PatientLayout.this.startActivity(myIntent);
            }
        });

    }
}