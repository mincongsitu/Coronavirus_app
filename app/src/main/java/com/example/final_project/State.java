package com.example.final_project;

public class State {

    public String state;
    public int infected;
    public int deaths;

    public State(){

    }

    public State(String state, int infected, int deaths){
        this.state = state;
        this.infected = infected;
        this.deaths = deaths;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
