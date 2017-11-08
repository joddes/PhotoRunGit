package com.example.janda.photorun.models;

import com.firebase.geofire.GeoLocation;

import java.util.Map;

/**
 * Created by User on 25.09.17.
 */

public class User {

    private String user_id;
    private String email;
    private String full_name;
    private String address;
    private String phonenumber;
    private String role;
    private String personalInf;
    private Map<String, String> createdRuns;
    private Map<String, String> participatedRuns;
    private Map<String, String> following;
    private Map<String, String> followers;
    private String profileimageUrl;
    private String titleImageUrl;
    private GeoLocation location;

    public User(String user_id, String email, String full_name, String address, String phonenumber, String role, String personalInf) {
        this.user_id = user_id;
        this.email = email;
        this.full_name = full_name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = role;
        this.personalInf = personalInf;
    }

    public User(String user_id, String email, String full_name, String address, String phonenumber, String role, String personalInf, Map<String, String> createdRuns, Map<String, String> participatedRuns) {
        this.user_id = user_id;
        this.email = email;
        this.full_name = full_name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = role;
        this.personalInf = personalInf;
        this.createdRuns = createdRuns;
        this.participatedRuns = participatedRuns;
    }

    public User(){

    }

    public User(String user_id, String email, String full_name, String address, String phonenumber, String role, String personalInf, Map<String, String> createdRuns, Map<String, String> participatedRuns, Map<String, String> following, Map<String, String> followers, String profileimageUrl) {
        this.user_id = user_id;
        this.email = email;
        this.full_name = full_name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = role;
        this.personalInf = personalInf;
        this.createdRuns = createdRuns;
        this.participatedRuns = participatedRuns;
        this.following = following;
        this.followers = followers;
        this.profileimageUrl = profileimageUrl;
    }

    public User(String user_id, String email, String full_name, String address, String phonenumber, String role, String personalInf, Map<String, String> createdRuns, Map<String, String> participatedRuns, Map<String, String> following, Map<String, String> followers, String profileimageUrl, String titleImageUrl, GeoLocation location) {
        this.user_id = user_id;
        this.email = email;
        this.full_name = full_name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = role;
        this.personalInf = personalInf;
        this.createdRuns = createdRuns;
        this.participatedRuns = participatedRuns;
        this.following = following;
        this.followers = followers;
        this.profileimageUrl = profileimageUrl;
        this.titleImageUrl = titleImageUrl;
        this.location = location;
    }



    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public String getProfileimageUrl() {
        return profileimageUrl;
    }

    public void setProfileimageUrl(String profileimageUrl) {
        this.profileimageUrl = profileimageUrl;
    }

    public void setCreatedRuns(Map<String, String> createdRuns) {
        this.createdRuns = createdRuns;
    }

    public void setParticipatedRuns(Map<String, String> participatedRuns) {
        this.participatedRuns = participatedRuns;
    }

    public Map<String, String> getFollowing() {
        return following;
    }

    public void setFollowing(Map<String, String> following) {
        this.following = following;
    }

    public Map<String, String> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<String, String> followers) {
        this.followers = followers;
    }

    public Map<String, String> getCreatedRuns() {
        return createdRuns;
    }

    public Map<String, String> getParticipatedRuns() {
        return participatedRuns;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalInf() {
        return personalInf;
    }

    public void setPersonalInf(String personalInf) {
        this.personalInf = personalInf;
    }

    public String getPhonenumber() {
        return phonenumber;
    }


    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", full_name='" + full_name + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", role='" + role + '\'' +
                ", personalInf='" + personalInf + '\'' +
                ", createdRuns=" + createdRuns +
                ", participatedRuns=" + participatedRuns +
                ", following=" + following +
                ", followers=" + followers +
                '}';
    }
}

