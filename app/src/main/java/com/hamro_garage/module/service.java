package com.hamro_garage.module;

public class service {
    private String id, service;

    public service() {

    }

    public service(String id, String service) {
        this.id = id;
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
