package com.project.myambulance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

public class Driver {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("no_plat")
    @Expose
    private String no_plat;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("no_telpon")
    @Expose
    private String no_telpon;
    @SerializedName("id_pengelola")
    @Expose
    private String id_pengelola;

    public Driver(String username, String no_plat, String alamat, String no_telpon, String id_pengelola) {
        this.username = username;
        this.no_plat = no_plat;
        this.alamat = alamat;
        this.no_telpon = no_telpon;
        this.id_pengelola = id_pengelola;
    }

    public String getUsername() {
        return username;
    }

    public String getNo_plat() {
        return no_plat;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNo_telpon() {
        return no_telpon;
    }

    public String getId_pengelola() {
        return id_pengelola;
    }
}
