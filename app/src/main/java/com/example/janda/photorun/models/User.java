package com.example.janda.photorun.models;

/**
 * Created by User on 25.09.17.
 */

public class User {

    private String user_id;
    private String email;
    private String full_name;
    private String username;

    public User(String user_id, String email, String full_name, String username) {
        this.user_id = user_id;
        this.email = email;
        this.full_name = full_name;
        this.username = username;
    }

    public User(){

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", full_name='" + full_name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
