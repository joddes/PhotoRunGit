package com.example.janda.photorun.models;

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

    public User(String user_id, String email, String full_name, String address, String phonenumber, String role, String personalInf) {
        this.user_id = user_id;
        this.email = email;
        this.full_name = full_name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = role;
        this.personalInf = personalInf;


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


    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", full_name='" + full_name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
