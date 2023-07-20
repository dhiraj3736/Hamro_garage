package com.hamro_garage.model;

public class listofbusiness {
    private String id,garage_name,available_time,mobile,service,location;

    public listofbusiness() {
    }

    public listofbusiness(String id, String garage_name, String available_time, String mobile, String service, String location) {
        this.id = id;
        this.garage_name = garage_name;
        this.available_time = available_time;
        this.mobile = mobile;
        this.service = service;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGarage_name() {
        return garage_name;
    }

    public void setGarage_name(String garage_name) {
        this.garage_name = garage_name;
    }

    public String getAvailable_time() {
        return available_time;
    }

    public void setAvailable_time(String available_time) {
        this.available_time = available_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
