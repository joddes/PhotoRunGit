package com.example.janda.photorun.models;

/**
 * Created by user on 25.09.17.
 */

public class user_account_settings {

    private String created_photowalks;
    private String experience;
    private String location;
    private String participated_photowalks;
    private String profile_photo;
    private String role;

    public user_account_settings(String created_photowalks, String experience, String location, String participated_photowalks, String profile_photo, String role) {
        this.created_photowalks = created_photowalks;
        this.experience = experience;
        this.location = location;
        this.participated_photowalks = participated_photowalks;
        this.profile_photo = profile_photo;
        this.role = role;
    }

    public user_account_settings(){

    }

    public String getCreated_photowalks() {
        return created_photowalks;
    }

    public void setCreated_photowalks(String created_photowalks) {
        this.created_photowalks = created_photowalks;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParticipated_photowalks() {
        return participated_photowalks;
    }

    public void setParticipated_photowalks(String participated_photowalks) {
        this.participated_photowalks = participated_photowalks;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "user_account_settings{" +
                "created_photowalks='" + created_photowalks + '\'' +
                ", experience='" + experience + '\'' +
                ", location='" + location + '\'' +
                ", participated_photowalks='" + participated_photowalks + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}


