package com.example.usbtin.ui;

public class SettingsAttributes {
    private String bus = "";
    private String com = "";
    private String rate = "";
    private String mode = "";


    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getCom() {
        return com;
    }
    public void setCom(String com) {
        this.com = com;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }
    public String getBus() {
        return bus;
    }
}