package com.example.janda.photorun.models;

import java.util.Map;

/**
 * Created by User on 25.09.17.
 */

public class Photorun {

    private String date;
    private String description;
    private String estimated_duration;
    private String photorun_id;
    private String starting_time;
    private String title;
    private String start_point;
    private String end_point;
    private String status;
    private Map<String, String> participants;
    private String ownerName;

    public Photorun(String date, String description, String estimated_duration, String photorun_id, String starting_time, String title, String start_point, String end_point, String status, String ownerName) {
        this.date = date;
        this.description = description;
        this.estimated_duration = estimated_duration;
        this.photorun_id = photorun_id;
        this.starting_time = starting_time;
        this.title = title;
        this.start_point = start_point;
        this.end_point = end_point;
        this.status = status;
        this.ownerName = ownerName;
    }

    public Photorun(String photorun_id,  String title) {
        this.photorun_id = photorun_id;
        this.title = title;
    }

    public Photorun(String date, String description, String estimated_duration, String max_participators, String photorun_id, String starting_time, String title, String start_point, String end_point, String status, Map<String, String> participants) {
        this.date = date;
        this.description = description;
        this.estimated_duration = estimated_duration;
        this.photorun_id = photorun_id;
        this.starting_time = starting_time;
        this.title = title;
        this.start_point = start_point;
        this.end_point = end_point;
        this.status = status;
        this.participants = participants;
    }

    public Photorun(){

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

    public String getEstimated_duration() {
        return estimated_duration;
    }

    public void setEstimated_duration(String estimated_duration) {
        this.estimated_duration = estimated_duration;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, String> participants) {
        this.participants = participants;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "Photorun{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", estimated_duration='" + estimated_duration + '\'' +
                ", photorun_id='" + photorun_id + '\'' +
                ", starting_time='" + starting_time + '\'' +
                ", title='" + title + '\'' +
                ", start_point='" + start_point + '\'' +
                ", end_point='" + end_point + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
