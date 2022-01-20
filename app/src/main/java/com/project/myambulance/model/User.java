package com.project.myambulance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("no_telpon")
    @Expose
    private String no_telpon;
    @SerializedName("no_kk")
    @Expose
    private String no_kk;
    @SerializedName("no_ktp")
    @Expose
    private String no_ktp;

    public User(String username, String password, String no_telpon, String no_kk, String no_ktp) {
        this.username = username;
        this.password = password;
        this.no_telpon = no_telpon;
        this.no_kk = no_kk;
        this.no_ktp = no_ktp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_telpon() {
        return no_telpon;
    }

    public void setNo_telpon(String no_telpon) {
        this.no_telpon = no_telpon;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }
}
