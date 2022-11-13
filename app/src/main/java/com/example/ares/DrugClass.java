package com.example.ares;

public class DrugClass {

    private int index;
    private String drugName;
    private String route;
    private String days;
    private String rxcode;
    private boolean morning;
    private boolean afternoon;
    private boolean night;
    private String date;

    public DrugClass(int index, String drugName, String route, String days, String rxcode, boolean morning, boolean afternoon, boolean night, String date) {
        this.index = index;
        this.drugName = drugName;
        this.route = route;
        this.days = days;
        this.rxcode = rxcode;
        this.morning = morning;
        this.afternoon = afternoon;
        this.night = night;
        this.date = date;
    }

    public DrugClass(String drugName) {
        this.drugName = drugName;
        this.route = "tablet";
        this.days = "0";
        this.morning = false;
        this.afternoon = false;
        this.night = false;
    }

    public int getIndex() {
        return index;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDays() {
        return days;
    }

    public String getRxcode() {
        return rxcode;
    }


    public boolean isMorning() {
        return morning;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public String getDate() {
        return date;
    }

    public boolean isNight() {
        return night;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public String getRoute() {
        return route;
    }
}
