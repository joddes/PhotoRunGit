package com.example.janda.photorun.models;

/**
 * Created by user on 25.09.17.
 */

public class photorun_settings {

    private boolean participators;
    private String status;

    public photorun_settings(boolean participators, String status) {
        this.participators = participators;
        this.status = status;
    }

    public photorun_settings(){

    }

    public boolean getParticipators() {
        return participators;
    }

    public photorun_settings(String status) {
        this.status = status;
    }

    public void setParticipators(boolean participators) {
        this.participators = participators;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "photorun_settings{" +
                "participators=" + participators +
                ", status='" + status + '\'' +
                '}';
    }
}


