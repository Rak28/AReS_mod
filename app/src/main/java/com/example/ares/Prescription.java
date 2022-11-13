package com.example.ares;

import java.util.ArrayList;

public class Prescription {
    private String name;
    private String date;
    private String age;
    private String bp;
    private String weight;
    private String sickness;
    private ArrayList<DrugClass> drugClassArrayList;

    public Prescription(String name, String date, String age, String bp, String weight, String sickness, ArrayList<DrugClass> drugClassArrayList) {
        this.name = name;
        this.date = date;
        this.age = age;
        this.bp = bp;
        this.weight = weight;
        this.sickness = sickness;
        this.drugClassArrayList = drugClassArrayList;
    }

    public String getAge() {
        return age;
    }

    public String getBp() {
        return bp;
    }

    public String getWeight() {
        return weight;
    }

    public String getSickness() {
        return sickness;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<DrugClass> getDrugClassArrayList() {
        return drugClassArrayList;
    }
}
