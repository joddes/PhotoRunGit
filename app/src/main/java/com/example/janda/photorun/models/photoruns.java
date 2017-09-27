package com.example.janda.photorun.models;

/**
 * Created by user on 25.09.17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class photoruns {

    private String date;
    private String description;
    private long estimated_duration;
    private long max_participators;
    private String photorun_id;
    private String starting_time;
    private String title;
    private String start_point;
    private String end_point;

    public photoruns(String date, String description, long estimated_duration, long max_participators, String photorun_id, String starting_time, String title, String start_point, String end_point) {
        this.date = date;
        this.description = description;
        this.estimated_duration = 0;
        this.max_participators = 0;
        this.photorun_id = photorun_id;
        this.starting_time = starting_time;
        this.title = title;
        this.start_point = start_point;
        this.end_point = end_point;
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

    public long getEstimated_duration() {
        return estimated_duration;
    }

    public void setEstimated_duration(long estimated_duration) {
        this.estimated_duration = estimated_duration;
    }

    public long getMax_participators() {
        return max_participators;
    }

    public void setMax_participators(long max_participators) {
        this.max_participators = max_participators;
    }

    public String getPhotorun_id() {
        return photorun_id;
    }

    public void setPhotorun_id(String photorun_id) {
        this.photorun_id = photorun_id;
    }

    public String getStarting_time() {
        return starting_time;
    }

    public void setStarting_time(String starting_time) {
        this.starting_time = starting_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_point() {
        return start_point;
    }

    public void setStart_point(String start_point) {
        this.start_point = start_point;
    }

    public String getEnd_point() {
        return end_point;
    }

    public void setEnd_point(String end_point) {
        this.end_point = end_point;
    }

    @Override
    public String toString() {
        return "photoruns{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", estimated_duration='" + estimated_duration + '\'' +
                ", max_participators='" + max_participators + '\'' +
                ", photorun_id='" + photorun_id + '\'' +
                ", starting_time='" + starting_time + '\'' +
                ", title='" + title + '\'' +
                ", start_point='" + start_point + '\'' +
                ", end_point='" + end_point + '\'' +
                '}';
    }
}
