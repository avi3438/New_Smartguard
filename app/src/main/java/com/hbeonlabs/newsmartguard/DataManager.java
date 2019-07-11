package com.hbeonlabs.newsmartguard;

public class DataManager {

    private String image;
    private String mob;
    private String title;
    private String device_status;
    private String siren_status;
    private String hub_status;

    public DataManager(String image, String mob, String title, String device_status, String siren_status, String hub_status) {
        this.image = image;
        this.mob = mob;
        this.title = title;
        this.device_status = device_status;
        this.siren_status = siren_status;
        this.hub_status = hub_status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMob() {

        return mob;
    }

    public void setMob(String mob) {

        this.mob = mob;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDevice_status() {
        return device_status;
    }

    public void setDevice_status(String device_status) {
        this.device_status = device_status;
    }


    public String getSiren_status() {
        return siren_status;
    }

    public void setSiren_status(String siren_status) {
        this.siren_status = siren_status;
    }

    public String getHub_status() {
        return hub_status;
    }

    public void setHub_status(String hub_status) {
        this.hub_status = hub_status;
    }

    public DataManager(){

    }
}
