package com.example.final_project;

public class Country {
    public String country;
    public int infected;
    public int deaths;

    public Country(){}

    public Country(String country, int infected, int deaths){
        this.country = country;
        this.infected = infected;
        this.deaths = deaths;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getInfected() {
        return infected;
    }

    public void setInfected(int infected) {
        this.infected = infected;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
