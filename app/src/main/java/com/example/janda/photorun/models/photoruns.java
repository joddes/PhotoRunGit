package com.example.janda.photorun.models;

/**
 * Created by user on 25.09.17.
 */

public class photoruns {

    private String date;
    private String description;
    private String estimaed_duration;
    private String max_participators;
    private String photorun_id;
    private String starting_tiem;
    private String title;

    public photoruns(String date, String description, String estimaed_duration, String max_participators, String photorun_id, String starting_tiem, String title) {
        this.date = date;
        this.description = description;
        this.estimaed_duration = estimaed_duration;
        this.max_participators = max_participators;
        this.photorun_id = photorun_id;
        this.starting_tiem = starting_tiem;
        this.title = title;
    }

    public photoruns(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimaed_duration() {
        return estimaed_duration;
    }

    public void setEstimaed_duration(String estimaed_duration) {
        this.estimaed_duration = estimaed_duration;
    }

    public String getMax_participators() {
        return max_participators;
    }

    public void setMax_participators(String max_participators) {
        this.max_participators = max_participators;
    }

    public String getPhotorun_id() {
        return photorun_id;
    }

    public void setPhotorun_id(String photorun_id) {
        this.photorun_id = photorun_id;
    }

    public String getStarting_tiem() {
        return starting_tiem;
    }

    public void setStarting_tiem(String starting_tiem) {
        this.starting_tiem = starting_tiem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "photoruns{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", estimaed_duration='" + estimaed_duration + '\'' +
                ", max_participators='" + max_participators + '\'' +
                ", photorun_id='" + photorun_id + '\'' +
                ", starting_tiem='" + starting_tiem + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
