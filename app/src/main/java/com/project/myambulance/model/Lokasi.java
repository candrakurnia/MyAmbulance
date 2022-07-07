package com.project.myambulance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lokasi {
    @SerializedName("id_lokasi")
    @Expose
    private Integer id_lokasi;
    @SerializedName("no_ktp")
    @Expose
    private String no_ktp;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Lokasi(Integer id_lokasi, String no_ktp, String alamat, Integer status) {
        this.id_lokasi = id_lokasi;
        this.no_ktp = no_ktp;
        this.alamat = alamat;
        this.status = status;
    }

    public Integer getid_lokasi() {
        return id_lokasi;
    }

    public void setId_lokasi(Integer id_lokasi) {
        this.id_lokasi = id_lokasi;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
