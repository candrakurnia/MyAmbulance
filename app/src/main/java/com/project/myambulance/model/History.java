package com.project.myambulance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama_driver")
    @Expose
    private String nama_driver;
    @SerializedName("no_plat")
    @Expose
    private String no_plat;
    @SerializedName("alamat")
    @Expose
    private String alamat;

    public History(Integer id, String nama_driver, String no_plat, String alamat) {
        this.id = id;
        this.nama_driver = nama_driver;
        this.no_plat = no_plat;
        this.alamat = alamat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
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
}
