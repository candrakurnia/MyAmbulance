package com.project.myambulance.model;

import java.time.OffsetDateTime;


public class DataCovid {
    private Integer positif;
    private Integer dirawat;
    private Integer sembuh;
    private Integer meninggal;
    private String lastUpdate;

    public Integer getPositif() {
        return positif;
    }

    public void setPositif(Integer positif) {
        this.positif = positif;
    }

    public Integer getDirawat() {
        return dirawat;
    }

    public void setDirawat(Integer dirawat) {
        this.dirawat = dirawat;
    }

    public Integer getSembuh() {
        return sembuh;
    }

    public void setSembuh(Integer sembuh) {
        this.sembuh = sembuh;
    }

    public Integer getMeninggal() {
        return meninggal;
    }

    public void setMeninggal(Integer meninggal) {
        this.meninggal = meninggal;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
