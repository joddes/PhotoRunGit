package com.example.janda.photorun.models;

/**
 * Created by user on 25.09.17.
 */

public class photorun_settings {

    private String participators;
    private String status;

    public photorun_settings(String participators, String status) {
        this.participators = participators;
        this.status = status;
    }

    public photorun_settings(){

    }

    public String getParticipators() {
        return participators;
    }

    public photorun_settings(String status) {
        this.status = status;
    }

    public void setParticipators(String participators) {
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


