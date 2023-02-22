package com.project.myambulance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("id_history")
    @Expose
    private Integer id_history;
    @SerializedName("no_ktp")
    @Expose
    private String no_ktp;
    @SerializedName("id_loc_user")
    @Expose
    private String id_loc_user;
    @SerializedName("id_driver")
    @Expose
    private String id_driver;
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
    @SerializedName("no_plat")
    @Expose
    private String no_plat;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("waktu")
    @Expose
    private String waktu;

    public History(Integer id_history, String no_ktp, String id_loc_user, String id_driver, String username, String password, String no_telpon, String no_kk, String no_plat, String alamat, String waktu) {
        this.id_history = id_history;
        this.no_ktp = no_ktp;
        this.id_loc_user = id_loc_user;
        this.id_driver = id_driver;
        this.username = username;
        this.password = password;
        this.no_telpon = no_telpon;
        this.no_kk = no_kk;
        this.no_plat = no_plat;
        this.alamat = alamat;
        this.waktu = waktu;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getNo_plat() {
        return no_plat;
    }

    public void setNo_plat(String no_plat) {
        this.no_plat = no_plat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getId_history() {
        return id_history;
    }

    public void setId_history(Integer id_history) {
        this.id_history = id_history;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getId_loc_user() {
        return id_loc_user;
    }

    public void setId_loc_user(String id_loc_user) {
        this.id_loc_user = id_loc_user;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
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
}
