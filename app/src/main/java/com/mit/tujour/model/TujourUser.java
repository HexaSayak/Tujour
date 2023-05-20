package com.mit.tujour.model;

import java.util.Objects;

public class TujourUser {
    private String email; // <-- New added
    private String uuid;
    private String fullName;
    private String emailId;
    private Long phoneNo;

    private String username;

    private String password;

    private String dob;

    private String gender;

    public TujourUser(){ }
    public TujourUser(String fullName, String username, String email, String phoneNo, String password, String dob, String gender) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phoneNo = Long.valueOf(phoneNo);
        this.password = password;
        this.dob = dob;
        this.gender = gender;
    }
    // <-- New added

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TujourUser)) return false;
        TujourUser that = (TujourUser) o;
        return getUuid().equals(that.getUuid())
                && getFullName().equals(that.getFullName())
                && getEmailId().equals(that.getEmailId())
                && getPhoneNo().equals(that.getPhoneNo())
                && getUsername().equals(that.getUsername())
                && getDob().equals(that.getDob())
                && getGender().equals(that.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), getEmailId(), getPhoneNo(), getUsername(), getDob(), getGender());
    }

    @Override
    public String toString() {
        return "TujourUser{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", phoneNo=" + phoneNo +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", date='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
