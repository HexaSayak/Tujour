package com.mit.tujour.Database;

import android.view.View;

public class TujourUserHelperClass {
    View fullName;
    View username;
    View email;
    String phoneNo;
    String password;
    String date;
    String gender;
    public TujourUserHelperClass() {
    }
    public TujourUserHelperClass(View fullName, View username, View email, String phoneNo, String password, String date, String gender) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.date = date;
        this.gender = gender;
    }

    public View getFullName() {
        return fullName;
    }

    public void setFullName(View fullName) {
        this.fullName = fullName;
    }

    public View getUsername() {
        return username;
    }

    public void setUsername(View username) {
        this.username = username;
    }

    public View getEmail() {
        return email;
    }

    public void setEmail(View email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
