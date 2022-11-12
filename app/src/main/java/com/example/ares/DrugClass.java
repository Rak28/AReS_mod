package com.example.ares;

public class DrugClass {

    private String drugName;
    private String route;
    private String days;
    private boolean morning;
    private boolean afternoon;
    private boolean night;

    public DrugClass(String drugName, String route, String days, boolean morning, boolean afternoon, boolean night) {
        this.drugName = drugName;
        this.route = route;
        this.days = days;
        this.morning = morning;
        this.afternoon = afternoon;
        this.night = night;
    }

    public DrugClass(String drugName) {
        this.drugName = drugName;
        this.route = "tablet";
        this.days = "0";
        this.morning = false;
        this.afternoon = false;
        this.night = false;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDays() {
        return days;
    }

    public boolean isMorning() {
        return morning;
    }

    public boolean isAfternoon() {
        return afternoon;
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
