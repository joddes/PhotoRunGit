package com.example.janda.photorun.models;

import java.util.List;

/**
 * Created by user on 25.09.17.
 */

public class photorun_settings {

    private List participators;
    private String status;

    public photorun_settings(List participators, String status) {
        this.participators = participators;
        this.status = status;
    }

    public photorun_settings(){

    }

    public List getParticipators() {
        return participators;
    }

    public void setParticipators(List participators) {
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


